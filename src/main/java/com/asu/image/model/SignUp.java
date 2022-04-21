package com.asu.image.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUp extends Login {

    String recoveryEmail;
    String phoneNumber;

}
