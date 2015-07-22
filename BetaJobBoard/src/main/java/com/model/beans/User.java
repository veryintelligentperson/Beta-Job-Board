package com.model.beans;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by michal on 27.06.15.
 */
@Entity
@Table(name = "Users")
public class User {


    private int id;
    @NotNull
    @NotEmpty
    private String username;
    private String name;
    private String lastname;
    @Email
    @NotNull
    @NotEmpty
    private String email;
    private String phone;
    @NotNull
    @NotEmpty
    private String password;
    private String authority;
    private List<Application> applications;


    public User(){

    }

    public User(String username, String password) {

        this.username = username;
        this.password = password;
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Application> getApplications() {
        return applications;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", applications=" + applications +
                '}';
    }
}
