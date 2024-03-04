package com.cilicili.auth2.service.login;

import com.cilicili.auth2.model.dto.UserLoginDto;
import com.cilicili.auth2.model.entity.User;

/**
 * 登录策略
 * @author Zhou JunJie
 */
public interface LoginStrategy {

    /**
     * 登录实现
     * @param userLoginDto
     * @return
     */
    User login(UserLoginDto userLoginDto);
}
