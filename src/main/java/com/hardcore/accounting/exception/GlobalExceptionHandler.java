package com.hardcore.accounting.exception;

import lombok.val;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// 这个包用来处理全局的try-catch的调用,将两个注解@ControllerAdvice和@ExceptionHandler配套
@ControllerAdvice  // RESTful的控制器增强
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    ResponseEntity<?> handleServiceException(ServiceException ex){
        // 编辑ResponseEntity的body 全部get InvalidParameterException中的属性
        // ResponseEntity代表整个HTTP响应,包括状态码status，标头head和正文body
        // 编辑ResponseEntity的body,
        // 和ex的父类ServiceException包设定一致的属性就直接get,不一致的就编辑
        val errorResponse=ErrorResponse.builder()
                .statusCode(ex.getStatusCode())
                .message(ex.getMessage())
                .errorCode(ex.getErrorCode())
                .errorType(ex.getErrorType())
                .build();
        // code如果不为空就get,为空就返回500(服务器错误)
        return ResponseEntity.status(ex.getStatusCode() != 0 ? ex.getStatusCode() : HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body(errorResponse);
    }
    // 此程序是当密码输入错误时,返回的response信息
    @ExceptionHandler(IncorrectCredentialsException.class)
    ResponseEntity<?> handleIncorrectCredentialsException(IncorrectCredentialsException ex){
        val errorResponse=ErrorResponse.builder()
                .statusCode(400)
                .message(ex.getMessage())
                .errorCode("INCORRECT_CREDENTIALS")
                .errorType(ServiceException.ErrorType.Client)
                .build();
        return ResponseEntity.status(400).body(errorResponse);
    }
}
