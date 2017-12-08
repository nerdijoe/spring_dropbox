package com.dropbox.repository;

import com.dropbox.model.Activities;
import com.dropbox.model.Users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ActivityRepository extends MongoRepository<Activities, String>{
    public List<Activities> findByUser(String user);
}
