package com.dropbox.repository;

import com.dropbox.model.Activities;
import com.dropbox.model.Folders;
import com.dropbox.model.Users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface FolderRepository extends MongoRepository<Folders, String> {
    public List<Folders> findByUser(Users user);

    public List<Folders> findByUsers(Users user);

    @Query("{ '_id': ?0 }")
    public Folders findById(String id);

    @Query("{ 'path': ?0 }")
    public List<Folders> findByPath(String path);

    @Query("{ 'user': ?0, 'path': ?1 }")
    public List<Folders> findByUserAndPath(Users user, String path);

}
