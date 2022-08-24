package com.arbib.admin_panel.objects;

import java.util.ArrayList;

public class User {
    private String id;
    private String username;
    private String firstname;
    private String lastname;
    private String phone;
    private String birthday;
    private String address;
    private ArrayList<Post> posts;
    private ArrayList<Skill> skills;
    private ArrayList<Experience> experiences;


    public User(String id, String username, String firstname,
                String lastname, String phone, String birthday,
                String address, ArrayList<Post> posts, ArrayList<Skill> skills,
                ArrayList<Experience> experiences) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.birthday = birthday;
        this.address = address;
        this.posts = posts;
        this.skills = skills;
        this.experiences = experiences;
    }

    public User(String username, String firstname, String lastname,
                String phone, String birthday, String address,
                ArrayList<Post> posts, ArrayList<Skill> skills,
                ArrayList<Experience> experiences) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.birthday = birthday;
        this.address = address;
        this.posts = posts;
        this.skills = skills;
        this.experiences = experiences;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }

    public ArrayList<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(ArrayList<Experience> experiences) {
        this.experiences = experiences;
    }
}
