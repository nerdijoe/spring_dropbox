package com.dropbox.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.dropbox.service.Users;

public interface UserRepository extends MongoRepository<Users, String> {
    //    public Iterable<User> findAll();
    public Users findByFirstName(String firstName);
}
