package com.dropbox.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;

public class Abouts {
    @Id
    String _id;

    String overview;
    String work;
    String education;
    String contact_info;
    String life_events;

    Users user;

    Date createdAt;
    Date updatedAt;

    public Abouts() {}

    public Abouts(String overview, String work, String education, String contact_info, String life_events, Users user) {
        this.overview = overview;
        this.work = work;
        this.education = education;
        this.contact_info = contact_info;
        this.life_events = life_events;

        this.user = user;

        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    @Override
    public String toString() {
        return String.format("Abouts[_id=%s, overview='%s', work='%s', education=%s, contact_info=%s, life_events=%s, user=%s, createdAt=%s, updatedAt=%s ]", _id, overview, work, education, contact_info, life_events, user, createdAt, updatedAt);
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getContact_info() {
        return contact_info;
    }

    public void setContact_info(String contact_info) {
        this.contact_info = contact_info;
    }

    public String getLife_events() {
        return life_events;
    }

    public void setLife_events(String life_events) {
        this.life_events = life_events;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
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
