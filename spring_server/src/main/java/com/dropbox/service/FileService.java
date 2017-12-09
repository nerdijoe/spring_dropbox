package com.dropbox.service;

import com.dropbox.model.Files;
import com.dropbox.model.Users;
import com.dropbox.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    @Autowired
    FileRepository fileRepository;

    @Autowired
    UserService userService;

    public Iterable<Files> getAll() {
//        System.out.println(activityRepository.findAll());
        return fileRepository.findAll();
    }

    public Iterable<Files> findByUser(Users user) {
//        System.out.println(activityRepository.findAll());
        return fileRepository.findByUser(user);
    }

    public Files addFile(String name, String path, String full_path, String aws_s3_path, String type, String size, Users user){
        Files newFile = fileRepository.save(new Files(name, path, full_path, aws_s3_path, type, size, user));

        System.out.println("After save new file......");
        System.out.println(newFile);
        return newFile;
    }
}
