package com.epicodus.kloutulator.models;

/**
 * Created by Guest on 5/10/16.
 */
public class Influencer {
    String name;
    String score;

    public Influencer(String name, String score) {
        this.name = name;
        this.score = score;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
