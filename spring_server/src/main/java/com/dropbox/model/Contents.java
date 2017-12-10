package com.dropbox.model;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Contents {
    @Id

    List<Files> files;
    List<Folders> folders;

    public Contents () {}

    public Contents (List<Files> files, List<Folders> folders) {
        this.files = files;
        this.folders = folders;
    }

    public List<Files> getFiles() {
        return files;
    }

    public void setFiles(List<Files> files) {
        this.files = files;
    }

    public List<Folders> getFolders() {
        return folders;
    }

    public void setFolders(List<Folders> folders) {
        this.folders = folders;
    }
}
