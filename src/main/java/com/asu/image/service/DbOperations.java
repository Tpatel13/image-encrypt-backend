package com.asu.image.service;


import com.asu.image.model.Login;
import com.asu.image.model.SignUp;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Log4j2
public class DbOperations {

    @Autowired
    MongoDbService mongoDbService;

    public Boolean loginUser(Login login) {
        log.info("Checking if user creds are matchin in DB {} for ", login.getUserName());
        Login loggedInUser = mongoDbService.findItemByName(login.getUserName());

        if (loggedInUser != null && loggedInUser.getPassword().equals(login.getPassword())) {
            log.info("Input creds are correct , allowing user to enter in the system");
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }


    public Boolean signUp(SignUp signUp) {
        log.info("Registering new user {}", signUp.getUserName());
        try {
            mongoDbService.insert(signUp);
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("Error while registering user intoDB {}", e.getMessage());
            return Boolean.FALSE;
        }


    }


}
