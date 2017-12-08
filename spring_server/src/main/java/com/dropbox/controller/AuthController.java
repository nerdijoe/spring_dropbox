package com.dropbox.controller;

import com.dropbox.model.Users;
import com.dropbox.repository.UserRepository;
import com.dropbox.service.UserService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin(origins = "http://localhost:3001")
@RestController
@RequestMapping(path="/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping(path="/signin",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Users login(@RequestBody String body, HttpSession session)
    {
        System.out.println("RequestBody user ------------");
        System.out.println(body);
        System.out.println("-----------------------------");
        JSONObject jsonObject = new JSONObject(body);
        session.setAttribute("name",jsonObject.getString("email"));
        System.out.println("Login hahah");
        ResponseEntity result = new ResponseEntity(userService.login(jsonObject.getString("email"),jsonObject.getString("password")),HttpStatus.OK);
//        return new ResponseEntity(userService.login(jsonObject.getString("username"),jsonObject.getString("password")),HttpStatus.OK);
        System.out.println(result);

        Users loggedin = userService.login(jsonObject.getString("email"),jsonObject.getString("password"));
        System.out.println((loggedin));
        return loggedin;
    }

    @PostMapping(path="/signup",consumes = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public  ResponseEntity<?> addNewUser (@RequestBody Users user) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        userService.addUser(user);
        System.out.println("Saved");
        return new ResponseEntity(null,HttpStatus.CREATED);
    }


}
