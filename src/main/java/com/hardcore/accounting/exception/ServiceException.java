package com.hardcore.accounting.exception;

import lombok.Data;

// 这个包用来被各种异常处理调用
@Data
public class ServiceException extends RuntimeException{
    private int statusCode;
    private String errorCode;
    private ServiceException.ErrorType errorType;  // 这个error是service抛出的还是client抛出的还是unknown
    private String message;  // 出错信息  可以不写,直接继承父类的message

    public enum ErrorType{  // enum: 枚举
        Client,
        Service,
        Unknown
    }
    public ServiceException(String message){
        super(message);
    }
}
