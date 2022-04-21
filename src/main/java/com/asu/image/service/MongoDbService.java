package com.asu.image.service;


import com.asu.image.model.Login;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MongoDbService extends MongoRepository<Login, String> {

    @Query("{name:'?0'}")
    Login findItemByName(String name);

    @Query(value = "{userName:'?0'}", fields = "{'userName' : 1, 'password' : 1}")
    List<Login> findAll(String category);

}
