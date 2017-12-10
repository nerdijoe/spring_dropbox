package com.dropbox.repository;

import com.dropbox.model.Files;
import com.dropbox.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface FileRepository extends MongoRepository<Files, String> {
    public List<Files> findByUser(Users user);

    public List<Files> findByUsers(Users user);

    @Query("{ '_id': ?0 }")
    public Files findById(String id);

    @Query("{ 'path': ?0 }")
    public List<Files> findByPath(String path);

    @Query("{ 'user': ?0, 'path': ?1 }")
    public List<Files> findByUserAndPath(Users user, String path);
}
