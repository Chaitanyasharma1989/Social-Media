package com.csharma.socialmedia.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Objects;


@Document(collection = "MediaPosts")
public class MediaPost {

    @Id
    private final String postId;

    private final LocalDateTime insertTimeStamp;

    @SuppressWarnings("unused")
    private MediaPost() {
        this(null, null);
    }

    public MediaPost(final String postId,
                     final LocalDateTime insertTimeStamp) {
        this.postId = postId;
        this.insertTimeStamp = insertTimeStamp;
    }

    public String getPostId() {
        return postId;
    }

    public LocalDateTime getInsertTimeStamp() {
        return insertTimeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MediaPost mediaPost = (MediaPost) o;
        return Objects.equals(postId, mediaPost.postId) && Objects.equals(insertTimeStamp, mediaPost.insertTimeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, insertTimeStamp);
    }
}
