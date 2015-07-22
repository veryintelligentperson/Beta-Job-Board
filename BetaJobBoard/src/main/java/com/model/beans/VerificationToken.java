package com.model.beans;



import javax.persistence.*;
import java.util.Date;

/**
 * Created by michal on 04.07.15.
 */
@Entity
public class VerificationToken {

    @Id
    @GeneratedValue
    private int id;
    private String token;
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User user;

    @Column(name = "signup_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;

    @Column(name = "expire")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationTime;

    public VerificationToken() {
    }

    public VerificationToken(String token, User user){
        this.dateTime = new Date();
        this.expirationTime = new Date(dateTime.getTime() + (1000 * 3600 * 24));
        this.token = token;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }
}
