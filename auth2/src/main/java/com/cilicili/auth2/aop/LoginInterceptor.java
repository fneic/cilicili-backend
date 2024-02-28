package com.cilicili.auth2.aop;

import com.cilicili.auth2.annotation.NoLogin;
import com.cilicili.common.common.UserContextHold;
import com.cilicili.common.exception.BusinessException;
import com.cilicili.common.exception.ThrowUtils;
import com.cilicili.common.model.SafeUser;
import com.cilicili.common.resp.StatusCode;
import com.cilicili.common.utils.JwtUtils;
import com.cilicili.common.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

import static com.cilicili.common.constant.UserStatus.FORBID;

/**
 * @ClassName LoginInterceptor
 * @Description 登录拦截器
 * @Author Zhou JunJie
 * @Date 2023/11/17 23:35
 **/
@Aspect
@Component
@Slf4j
@Order(0)
public class LoginInterceptor {

    @Pointcut("execution(* com.cilicili.auth2.controller.*.*(..))")
    public void loginPointcut() {}

    /**
     * 执行拦截
     *
     * @param point
     * @return
     */
    @Around("loginPointcut()")
    public Object doInterceptor(ProceedingJoinPoint  point) throws Throwable {
        //controller 无需登录则放行
        Class<?> controller = point.getTarget().getClass();
        if (controller.getAnnotation(NoLogin.class) != null) {
            return point.proceed();
        }
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if(method != null) {
            NoLogin annotation = method.getAnnotation(NoLogin.class);
            //如果无需登录就放行
            if (annotation != null) {
                return point.proceed();
            } else {
                RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
                HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
                String accessToken = request.getHeader("Authorization");
                try {
                    //校验用户accessToken是否有效
                    SafeUser user = JwtUtils.getUserByAccessToken(accessToken);
                    if (user != null) {
                        //账号是否被封禁
                        if(Objects.equals(user.getStatus(), FORBID)){
                            ThrowUtils.throwException(StatusCode.ACCOUNT_FORBID);
                        }
                        //将User保存，供后续使用
                        UserContextHold.keepLoginUser(user);
                        return point.proceed();
                    }
                } catch (BusinessException e) {
                    ThrowUtils.throwException(StatusCode.statusCodeOfCode(e.getCode()));
                }
            }
        }
        return ResultUtils.error(StatusCode.SYSTEM_ERROR);
    }

    /**
     * 切点方法执行后运行，不管切点方法执行成功还是出现异常
     * @param point
     */
    @After(value = "loginPointcut()")
    public void doAfter(JoinPoint point) {
        //请求结束，移除user，节省内存
        UserContextHold.removeUser();
    }

}
