package com.epicodus.kloutulator.models;

/**
 * Created by Guest on 5/12/16.
 */
public class User {
    private String name;
    private String email;

    public User() {}

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}