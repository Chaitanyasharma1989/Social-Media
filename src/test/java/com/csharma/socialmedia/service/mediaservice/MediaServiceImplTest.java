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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MediaServiceImplTest extends TestCase {

    @Mock
    private UserRepository userRepository;

    private User userFollowee;

    private List<User> users;

    private final String userIdFollowee = "userIdFollowee";
    private final String postIdFollowee = "postIdFollowee";
    private final String postContent = "postContent";
    private final String userIdFollower = "userIdFollower";

    private MediaServiceImpl mediaService;

    @Before
    public void setUp() {
        mediaService = new MediaServiceImpl(userRepository);

        users = new ArrayList<>();

        MediaPost mediaPost1 = new MediaPost("postId1", LocalDateTime.now().minusDays(30));
        MediaPost mediaPost2 = new MediaPost("postId2", LocalDateTime.now().minusDays(31));
        MediaPost mediaPost3 = new MediaPost("postId3", LocalDateTime.now().minusDays(32));
        MediaPost mediaPost4 = new MediaPost("postId4", LocalDateTime.now().minusDays(33));

        MediaPost mediaPost01 = new MediaPost("mediaPost1Minus1Day", LocalDateTime.now().minusDays(1));
        MediaPost mediaPost02 = new MediaPost("mediaPost2Minus2Day", LocalDateTime.now().minusDays(2));
        MediaPost mediaPost03 = new MediaPost("mediaPost3Minus3Day", LocalDateTime.now().minusDays(3));
        MediaPost mediaPost04 = new MediaPost("mediaPost4Minus4Day", LocalDateTime.now().minusDays(4));
        MediaPost mediaPost5 = new MediaPost("mediaPost5Minus5Day", LocalDateTime.now().minusDays(5));
        MediaPost mediaPost6 = new MediaPost("mediaPost6Minus6Day", LocalDateTime.now().minusDays(6));
        MediaPost mediaPost7 = new MediaPost("mediaPost7Minus7Day", LocalDateTime.now().minusDays(7));
        MediaPost mediaPost8 = new MediaPost("mediaPost8Minus8Day", LocalDateTime.now().minusDays(8));
        MediaPost mediaPost9 = new MediaPost("mediaPost9Minus9Day", LocalDateTime.now().minusDays(9));
        MediaPost mediaPost10 = new MediaPost("mediaPost10Minus10Day", LocalDateTime.now().minusDays(10));
        MediaPost mediaPost11 = new MediaPost("mediaPost11Minus11Day", LocalDateTime.now().minusDays(11));
        MediaPost mediaPost12 = new MediaPost("mediaPost12Minus12Day", LocalDateTime.now().minusDays(12));
        MediaPost mediaPost13 = new MediaPost("mediaPost13Minus13Day", LocalDateTime.now().minusDays(13));
        MediaPost mediaPost14 = new MediaPost("mediaPost14Minus14Day", LocalDateTime.now().minusDays(14));
        MediaPost mediaPost15 = new MediaPost("mediaPost15Minus15Day", LocalDateTime.now().minusDays(15));


        User userFollower1 = new User("userFollower1", Collections.emptyList(), Collections.singletonList(mediaPost1));
        User userFollower2 = new User("userFollower1", Collections.emptyList(), Collections.singletonList(mediaPost2));
        User userFollower3 = new User("userFollower3", Collections.emptyList(), Collections.singletonList(mediaPost3));
        User userFollower4 = new User("userFollower4", Collections.emptyList(), Collections.singletonList(mediaPost4));
        User userFollower5 = new User("userFollower5", Collections.emptyList(), Collections.singletonList(mediaPost01));
        User userFollower6 = new User("userFollower6", Collections.emptyList(), Collections.singletonList(mediaPost02));
        User userFollower7 = new User("userFollower7", Collections.emptyList(), Collections.singletonList(mediaPost03));
        User userFollower8 = new User("userFollower8", Collections.emptyList(), Collections.singletonList(mediaPost04));
        User userFollower9 = new User("userFollower9", Collections.emptyList(), Collections.singletonList(mediaPost5));
        User userFollower10 = new User("userFollower10", Collections.emptyList(), Collections.singletonList(mediaPost5));
        User userFollower11 = new User("userFollower11", Collections.emptyList(), Collections.singletonList(mediaPost6));
        User userFollower12 = new User("userFollower12", Collections.emptyList(), Collections.singletonList(mediaPost7));
        User userFollower13 = new User("userFollower13", Collections.emptyList(), Collections.singletonList(mediaPost8));
        User userFollower14 = new User("userFollower14", Collections.emptyList(), Collections.singletonList(mediaPost9));
        User userFollower15 = new User("userFollower15", Collections.emptyList(), Collections.singletonList(mediaPost10));
        User userFollower16 = new User("userFollower16", Collections.emptyList(), Collections.singletonList(mediaPost11));
        User userFollower17 = new User("userFollower17", Collections.emptyList(), Collections.singletonList(mediaPost12));
        User userFollower18 = new User("userFollower18", Collections.emptyList(), Collections.singletonList(mediaPost13));
        User userFollower19 = new User("userFollower19", Collections.emptyList(), Collections.singletonList(mediaPost14));
        User userFollower20 = new User("userFollower20", Collections.emptyList(), Collections.singletonList(mediaPost15));

        users.add(userFollower1);
        users.add(userFollower2);
        users.add(userFollower3);
        users.add(userFollower4);
        users.add(userFollower5);
        users.add(userFollower6);
        users.add(userFollower7);
        users.add(userFollower8);
        users.add(userFollower9);
        users.add(userFollower10);
        users.add(userFollower11);
        users.add(userFollower12);
        users.add(userFollower13);
        users.add(userFollower14);
        users.add(userFollower15);
        users.add(userFollower16);
        users.add(userFollower17);
        users.add(userFollower18);
        users.add(userFollower19);
        users.add(userFollower20);


        List<MediaPost> mediaPosts = new ArrayList<>();
        mediaPosts.add(mediaPost1);
        mediaPosts.add(mediaPost2);
        mediaPosts.add(mediaPost3);
        mediaPosts.add(mediaPost4);

        userFollowee = new User(userIdFollowee, users, mediaPosts);
    }

    @Test
    public void shouldAbleToPostContentForExistingUser() throws ServiceException {
        int numberOfPostAfterAdditionOfNewPost = userFollowee.getPosts().size() + 1;
        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);
        mediaService.createPost(userIdFollowee, postIdFollowee, postContent);
        Assert.assertEquals(numberOfPostAfterAdditionOfNewPost, userFollowee.getPosts().size());
    }

    @Test(expected = ServiceException.class)
    public void shouldNotAbleToPostForNoExistingUser() throws ServiceException {
        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);
        mediaService.createPost(userIdFollower, postIdFollowee, postContent);
    }

    @Test
    public void shouldBeAbleToFetchUserFeedForExistingUserWithLessThanTwentyFeeds() throws ServiceException {
        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);
        List<MediaPost> mediaPosts = mediaService.newsFeeds(userIdFollowee);
        Assert.assertNotEquals(mediaPosts.size(), userFollowee.getPosts().size());
        Assert.assertNotNull(mediaPosts);
    }

    @Test(expected = ServiceException.class)
    public void shouldNotBeAbleToFetchUserFeedForNullUserID() throws ServiceException {
        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);
        List<MediaPost> mediaPosts = mediaService.newsFeeds(null);
    }

    @Test(expected = ServiceException.class)
    public void shouldNotBeAbleToFetchUserFeedForBlankUserID() throws ServiceException {
        String blankString = " ";
        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);
        List<MediaPost> mediaPosts = mediaService.newsFeeds(blankString);
    }

    @Test(expected = ServiceException.class)
    public void shouldNotBeAbleToFetchUserFeedForEmptyUserID() throws ServiceException {
        String emptyString = " ";
        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);
        List<MediaPost> mediaPosts = mediaService.newsFeeds(emptyString);
    }

    @Test
    public void shouldBeAbleToFetchOnlyTwentyUserFeedForExistingUserIfOnlyFolloweeHasMoreThanLimitFeed() throws ServiceException {
        getUserWithMoreThan20Feeds();
        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);
        List<MediaPost> mediaPosts = mediaService.newsFeeds(userIdFollowee);

        Assert.assertNotNull(mediaPosts);
        Assert.assertNotEquals(mediaPosts.size(), userFollowee.getPosts().size());
        Assert.assertEquals(mediaPosts.get(0).getInsertTimeStamp().getDayOfMonth(), LocalDateTime.now().minusDays(1).getDayOfMonth());
        Assert.assertEquals(mediaPosts.get(19).getInsertTimeStamp().getDayOfMonth(), LocalDateTime.now().minusDays(10).getDayOfMonth());
    }

    @Test
    public void shouldBeAbleToFetchOnlyTwentyUserFeedForExistingUserIfFollowerHasMoreThan20Feeds() throws ServiceException {
        userFollowee.getPosts().clear();
        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);
        List<MediaPost> mediaPosts = mediaService.newsFeeds(userIdFollowee);

        Assert.assertNotNull(mediaPosts);
        Assert.assertNotEquals(mediaPosts.size(), userFollowee.getPosts().size());
        Assert.assertEquals(mediaPosts.get(0).getInsertTimeStamp().getDayOfMonth(), LocalDateTime.now().minusDays(1).getDayOfMonth());
    }

    @Test
    public void shouldBeAbleToFetchOnlyTwentyUserFeedForExistingUserIFBothFolloweeAndFollowerHasMoreThanLimit() throws ServiceException {
        MediaPost mediaPost1 = new MediaPost("mediaPost1PresentDay", LocalDateTime.now());
        MediaPost mediaPost2 = new MediaPost("mediaPostMinus20days", LocalDateTime.now().minusDays(20));
        MediaPost mediaPost3 = new MediaPost("mediaPostMinus21days", LocalDateTime.now().minusDays(21));
        MediaPost mediaPost4 = new MediaPost("mediaPostMinus22days", LocalDateTime.now().minusDays(22));
        MediaPost mediaPost5 = new MediaPost("mediaPostMinus23days", LocalDateTime.now().minusDays(23));
        MediaPost mediaPost6 = new MediaPost("mediaPostMinus24days", LocalDateTime.now().minusDays(24));
        MediaPost mediaPost7 = new MediaPost("mediaPostMinus25days", LocalDateTime.now().minusDays(25));
        MediaPost mediaPost8 = new MediaPost("mediaPostMinus26days", LocalDateTime.now().minusDays(26));
        MediaPost mediaPost9 = new MediaPost("mediaPostMinus27days", LocalDateTime.now().minusDays(27));
        MediaPost mediaPost10 = new MediaPost("mediaPostMinus28days", LocalDateTime.now().minusDays(28));
        MediaPost mediaPost11 = new MediaPost("mediaPostMinus29days", LocalDateTime.now().minusDays(29));
        MediaPost mediaPost12 = new MediaPost("mediaPostMinus30days", LocalDateTime.now().minusDays(30));
        MediaPost mediaPost13 = new MediaPost("mediaPostMinus31days", LocalDateTime.now().minusDays(31));
        MediaPost mediaPost14 = new MediaPost("mediaPostMinus30days", LocalDateTime.now().minusDays(32));


        userFollowee.getPosts().add(mediaPost1);
        userFollowee.getPosts().add(mediaPost2);
        userFollowee.getPosts().add(mediaPost3);
        userFollowee.getPosts().add(mediaPost4);
        userFollowee.getPosts().add(mediaPost5);
        userFollowee.getPosts().add(mediaPost6);
        userFollowee.getPosts().add(mediaPost7);
        userFollowee.getPosts().add(mediaPost8);
        userFollowee.getPosts().add(mediaPost9);
        userFollowee.getPosts().add(mediaPost10);
        userFollowee.getPosts().add(mediaPost11);
        userFollowee.getPosts().add(mediaPost12);
        userFollowee.getPosts().add(mediaPost13);
        userFollowee.getPosts().add(mediaPost14);

        when(userRepository.findUserByUserId(userIdFollowee)).thenReturn(userFollowee);
        List<MediaPost> mediaPosts = mediaService.newsFeeds(userIdFollowee);

        Assert.assertEquals(mediaPosts.get(0).getInsertTimeStamp().getDayOfMonth(), LocalDateTime.now().getDayOfMonth());
        Assert.assertNotEquals(mediaPosts.size(), userFollowee.getPosts().size());
        Assert.assertNotNull(mediaPosts);
    }


    private void getUserWithMoreThan20Feeds() {
        MediaPost mediaPost1 = new MediaPost("mediaPost1Minus1Day", LocalDateTime.now().minusDays(1));
        MediaPost mediaPost2 = new MediaPost("mediaPost2Minus2Day", LocalDateTime.now().minusDays(2));
        MediaPost mediaPost3 = new MediaPost("mediaPost3Minus3Day", LocalDateTime.now().minusDays(3));
        MediaPost mediaPost4 = new MediaPost("mediaPost4Minus4Day", LocalDateTime.now().minusDays(4));
        MediaPost mediaPost5 = new MediaPost("mediaPost5Minus5Day", LocalDateTime.now().minusDays(5));
        MediaPost mediaPost6 = new MediaPost("mediaPost6Minus6Day", LocalDateTime.now().minusDays(6));
        MediaPost mediaPost7 = new MediaPost("mediaPost7Minus7Day", LocalDateTime.now().minusDays(7));
        MediaPost mediaPost8 = new MediaPost("mediaPost8Minus8Day", LocalDateTime.now().minusDays(8));
        MediaPost mediaPost9 = new MediaPost("mediaPost9Minus9Day", LocalDateTime.now().minusDays(9));
        MediaPost mediaPost10 = new MediaPost("mediaPost10Minus10Day", LocalDateTime.now().minusDays(10));
        MediaPost mediaPost11 = new MediaPost("mediaPost11Minus11Day", LocalDateTime.now().minusDays(11));
        MediaPost mediaPost12 = new MediaPost("mediaPost12Minus12Day", LocalDateTime.now().minusDays(12));
        MediaPost mediaPost13 = new MediaPost("mediaPost13Minus13Day", LocalDateTime.now().minusDays(13));
        MediaPost mediaPost14 = new MediaPost("mediaPost14Minus14Day", LocalDateTime.now().minusDays(14));
        MediaPost mediaPost15 = new MediaPost("mediaPost15Minus15Day", LocalDateTime.now().minusDays(15));
        MediaPost mediaPost16 = new MediaPost("mediaPost16Minus16Day", LocalDateTime.now().minusDays(16));
        MediaPost mediaPost17 = new MediaPost("mediaPost17Minus17Day", LocalDateTime.now().minusDays(17));
        MediaPost mediaPost18 = new MediaPost("mediaPost18Minus18Day", LocalDateTime.now().minusDays(18));

        userFollowee.getPosts().add(mediaPost1);
        userFollowee.getPosts().add(mediaPost2);
        userFollowee.getPosts().add(mediaPost3);
        userFollowee.getPosts().add(mediaPost4);
        userFollowee.getPosts().add(mediaPost5);
        userFollowee.getPosts().add(mediaPost6);
        userFollowee.getPosts().add(mediaPost7);
        userFollowee.getPosts().add(mediaPost8);
        userFollowee.getPosts().add(mediaPost9);
        userFollowee.getPosts().add(mediaPost10);
        userFollowee.getPosts().add(mediaPost11);
        userFollowee.getPosts().add(mediaPost12);
        userFollowee.getPosts().add(mediaPost13);
        userFollowee.getPosts().add(mediaPost14);
        userFollowee.getPosts().add(mediaPost15);
        userFollowee.getPosts().add(mediaPost16);
        userFollowee.getPosts().add(mediaPost17);
        userFollowee.getPosts().add(mediaPost18);
    }
}