package com.asu.image.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class LoginController {

    @GetMapping("health")
    public String healthCheck(){
        return "OK";
    }


    @PostMapping
    public String login(){
        return null;
    }
}
