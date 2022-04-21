package com.asu.image;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories

public class ImageApplication {

    public static void main(String[] args) {

        SpringApplication.run(ImageApplication.class);
    }

}
