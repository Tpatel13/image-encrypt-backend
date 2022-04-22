package com.asu.image.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUp extends Login {

    String recoveryEmail;
    String phoneNumber;

}
