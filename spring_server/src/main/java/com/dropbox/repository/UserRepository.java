package com.dropbox.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.dropbox.model.Users;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<Users, String> {
    //    public Iterable<User> findAll();
    public Users findByFirstname(String firstname);

    public Users findByEmailAndPassword(String email, String password);

    @Query("{ '_id': ?0 }")
    public Users findById(String id);
}
