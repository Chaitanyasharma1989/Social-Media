package com.csharma.socialmedia.service.userservice;

public interface UserService {

    void followUser(String followerId, String followeeId);

    void unFollowUser(String followerId, String followeeId);
}
