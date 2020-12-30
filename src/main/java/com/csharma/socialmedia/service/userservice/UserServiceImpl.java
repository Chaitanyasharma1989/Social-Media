package com.csharma.socialmedia.service.userservice;


import com.csharma.socialmedia.model.User;
import com.csharma.socialmedia.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void followUser(final String followerId, final String followeeId) {
        Optional<User> followerUser = userRepository.findUserByUserId(followeeId);
        Optional<User> followeeUser = userRepository.findUserByUserId(followeeId);
        if (followerId.equalsIgnoreCase(followeeId)
                && followeeUser.isPresent()
                && followerUser.isPresent()) {
            LOGGER.info("");
        } else {
            LOGGER.info("");
            followerUser.get().getFollowing().add(followeeUser.get());
            LOGGER.info("");
        }
    }

    @Override
    public void unFollowUser(final String followerId, final String followeeId) {
        Optional<User> followerUser = userRepository.findUserByUserId(followeeId);
        Optional<User> followeeUser = userRepository.findUserByUserId(followeeId);
        if (followerId.equalsIgnoreCase(followeeId)
                && followeeUser.isPresent()
                && followerUser.isPresent()) {
            LOGGER.info("");
        } else {
            LOGGER.info("");
            followerUser.get().getFollowing().remove(followeeUser.get());
            LOGGER.info("");
        }
    }
}
