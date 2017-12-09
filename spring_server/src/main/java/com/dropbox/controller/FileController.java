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

    @PutMapping(path="/star",consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Boolean starFile(@RequestHeader(value="token") String token, @RequestBody String body) {
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

//            System.out.println(userId);
//            Users user = userService.findById(decoded.getString("_id"));
//            System.out.println("Who is user ....");
//            System.out.println(user);

//            return folderService.findByUser(user);

            JSONObject jsonObject = new JSONObject(body);
            System.out.println("Star File......");
            System.out.println(jsonObject);
            String fileId = jsonObject.getString("_id");
            fileService.starFile(fileId);
            return true;

        } catch (SignatureException e) {

            //don't trust the JWT!
            System.out.println("jwt decode error xxxxxxxx");
            System.out.println(e);
            System.out.println("Error----");
            return false;

        }
    }

    @PutMapping(path="/delete",consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Boolean deleteFile(@RequestHeader(value="token") String token, @RequestBody String body) {
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

            JSONObject jsonObject = new JSONObject(body);
            System.out.println("Star Folder......");
            System.out.println(jsonObject);
            String fileId = jsonObject.getString("_id");
            fileService.deleteFile(fileId);
            return true;

        } catch (SignatureException e) {

            //don't trust the JWT!
            System.out.println("jwt decode error xxxxxxxx");
            System.out.println(e);
            System.out.println("Error----");
            return false;

        }
    }

    @PostMapping(path="/share",consumes = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public Files addFileSharing (@RequestHeader(value="token") String token, @RequestBody String body) {
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

            String users = jsonObject.getString("users");
            String file_id = jsonObject.getString("file_id");
            System.out.println("usersss");
            System.out.println(users);
            System.out.println("file_id");
            System.out.println(file_id);

            Files updatedFile = fileService.addFileSharing(users, file_id);
            return updatedFile;

        } catch (SignatureException e) {

            //don't trust the JWT!
            System.out.println("jwt decode error xxxxxxxx");
            System.out.println(e);
            System.out.println("Error----");
            return null;

        }
    }

    @PutMapping(path="/share",consumes = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public Files removeFileSharing (@RequestHeader(value="token") String token, @RequestBody String body) {
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

            String user_id = jsonObject.getString("user_id");
            String file_id = jsonObject.getString("file_id");
            System.out.println("usersss");
            System.out.println(user_id);
            System.out.println("file_id");
            System.out.println(file_id);

            Files updatedFile = fileService.removeFileSharing(user_id, file_id);
            return updatedFile;

        } catch (SignatureException e) {

            //don't trust the JWT!
            System.out.println("jwt decode error xxxxxxxx");
            System.out.println(e);
            System.out.println("Error----");
            return null;

        }

    }
}
