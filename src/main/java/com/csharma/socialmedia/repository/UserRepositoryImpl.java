package com.csharma.socialmedia.repository;

import com.csharma.socialmedia.model.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserRepositoryImpl implements UserRepository {

    public Map<String, User> userMap = new HashMap<>();

    @Override
    public void saveUser(final String userId) {
        if (null != userId && !userId.isEmpty()) {
            this.userMap.put(userId, new User(userId));
        }
    }

    @Override
    public User findUserByUserId(String userId) {
        if (null != userId && !userId.isEmpty() && userMap.containsKey(userId)) {
            return this.userMap.get(userId);
        }
        return null;
    }

}
