package com.dropbox.service;

import com.dropbox.model.Files;
import com.dropbox.model.Users;
import com.dropbox.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    public void starFile(String id) {
        Files updatedFile = fileRepository.findById(id);
        Boolean star_status = updatedFile.getIs_starred();
        if(star_status) {
            updatedFile.setIs_starred(Boolean.FALSE);
        } else {
            updatedFile.setIs_starred(Boolean.TRUE);
        }

        fileRepository.save(updatedFile);
    }

    public void deleteFile(String id) {
        Files updatedFile = fileRepository.findById(id);
        Boolean delete_status = updatedFile.getIs_deleted();
        if(delete_status) {
            updatedFile.setIs_deleted(Boolean.FALSE);
        } else {
            updatedFile.setIs_deleted(Boolean.TRUE);
        }

        fileRepository.save(updatedFile);
    }

    public Files addFileSharing(String users, String id) {
        Files updatedFile = fileRepository.findById(id);

        if(updatedFile.getUsers() == null) {
            List<Users> init = new ArrayList<Users>();
            updatedFile.setUsers(init);
        }

        Iterable<Users> foundUsers = userService.findByEmails(users);
        for( Users u: foundUsers) {
            System.out.println(u);
            updatedFile.getUsers().add(u);
        }

        System.out.println("After adding users");
        System.out.println(updatedFile);
        fileRepository.save(updatedFile);

        return updatedFile;
    }

    public Files removeFileSharing(String user_id, String file_id) {
        Files updatedFile = fileRepository.findById(file_id);

        Users foundUser = userService.findById(user_id);
//        for( Users u: foundUsers) {
//            System.out.println(u);
//            updatedFolder.getUsers().add(u);
//        }

        for (Iterator<Users> iter = updatedFile.getUsers().listIterator(); iter.hasNext(); ) {
            Users a = iter.next();
            if (a._id.equals(user_id)) {
                iter.remove();
                break;
            }
        }

        System.out.println("After removing user");
        System.out.println(updatedFile);

        fileRepository.save(updatedFile);

        return updatedFile;
    }

}
