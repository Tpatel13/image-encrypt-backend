package com.asu.image.controller;


import com.asu.image.model.Login;
import com.asu.image.model.SignUp;
import com.asu.image.service.DbOperations;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
public class LoginController {


    @Autowired
    DbOperations dbOperations;

    @GetMapping("health")
    public String healthCheck() {
        return "OK";
    }


    @RequestMapping("/login")
    public String login(Login login) {
        return String.valueOf(dbOperations.loginUser(login));
    }


    @PostMapping("/signup")
    public ResponseEntity<HttpStatus> signupUser(@RequestParam SignUp signUp) {
        log.info("Requested signup for {}",signUp.toString());
        Boolean successful = dbOperations.signUp(signUp);
        if (Boolean.TRUE.equals(successful)) {
            log.info("User created successfully ");
            return (ResponseEntity) ResponseEntity.status(HttpStatus.CREATED);
        }
        return (ResponseEntity) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
