package com.model.beans;

import javax.persistence.*;

/**
 * Created by michal on 29.06.15.
 */
@Entity
@Table(name = "Applications")
public class Application {


    private int id;
    private User user;
    private Ad ad;

    public Application(User user, Ad ad) {
        this.user = user;
        this.ad = ad;
    }

    public Application(){}

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }
}
