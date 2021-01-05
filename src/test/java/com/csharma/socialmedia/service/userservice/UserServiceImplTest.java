package com.csharma.socialmedia.service.userservice;

import com.csharma.socialmedia.exception.ServiceException;
import com.csharma.socialmedia.model.User;
import com.csharma.socialmedia.repository.UserRepository;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest extends TestCase {

    @Mock
    private UserRepository userRepository;

    private User userFollowee;
    private User userFollower;

    private final String userIdFollowee = "userIdFollowee";
    private final String userIdFollower = "userIdFollower";

    private UserServiceImpl userService;

    @Before
    public void setUp() {
        userService = new UserServiceImpl(userRepository);
        userFollowee = new User(userIdFollowee);
        userFollower = new User(userIdFollower);
    }

    @Test
    public void shouldAbleToFollowUserIfBothArePresentInDatabaseAndFollowerIsNotPresentInFollowingList() throws ServiceException {
        int updatedFollowingSize = 2;
        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);
        when(userRepository.findUserByUserId(userIdFollower)).thenReturn(userFollower);
        userService.followUser(userIdFollower, userIdFollowee);
        Assert.assertEquals(updatedFollowingSize, userFollower.following.size());
    }

    @Test
    public void shouldNotAbleToFollowUserIfBothArePresentInDatabaseAndFolloweeIsPresentInFollowingList() throws ServiceException {
        int updatedFollowingSize = 1;
        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);
        when(userRepository.findUserByUserId(userIdFollower)).thenReturn(userFollowee);
        userService.followUser(userIdFollower, userIdFollowee);
        Assert.assertEquals(updatedFollowingSize, userFollower.following.size());
    }

    @Test
    public void shouldAbleToUnFollowUserIfBothArePresentInDatabaseAndFollowerIsPresentInFollowingList() throws ServiceException {
        int updatedFollowingSize = 0;
        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);
        when(userRepository.findUserByUserId(userIdFollower)).thenReturn(userFollowee);
        userService.unFollowUser(userIdFollower, userIdFollowee);
        Assert.assertEquals(updatedFollowingSize, userFollowee.following.size());
    }

    @Test
    public void shouldAbleToUnFollowUserIfBothArePresentInDatabaseAndFollowerIsNotPresentInFollowingList() throws ServiceException {
        int updatedFollowingSize = 0;
        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);
        when(userRepository.findUserByUserId(userIdFollower)).thenReturn(userFollowee);
        userService.unFollowUser(userIdFollower, userIdFollowee);
        Assert.assertEquals(updatedFollowingSize, userFollowee.following.size());
    }

    @Test(expected = ServiceException.class)
    public void shouldNotAbleToUnFollowUserIdAndFollowerUserIdAreSame() throws ServiceException {
        userService.unFollowUser(userIdFollowee, userIdFollowee);
    }

    @Test(expected = ServiceException.class)
    public void shouldNotAbleToFollowUserIfFolloweeIdIsNotPresent() throws ServiceException {
        userService.followUser(userIdFollower, null);
    }

    @Test(expected = ServiceException.class)
    public void shouldNotAbleToFollowUserIfFollowerIdIsNull() throws ServiceException {
        userService.followUser(null, userIdFollowee);
    }

    @Test(expected = ServiceException.class)
    public void shouldNotAbleToFollowUserIfFollowerIdAndFolloweeIdAreNull() throws ServiceException {
        userService.followUser(null, null);
    }

    @Test(expected = ServiceException.class)
    public void shouldNotAbleToFollowUserIfFollowerAndFolloweeIdAreEmpty() throws ServiceException {
        userService.followUser("", "");
    }

    @Test(expected = ServiceException.class)
    public void shouldNotAbleToFollowUserIfFolloweeIdIsEmpty() throws ServiceException {
        userService.followUser(userIdFollower, "");
    }

    @Test(expected = ServiceException.class)
    public void shouldNotAbleToFollowUserIfFollowerIdIsEmpty() throws ServiceException {
        userService.followUser("", userIdFollowee);
    }

    @Test(expected = ServiceException.class)
    public void shouldNotAbleToFollowUserIfFollowerIdAndFolloweeIdIsEmpty() throws ServiceException {
        userService.followUser("", "");
    }
}