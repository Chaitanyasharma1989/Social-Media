package com.csharma.socialmedia.request;

public class CreatePostRequest {

    private final String userId;
    private final String postId;
    private final String content;

    @SuppressWarnings("unused")
    private CreatePostRequest() {
        this(null, null, null);
    }


    public CreatePostRequest(final String userId,
                             final String postId,
                             final String content) {
        this.userId = userId;
        this.postId = postId;
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public String getPostId() {
        return postId;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "CreatePostRequest{" +
                "userId='" + userId + '\'' +
                ", postId='" + postId + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
