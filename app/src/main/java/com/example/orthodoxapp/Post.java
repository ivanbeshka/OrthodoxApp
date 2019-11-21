package com.example.orthodoxapp;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class Post extends BaseObservable {
    private int likes;
    private int reposts;
    private int views;
    private int comments;
    private String userName;
    private String userStatus;
    private String text;
    private String image;
    private User user;



    public Post(int likes, int reposts, int views, int comments, User user, String text, String image) {
        this.likes = likes;
        this.reposts = reposts;
        this.views = views;
        this.comments = comments;
        this.text = text;
        this.image = image;
        this.user = user;
        userName = user.getName();
        userStatus = user.getStatus();
    }

    public String getUserName() {
        return userName;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    @Bindable
    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Bindable
    public int getReposts() {
        return reposts;
    }

    public void setReposts(int reposts) {
        this.reposts = reposts;
    }

    @Bindable
    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    @Bindable
    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
