package com.dropbox.controller;


import com.dropbox.service.Users;
import com.dropbox.repository.UserRepository;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.w3c.dom.html.HTMLParagraphElement;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin(origins = "http://localhost:3001")
@RestController
@RequestMapping(path="/users")
public class UserController {
    @Autowired
    private UserRepository repository;

    @GetMapping(path="/",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Iterable<Users> findAll() {
//        repository.save(new Users("James", "Harden", "harden@rockets.com", "haha"));
//        repository.save(new Users("LeBron", "James", "lebron@cavs.com", "haha"));

        // This returns a JSON with the users
        return repository.findAll();
    }

    @GetMapping(path="/{name}",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Users findByFirstName(@PathVariable("name") String name) {
        // This returns a JSON with the users
        return repository.findByFirstName(name);
    }


}
