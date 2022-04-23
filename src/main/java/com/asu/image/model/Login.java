package com.asu.image.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("userDetails")
@AllArgsConstructor
@NoArgsConstructor
public class Login {
    @Id
    String userName;
    String password;

    String encryptionKey;
}
