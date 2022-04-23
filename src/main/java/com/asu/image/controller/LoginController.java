package com.asu.image.controller;


import com.asu.image.model.Login;
import com.asu.image.model.SignUp;
import com.asu.image.service.DbOperations;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Key;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@Log4j2
public class LoginController {


    @Autowired
    DbOperations dbOperations;

    @GetMapping("health")
    public String healthCheck() {
        return "OK";
    }


    @GetMapping("/login")
    public String login(@RequestParam HashMap<String,String> login) {
        Login incomingLogin=new Login();
        incomingLogin.setPassword(login.get("password"));
        incomingLogin.setUserName(login.get("username"));
        return String.valueOf(dbOperations.loginUser(incomingLogin));
    }


    @PostMapping("/signup")
    public ResponseEntity<String> signupUser(@RequestBody @Valid SignUp signUp) {
        log.info("Requested signup for {}", signUp.toString());
        Boolean successful = dbOperations.signUp(signUp);
        if (Boolean.TRUE.equals(successful)) {
            log.info("User created successfully ");
            return new ResponseEntity<>("Created new User", HttpStatus.OK);
        }
        return  new ResponseEntity<>("Internal server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
