package com.csharma.socialmedia.controller;


import com.csharma.socialmedia.model.MediaPost;
import com.csharma.socialmedia.request.CreatePostRequest;
import com.csharma.socialmedia.service.mediaservice.MediaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/media")
public class MediaController {

    private final MediaService mediaService;
    private final static Logger LOGGER = LoggerFactory.getLogger(MediaController.class);

    @Autowired
    public MediaController(final MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping(value = "/user-post")
    public ResponseEntity mediaPost(@RequestBody final CreatePostRequest createPostRequest) {
        mediaService.createPost(createPostRequest.getUserId(), createPostRequest.getPostId(), createPostRequest.getContent());
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/user-posts/{userId}")
    public ResponseEntity newsFeeds(@PathVariable("userId") String userId) {
        List<MediaPost> mediaPostList = mediaService.newsFeeds(userId);
        return ResponseEntity.ok(mediaPostList);
    }
}
