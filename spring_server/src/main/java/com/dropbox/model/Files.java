package com.dropbox.model;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

public class Files {
    @Id
    String _id;

    String name;
    String path;
    String full_path;
    String aws_s3_path;

    String type;
    String size;

    Boolean is_starred;
    Boolean is_deleted;

    Users user;

    List<Users> users;

    Date createdAt;
    Date updatedAt;


    public Files() {}

    public Files(String name, String path, String full_path, String aws_s3_path, String type, String size, Users user) {
        this.name = name;
        this.path = path;
        this.full_path = full_path;
        this.aws_s3_path = aws_s3_path;

        this.user = user;

        this.type = type;
        this.size = size;

        this.is_starred = false;
        this.is_deleted = false;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    @Override
    public String toString() {
        return String.format("File[_id=%s, name='%s', path='%s', full_path=%s, aws_s3_path=%s, is_starred=%s, is_deleted=%s, user=%s, users=%s, createdAt=%s, updatedAt=%s ]", _id, name, path, full_path, aws_s3_path, is_starred, is_deleted, user, users, createdAt, updatedAt);
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFull_path() {
        return full_path;
    }

    public void setFull_path(String full_path) {
        this.full_path = full_path;
    }

    public String getAws_s3_path() {
        return aws_s3_path;
    }

    public void setAws_s3_path(String aws_s3_path) {
        this.aws_s3_path = aws_s3_path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Boolean getIs_starred() {
        return is_starred;
    }

    public void setIs_starred(Boolean is_starred) {
        this.is_starred = is_starred;
    }

    public Boolean getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
