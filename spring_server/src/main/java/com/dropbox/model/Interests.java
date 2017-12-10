package com.dropbox.model;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Interests {
    @Id
    String _id;

    String music;
    String shows;
    String sports;
    String fav_teams;

    Users user;

    Date createdAt;
    Date updatedAt;

    public Interests() {}

    public Interests(String music, String shows, String sports, String fav_teams, Users user) {
        this.music = music;
        this.shows = shows;
        this.sports = sports;
        this.fav_teams = fav_teams;

        this.user = user;

        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    @Override
    public String toString() {
        return String.format("Abouts[_id=%s, music='%s', shows='%s', sports=%s, fav_teams=%s, user=%s, createdAt=%s, updatedAt=%s ]", _id, music, shows, sports, fav_teams, user, createdAt, updatedAt);
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getShows() {
        return shows;
    }

    public void setShows(String shows) {
        this.shows = shows;
    }

    public String getSports() {
        return sports;
    }

    public void setSports(String sports) {
        this.sports = sports;
    }

    public String getFav_teams() {
        return fav_teams;
    }

    public void setFav_teams(String fav_teams) {
        this.fav_teams = fav_teams;
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
