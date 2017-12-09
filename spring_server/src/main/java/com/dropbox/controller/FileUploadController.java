package com.dropbox.controller;

import java.io.IOException;
import java.util.stream.Collectors;

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
@RequestMapping(path="/")
public class FileUploadController {
    private final StorageService storageService;

    @Autowired
    UserService userService;

    @Autowired
    FolderService folderService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

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


    @PostMapping(path="uploads/createfolder",consumes = MediaType.APPLICATION_JSON_VALUE) // Map ONLY POST Requests
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
            Folders newFolder = folderService.addFolder(jsonObject.getString("name"), "test", "localhost:3000/test", user);
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

    @CrossOrigin(origins = { "*" },
            methods={RequestMethod.POST, RequestMethod.OPTIONS},
            allowedHeaders={"Origin", "X-Requested-With", "content-type", "Accept", "Access-Control-Allow-Origin", "token"})
    @PostMapping(value = "/uploads/{currentPath}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String handleFileUpload(@RequestHeader(value="token") String token, @RequestParam("file") MultipartFile file, @PathVariable("currentPath") Integer currentPath,
                                   RedirectAttributes redirectAttributes) {
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


        } catch (SignatureException e) {

            //don't trust the JWT!
            System.out.println("jwt decode error xxxxxxxx");
            System.out.println(e);
            System.out.println("Error----");
        }

        System.out.println("POST uploads");
        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }


//    @SuppressWarnings("unchecked")
//    @PostMapping(path="/uploadFile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE) // Map ONLY POST Requests
//    public ResponseEntity<?> uploadFile(@RequestParam("uploadThis") MultipartFile file, @RequestParam("parentFolder") String parentFolder,
//                                        @RequestParam("parentFolderPath") String parentFolderPath, @RequestParam("userId") String userId) {
//
//        List<String> tempList = new ArrayList<String>();
//        String tempFileId = userId + String.valueOf(Calendar.getInstance().getTimeInMillis());
//
//        try {
//            byte[] bytes = file.getBytes();
//            ApplicationHome home = new ApplicationHome(this.getClass());
//            Path path = Paths.get(home.getDir() + "\\files\\" + file.getOriginalFilename());
//            Files.write(path, bytes);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }





}
