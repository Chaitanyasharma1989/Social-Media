package com.csharma.socialmedia.service.userservice;

import com.csharma.socialmedia.exception.ServiceException;

public interface UserService {

    void followUser(String followerId, String followeeId) throws ServiceException;

    void unFollowUser(String followerId, String followeeId) throws ServiceException;
}
