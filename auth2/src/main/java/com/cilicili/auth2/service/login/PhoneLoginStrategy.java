package com.cilicili.auth2.service.login;

import cn.hutool.core.util.RandomUtil;
import com.cilicili.auth2.dao.UserDao;
import com.cilicili.auth2.model.dto.UserLoginDto;
import com.cilicili.auth2.model.entity.AccountInfo;
import com.cilicili.auth2.model.entity.Exp;
import com.cilicili.auth2.model.entity.User;
import com.cilicili.auth2.service.AccountInfoService;
import com.cilicili.auth2.service.ExpService;
import com.cilicili.common.exception.ThrowUtils;
import com.cilicili.common.resp.StatusCode;
import com.cilicili.common.utils.ParamsCheck;
import com.cilicili.redisutil.common.CodeManage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName PhoneLoginStrategy
 * @Description
 * @Author Zhou JunJie
 * @Date 2024/3/4 23:05
 **/
@Component("phoneLogin")
public class PhoneLoginStrategy implements LoginStrategy {
    @Resource
    private UserDao userDao;

    @Resource
    private CodeManage codeManage;
    @Resource
    private AccountInfoService accountInfoService;

    @Resource
    private ExpService expService;

    @Override
    public User login(UserLoginDto userLoginDto) {
        String phone = userLoginDto.getPhone();
        Integer msgCode = userLoginDto.getMsgCode();
        ParamsCheck.isNoNull(phone, msgCode);
        //手机格式校验
        ParamsCheck.checkPhoneNumber(phone);
        //验证码校验
        codeManage.checkMsgCode(phone, msgCode);
        User user = userDao.getUserByPhone(phone);
        //用户不存在，走注册
        if (user == null) {
            user = register(phone);
        }
        return user;
    }

    private User register(String phone) {
        User user = new User();
        user.setPhone(phone);
        // TODO: 2023/11/28 用户随机昵称
        String account = generateAccount();
        user.setAccount(account);
        AccountInfo accountInfo = AccountInfo.getDefault();
        boolean save = accountInfoService.save(accountInfo);
        //注册失败
        ThrowUtils.throwIf(!save, StatusCode.USER_REGISTER_FAILED);
        user.setAccountInfoId(accountInfo.getId());
        Exp exp = new Exp();
        exp.setNextExp(100);
        boolean save1 = expService.save(exp);
        //注册失败
        ThrowUtils.throwIf(!save1, StatusCode.USER_REGISTER_FAILED);
        user.setExpId(exp.getId());
        //保存数据库，完成注册
        boolean isRegister = userDao.save(user);
        //注册失败
        ThrowUtils.throwIf(!isRegister, StatusCode.USER_REGISTER_FAILED);
        return user;
    }

    private String generateAccount() {
        String account = null;
        do {
            account = "cili_" + RandomUtil.randomNumbers(12);
        } while (userDao.getUserByAccount(account) != null);
        return account;
    }
}
