package com.csharma.socialmedia.model;

import java.time.LocalDateTime;
import java.util.Objects;


public class MediaPost {

    public static int counter = 0;

    public final String postId;
    public String content;
    public int postCounter;
    public MediaPost nextMediaPost;
    public final LocalDateTime insertTimeStamp;

    @SuppressWarnings("unused")
    private MediaPost() {
        this(null, null, null);
    }

    public MediaPost(final String postId,
                     final String content,
                     final LocalDateTime insertTimeStamp) {
        this.postId = postId;
        this.content = content;
        this.nextMediaPost = null;
        this.postCounter = counter++;
        this.insertTimeStamp = insertTimeStamp;
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
