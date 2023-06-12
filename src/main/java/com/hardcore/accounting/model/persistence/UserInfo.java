package com.hardcore.accounting.model.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data  // lombok上的注解.有了它可以不用写getter和setter了
@Builder  // lombok上的注解.有了它可以不用写builder建造模式了
@NoArgsConstructor  // 有了它可以有无参数模式
@AllArgsConstructor
public class UserInfo {
    private Long id;
    private String Username;
    private String password;
    private LocalDate createTime;
    private LocalDate updateTime;
}
