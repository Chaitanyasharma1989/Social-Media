package com.csharma.socialmedia.controller;

import com.csharma.socialmedia.request.UserRequest;
import com.csharma.socialmedia.service.userservice.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest extends TestCase {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private final String userIdFollower = "userIdFollower";
    private final String userIdFollowee = "userIdFollowee";


    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
        SimpleModule timeModule = new JavaTimeModule();
        objectMapper.registerModule(timeModule);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void shouldAbleToFollowUserForExistingUser() throws Exception {
        String urlTemplate = "/user/follow";
        UserRequest userRequest = new UserRequest(userIdFollower, userIdFollowee);

        doNothing().when(userService).followUser(userIdFollower, userIdFollowee);

        this.mockMvc.perform(put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotAbleToFollowUserForNullFollowerId() throws Exception {
        String urlTemplate = "/user/follow";
        UserRequest userRequest = new UserRequest(null, userIdFollowee);

        this.mockMvc.perform(put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotAbleToFollowUserForNullFolloweeId() throws Exception {
        String urlTemplate = "/user/follow";
        UserRequest userRequest = new UserRequest(userIdFollower, null);

        this.mockMvc.perform(put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userRequest)))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void shouldNotAbleToFollowUserForEmptyFollowerId() throws Exception {
        String urlTemplate = "/user/follow";
        UserRequest userRequest = new UserRequest("", userIdFollowee);

        this.mockMvc.perform(put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotAbleToFollowUserForEmptyFolloweeId() throws Exception {
        String urlTemplate = "/user/follow";
        UserRequest userRequest = new UserRequest(userIdFollower, "");

        this.mockMvc.perform(put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotAbleToFollowUserForBlankFollowerId() throws Exception {
        String urlTemplate = "/user/follow";
        UserRequest userRequest = new UserRequest(" ", userIdFollowee);

        this.mockMvc.perform(put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotAbleToFollowUserForBlankFolloweeId() throws Exception {
        String urlTemplate = "/user/follow";
        UserRequest userRequest = new UserRequest(userIdFollower, " ");

        this.mockMvc.perform(put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldAbleToUnFollowUserForExistingUser() throws Exception {
        String urlTemplate = "/user/follow";
        UserRequest userRequest = new UserRequest(userIdFollower, userIdFollowee);


        this.mockMvc.perform(put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotAbleToUnFollowUserForNullFollowerId() throws Exception {
        String urlTemplate = "/user/follow";
        UserRequest userRequest = new UserRequest(null, userIdFollowee);

        this.mockMvc.perform(put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotAbleToUnFollowUserForNullFolloweeId() throws Exception {
        String urlTemplate = "/user/follow";
        UserRequest userRequest = new UserRequest(userIdFollower, null);

        this.mockMvc.perform(put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotAbleToUnFollowUserForEmptyFollowerId() throws Exception {
        String urlTemplate = "/user/follow";
        UserRequest userRequest = new UserRequest("", userIdFollowee);

        this.mockMvc.perform(put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotAbleToUnFollowUserForEmptyFolloweeId() throws Exception {
        String urlTemplate = "/user/follow";
        UserRequest userRequest = new UserRequest(userIdFollower, "");

        this.mockMvc.perform(put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotAbleToUnFollowUserForBlankFollowerId() throws Exception {
        String urlTemplate = "/user/follow";
        UserRequest userRequest = new UserRequest(" ", userIdFollowee);

        this.mockMvc.perform(put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldNotAbleToUnFollowUserForBlankFolloweeId() throws Exception {
        String urlTemplate = "/user/follow";
        UserRequest userRequest = new UserRequest(userIdFollower, " ");

        this.mockMvc.perform(put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userRequest)))
                .andExpect(status().isBadRequest());
    }
}