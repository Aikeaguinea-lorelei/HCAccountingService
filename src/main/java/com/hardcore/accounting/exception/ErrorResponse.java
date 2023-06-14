package com.hardcore.accounting.exception;

import lombok.Builder;
import lombok.Data;

// 用来被各种错误请求调用..其中errorType引用了ServiceException的
@Data
@Builder
public class ErrorResponse {
    private String errorCode;
    private ServiceException.ErrorType errorType;
    private String message;
    private int statusCode;
}
