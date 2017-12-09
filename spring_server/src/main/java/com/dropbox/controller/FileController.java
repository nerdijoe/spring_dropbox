package com.dropbox.controller;

import com.dropbox.model.Files;
import com.dropbox.model.Users;
import com.dropbox.service.FileService;
import com.dropbox.service.FolderService;
import com.dropbox.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "http://localhost:3001")
@RestController
@RequestMapping(path="/files")
public class FileController {
    @Autowired
    UserService userService;

    @Autowired
    FileService fileService;

    @Value("${jwt.secret}")
    public String SECRET;

    @GetMapping(path="/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Iterable<Files> findAll() {
        return fileService.getAll();
    }

    @GetMapping(path="/root",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Iterable<Files> findByUser(@RequestHeader(value="token") String token) {
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
            Users user = userService.findById(decoded.getString("_id"));
            System.out.println("Who is user ....");
            System.out.println(user);

            return fileService.findByUser(user);


        } catch (SignatureException e) {

            //don't trust the JWT!
            System.out.println("jwt decode error xxxxxxxx");
            System.out.println(e);
            System.out.println("Error----");
            return null;

        }
    }


}
