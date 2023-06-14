package com.hardcore.accounting.exception;

import org.springframework.http.HttpStatus;

// 通用的处理异常的包: code是BadRequest,再改errorCode,其他属性继承ServiceException
public class InvalidParameterException extends ServiceException{
    public InvalidParameterException(String message){
        super(message);
        this.setStatusCode(HttpStatus.BAD_REQUEST.value());
        this.setErrorCode("INVALID_PARAMETER");
        this.setErrorType(ErrorType.Client);
    }
}
