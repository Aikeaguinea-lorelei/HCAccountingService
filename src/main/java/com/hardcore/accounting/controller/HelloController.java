package com.hardcore.accounting.controller;

import com.hardcore.accounting.model.service.Greeting;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
// @RestController作用: 让这个Controller知道自己是Controller.(相当于@Controller注解和@Responcebody的集合)
public class HelloController {
    // (1)@RequestMapping()作用: 将这个函数暴露出来,作为一个接口供别人;进行GET调用
    @RequestMapping(path="v1.0/greeting/{name}",method= RequestMethod.GET)
    // (2)或者直接: @GetMapping(path="v1/greeting")
     public String sayHello(@PathVariable("name") String name) {  // @PathVariable是内容变量
        // (1)这样localhost:8080/v1/greeting/laowang  就会在页面中显示"hello,laowang"
        // (2)或者sayHello(@RequestParam("name") String name)    这样localhost:8080/v1/greeting?name=hardcore
        return String.format("hello,%s",name);
        // (2)就会在页面中显示"hello,hardcore"
     }
}