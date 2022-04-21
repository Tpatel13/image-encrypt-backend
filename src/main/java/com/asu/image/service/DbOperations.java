package com.asu.image.service;


import com.asu.image.model.Login;
import com.asu.image.model.SignUp;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class DbOperations {
    static HashMap<String, String> dummyMap = new HashMap<>();

    static {
        dummyMap.put("admin", "admin");
        dummyMap.put("user", "user");
    }

    public Boolean loginUser(Login login) {
        if (dummyMap.containsKey(login.getUserName())) {
            if (dummyMap.get(login.getUserName()).equals(login.getPassword())) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }


    public Boolean signUp(SignUp signUp) {
        dummyMap.put(signUp.getUserName(), signUp.getPassword());
        return true;
    }




}
