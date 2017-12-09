package com.dropbox.repository;

import com.dropbox.model.Files;
import com.dropbox.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface FileRepository extends MongoRepository<Files, String> {
    public List<Files> findByUser(Users user);

    @Query("{ '_id': ?0 }")
    public Files findById(String id);
}
