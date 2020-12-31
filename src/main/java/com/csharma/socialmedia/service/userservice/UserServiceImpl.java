package com.csharma.socialmedia.service.userservice;


import com.csharma.socialmedia.exception.ServiceException;
import com.csharma.socialmedia.model.User;
import com.csharma.socialmedia.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void followUser(final String followerId, final String followeeId) throws ServiceException {
        Optional<User> followeeUser = Optional.ofNullable(userRepository.findUserByUserId(followeeId));
        Optional<User> followerUser = Optional.ofNullable(userRepository.findUserByUserId(followerId));

        if (followeeUser.isPresent() && followerUser.isPresent()) {
            LOGGER.info("Both follower and following are present in the database");
            if (followerUser.get().getFollowing().stream()
                    .anyMatch(user -> user.getUserId().equalsIgnoreCase(followerId))) {
                LOGGER.info("Follower already present in the following list");
            } else {
                followeeUser.get().getFollowing().add(followeeUser.get());
                LOGGER.info("Successfully added followee to followers list");
            }
        } else {
            throw new ServiceException("Either follower or followee are not present in the database");
        }
    }

    @Override
    public void unFollowUser(final String followerId, final String followeeId) throws ServiceException {
        Optional<User> followeeUser = Optional.ofNullable(userRepository.findUserByUserId(followeeId));
        Optional<User> followerUser = Optional.ofNullable(userRepository.findUserByUserId(followerId));

        if (followeeUser.isPresent() && followerUser.isPresent()) {
            LOGGER.info("Both follower and following are present in the database");
            if (followeeUser.get().getFollowing().stream()
                    .anyMatch(user -> user.getUserId().equalsIgnoreCase(followerId))) {

                followeeUser.get().getFollowing().remove(followerUser.get());

                LOGGER.info("Successfully removed followee from followers list");
            } else {
                LOGGER.info("Follower not found in followee following list");
            }
        } else {
            throw new ServiceException("Either follower or followee are not present in the database");
        }
    }
}
