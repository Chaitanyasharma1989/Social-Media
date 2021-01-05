package com.csharma.socialmedia.repository;

import com.csharma.socialmedia.model.User;

public interface UserRepository {
    User findUserByUserId(final String userId);

    void saveUser(final String userId);
}
