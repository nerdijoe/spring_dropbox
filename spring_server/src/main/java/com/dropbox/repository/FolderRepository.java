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

    @Query("{ '_id': ?0 }")
    public Folders findById(String id);
}
