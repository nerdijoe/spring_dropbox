package com.dropbox.controller;

import com.dropbox.model.Activities;
import com.dropbox.model.Users;
import com.dropbox.repository.ActivityRepository;
import com.dropbox.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "http://localhost:3001")
@RestController
@RequestMapping(path="/activities")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @GetMapping(path="/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Iterable<Activities> findAll() {
//        repository.save(new Users("James", "Harden", "harden@rockets.com", "haha"));
//        repository.save(new Users("LeBron", "James", "lebron@cavs.com", "haha"));

        // This returns a JSON with the users
        return activityService.getAllActivities();
    }


}
