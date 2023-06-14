package com.hardcore.accounting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND)
// 这个文件中写notFound的异常处理,编辑errorCode和statusCode,其他的继承父类
public class ResourceNotFoundException extends ServiceException{
    // 在extends上alt + enter引入在serviceException里写的构造器
    public ResourceNotFoundException(String message) {
        super(message);  // 继承父类ServiceException的massage实现
        this.setStatusCode(HttpStatus.NOT_FOUND.value());
        this.setErrorCode("USER_INFO_NOT_FOUND");
        this.setErrorType(ErrorType.Client);
    }
}
