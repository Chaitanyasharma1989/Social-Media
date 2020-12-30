package com.csharma.socialmedia.controller;


import com.csharma.socialmedia.service.userservice.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class UserController {

    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PatchMapping(value = "follow")
    public ResponseEntity followUser(@RequestParam String followerId, @RequestParam String followeeId) {
        userService.followUser(followerId, followeeId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "unfollow")
    public ResponseEntity unFollowUser(@RequestParam String followerId, @RequestParam String followeeId) {
        userService.unFollowUser(followerId, followeeId);
        return ResponseEntity.ok().build();
    }

}
