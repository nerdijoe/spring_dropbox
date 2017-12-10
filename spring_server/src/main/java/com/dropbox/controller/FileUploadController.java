package com.dropbox.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.dropbox.model.Files;
import com.dropbox.model.Folders;
import com.dropbox.model.Users;
import com.dropbox.s3.S3Wrapper;
import com.dropbox.service.FileService;
import com.dropbox.service.FolderService;
import com.dropbox.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dropbox.storage.StorageFileNotFoundException;
import com.dropbox.storage.StorageService;


@RestController
//@Controller
@CrossOrigin(origins = "http://localhost:3001")
@RequestMapping(path="/uploads")
public class FileUploadController {
    private final StorageService storageService;

    @Autowired
    UserService userService;

    @Autowired
    FolderService folderService;

    @Autowired
    FileService fileService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @Autowired
    private S3Wrapper s3Wrapper;

    @Value("${jwt.secret}")
    public String SECRET;

    @GetMapping("/uploads")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    @PostMapping(path="/createfolder",consumes = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
    public Folders addNewFolder (@RequestHeader(value="token") String token, @RequestBody String body) {
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
            Users user = userService.findById(decoded.getString("_id"));
            System.out.println("Who is user ....");
            System.out.println(user);


            JSONObject jsonObject = new JSONObject(body);
            String newDirName = jsonObject.getString("name");
            String path = jsonObject.getString("currentPath");
            String full_path = path + "/" + newDirName;
            Folders newFolder = folderService.addFolder(newDirName, path, full_path, user);
            System.out.println("Folder Saved");
//            return new ResponseEntity(null, HttpStatus.CREATED);
            return newFolder;


        } catch (SignatureException e) {

            //don't trust the JWT!
            System.out.println("jwt decode error xxxxxxxx");
            System.out.println(e);
            System.out.println("Error----");
            return null;

        }

    }

//    @CrossOrigin(origins = { "*" },
//            methods={RequestMethod.POST, RequestMethod.OPTIONS},
//            allowedHeaders={"Origin", "X-Requested-With", "content-type", "Accept", "Access-Control-Allow-Origin", "token"})
////    @CrossOrigin(origins = { "*" })
//    @SuppressWarnings("unchecked")
    @PostMapping(value = "/{currentPath}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Files handleFileUpload(@RequestHeader(value="token") String token, @RequestParam("file") MultipartFile file, @PathVariable("currentPath") String currentPath) {
        System.out.println("POST uploads heyoooo");

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
            // get currentPath folder
            // get file name

            System.out.println("POST uploads");
            System.out.println(file);
            System.out.println(file.getName());
            System.out.println(file.getOriginalFilename());
            System.out.println(file.getContentType());
            System.out.println(file.getSize());


            String dir = "./public/uploads/" + decoded.getString("email");
            Folders folder = folderService.findById(currentPath);
            System.out.println("-----");
            System.out.println(folder);
            if(folder != null) {
                dir = folder.getFull_path();
            }
            String full_path = dir + "/" + file.getOriginalFilename();

            String user_email = decoded.getString("email");
            String aws_s3_path = "https://s3-us-west-2.amazonaws.com/dropbox-kafka/" + user_email + "/" + file.getOriginalFilename();

            String size = Long.toString(file.getSize());

            Files newFile = fileService.addFile(file.getOriginalFilename(), dir, full_path, aws_s3_path, file.getContentType(), size, user);
            storageService.store(file);

            System.out.println("s3 babyyy-----");
//            MultipartFile[] files = ;
            s3Wrapper.uploadOne(file, user_email);

            System.out.println("-----");

//        redirectAttributes.addFlashAttribute("message",
//                "You successfully uploaded " + file.getOriginalFilename() + "!");

            return newFile;

        } catch (SignatureException e) {

            //don't trust the JWT!
            System.out.println("jwt decode error xxxxxxxx");
            System.out.println(e);
            System.out.println("Error----");

            return null;
        }
    }


    @PostMapping(value = "/ijo")
    public @ResponseBody String handleFileUploadTest(@RequestParam("file") MultipartFile file) {
        System.out.println("POST uploads tesssst");

        storageService.store(file);

        return "yeaaah";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }





}
