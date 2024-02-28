package com.cilicili.gateway.exception;

import com.cilicili.common.exception.BusinessException;
import com.cilicili.common.resp.BaseResponse;
import com.cilicili.common.resp.StatusCode;
import com.cilicili.common.utils.ResultUtils;
import com.cilicili.gateway.utils.WebFrameworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @ClassName GlobalExceptionHandler
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/11/28 16:19
 **/
@Slf4j
@Order(-1)
@Component
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        // 已经 commit，则直接返回异常
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }

        // 转换成 CommonResult
        BaseResponse<?> result;
        if (ex instanceof BusinessException) {
            result = responseStatusExceptionHandler(exchange, (BusinessException) ex);
        } else {
            result = systemExceptionHandler(exchange, ex);
        }

        // 返回给前端
        return WebFrameworkUtils.writeJSON(exchange, result);

    }

    /**
     * 处理 Spring Cloud Gateway 默认抛出的 ResponseStatusException 异常
     */
    private  BaseResponse<?> responseStatusExceptionHandler(ServerWebExchange exchange,
                                                         BusinessException ex) {
        // TODO zw 这里要精细化翻译，默认返回用户是看不懂的
        ServerHttpRequest request = exchange.getRequest();
        log.info("[BusinessExceptionHandler][uri({}/{}) 发生异常]", request.getURI(), request.getMethod(), ex);
        return ResultUtils.error(ex.getCode(), ex.getMessage());
    }

    /**
     * 处理系统异常，兜底处理所有的一切
     */
    @ExceptionHandler(value = Exception.class)
    public  BaseResponse<?> systemExceptionHandler(ServerWebExchange exchange,
                                                 Throwable ex) {
        ServerHttpRequest request = exchange.getRequest();
        log.error("[systemExceptionHandler][uri({}/{}) 系统发生异常]", request.getURI(), request.getMethod(), ex);
        //  TODO zw 是否要插入异常日志呢？
        return ResultUtils.error(StatusCode.SYSTEM_ERROR, "系统错误");
    }
}
