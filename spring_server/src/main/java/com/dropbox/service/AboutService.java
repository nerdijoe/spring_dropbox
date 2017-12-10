package com.dropbox.service;

import com.dropbox.model.Abouts;
import com.dropbox.model.Folders;
import com.dropbox.model.Users;
import com.dropbox.repository.AboutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AboutService {
    @Autowired
    private AboutRepository aboutRepository;

    public Abouts findById(String id) {
        return aboutRepository.findById(id);
    }

    public Abouts findByUser(Users user) {
//        System.out.println(activityRepository.findAll());
        return aboutRepository.findByUser(user);
    }

    public Abouts addAbout(String overview, String work, String education, String contact_info,String life_events, Users user){
        Abouts newItem = aboutRepository.save(new Abouts(overview, work, education, contact_info, life_events, user));

        System.out.println("After save new about......");
        System.out.println(newItem);
        return newItem;
    }

    public void updateAbout(String overview, String work, String education, String contact_info,String life_events, Users user) {
        Abouts updatedItem = aboutRepository.findByUser(user);

        updatedItem.setOverview(overview);
        updatedItem.setWork(work);
        updatedItem.setEducation(education);
        updatedItem.setContact_info(contact_info);
        updatedItem.setLife_events(life_events);

        aboutRepository.save(updatedItem);
    }

}
