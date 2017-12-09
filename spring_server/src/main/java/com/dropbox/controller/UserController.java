package com.dropbox.controller;


import com.dropbox.model.Activities;
import com.dropbox.model.Users;
import com.dropbox.repository.ActivityRepository;
import com.dropbox.repository.UserRepository;
import com.dropbox.service.ActivityService;
import com.dropbox.service.UserService;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.servlet.http.HttpSession;

@Controller
@CrossOrigin(origins = "http://localhost:3001")
@RestController
@RequestMapping(path="/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @Value("${jwt.secret}")
    public String SECRET;


    @GetMapping(path="/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Iterable<Users> findAll() {
//        repository.save(new Users("James", "Harden", "harden@rockets.com", "haha"));
//        repository.save(new Users("LeBron", "James", "lebron@cavs.com", "haha"));

        // This returns a JSON with the users
        return userService.getAllUsers();
    }

    @GetMapping(path="/id/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Users findById(@PathVariable("id") String id) {
        // This returns a JSON with the users
        return userService.findById(id);
    }

    @GetMapping(path="/{name}",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Users findByFirstName(@PathVariable("name") String name) {
        // This returns a JSON with the users
        return userService.findByFirstname(name);
    }

//    @PostMapping(path="/signin",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public @ResponseBody Users login(@RequestBody String body, HttpSession session)
//    {
//        System.out.println("RequestBody user ------------");
//        System.out.println(body);
//        System.out.println("-----------------------------");
//        JSONObject jsonObject = new JSONObject(body);
//        session.setAttribute("name",jsonObject.getString("email"));
//        System.out.println("Login hahah");
//        ResponseEntity result = new ResponseEntity(userService.login(jsonObject.getString("email"),jsonObject.getString("password")),HttpStatus.OK);
////        return new ResponseEntity(userService.login(jsonObject.getString("username"),jsonObject.getString("password")),HttpStatus.OK);
//        System.out.println(result);
//
//        Users loggedin = userService.login(jsonObject.getString("email"),jsonObject.getString("password"));
//        System.out.println((loggedin));
//        return loggedin;
//    }
//
//    @PostMapping(path="/signup",consumes = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
//    public  ResponseEntity<?> addNewUser (@RequestBody Users user) {
//        // @ResponseBody means the returned String is the response, not a view name
//        // @RequestParam means it is a parameter from the GET or POST request
//        userService.addUser(user);
//        System.out.println("Saved");
//        return new ResponseEntity(null,HttpStatus.CREATED);
//    }


    @PostMapping(path="/activities",consumes = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public ResponseEntity<?> addNewActivity (@RequestHeader(value="token") String token, @RequestBody String body) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        String decodedString = "";
        try {

            decodedString = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
            System.out.println("activities jwt decode  ------------");
            System.out.println(decodedString);
            //OK, we can trust this JWT

            JSONObject decoded = new JSONObject(decodedString);
            System.out.println("decoded VVV");
            System.out.println(decoded);
            ObjectId userId = new ObjectId(decoded.getString("_id"));

            System.out.println(userId);


            JSONObject jsonObject = new JSONObject(body);
            activityService.addActivity(jsonObject.getString("action"), jsonObject.getString("description"), decoded.getString("_id"));
            System.out.println("Saved");
            return new ResponseEntity(null, HttpStatus.CREATED);


        } catch (SignatureException e) {

            //don't trust the JWT!
            System.out.println("jwt decode error xxxxxxxx");
            System.out.println(e);
            System.out.println("Error----");
            return new ResponseEntity(null, HttpStatus.FORBIDDEN);

        }

    }

    //    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @GetMapping(path="/activities/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Iterable<Activities> getAllActivities() {
        // This returns a JSON with the activities

//        String decodedString = "";
//        try {
//
//            decodedString = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
//            System.out.println("activities jwt decode  ------------");
//            System.out.println(decodedString);
//            //OK, we can trust this JWT
//
//            JSONObject decoded = new JSONObject(decodedString);
//            System.out.println("decoded VVV");
//            System.out.println(decoded);
////            System.out.println(decoded.getString("_id"));
////
////            System.out.println(decoded);
//            ObjectId userId = new ObjectId(decoded.getString("_id"));
//
//            System.out.println(userId);
//
//
//        } catch (SignatureException e) {
//
//            //don't trust the JWT!
//            System.out.println("jwt decode error xxxxxxxx");
//            System.out.println(e);
//
//        }

        System.out.println("activityService.getAllActivities()");
        System.out.println(activityService.getAllActivities());

        Iterable<Activities> activities = activityService.getAllActivities();
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = objectMapper.writeValueAsString(activities);

        return activityService.getAllActivities();
    }

//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @GetMapping(path="/activities",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Iterable<Activities> getUserActivities(@RequestHeader(value="token") String token) {
//         This returns a JSON with the activities

        String decodedString = "";
        try {

            decodedString = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
            System.out.println("activities jwt decode  ------------");
            System.out.println(decodedString);
            //OK, we can trust this JWT

            JSONObject decoded = new JSONObject(decodedString);
            System.out.println("decoded VVV");
            System.out.println(decoded);
//            System.out.println(decoded.getString("_id"));
//
//            System.out.println(decoded);
            ObjectId userId = new ObjectId(decoded.getString("_id"));

            System.out.println(userId);
            List<Activities> results = activityService.findByUser(decoded.getString("_id"));
            System.out.println(results);

            return activityService.findByUser(decoded.getString("_id"));


        } catch (SignatureException e) {

            //don't trust the JWT!
            System.out.println("jwt decode error xxxxxxxx");
            System.out.println(e);

        }

        return activityService.findByUser("ddddd");
    }


}
