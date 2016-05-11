package com.epicodus.kloutulator.models;

/**
 * Created by Guest on 5/10/16.
 */
public class Person {
    String kloutID;
    String twitterUsername;

    public Person (String kloutID) {
        this.kloutID = kloutID;
    }

    public String getTwitterUsername() {
        return twitterUsername;
    }

    public String getKloutID() {
        return kloutID;
    }

    public void setKloutID(String kloutID) {
        this.kloutID = kloutID;
    }

    public void setTwitterUsername(String twitterUsername) {
        this.twitterUsername = twitterUsername;
    }

}
