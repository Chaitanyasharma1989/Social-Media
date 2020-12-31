package com.csharma.socialmedia.service.userservice;

import com.csharma.socialmedia.exception.ServiceException;
import com.csharma.socialmedia.model.MediaPost;
import com.csharma.socialmedia.model.User;
import com.csharma.socialmedia.repository.UserRepository;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest extends TestCase {

    @Mock
    private UserRepository userRepository;

    private User userFollowee;
    private User userFollower;

    private List<User> users;

    private final String userIdFollowee = "userIdFollowee";
    private final String userIdFollower = "userIdFollower";

    private UserServiceImpl userService;

    @Before
    public void setUp() {
        userService = new UserServiceImpl(userRepository);

        users = new ArrayList<>();
        MediaPost mediaPost1 = new MediaPost("postId1", LocalDateTime.now());
        MediaPost mediaPost2 = new MediaPost("postId2", LocalDateTime.now());
        MediaPost mediaPost3 = new MediaPost("postId3", LocalDateTime.now());
        MediaPost mediaPost4 = new MediaPost("postId4", LocalDateTime.now());

        User userFollower1 = new User("userFollower1", Collections.emptyList(), Collections.singletonList(mediaPost1));
        User userFollower2 = new User("userFollower1", Collections.emptyList(), Collections.singletonList(mediaPost2));
        User userFollower3 = new User("userFollower3", Collections.emptyList(), Collections.singletonList(mediaPost3));
        User userFollower4 = new User("userFollower4", Collections.emptyList(), Collections.singletonList(mediaPost4));

        users.add(userFollower1);
        users.add(userFollower2);
        users.add(userFollower3);
        users.add(userFollower4);

        List<MediaPost> mediaPosts = new ArrayList<>();
        mediaPosts.add(mediaPost1);
        mediaPosts.add(mediaPost2);
        mediaPosts.add(mediaPost3);
        mediaPosts.add(mediaPost4);

        userFollowee = new User(userIdFollowee, users, mediaPosts);
        userFollower = new User(userIdFollower, Collections.emptyList(), Collections.emptyList());
    }

    @Test
    public void shouldAbleToFollowUserIfBothArePresentInDatabaseAndFollowerIsNotPresentInFollowingList() {
        int updatedFollowingSize = 5;
        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);
        when(userRepository.findUserByUserId(userIdFollower)).thenReturn(userFollower);
        userService.followUser(userIdFollower, userIdFollowee);
        Assert.assertEquals(updatedFollowingSize, userFollowee.getFollowing().size());
    }

    @Test
    public void shouldNotAbleToFollowUserIfBothArePresentInDatabaseAndFollowerIsPresentInFollowingList() {
        int updatedFollowingSize = 5;
        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);
        when(userRepository.findUserByUserId(userIdFollower)).thenReturn(userFollower);
        userService.followUser(userIdFollower, userIdFollowee);
        Assert.assertEquals(updatedFollowingSize, userFollowee.getFollowing().size());
    }

    @Test
    public void shouldAbleToUnFollowUserIfBothArePresentInDatabaseAndFollowerIsPresentInFollowingList() {
        int updatedFollowingSize = 4;
        users.add(new User(userIdFollower, Collections.emptyList(), Collections.emptyList()));
        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);
        when(userRepository.findUserByUserId(userIdFollower)).thenReturn(userFollower);
        userService.unFollowUser(userIdFollower, userIdFollowee);
        Assert.assertEquals(updatedFollowingSize, userFollowee.getFollowing().size());
    }

    @Test
    public void shouldAbleToUnFollowUserIfBothArePresentInDatabaseAndFollowerIsNotPresentInFollowingList() {
        int updatedFollowingSize = 4;
        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);
        when(userRepository.findUserByUserId(userIdFollower)).thenReturn(userFollower);
        userService.unFollowUser(userIdFollower, userIdFollowee);
        Assert.assertEquals(updatedFollowingSize, userFollowee.getFollowing().size());
    }

    @Test(expected = ServiceException.class)
    public void shouldNotAbleToFollowUserIfFolloweeIdIsNotPresent() {
        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);
        when(userRepository.findUserByUserId(userIdFollower)).thenReturn(userFollower);
        userService.followUser(userIdFollower, null);
    }

    @Test(expected = ServiceException.class)
    public void shouldNotAbleToFollowUserIfFollowerIdIsNull() {
        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);
        when(userRepository.findUserByUserId(userIdFollower)).thenReturn(userFollower);
        userService.followUser(null, userIdFollowee);
    }

    @Test(expected = ServiceException.class)
    public void shouldNotAbleToFollowUserIfFollowerIdAndFolloweeIdAreNull() {
        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);
        when(userRepository.findUserByUserId(userIdFollower)).thenReturn(userFollower);
        userService.followUser(null, null);
    }

    @Test(expected = ServiceException.class)
    public void shouldNotAbleToFollowUserIfFollowerAndFolloweeIdAreEmpty() {
        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);
        when(userRepository.findUserByUserId(userIdFollower)).thenReturn(userFollower);
        userService.followUser("", "");
    }

    @Test(expected = ServiceException.class)
    public void shouldNotAbleToFollowUserIfFolloweeIdIsEmpty() {
        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);
        when(userRepository.findUserByUserId(userIdFollower)).thenReturn(userFollower);
        userService.followUser(userIdFollower, "");
    }

    @Test(expected = ServiceException.class)
    public void shouldNotAbleToFollowUserIfFollowerIdIsEmpty() {
        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);
        when(userRepository.findUserByUserId(userIdFollower)).thenReturn(userFollower);
        userService.followUser("", userIdFollowee);
    }

    @Test(expected = ServiceException.class)
    public void shouldNotAbleToFollowUserIfFollowerIdAndFolloweeIdIsEmpty() {
        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);
        when(userRepository.findUserByUserId(userIdFollower)).thenReturn(userFollower);
        userService.followUser("", "");
    }
}