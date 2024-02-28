package com.cilicili.common.utils;


import com.cilicili.common.resp.StatusCode;
import com.cilicili.common.exception.ThrowUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Objects;

/**
 * @ClassName ParamsCheck
 * @Description 参数校验工具
 * @Author Zhou JunJie
 * @Date 2023/11/9 13:29
 **/
public class ParamsCheck {
    private ParamsCheck(){}

    /**
     * 包含数字、大小写字母、特殊字符[#?!@$%^&*-]、8~32位
     */
    private static final String PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,32}$";

    /**
     * 手机格式
     */
    private static final String PHONE_NUMBER_PATTERN = "^1\\d{10}$";


    public static void isNoNull(Object... os){
        for (Object o : os) {
            if(o instanceof String){
                ThrowUtils.throwIf(StringUtils.isEmpty((String)o), StatusCode.PARAMS_ERROR);
            }
            if(o instanceof Collection){
                ThrowUtils.throwIf(((Collection<?>) o).isEmpty(), StatusCode.PARAMS_ERROR);
            }
            ThrowUtils.throwIf(Objects.isNull(o), StatusCode.PARAMS_ERROR);
        }
    }

    public static void checkPhoneNumber(String phone) {
        isNoNull(phone);
        boolean matches = phone.matches(PHONE_NUMBER_PATTERN);
        ThrowUtils.throwIf(!matches,StatusCode.PARAMS_ERROR,"手机号格式不正确");
    }

    public static Boolean checkPassword(String password){
        isNoNull(password);
        return password.matches(PASSWORD_PATTERN);
    }

}
