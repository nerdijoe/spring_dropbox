package com.dropbox.service;

import org.springframework.data.annotation.Id;

public class Users {
    @Id
    public String id;

    public String firstName;
    public String lastName;
    public String email;
    public String password;

    public Users() {}

    public Users(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("User[id=%s, firstName='%s', lastName='%s', , email='%s' , password='%s']", id, firstName, lastName, email, password);
    }

}
