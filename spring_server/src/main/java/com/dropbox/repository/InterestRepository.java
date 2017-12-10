package com.dropbox.repository;

import com.dropbox.model.Interests;
import com.dropbox.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface InterestRepository extends MongoRepository<Interests, String> {
    public Interests findByUser(Users user);

    @Query("{ '_id': ?0 }")
    public Interests findById(String id);
}
