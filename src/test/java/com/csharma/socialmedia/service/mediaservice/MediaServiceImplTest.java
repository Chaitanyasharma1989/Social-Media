package com.csharma.socialmedia.service.mediaservice;

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
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MediaServiceImplTest extends TestCase {

    @Mock
    private UserRepository userRepository;

    private User userFollowee;

    private final String userIdFollowee = "userIdFollowee";
    private final String postIdFollowee = "postIdFollowee";
    private final String postContent = "postContent";

    private MediaServiceImpl mediaService;

    @Before
    public void setUp() {
        mediaService = new MediaServiceImpl(userRepository);
        userFollowee = new User(userIdFollowee);
    }

    @Test
    public void shouldAbleToPostFirstMediaContentForExistingUser() {
        userFollowee.following.add("userFollowing1");
        userFollowee.following.add("userFollowing2");
        userFollowee.following.add("userFollowing3");
        userFollowee.following.add("userFollowing4");

        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);
        mediaService.createPost(userIdFollowee, postIdFollowee, postContent);
        Assert.assertEquals(userFollowee.following.size(), 5);
    }

    @Test
    public void shouldAbleToPostMediaContentForExistingUserWithExistingMediaPosts() {
        userFollowee.following.add("userFollowing1");
        userFollowee.following.add("userFollowing2");
        userFollowee.following.add("userFollowing3");
        userFollowee.following.add("userFollowing4");

        userFollowee.mediaPost = new MediaPost("latestPostId", "latestContent", LocalDateTime.now());

        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);
        mediaService.createPost(userIdFollowee, postIdFollowee, postContent);
        Assert.assertEquals(userFollowee.following.size(), 5);

        Assert.assertEquals(userFollowee.mediaPost.nextMediaPost.content, "latestContent");
        Assert.assertEquals(userFollowee.mediaPost.nextMediaPost.postId, "latestPostId");
    }

    @Test
    public void shouldSaveUserIfNotExists() {
        String userIdFollower = "userIdFollower";
        mediaService.createPost(userIdFollower, postIdFollowee, postContent);
        verify(userRepository, atLeastOnce()).saveUser(userIdFollower);
    }

    @Test
    public void shouldBeAbleToFetchUserFeedForExistingUserWithLessThanTwentyFeeds() throws ServiceException {
        userFollowee.following.add("userFollowing1");
        userFollowee.following.add("userFollowing2");
        userFollowee.following.add("userFollowing3");
        userFollowee.following.add("userFollowing4");

        userFollowee.mediaPost = new MediaPost("latestPostId", "latestContent", LocalDateTime.now());

        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);

        User userFollowing1 = new User("userFollowing1");
        User userFollowing2 = new User("userFollowing1");
        User userFollowing3 = new User("userFollowing1");
        User userFollowing4 = new User("userFollowing1");

        userFollowing1.mediaPost = new MediaPost("postIdFollowing1", "contentId1", LocalDateTime.now());
        userFollowing2.mediaPost = new MediaPost("postIdFollowing2", "contentId1", LocalDateTime.now());
        userFollowing3.mediaPost = new MediaPost("postIdFollowing3", "contentId1", LocalDateTime.now());
        userFollowing4.mediaPost = new MediaPost("postIdFollowing4", "contentId1", LocalDateTime.now());

        when(userRepository.findUserByUserId("userFollowing1")).thenReturn(userFollowing1);
        when(userRepository.findUserByUserId("userFollowing2")).thenReturn(userFollowing2);
        when(userRepository.findUserByUserId("userFollowing3")).thenReturn(userFollowing3);
        when(userRepository.findUserByUserId("userFollowing4")).thenReturn(userFollowing4);

        List<MediaPost> mediaPosts = mediaService.newsFeeds(userIdFollowee);
        Assert.assertEquals(mediaPosts.size(), 5);
    }

    @Test
    public void shouldReturnListOfSizeZeroUserFeedForExistingUserAndHisFollowingHasNoMediaFeed() throws ServiceException {
        userFollowee.following.add("userFollowing1");
        userFollowee.following.add("userFollowing2");
        userFollowee.following.add("userFollowing3");
        userFollowee.following.add("userFollowing4");

        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);

        User userFollowing1 = new User("userFollowing1");
        User userFollowing2 = new User("userFollowing1");
        User userFollowing3 = new User("userFollowing1");
        User userFollowing4 = new User("userFollowing1");

        when(userRepository.findUserByUserId("userFollowing1")).thenReturn(userFollowing1);
        when(userRepository.findUserByUserId("userFollowing2")).thenReturn(userFollowing2);
        when(userRepository.findUserByUserId("userFollowing3")).thenReturn(userFollowing3);
        when(userRepository.findUserByUserId("userFollowing4")).thenReturn(userFollowing4);

        List<MediaPost> mediaPosts = mediaService.newsFeeds(userIdFollowee);
        Assert.assertEquals(mediaPosts.size(), 0);
    }

    @Test
    public void shouldReturnListOfSizeZeroUserFeedForExistingUserHasNoFollowingAndHasNoMediaFeed() throws ServiceException {
        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);
        List<MediaPost> mediaPosts = mediaService.newsFeeds(userIdFollowee);
        Assert.assertEquals(mediaPosts.size(), 0);
    }

    @Test
    public void shouldBeAbleToFetchUserFeedForAnyUserHavingOnlyTwentyMediaPosts() throws ServiceException {
        userFollowee.following.add("userFollowing1");
        userFollowee.following.add("userFollowing2");
        userFollowee.following.add("userFollowing3");
        userFollowee.following.add("userFollowing4");

        getMediaPosts();
        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);

        User userFollowing1 = new User("userFollowing1");
        User userFollowing2 = new User("userFollowing1");
        User userFollowing3 = new User("userFollowing1");
        User userFollowing4 = new User("userFollowing1");


        when(userRepository.findUserByUserId("userFollowing1")).thenReturn(userFollowing1);
        when(userRepository.findUserByUserId("userFollowing2")).thenReturn(userFollowing2);
        when(userRepository.findUserByUserId("userFollowing3")).thenReturn(userFollowing3);
        when(userRepository.findUserByUserId("userFollowing4")).thenReturn(userFollowing4);

        List<MediaPost> mediaPosts = mediaService.newsFeeds(userIdFollowee);
        Assert.assertEquals(mediaPosts.size(), 20);
    }

    @Test
    public void shouldBeAbleToFetchUserFeedForAnyUserAmdHisFollowingUsersHavingMoreThanTwentyMediaPosts() throws ServiceException {
        getUserFollowingMediaPosts();
        List<MediaPost> mediaPosts = mediaService.newsFeeds(userIdFollowee);
        Assert.assertEquals(mediaPosts.size(), 20);
        Assert.assertEquals(mediaPosts.get(0).postId, "postIdFollowing20");
    }


    @Test(expected = ServiceException.class)
    public void shouldNotBeAbleToFetchUserFeedForNullUserID() throws ServiceException {
        mediaService.newsFeeds(null);
    }

    @Test(expected = ServiceException.class)
    public void shouldNotBeAbleToFetchUserFeedForBlankUserID() throws ServiceException {
        String blankString = " ";
        mediaService.newsFeeds(blankString);
    }

    @Test(expected = ServiceException.class)
    public void shouldNotBeAbleToFetchUserFeedForEmptyUserID() throws ServiceException {
        String emptyString = " ";
        mediaService.newsFeeds(emptyString);
    }

    private void getUserFollowingMediaPosts() {
        userFollowee.following.add("userFollowing1");
        userFollowee.following.add("userFollowing2");
        userFollowee.following.add("userFollowing3");
        userFollowee.following.add("userFollowing4");
        userFollowee.following.add("userFollowing5");
        userFollowee.following.add("userFollowing6");
        userFollowee.following.add("userFollowing7");
        userFollowee.following.add("userFollowing8");
        userFollowee.following.add("userFollowing9");
        userFollowee.following.add("userFollowing10");
        userFollowee.following.add("userFollowing11");
        userFollowee.following.add("userFollowing12");
        userFollowee.following.add("userFollowing13");
        userFollowee.following.add("userFollowing14");
        userFollowee.following.add("userFollowing15");
        userFollowee.following.add("userFollowing16");
        userFollowee.following.add("userFollowing17");
        userFollowee.following.add("userFollowing18");
        userFollowee.following.add("userFollowing19");
        userFollowee.following.add("userFollowing20");

        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);

        User userFollowing1 = new User("userFollowing1");
        User userFollowing2 = new User("userFollowing2");
        User userFollowing3 = new User("userFollowing3");
        User userFollowing4 = new User("userFollowing4");
        User userFollowing5 = new User("userFollowing5");
        User userFollowing6 = new User("userFollowing6");
        User userFollowing7 = new User("userFollowing7");
        User userFollowing8 = new User("userFollowing8");
        User userFollowing9 = new User("userFollowing9");
        User userFollowing10 = new User("userFollowing10");
        User userFollowing11 = new User("userFollowing11");
        User userFollowing12 = new User("userFollowing12");
        User userFollowing13 = new User("userFollowing13");
        User userFollowing14 = new User("userFollowing14");
        User userFollowing15 = new User("userFollowing15");
        User userFollowing16 = new User("userFollowing16");
        User userFollowing17 = new User("userFollowing17");
        User userFollowing18 = new User("userFollowing18");
        User userFollowing19 = new User("userFollowing19");
        User userFollowing20 = new User("userFollowing20");

        userFollowing1.mediaPost = new MediaPost("postIdFollowing1", "contentId1", LocalDateTime.now());
        userFollowing2.mediaPost = new MediaPost("postIdFollowing2", "contentId1", LocalDateTime.now());
        userFollowing3.mediaPost = new MediaPost("postIdFollowing3", "contentId1", LocalDateTime.now());
        userFollowing4.mediaPost = new MediaPost("postIdFollowing4", "contentId1", LocalDateTime.now());
        userFollowing5.mediaPost = new MediaPost("postIdFollowing5", "contentId1", LocalDateTime.now());
        userFollowing6.mediaPost = new MediaPost("postIdFollowing6", "contentId1", LocalDateTime.now());
        userFollowing7.mediaPost = new MediaPost("postIdFollowing7", "contentId1", LocalDateTime.now());
        userFollowing8.mediaPost = new MediaPost("postIdFollowing8", "contentId1", LocalDateTime.now());
        userFollowing9.mediaPost = new MediaPost("postIdFollowing9", "contentId1", LocalDateTime.now());
        userFollowing10.mediaPost = new MediaPost("postIdFollowing10", "contentId1", LocalDateTime.now());
        userFollowing11.mediaPost = new MediaPost("postIdFollowing11", "contentId1", LocalDateTime.now());
        userFollowing12.mediaPost = new MediaPost("postIdFollowing12", "contentId1", LocalDateTime.now());
        userFollowing13.mediaPost = new MediaPost("postIdFollowing13", "contentId1", LocalDateTime.now());
        userFollowing14.mediaPost = new MediaPost("postIdFollowing14", "contentId1", LocalDateTime.now());
        userFollowing15.mediaPost = new MediaPost("postIdFollowing15", "contentId1", LocalDateTime.now());
        userFollowing16.mediaPost = new MediaPost("postIdFollowing16", "contentId1", LocalDateTime.now());
        userFollowing17.mediaPost = new MediaPost("postIdFollowing17", "contentId1", LocalDateTime.now());
        userFollowing18.mediaPost = new MediaPost("postIdFollowing18", "contentId1", LocalDateTime.now());
        userFollowing19.mediaPost = new MediaPost("postIdFollowing18", "contentId1", LocalDateTime.now());
        userFollowing20.mediaPost = new MediaPost("postIdFollowing20", "contentId1", LocalDateTime.now());

        when(userRepository.findUserByUserId("userFollowing1")).thenReturn(userFollowing1);
        when(userRepository.findUserByUserId("userFollowing2")).thenReturn(userFollowing2);
        when(userRepository.findUserByUserId("userFollowing3")).thenReturn(userFollowing3);
        when(userRepository.findUserByUserId("userFollowing4")).thenReturn(userFollowing4);
        when(userRepository.findUserByUserId("userFollowing5")).thenReturn(userFollowing5);
        when(userRepository.findUserByUserId("userFollowing6")).thenReturn(userFollowing6);
        when(userRepository.findUserByUserId("userFollowing7")).thenReturn(userFollowing7);
        when(userRepository.findUserByUserId("userFollowing8")).thenReturn(userFollowing8);
        when(userRepository.findUserByUserId("userFollowing9")).thenReturn(userFollowing9);
        when(userRepository.findUserByUserId("userFollowing10")).thenReturn(userFollowing10);
        when(userRepository.findUserByUserId("userFollowing11")).thenReturn(userFollowing11);
        when(userRepository.findUserByUserId("userFollowing12")).thenReturn(userFollowing12);
        when(userRepository.findUserByUserId("userFollowing13")).thenReturn(userFollowing13);
        when(userRepository.findUserByUserId("userFollowing14")).thenReturn(userFollowing14);
        when(userRepository.findUserByUserId("userFollowing15")).thenReturn(userFollowing15);
        when(userRepository.findUserByUserId("userFollowing16")).thenReturn(userFollowing16);
        when(userRepository.findUserByUserId("userFollowing17")).thenReturn(userFollowing17);
        when(userRepository.findUserByUserId("userFollowing18")).thenReturn(userFollowing18);
        when(userRepository.findUserByUserId("userFollowing19")).thenReturn(userFollowing19);
        when(userRepository.findUserByUserId("userFollowing20")).thenReturn(userFollowing20);
    }

    private void getMediaPosts() {
        userFollowee.mediaPost = new MediaPost("latestPostId", "latestContent", LocalDateTime.now());

        userFollowee.mediaPost.nextMediaPost = new MediaPost("latestPostId1", "latestContent1",
                LocalDateTime.now());

        userFollowee.mediaPost.nextMediaPost.nextMediaPost = new MediaPost("latestPostId2",
                "latestContent2", LocalDateTime.now());

        userFollowee.mediaPost.nextMediaPost.nextMediaPost.nextMediaPost = new MediaPost("latestPostId3",

                "latestContent3", LocalDateTime.now());
        userFollowee.mediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost =
                new MediaPost("latestPostId4", "latestContent4", LocalDateTime.now());

        userFollowee.mediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost =
                new MediaPost("latestPostId5", "latestContent5", LocalDateTime.now());

        userFollowee.mediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost =
                new MediaPost("latestPostId6", "latestContent6", LocalDateTime.now());

        userFollowee.mediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.
                nextMediaPost = new MediaPost("latestPostId7", "latestContent7", LocalDateTime.now());

        userFollowee.mediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost
                .nextMediaPost.nextMediaPost = new MediaPost("latestPostId8", "latestContent8", LocalDateTime.now());

        userFollowee.mediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost
                .nextMediaPost.nextMediaPost.nextMediaPost = new MediaPost("latestPostId9", "latestContent9", LocalDateTime.now());

        userFollowee.mediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost
                .nextMediaPost.nextMediaPost = new MediaPost("latestPostId10", "latestContent10", LocalDateTime.now());

        userFollowee.mediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost
                .nextMediaPost.nextMediaPost.nextMediaPost = new MediaPost("latestPostId11", "latestContent11", LocalDateTime.now());

        userFollowee.mediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost
                .nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost = new MediaPost("latestPostId12", "latestContent12", LocalDateTime.now());

        userFollowee.mediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost
                .nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost = new MediaPost("latestPostId13",
                "latestContent13", LocalDateTime.now());

        userFollowee.mediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost
                .nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost = new MediaPost("latestPostId14",
                "latestContent14", LocalDateTime.now());

        userFollowee.mediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost
                .nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost = new MediaPost(
                "latestPostId15", "latestContent15", LocalDateTime.now());

        userFollowee.mediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost
                .nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost =
                new MediaPost("latestPostId16", "latestContent16", LocalDateTime.now());

        userFollowee.mediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost
                .nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost
                .nextMediaPost = new MediaPost("latestPostId17", "latestContent17", LocalDateTime.now());

        userFollowee.mediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost
                .nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost
                .nextMediaPost = new MediaPost("latestPostId18", "latestContent18", LocalDateTime.now());

        userFollowee.mediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost
                .nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost.nextMediaPost
                .nextMediaPost.nextMediaPost = new MediaPost("latestPostId19", "latestContent19", LocalDateTime.now());

    }
}
