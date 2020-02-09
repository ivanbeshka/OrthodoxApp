package com.example.orthodoxapp.ui.news;

import androidx.lifecycle.ViewModel;

import com.example.orthodoxapp.dataModel.Post;
import com.example.orthodoxapp.dataModel.User;

import java.util.LinkedList;
import java.util.List;

public class NewsViewModel extends ViewModel {


    //public ObservableField<Post> post = new ObservableField<>();

    private List<Post> posts = new LinkedList<>();


    public NewsViewModel() {

//        User user = User.builder().name("ivan").status("online").build();
//
//        Post post = Post.builder().comments(8).likes(999)
//                .reposts(957).user(user)
//                .views(465).text("gbshfbgsdbfgbgbs").build();
//
//        for (int i = 0; i < 50; i++) {
//            posts.add(post);
//        }
    }

    List<Post> getPosts() {
        return posts;
    }
}