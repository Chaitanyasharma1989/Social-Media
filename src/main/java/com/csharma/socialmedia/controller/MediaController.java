package com.csharma.socialmedia.controller;


import com.csharma.socialmedia.model.MediaPost;
import com.csharma.socialmedia.request.CreatePostRequest;
import com.csharma.socialmedia.service.mediaservice.MediaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "/media")
public class MediaController {

    private final MediaService mediaService;
    private final static Logger LOGGER = LoggerFactory.getLogger(MediaController.class);

    @Autowired
    public MediaController(final MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping(value = "/user-post")
    public ResponseEntity mediaPost(@RequestBody @Valid final CreatePostRequest createPostRequest) {
        LOGGER.info("Received request to post the user media content for user {}", createPostRequest.getUserId());
        mediaService.createPost(createPostRequest.getUserId(), createPostRequest.getPostId(), createPostRequest.getContent());
        LOGGER.info("Successfully completed request to post the user media content for user {}", createPostRequest.getUserId());
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/user-posts/{userId}")
    public ResponseEntity<List<MediaPost>> newsFeeds(@PathVariable("userId") @NotNull String userId) {
        LOGGER.info("Received request to fetch top 20 new feed for user {}", userId);
        List<MediaPost> mediaPostList = mediaService.newsFeeds(userId);
        LOGGER.info("Successfully completed request to fetch top 20 new feed for user {}", userId);
        return ResponseEntity.ok(mediaPostList);
    }
}
