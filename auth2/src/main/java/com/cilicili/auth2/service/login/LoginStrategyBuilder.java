package com.cilicili.auth2.service.login;

import com.cilicili.common.exception.ThrowUtils;
import com.cilicili.common.resp.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName LoginStrategyBuilder
 * @Description
 * @Author Zhou JunJie
 * @Date 2024/3/4 23:13
 **/
@Component
public class LoginStrategyBuilder {

    @Autowired
    private Map<String,LoginStrategy> loginStrategyMap;

    public LoginStrategy getLoginStrategy(String type){
        LoginStrategy loginStrategy = loginStrategyMap.get(type);
        if(loginStrategy == null){
            ThrowUtils.throwException(StatusCode.LOGIN_FAILED);
        }
        return loginStrategy;
    }
}
