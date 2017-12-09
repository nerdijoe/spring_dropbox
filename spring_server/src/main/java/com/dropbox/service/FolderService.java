package com.dropbox.service;

import com.dropbox.model.Folders;
import com.dropbox.model.Users;
import com.dropbox.repository.FolderRepository;
import com.dropbox.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class FolderService {
    @Autowired
    FolderRepository folderRepository;

    @Autowired
    UserService userService;

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

    public void starFolder(String id) {
        Folders updatedFolder = folderRepository.findById(id);
        Boolean star_status = updatedFolder.getIs_starred();
        if(star_status) {
            updatedFolder.setIs_starred(Boolean.FALSE);
        } else {
            updatedFolder.setIs_starred(Boolean.TRUE);
        }

        folderRepository.save(updatedFolder);
    }

    public void deleteFolder(String id) {
        Folders updatedFolder = folderRepository.findById(id);
        Boolean delete_status = updatedFolder.getIs_deleted();
        if(delete_status) {
            updatedFolder.setIs_deleted(Boolean.FALSE);
        } else {
            updatedFolder.setIs_deleted(Boolean.TRUE);
        }

        folderRepository.save(updatedFolder);
    }

    public Folders addFolderSharing(String users, String id) {
        Folders updatedFolder = folderRepository.findById(id);

        if(updatedFolder.getUsers() == null) {
            List<Users> init = new ArrayList<Users>();
            updatedFolder.setUsers(init);
        }

        Iterable<Users> foundUsers = userService.findByEmails(users);
        for( Users u: foundUsers) {
            System.out.println(u);
            updatedFolder.getUsers().add(u);
        }

        System.out.println("After adding users");
        System.out.println(updatedFolder);
        folderRepository.save(updatedFolder);

        return updatedFolder;
    }

    public Folders removeFolderSharing(String user_id, String folder_id) {
        Folders updatedFolder = folderRepository.findById(folder_id);

        Users foundUser = userService.findById(user_id);
//        for( Users u: foundUsers) {
//            System.out.println(u);
//            updatedFolder.getUsers().add(u);
//        }

        for (Iterator<Users> iter = updatedFolder.getUsers().listIterator(); iter.hasNext(); ) {
            Users a = iter.next();
            if (a._id.equals(user_id)) {
                iter.remove();
                break;
            }
        }

        System.out.println("After removing user");
        System.out.println(updatedFolder);

        folderRepository.save(updatedFolder);

        return updatedFolder;
    }


}
