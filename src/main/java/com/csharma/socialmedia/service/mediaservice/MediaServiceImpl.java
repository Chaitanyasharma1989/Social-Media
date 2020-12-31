package com.csharma.socialmedia.service.mediaservice;


import com.csharma.socialmedia.exception.ServiceException;
import com.csharma.socialmedia.model.MediaPost;
import com.csharma.socialmedia.model.User;
import com.csharma.socialmedia.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;


@Service
public class MediaServiceImpl implements MediaService {

    private final UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(MediaServiceImpl.class);

    @Autowired
    public MediaServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createPost(final String userId, final String postId, final String content) {
        Optional<User> currentUser = Optional.ofNullable(userRepository.findUserByUserId(userId));
        LOGGER.info("Checking for existing user with userId {} in the system", userId);
        if (currentUser.isPresent()) {
            LOGGER.info("User with user id {} found in database", userId);
            MediaPost post = new MediaPost(postId, LocalDateTime.now());
            currentUser.get().getPosts().add(post);
            LOGGER.info("Updating current user with userId {} post with postId {} in database", userId, postId);
            userRepository.save(currentUser.get());
            LOGGER.info("Successfully updated current user with userId {} post with postId {} in database", userId, postId);
        } else {
            throw new ServiceException("User found in database");
        }
    }

    @Override
    public List<MediaPost> newsFeeds(final String userId) {
        List<MediaPost> userMediaPosts = new ArrayList<>();
        Optional<User> currentUser = Optional.ofNullable(userRepository.findUserByUserId(userId));
        if (!currentUser.isPresent()) {
            LOGGER.info("User not found in database with userId {}", userId);
            throw new ServiceException("Either follower or followee are not present in the database");
        } else {
            LOGGER.info("User found in database with userId {}", userId);
            PriorityQueue<MediaPost> mediaPosts = mediaPosts(currentUser.get());
            LOGGER.info("Mapping and retrieving media post for user with userId {}", userId);
            for (int j = 0; j < 20 && !mediaPosts.isEmpty(); j++) {
                userMediaPosts.add(mediaPosts.poll());
            }
        }
        LOGGER.info("Mapping and retrieving media post for user with userId {}", userId);
        return userMediaPosts;
    }

    private PriorityQueue<MediaPost> mediaPosts(final User user) {
        PriorityQueue<MediaPost> mediaPosts =
                new PriorityQueue<>((a, b) -> b.getInsertTimeStamp().compareTo(a.getInsertTimeStamp()));
        LOGGER.info("Adding posts of current users to heap");
        user.getPosts().stream()
                .limit(20)
                .forEach(mediaPosts::add);
        LOGGER.info("Adding posts of current user followings to heap");
        user.getFollowing()
                .forEach(currentUser -> currentUser.getPosts()
                        .stream().limit(20)
                        .forEach(mediaPosts::add));
        LOGGER.info("Successfully retrieved media post for userId {}", user.getUserId());
        return mediaPosts;
    }
}
