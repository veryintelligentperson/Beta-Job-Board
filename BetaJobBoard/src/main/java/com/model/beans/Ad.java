package com.model.beans;

import javax.persistence.*;
import java.util.List;


/**
 * Created by michal on 27.06.15.
 */
@Entity
@Table(name = "Ads")
public class Ad {


    private int id;
    private String name;
    private String text;
    private List<Application> applications;

    public Ad(){}

    public Ad(String name, String text) {
        this.name = name;
        this.text = text;
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length = 1000)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @OneToMany(mappedBy = "ad", fetch = FetchType.LAZY)
    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    @Override
    public String toString() {
        return "Ad{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
