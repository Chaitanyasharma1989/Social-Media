package com.csharma.socialmedia.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;


@Document(collection = "Users")
public class User {

    @Id
    private final String userId;
    private final List<User> following;
    private final List<MediaPost> posts;

    @SuppressWarnings("unused")
    private User() {
        this(null, null, null);
    }

    public User(final String userId,
                final List<User> following,
                final List<MediaPost> posts) {
        this.userId = userId;
        this.following = following;
        this.posts = posts;
    }

    public String getUserId() {
        return userId;
    }

    public List<User> getFollowing() {
        return following;
    }

    public List<MediaPost> getPosts() {
        return posts;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(following, user.following) && Objects.equals(posts, user.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, following, posts);
    }
}
