package com.dropbox.service;

import com.dropbox.model.Interests;
import com.dropbox.model.Users;
import com.dropbox.repository.AboutRepository;
import com.dropbox.repository.InterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterestService {
    @Autowired
    private InterestRepository interestRepository;

    public Interests findById(String id) {
        return interestRepository.findById(id);
    }

    public Interests findByUser(Users user) {
//        System.out.println(activityRepository.findAll());
        return interestRepository.findByUser(user);
    }

    public Interests addInterest(String music, String shows, String sports, String fav_teams, Users user){
        Interests newItem = interestRepository.save(new Interests(music, shows, sports, fav_teams, user));

        System.out.println("After save new interest......");
        System.out.println(newItem);
        return newItem;
    }

    public void updateInterest(String music, String shows, String sports, String fav_teams, Users user) {
        Interests updatedItem = interestRepository.findByUser(user);

        updatedItem.setMusic(music);
        updatedItem.setShows(shows);
        updatedItem.setSports(sports);
        updatedItem.setFav_teams(fav_teams);

        interestRepository.save(updatedItem);
    }
}
