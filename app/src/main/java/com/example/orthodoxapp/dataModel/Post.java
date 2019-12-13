package com.example.orthodoxapp.dataModel;

import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder(toBuilder = true)
public class Post {

    @Builder.Default
    private int likes = 0;
    @Builder.Default
    private int reposts = 0;
    @Builder.Default
    private int views = 0;
    @Builder.Default
    private int comments = 0;
    @Builder.Default
    private Date postDate = new Date();
    private String text;
    private String image;
    private User user;

}
