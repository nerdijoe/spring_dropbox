package com.dropbox.service;

import com.dropbox.model.Folders;
import com.dropbox.model.Users;
import com.dropbox.repository.FolderRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderService {
    @Autowired
    FolderRepository folderRepository;

    public Iterable<Folders> getAll() {
//        System.out.println(activityRepository.findAll());
        return folderRepository.findAll();
    }

    public Iterable<Folders> findByUser(Users user) {
//        System.out.println(activityRepository.findAll());
        return folderRepository.findByUser(user);
    }
    public Folders addFolder(String name, String path, String full_path, Users user){
        Folders newFolder = folderRepository.save(new Folders(name, path, full_path, user));

        System.out.println("After save new folder......");
        System.out.println(newFolder);
        return newFolder;
    }




}
