package com.young.test1.exception;

import com.young.test1.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;


/**
 * 读书破万卷，下笔如有神 *
 * 代码反行之，算法记于心 *
 * 项目名: test
 * author: 0YOUNG
 * data:2022/7/20
 */
@RestControllerAdvice
public class GlobleExceptionHandle {

    Logger logger = LoggerFactory.getLogger(GlobleExceptionHandle.class);

    //括号内可填写具体的异常，这样这个方法就只会捕捉这个异常
    //自定义异常
    @ExceptionHandler(CustomerException.class)
    public Response numberException(CustomerException e, HttpServletRequest request){
        StringBuffer requestURL = request.getRequestURL();
        //返回信息可以自定义，例如自定义response结构来设置code和message
        logger.error(e.getMessage(),e);
        return Response.error(e.getMessage());
    }
    //其他异常
    @ExceptionHandler(Exception.class)
    public Response numberException(Exception e, HttpServletRequest request){
        StringBuffer requestURL = request.getRequestURL();
        //返回信息可以自定义，例如自定义response结构来设置code和message
        logger.error(e.getMessage(),e);
        return Response.error(e.getMessage());
    }



}
