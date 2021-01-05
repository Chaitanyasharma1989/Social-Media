package com.csharma.socialmedia.service.mediaservice;


import com.csharma.socialmedia.exception.ServiceException;
import com.csharma.socialmedia.model.MediaPost;
import com.csharma.socialmedia.model.User;
import com.csharma.socialmedia.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


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
        LOGGER.info("Checking for existing user with userId : {} in the system", userId);
        if (!currentUser.isPresent()) {
            LOGGER.info("Saving new user with userId : {} in the system", userId);
            userRepository.saveUser(userId);
            LOGGER.info("Successfully saved new user with userId : {} in the system", userId);
        } else {
            LOGGER.info("Posting new post for user with userId : {} in the system with post id : {}", userId, postId);
            currentUser.get().post(postId, content);
            LOGGER.info("Successfully posted new post for user with userId : {} in the system with post id : {}", userId, postId);
        }
    }

    @Override
    public List<MediaPost> newsFeeds(final String userId) throws ServiceException {
        List<MediaPost> userMediaPosts;
        Optional<User> currentUser = Optional.ofNullable(userRepository.findUserByUserId(userId));
        if (!currentUser.isPresent()) {
            LOGGER.info("User not found in database with userId {}", userId);
            throw new ServiceException("User not found");
        } else {
            Set<String> followingUsers = currentUser.get().following;
            PriorityQueue<MediaPost> mediaPostQueue = new PriorityQueue<>(followingUsers.size(), (a, b) -> (b.postCounter - a.postCounter));
            followingUsers.forEach(followeeUserId -> {
                MediaPost mediaPost = userRepository.findUserByUserId(followeeUserId).mediaPost;
                if (mediaPost != null) {
                    mediaPostQueue.add(mediaPost);
                }
            });
            userMediaPosts = getMediaPosts(mediaPostQueue);
        }
        LOGGER.info("Mapping and retrieving media post for user with userId {}", userId);
        return userMediaPosts;
    }

    private List<MediaPost> getMediaPosts(PriorityQueue<MediaPost> mediaPostQueue) {
        int mediaPostCounter = 0;
        List<MediaPost> mediaPostList = new ArrayList<>();
        while (!mediaPostQueue.isEmpty() && mediaPostCounter < 20) {
            MediaPost mediaPost = mediaPostQueue.poll();
            mediaPostList.add(mediaPost);
            mediaPostCounter++;
            if (mediaPost.nextMediaPost != null) {
                mediaPostQueue.add(mediaPost.nextMediaPost);
            }
        }
        return mediaPostList;
    }
}
