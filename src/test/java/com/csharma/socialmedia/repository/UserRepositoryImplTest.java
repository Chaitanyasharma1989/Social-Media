package com.csharma.socialmedia.repository;

import com.csharma.socialmedia.model.MediaPost;
import com.csharma.socialmedia.model.User;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.HashMap;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryImplTest extends TestCase {

    @InjectMocks
    private UserRepositoryImpl userRepository;

    private static final String userId = "userId";

    @Test
    public void shouldReturnUser() {
        userRepository.userMap = new HashMap<>();
        userRepository.userMap.put(userId, new User(userId));
        User user = userRepository.findUserByUserId(userId);

        Assert.assertNull(user.mediaPost);
        Assert.assertFalse(user.following.isEmpty());
    }

    @Test
    public void shouldNotReturnUserIfUserIdIsNull() {
        userRepository.userMap = new HashMap<>();
        userRepository.userMap.put(userId, new User(userId));
        User user = userRepository.findUserByUserId(null);
        Assert.assertNull(user);
    }

    @Test
    public void shouldNotReturnUserIfUserIdIsEmpty() {
        userRepository.userMap = new HashMap<>();
        userRepository.userMap.put(userId, new User(userId));
        User user = userRepository.findUserByUserId("");
        Assert.assertNull(user);
    }

    @Test
    public void shouldNotReturnUserIfUserIdIsNotPresent() {
        userRepository.userMap = new HashMap<>();
        userRepository.userMap.put(userId, new User(userId));
        User user = userRepository.findUserByUserId("userId1");
        Assert.assertNull(user);
    }

    @Test
    public void shouldReturnUserWithMediaPost() {
        userRepository.userMap = new HashMap<>();
        User testUser = new User(userId);
        testUser.mediaPost = new MediaPost("postId", "contentId", LocalDateTime.now());

        userRepository.userMap.put(userId, testUser);

        User user = userRepository.findUserByUserId(userId);

        Assert.assertNotNull(user.mediaPost);
        Assert.assertFalse(user.following.isEmpty());
    }

    @Test
    public void shouldReturnUserWithFollowingAndMediaPosts() {
        userRepository.userMap = new HashMap<>();
        User testUser = new User(userId);
        testUser.mediaPost = new MediaPost("postId", "contentId", LocalDateTime.now());
        testUser.following.add("userFollowing1");
        testUser.following.add("userFollowing2");

        userRepository.userMap.put(userId, testUser);

        User user = userRepository.findUserByUserId(userId);

        Assert.assertNotNull(user.mediaPost);
        Assert.assertFalse(user.following.isEmpty());
        Assert.assertEquals(user.following.size(), testUser.following.size());
    }

    @Test
    public void shouldAbleToSaveUser() {
        userRepository.saveUser(userId);
        Assert.assertEquals(userRepository.userMap.get(userId).userId, userId);
    }
}