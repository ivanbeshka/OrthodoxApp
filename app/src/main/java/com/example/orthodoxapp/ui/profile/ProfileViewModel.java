package com.example.orthodoxapp.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.orthodoxapp.Post;
import com.example.orthodoxapp.User;

import java.util.LinkedList;
import java.util.List;

public class ProfileViewModel extends ViewModel {

    private List<Post> posts = new LinkedList<>();


    public ProfileViewModel() {
        User user = new User("UserName", 323, "online", 4735, 5486, 85,
                "Moscow", "selfie");
        Post post = new Post(1, 3, 4, 1, user, "some text", "bdfsbsbd");

        posts.add(0, post);
        posts.add(1, post);
        posts.add(2, post);
        posts.add(3, post);
        posts.add(4, post);
        posts.add(5, post);
        posts.add(6, post);
        posts.add(7, post);
        posts.add(8, post);
        posts.add(9, post);
        posts.add(10, post);
    }

    List<Post> getPosts() {
        return posts;
    }
}