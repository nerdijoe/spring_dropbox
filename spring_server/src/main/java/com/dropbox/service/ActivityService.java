package com.dropbox.service;

import com.dropbox.model.Activities;
import com.dropbox.model.Users;
import com.dropbox.repository.ActivityRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    public Iterable<Activities> getAllActivities() {
//        System.out.println(activityRepository.findAll());
        return activityRepository.findAll();
    }

    public void addActivity(String action, String description, String userId){
        activityRepository.save(new Activities(action, description, userId));
    }

    public List<Activities> findByUser(String userid) {
//        ObjectId id = new ObjectId(userid);
        return activityRepository.findByUser(userid);
    }
}
