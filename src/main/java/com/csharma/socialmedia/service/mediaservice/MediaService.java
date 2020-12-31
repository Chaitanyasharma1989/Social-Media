package com.csharma.socialmedia.service.mediaservice;

import com.csharma.socialmedia.exception.ServiceException;
import com.csharma.socialmedia.model.MediaPost;

import java.util.List;

public interface MediaService {

    void createPost(final String userId, final String postId, final String content) throws ServiceException;

    List<MediaPost> newsFeeds(final String userId) throws ServiceException;
}
