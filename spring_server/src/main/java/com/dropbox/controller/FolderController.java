package com.dropbox.controller;


import com.dropbox.model.Folders;
import com.dropbox.model.Users;
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
@RequestMapping(path="/folders")
public class FolderController {
    @Autowired
    UserService userService;

    @Autowired
    FolderService folderService;

    @Value("${jwt.secret}")
    public String SECRET;

    @GetMapping(path="/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Iterable<Folders> findAll() {
//        repository.save(new Users("James", "Harden", "harden@rockets.com", "haha"));
//        repository.save(new Users("LeBron", "James", "lebron@cavs.com", "haha"));

        // This returns a JSON with the users
        return folderService.getAll();
    }

    @GetMapping(path="/root",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Iterable<Folders> findByUser(@RequestHeader(value="token") String token) {
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

            return folderService.findByUser(user);


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
    Boolean starFolder(@RequestHeader(value="token") String token, @RequestBody String body) {
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
            System.out.println("Star Folder......");
            System.out.println(jsonObject);
            String folderId = jsonObject.getString("_id");
            folderService.starFolder(folderId);
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
    Boolean deleteFolder(@RequestHeader(value="token") String token, @RequestBody String body) {
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
            System.out.println("Star Folder......");
            System.out.println(jsonObject);
            String folderId = jsonObject.getString("_id");
            folderService.deleteFolder(folderId);
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
    public Folders addFolderSharing (@RequestHeader(value="token") String token, @RequestBody String body) {
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
            String folder_id = jsonObject.getString("folder_id");
            System.out.println("usersss");
            System.out.println(users);
            System.out.println("folder_id");
            System.out.println(folder_id);

            Folders updatedFolder = folderService.addFolderSharing(users, folder_id);
            return updatedFolder;

        } catch (SignatureException e) {

            //don't trust the JWT!
            System.out.println("jwt decode error xxxxxxxx");
            System.out.println(e);
            System.out.println("Error----");
            return null;

        }
    }

    @PutMapping(path="/share",consumes = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public Folders removeFolderSharing (@RequestHeader(value="token") String token, @RequestBody String body) {
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
            String folder_id = jsonObject.getString("folder_id");
            System.out.println("usersss");
            System.out.println(user_id);
            System.out.println("folder_id");
            System.out.println(folder_id);

            Folders updatedFolder = folderService.removeFolderSharing(user_id, folder_id);
            return updatedFolder;

        } catch (SignatureException e) {

            //don't trust the JWT!
            System.out.println("jwt decode error xxxxxxxx");
            System.out.println(e);
            System.out.println("Error----");
            return null;

        }
    }

    @GetMapping(path="/share",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Iterable<Folders> getFolderSharing(@RequestHeader(value="token") String token) {
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

            return folderService.findByUsers(user);


        } catch (SignatureException e) {

            //don't trust the JWT!
            System.out.println("jwt decode error xxxxxxxx");
            System.out.println(e);
            System.out.println("Error----");
            return null;

        }
    }
}
