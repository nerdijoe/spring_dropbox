package com.dropbox.repository;

import com.dropbox.model.Abouts;
import com.dropbox.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AboutRepository extends MongoRepository<Abouts, String> {
    public Abouts findByUser(Users user);

    @Query("{ '_id': ?0 }")
    public Abouts findById(String id);
}
