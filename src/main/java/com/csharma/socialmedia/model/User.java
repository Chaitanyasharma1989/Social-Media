package com.csharma.socialmedia.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User {

    public String userId;
    public Set<String> following;
    public MediaPost mediaPost;

    public User(final String userId) {
        this.userId = userId;
        this.following = new HashSet<>();
        follow(userId);
        this.mediaPost = null;
    }

    public void follow(String userId) {
        following.add(userId);
    }

    public void unfollow(String userId) {
        following.remove(userId);
    }

    public void post(String postId, String content) {
        MediaPost mediaPost = new MediaPost(postId, content, LocalDateTime.now());
        mediaPost.nextMediaPost = this.mediaPost;
        this.mediaPost = mediaPost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(following, user.following) && Objects.equals(mediaPost, user.mediaPost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, following, mediaPost);
    }
}
