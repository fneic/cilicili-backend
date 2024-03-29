package com.cilicili.auth2.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.cilicili.auth2.dao.UserDao;
import com.cilicili.auth2.model.dto.UserLoginDto;
import com.cilicili.auth2.model.dto.UserUpdateDto;
import com.cilicili.auth2.model.entity.AccountInfo;
import com.cilicili.auth2.model.entity.Exp;
import com.cilicili.auth2.model.entity.User;
import com.cilicili.auth2.model.vo.*;
import com.cilicili.auth2.service.AccountInfoService;
import com.cilicili.auth2.service.ExpService;
import com.cilicili.auth2.service.RelationService;
import com.cilicili.auth2.service.login.LoginStrategy;
import com.cilicili.auth2.service.login.LoginStrategyBuilder;
import com.cilicili.auth2.service.login.LoginType;
import com.cilicili.common.common.UserContextHold;
import com.cilicili.common.exception.BusinessException;
import com.cilicili.common.model.SafeUser;
import com.cilicili.auth2.service.UserService;
import com.cilicili.auth2.service.adapter.UserAdapter;
import com.cilicili.common.utils.JwtUtils;
import com.cilicili.common.exception.ThrowUtils;
import com.cilicili.common.resp.StatusCode;
import com.cilicili.common.utils.ParamsCheck;
import com.cilicili.redisutil.common.CodeManage;
import com.cilicili.redisutil.common.CommonRedisTemplate;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.cilicili.auth2.constant.UserConstant.*;

/**
 * @author Zhou JunJie
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2023-11-28 00:48:23
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private CommonRedisTemplate commonRedisTemplate;

    @Resource
    private AccountInfoService accountInfoService;

    @Resource
    private ExpService expService;

    @Resource
    @Lazy
    private RelationService relationService;

    @Resource
    private LoginStrategyBuilder loginStrategyBuilder;


    @Override
    @Transactional
    public Tokens login(UserLoginDto userLoginDto) {
        Integer type = userLoginDto.getType();
        ParamsCheck.isNoNull(type);
        User user = null;
        //策略模式
        LoginStrategy loginStrategy = loginStrategyBuilder.getLoginStrategy(LoginType.codeToType(type));
        user = loginStrategy.login(userLoginDto);
        //账户状态判断
        checkUser(user);
        SafeUser safeUser = UserAdapter.convertToSafeUser(user);
        //将用户信息保存到redis
        saveRedisUserInfo(safeUser);
        //双token机制
        return getTokens(safeUser);
    }

    @Override
    public UserBaseInfo currentUser() {
        Long uid = UserContextHold.getCurrentUser().getId();
        SafeUser safeUser = getUserById(uid);
        return UserAdapter.safeUser2BaseUser(safeUser);
    }

    @Override
    public SafeUser getUserById(Long uid){
        String key = USER_LOGIN_STATE_PREFIX + uid;
        SafeUser safeUser = commonRedisTemplate.getHashObj(key, SafeUser.class);
        //redis中user过期
        if(safeUser == null){
            User latestUser = userDao.getById(uid);
            ThrowUtils.throwIf(latestUser == null,StatusCode.NOT_LOGIN_ERROR);
            checkUser(latestUser);
            safeUser = UserAdapter.convertToSafeUser(latestUser);
            saveRedisUserInfo(safeUser);
        }
        return safeUser;
    }

    @Override
    public Tokens refresh(Long uid) {
        SafeUser safeUser = getUserById(uid);
        return getTokens(safeUser);
    }

    @Override
    public boolean isExist(Long uid) {
        try {
            getUserById(uid);
        }catch (BusinessException e){
            return false;
        }
        return true;
    }

    @Override
    public UpInfo upInfo(Long uid) {
        SafeUser safeUser = getUserById(uid);
        //用户不存在，则直接返回
        if(safeUser == null){
            return null;
        }
        UserBaseInfo userBaseInfo = UserAdapter.safeUser2BaseUser(safeUser);
        Long accountInfoId = safeUser.getAccountInfoId();
        AccountInfo accountInfo = accountInfoService.getById(accountInfoId);
        UpInfo upInfo = new UpInfo();
        upInfo.setUserBaseInfo(userBaseInfo);
        BeanUtil.copyProperties(accountInfo, upInfo);
        return upInfo;
    }


    @Override
    public NavUserInfo userInfo() {
        SafeUser currentUser = UserContextHold.getCurrentUser();
        Long uid = currentUser.getId();
        Long expId = currentUser.getExpId();
        Long accountInfoId = currentUser.getAccountInfoId();
        //获取用户基本信息
        SafeUser safeUser = getUserById(uid);
        UserBaseInfo userBaseInfo = UserAdapter.safeUser2BaseUser(safeUser);
        //获取用户等级信息
        Exp exp = expService.getById(expId);
        ExpInfo expInfo = BeanUtil.copyProperties(exp, ExpInfo.class);
        //获取用户账号信息
        AccountInfo accountInfo = accountInfoService.getById(accountInfoId);
        NavUserInfo navUserInfo = BeanUtil.copyProperties(accountInfo, NavUserInfo.class);
        navUserInfo.setExpInfo(expInfo);
        navUserInfo.setUserBaseInfo(userBaseInfo);
        return navUserInfo;
    }

    @Override
    public UpCard getUpCard(Long uid) {
        UpInfo upInfo = upInfo(uid);
        Boolean isFollow = relationService.isFollow(uid);
        UpCard upCard = BeanUtil.copyProperties(upInfo, UpCard.class);
        upCard.setIsFollow(isFollow);
        return upCard;
    }

    @Override
    public void updateUserInfo(UserUpdateDto userUpdateDto) {
        User user = BeanUtil.copyProperties(userUpdateDto, User.class);
        updateUser(user);
    }

    private void updateUser(User user){
        // TODO: 2023/12/22 修改名字需要消耗硬币
        Long uid = UserContextHold.getUid();
        user.setId(uid);
        boolean b = userDao.updateById(user);
        ThrowUtils.throwIf(!b,StatusCode.OPERATION_FAILED);
        String key = USER_LOGIN_STATE_PREFIX + uid;
        //todo 可以异步执行
        commonRedisTemplate.updateHashAll(key,user,USER_STATE_REDIS_EXPIRE, TimeUnit.DAYS);
    }

    @Override
    public UserAccount getAccount() {
        Long uid = UserContextHold.getUid();
        SafeUser safeUser = getUserById(uid);
        return UserAdapter.safeUser2UserAccount(safeUser);
    }

    @Override
    public void updateAccount(UserAccount userAccount) {
        User user = BeanUtil.copyProperties(userAccount, User.class);
        updateUser(user);
    }

    private Tokens getTokens(SafeUser safeUser){
        String accessToken = JwtUtils.generateAccessToken(safeUser);
        String refreshToken = JwtUtils.generateRefreshToken(safeUser);
        return new Tokens(accessToken, refreshToken);
    }

    private void saveRedisUserInfo(SafeUser userInfo){
        String key = USER_LOGIN_STATE_PREFIX + userInfo.getId();
        commonRedisTemplate.updateHashAll(key,userInfo,USER_STATE_REDIS_EXPIRE, TimeUnit.DAYS);
    }


    /**
     * 校验用户合法性，比如是否被禁用之类的
     * @param user
     */
    private void checkUser(User user){
        if(user == null){
            ThrowUtils.throwException(StatusCode.OPERATION_FAILED);
            return ;
        }
        //账户状态判断
        Integer status = user.getStatus();
        ThrowUtils.throwIf(Objects.equals(status, FORBID), StatusCode.ACCOUNT_FORBID);
    }

    public static void main(String[] args) {
        char n = 1;
    }

}




