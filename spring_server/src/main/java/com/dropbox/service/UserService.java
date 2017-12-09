package com.dropbox.service;

import com.dropbox.model.Users;
import com.dropbox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Iterable<Users> getAllUsers() {
        System.out.println("userRepository.findAll");
        System.out.println(userRepository.findAll());
        return userRepository.findAll();
    }

    public Users findByFirstname(String name) {
        return userRepository.findByFirstname(name);
    }

    public Users findById(String id) {
        return userRepository.findById(id);
    }

    public Users findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public Iterable<Users> findByEmails(String users) {
        String[] emails = users.split("[,]\\s+");
        System.out.println(emails);
        Iterable<Users> result =  userRepository.findByEmails(emails);
        System.out.println("looping the iterable users...");
        for( Users u: result) {
            System.out.println(u);
        }
        return result;
    }


    public void addUser(Users user){
        userRepository.save(user);
    }

    public Users login(String email,String password){
        System.out.format("   UserRepository.login %s %s", email, password);

        Users result = userRepository.findByEmailAndPassword(email, password);
        System.out.println(result);
//        System.out.println(result.size());
//        System.out.println(result.get(0).getEmail());
        System.out.println(result.getEmail());
//        return userRepository.findByEmailAndPassword(email,password);
        return result;
    }

}
