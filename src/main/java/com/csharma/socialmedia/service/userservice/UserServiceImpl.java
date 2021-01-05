package com.csharma.socialmedia.service.userservice;


import com.csharma.socialmedia.exception.ServiceException;
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
    public void followUser(final String followerId, final String followeeId) throws ServiceException {
        Optional<User> followeeUser = Optional.ofNullable(userRepository.findUserByUserId(followeeId));
        Optional<User> followerUser = Optional.ofNullable(userRepository.findUserByUserId(followerId));

        if (followeeUser.isPresent() && followerUser.isPresent()) {
            LOGGER.info("Adding user {} to following list of {}", followeeId, followerId);
            followerUser.get().follow(followeeId);
            LOGGER.info("Successfully adding user {} to following list of {}", followeeId, followerId);
        } else {
            throw new ServiceException("User not found");
        }
    }

    @Override
    public void unFollowUser(final String followerId, final String followeeId) throws ServiceException {
        Optional<User> followerUser = Optional.ofNullable(userRepository.findUserByUserId(followerId));
        Optional<User> followeeUser = Optional.ofNullable(userRepository.findUserByUserId(followeeId));

        if (followerUser.isPresent() && followeeUser.isPresent() && !followeeId.equalsIgnoreCase(followerId)) {
            if (followerUser.get().following.contains(followeeId)) {
                LOGGER.info("Removed user {} to following list of {}", followeeId, followerId);
                followerUser.get().unfollow(followeeId);
                LOGGER.info("Successfully removed user {} to following list of {}", followeeId, followerId);
            } else {
                throw new ServiceException("User already present in not following list");
            }
        } else {
            throw new ServiceException("User not found or FolloweeId and FollowerId cannot be same");
        }
    }
}
