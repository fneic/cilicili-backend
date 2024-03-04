package com.cilicili.auth2.service.login;

import cn.hutool.crypto.digest.DigestUtil;
import com.cilicili.auth2.dao.UserDao;
import com.cilicili.auth2.model.dto.UserLoginDto;
import com.cilicili.auth2.model.entity.User;
import com.cilicili.common.exception.ThrowUtils;
import com.cilicili.common.resp.StatusCode;
import com.cilicili.common.utils.ParamsCheck;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @ClassName AccountLoginStrategy
 * @Description
 * @Author Zhou JunJie
 * @Date 2024/3/4 23:00
 **/
@Component("accountLogin")
public class AccountLoginStrategy implements LoginStrategy{

    @Resource
    private UserDao userDao;

    @Override
    public User login(UserLoginDto userLoginDto) {
        String account = userLoginDto.getAccount();
        String password = userLoginDto.getPassword();
        ParamsCheck.isNoNull(account, password);
        // TODO: 2023/11/28 图形验证码待完善
//        CodeManage.checkImgCode(account, imgCode);
        User user = userDao.getUserByAccount(account);
        //账号不存在
        ThrowUtils.throwIf(Objects.isNull(user), StatusCode.LOGIN_FAILED, "账号或密码错误");
        String realPassword = user.getPassword();
        String encryptPassword = encryptPassword(password,account);
        //密码错误
        ThrowUtils.throwIf(!Objects.equals(realPassword, encryptPassword), StatusCode.LOGIN_FAILED, "账号或密码错误");
        return user;
    }

    /**
     * 密码加密
     *
     * @param password
     * @return
     */
    private String encryptPassword(String password,String salt) {
        return DigestUtil.sha256Hex(password + salt);
    }
}
