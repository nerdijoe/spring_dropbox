package com.dropbox.controller;

import com.dropbox.model.Users;
import com.dropbox.repository.UserRepository;
import com.dropbox.service.AboutService;
import com.dropbox.service.InterestService;
import com.dropbox.service.UserService;
//import com.dropbox.security.TokenHelper;
import com.dropbox.security.TokenHelper;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collection;

@Controller
@CrossOrigin(origins = "http://localhost:3001")
@RestController
@RequestMapping(path="/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AboutService aboutService;

    @Autowired
    private InterestService interestService;

    @Autowired
    private TokenHelper tokenHelper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public class SigninResponse {
        private String token;
        private String firstname;
        private String lastname;
        private String email;
        public SigninResponse(String token, String firstname, String lastname, String email) {
            this.token = token;
            this.firstname = firstname;
            this.lastname = lastname;
            this.email = email;
        }
    }

    @PostMapping(path="/signin",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String login(@RequestBody String body, HttpSession session)
    {
        System.out.println("RequestBody user ------------");
        System.out.println(body);
        System.out.println("-----------------------------");
        JSONObject jsonObject = new JSONObject(body);
        session.setAttribute("name",jsonObject.getString("email"));
        System.out.println("Login hahah");

        // Get users
        Users foundUser = userService.findByEmail(jsonObject.getString("email"));
        System.out.println("hereeee");
        System.out.println(foundUser);
        System.out.println("end of herreee");
        // -------
        if (foundUser != null) {
            // check password
            if (passwordEncoder.matches(jsonObject.getString("password"), foundUser.getPassword())) {
                System.out.println("horeeee");

                // generate jwttoken
                String jwtToken = tokenHelper.generateToken(foundUser);
                System.out.println("jwtToken:");
                System.out.println(jwtToken);

                if(aboutService.findByUser(foundUser) == null) {
                    aboutService.addAbout("", "", "", "", "", foundUser);
                }
                if(interestService.findByUser(foundUser) == null) {
                    interestService.addInterest("", "", "", "", foundUser);
                }

                ResponseEntity result = new ResponseEntity(userService.login(jsonObject.getString("email"),jsonObject.getString("password")),HttpStatus.OK);
//        return new ResponseEntity(userService.login(jsonObject.getString("username"),jsonObject.getString("password")),HttpStatus.FORBIDDEN);
                System.out.println(result);

                return String.format("{ \"token\": \"%s\", \"firstname\": \"%s\",\"lastname\": \"%s\",\"email\": \"%s\",\"_id\": \"%s\" }", jwtToken, foundUser.firstname, foundUser.lastname, foundUser.email, foundUser._id );

            }
        }



        ResponseEntity result = new ResponseEntity(userService.login(jsonObject.getString("email"),jsonObject.getString("password")),HttpStatus.OK);
//        return new ResponseEntity(userService.login(jsonObject.getString("username"),jsonObject.getString("password")),HttpStatus.FORBIDDEN);
        System.out.println(result);
        return String.format("{ \"message\": \"User and Password don't match.\" }" );

        // -----
//        Users loggedin = userService.login(jsonObject.getString("email"),jsonObject.getString("password"));
//        System.out.println((loggedin));
//
//        String jwtToken = tokenHelper.generateToken(loggedin);
//        System.out.println("jwtToken:");
//        System.out.println(jwtToken);
//
//
//        if(aboutService.findByUser(loggedin) == null) {
//            aboutService.addAbout("", "", "", "", "", loggedin);
//        }
//        if(interestService.findByUser(loggedin) == null) {
//            interestService.addInterest("", "", "", "", loggedin);
//        }

//        return String.format("{ \"token\": \"%s\", \"firstname\": \"%s\",\"lastname\": \"%s\",\"email\": \"%s\",\"_id\": \"%s\" }", jwtToken, loggedin.firstname, loggedin.lastname, loggedin.email, loggedin._id );

        // ----

    }

    @PostMapping(path="/signinpremium",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity loginPremium(@RequestBody String body, HttpSession session)
    {
        System.out.println("RequestBody user ------------");
        System.out.println(body);
        System.out.println("-----------------------------");
        JSONObject jsonObject = new JSONObject(body);
        session.setAttribute("name",jsonObject.getString("email"));
        System.out.println("Login hahah");

        // Get users
        Users foundUser = userService.findByEmail(jsonObject.getString("email"));
        System.out.println("hereeee");
        System.out.println(foundUser);
        System.out.println("end of herreee");
        // -------
        if (foundUser != null) {
            // check password
            if (passwordEncoder.matches(jsonObject.getString("password"), foundUser.getPassword())) {
                System.out.println("horeeee");

                // generate jwttoken
                String jwtToken = tokenHelper.generateToken(foundUser);
                System.out.println("jwtToken:");
                System.out.println(jwtToken);

                if(aboutService.findByUser(foundUser) == null) {
                    aboutService.addAbout("", "", "", "", "", foundUser);
                }
                if(interestService.findByUser(foundUser) == null) {
                    interestService.addInterest("", "", "", "", foundUser);
                }

                String resultJson = String.format("{ \"token\": \"%s\", \"firstname\": \"%s\",\"lastname\": \"%s\",\"email\": \"%s\",\"_id\": \"%s\" }", jwtToken, foundUser.firstname, foundUser.lastname, foundUser.email, foundUser._id );

                ResponseEntity result = new ResponseEntity(resultJson, HttpStatus.OK);
                System.out.println(result);

//                return String.format("{ \"token\": \"%s\", \"firstname\": \"%s\",\"lastname\": \"%s\",\"email\": \"%s\",\"_id\": \"%s\" }", jwtToken, foundUser.firstname, foundUser.lastname, foundUser.email, foundUser._id );
                return result;
            }
        }




        String errorJson = String.format("{ \"message\": \"User and Password don't match.\" }" );
        ResponseEntity result = new ResponseEntity(errorJson,HttpStatus.FORBIDDEN);
//        return new ResponseEntity(userService.login(jsonObject.getString("username"),jsonObject.getString("password")),HttpStatus.FORBIDDEN);
        System.out.println(result);
        return result;
        // -----
//        Users loggedin = userService.login(jsonObject.getString("email"),jsonObject.getString("password"));
//        System.out.println((loggedin));
//
//        String jwtToken = tokenHelper.generateToken(loggedin);
//        System.out.println("jwtToken:");
//        System.out.println(jwtToken);
//
//
//        if(aboutService.findByUser(loggedin) == null) {
//            aboutService.addAbout("", "", "", "", "", loggedin);
//        }
//        if(interestService.findByUser(loggedin) == null) {
//            interestService.addInterest("", "", "", "", loggedin);
//        }

//        return String.format("{ \"token\": \"%s\", \"firstname\": \"%s\",\"lastname\": \"%s\",\"email\": \"%s\",\"_id\": \"%s\" }", jwtToken, loggedin.firstname, loggedin.lastname, loggedin.email, loggedin._id );

        // ----

    }


    @PostMapping(path="/signup",consumes = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public  ResponseEntity<?> addNewUser (@RequestBody Users user) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        Users newUser = userService.addUser(user);
        aboutService.addAbout("", "", "", "", "", user);
        interestService.addInterest("", "", "", "", user);

        System.out.println("Saved");
        return new ResponseEntity(null,HttpStatus.CREATED);
    }


}
