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
        return String.format("User[id=%s, firstname='%s', lastname='%s', , email='%s' , password='%s']", _id, firstname, lastname, email, password);
    }

}
