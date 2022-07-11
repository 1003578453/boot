package com.li.web.boot.exception;

import com.li.web.boot.entity.ResultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 异常处理类
 * @author: LiZl
 * @date: 2022/6/22 21:41
 * @version: v1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //Security用户访问异常
    @ExceptionHandler(value = AccessDeniedException.class)
    public String accessDeniedExceptionHandler(AccessDeniedException e){
        logger.error("Security用户访问异常>>>:{}",e);
        return "error/500";
    }

   /* @ExceptionHandler(value = AuthenticationException.class)
    public String authenticationExceptionHandler(AuthenticationException e){
        logger.error("Security用户登录/认证异常>>>:{}",e);
        return "error/500";
    }*/

    //

    @ExceptionHandler(value = Exception.class)
    public String exceptionHandler(Exception e){
        logger.error("Exception>>>:{}",e);
        return "error/500";
    }

}
