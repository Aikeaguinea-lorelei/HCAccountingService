package com.hardcore.accounting.model.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfo {
    private Long id;
    private String username;
    private String password;  // service层可以传password数据,但是不要在C2S的逻辑中将password的代码进行传递
    // password数据需要进行加盐传输
}
