package com.dropbox.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.dropbox.model.Users;

    public interface UserRepository extends MongoRepository<Users, String> {
    //    public Iterable<User> findAll();
    public Users findByFirstname(String firstname);

    public Users findByEmailAndPassword(String email, String password);


}
