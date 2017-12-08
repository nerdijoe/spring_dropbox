package com.dropbox.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

public class Activities {
    @Id
    String _id;

    String action;
    String description;

    String user;


    Date createdAt;


    Date updatedAt;

    public Activities() {}

    public Activities(String action, String description, String user) {
        this.action = action;
        this.description = description;
        this.user = user;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    @Override
    public String toString() {
        return String.format("Activity[_id=%s, action='%s', description='%s', user=%s, createdAt=%s, updatedAt=%s ]", _id, action, description, user, createdAt, updatedAt);
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String get_id() {
        return _id;
    }

    public String getAction() {
        return action;
    }

    public String getDescription() {
        return description;
    }

    public String getUser() {
        return user;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}
