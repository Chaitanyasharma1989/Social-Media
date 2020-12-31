package com.csharma.socialmedia.controller;


import com.csharma.socialmedia.exception.ServiceException;
import com.csharma.socialmedia.request.UserRequest;
import com.csharma.socialmedia.service.userservice.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PutMapping(value = "follow")
    public ResponseEntity followUser(@RequestBody @Valid UserRequest userRequest) throws ServiceException {
        LOGGER.info("Received request to follow user : {}", userRequest.getFolloweeId());
        userService.followUser(userRequest.getFollowerId(), userRequest.getFolloweeId());
        LOGGER.info("Successfully completed request to follow user : {}", userRequest.getFolloweeId());
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "unfollow")
    public ResponseEntity unFollowUser(@RequestBody @Valid UserRequest userRequest) throws ServiceException {
        LOGGER.info("Received request to unfollow user : {}", userRequest.getFolloweeId());
        userService.unFollowUser(userRequest.getFollowerId(), userRequest.getFolloweeId());
        LOGGER.info("Successfully completed request to unfollow user : {}", userRequest.getFolloweeId());
        return ResponseEntity.ok().build();
    }
}
