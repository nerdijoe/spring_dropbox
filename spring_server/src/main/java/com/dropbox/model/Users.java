package com.dropbox.model;

import org.springframework.data.annotation.Id;

public class Users {
    @Id
    public String _id;

    public String firstname;
    public String lastname;
    public String email;
    public String password;

    public Users() {}

    public Users(String firstname, String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return String.format("User[_id=%s, firstname='%s', lastname='%s', email='%s' , password='%s']", _id, firstname, lastname, email, password);
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
