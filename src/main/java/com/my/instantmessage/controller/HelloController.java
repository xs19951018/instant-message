package com.my.instantmessage.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/say")
    public String sayHi(){
        String msg = "111";
        return msg;
    }
}
