package com.theakatsuki.hiredevelopers.Model;

import java.lang.reflect.Array;
import java.util.List;

public class User {
    String id;
    String fullname;
    String phoneNumber;
    String work;
    String country;
    String bio;
    String email;
    String password;
    String profileImage;
    String status;
    List<String> programming;


    public User() {
    }


    public User(String id, String fullname, String phoneNumber, String work, String country, String bio, String email, String password, String profileImage, String status, List<String> programming) {
        this.id = id;
        this.fullname = fullname;
        this.phoneNumber = phoneNumber;
        this.work = work;
        this.country = country;
        this.bio = bio;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
        this.status = status;
        this.programming = programming;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<String> getProgramming() {
        return programming;
    }

    public void setProgramming(List<String> programming) {
        this.programming = programming;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
