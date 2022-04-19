package com.asu.image.model;

import lombok.Data;

@Data
public class SignUp extends Login {

    String recoveryEmail;
    String phoneNumber;

}
