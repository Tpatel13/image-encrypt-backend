package com.asu.image.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("userDetails")
@AllArgsConstructor
@NoArgsConstructor
public class Login {
    @Indexed(unique = true)
    String userName;
    String password;
}
