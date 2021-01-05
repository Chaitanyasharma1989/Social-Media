package com.csharma.socialmedia.controller;

import com.csharma.socialmedia.model.MediaPost;
import com.csharma.socialmedia.request.CreatePostRequest;
import com.csharma.socialmedia.service.mediaservice.MediaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(controllers = MediaController.class)
public class MediaControllerTest extends TestCase {

    @Mock
    private MediaService mediaService;

    @InjectMocks
    private MediaController mediaController;

    private MockMvc mockMvc;
    private String response;
    private ObjectMapper objectMapper;

    private final String userId = "userId";
    private final String postId = "postId";
    private final String content = "content";

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        SimpleModule timeModule = new JavaTimeModule();
        objectMapper.registerModule(timeModule);

        mockMvc = MockMvcBuilders.standaloneSetup(mediaController).build();
    }

    @Test
    public void shouldPostMediaContentForExistingUser() throws Exception {
        String urlTemplate = "/media/user-post";
        CreatePostRequest createPostRequest = new CreatePostRequest(userId, postId, content);

        this.mockMvc.perform(post(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createPostRequest)))
                .andExpect(status().isOk())
                .andDo(rs -> response = rs.getResponse().getContentAsString());
    }

    @Test
    public void shouldNotPostMediaContentForExistingUserIfUserIdIsNull() throws Exception {
        String urlTemplate = "/media/user-post";
        CreatePostRequest createPostRequest = new CreatePostRequest(null, postId, content);

        this.mockMvc.perform(post(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createPostRequest)))
                .andExpect(status().isBadRequest())
                .andDo(rs -> response = rs.getResponse().getContentAsString());
    }

    @Test
    public void shouldNotPostMediaContentForExistingUserIfPostIdIsNull() throws Exception {
        String urlTemplate = "/media/user-post";
        CreatePostRequest createPostRequest = new CreatePostRequest(userId, null, content);

        this.mockMvc.perform(post(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createPostRequest)))
                .andExpect(status().isBadRequest())
                .andDo(rs -> response = rs.getResponse().getContentAsString());
    }

    @Test
    public void shouldNotPostMediaContentForExistingUserIfContentIdIsNull() throws Exception {
        String urlTemplate = "/media/user-post";
        CreatePostRequest createPostRequest = new CreatePostRequest(userId, postId, null);

        this.mockMvc.perform(post(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createPostRequest)))
                .andExpect(status().isBadRequest())
                .andDo(rs -> response = rs.getResponse().getContentAsString());
    }

    @Test
    public void shouldNotPostMediaContentForExistingUserIfAllThreeParametersAreNull() throws Exception {
        String urlTemplate = "/media/user-post";
        CreatePostRequest createPostRequest = new CreatePostRequest(null, null, null);

        this.mockMvc.perform(post(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createPostRequest)))
                .andExpect(status().isBadRequest())
                .andDo(rs -> response = rs.getResponse().getContentAsString());
    }

    @Test
    public void shouldNotPostMediaContentForExistingUserIfUserIdIsEmpty() throws Exception {
        String urlTemplate = "/media/user-post";
        CreatePostRequest createPostRequest = new CreatePostRequest("", postId, content);

        this.mockMvc.perform(post(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createPostRequest)))
                .andExpect(status().isBadRequest())
                .andDo(rs -> response = rs.getResponse().getContentAsString());
    }

    @Test
    public void shouldNotPostMediaContentForExistingUserIfPostIdIsEmpty() throws Exception {
        String urlTemplate = "/media/user-post";
        CreatePostRequest createPostRequest = new CreatePostRequest(userId, "", content);

        this.mockMvc.perform(post(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createPostRequest)))
                .andExpect(status().isBadRequest())
                .andDo(rs -> response = rs.getResponse().getContentAsString());
    }

    @Test
    public void shouldNotPostMediaContentForExistingUserIfContentIdIsEmpty() throws Exception {
        String urlTemplate = "/media/user-post";
        CreatePostRequest createPostRequest = new CreatePostRequest(userId, postId, "");

        this.mockMvc.perform(post(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createPostRequest)))
                .andExpect(status().isBadRequest())
                .andDo(rs -> response = rs.getResponse().getContentAsString());
    }

    @Test
    public void shouldNotPostMediaContentForExistingUserIfAllThreeParametersAreEmpty() throws Exception {
        String urlTemplate = "/media/user-post";
        CreatePostRequest createPostRequest = new CreatePostRequest("", "", "");

        this.mockMvc.perform(post(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createPostRequest)))
                .andExpect(status().isBadRequest())
                .andDo(rs -> response = rs.getResponse().getContentAsString());
        System.out.println(response);
    }

    @Test
    public void shouldAbleToFetchTop20NewsFeedForExistingUser() throws Exception {
        String urlTemplate = "/media/user-posts/{userId}";
        when(mediaService.newsFeeds(userId)).thenReturn(Collections.singletonList(new MediaPost(postId, content, LocalDateTime.now())));
        this.mockMvc.perform(get(urlTemplate, userId))
                .andExpect(status().isOk())
                .andDo(rs -> response = rs.getResponse().getContentAsString());
        List<MediaPost> mediaPosts = objectMapper.readValue(response, objectMapper.getTypeFactory().constructCollectionType(List.class, MediaPost.class));
        Assert.assertNotNull(mediaPosts);
    }

    @Test
    public void shouldNotBeAbleToFetchTop20NewsFeedForNonExistingUser() throws Exception {
        String urlTemplate = "/media/user-posts/{userId}";
        when(mediaService.newsFeeds(userId)).thenReturn(Collections.emptyList());
        this.mockMvc.perform(get(urlTemplate, userId))
                .andExpect(status().isOk())
                .andDo(rs -> response = rs.getResponse().getContentAsString());
        List<MediaPost> myObjects = objectMapper.readValue(response, objectMapper.getTypeFactory().constructCollectionType(List.class, MediaPost.class));
        assertEquals(myObjects.size(), 0);
    }

    @Test
    public void shouldNotBeAbleToFetchNewsFeedForNullUserId() throws Exception {
        String urlTemplate = "/media/user-posts/{userId}";

        this.mockMvc.perform(get(urlTemplate, ""))
                .andExpect(status().isNotFound())
                .andDo(rs -> response = rs.getResponse().getContentAsString());
    }

}