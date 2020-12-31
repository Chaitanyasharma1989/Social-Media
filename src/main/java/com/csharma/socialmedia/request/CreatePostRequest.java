package com.csharma.socialmedia.request;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class CreatePostRequest {

    @NotNull(message = "{UserId should not be null}")
    @NotEmpty(message = "{UserId should not be empty}")
    @NotBlank(message = "{UserId should not be blank}")
    private final String userId;

    @NotNull(message = "{PostId should not be null}")
    @NotEmpty(message = "{PostId should not be empty}")
    @NotBlank(message = "{PostId should not be blank}")
    private final String postId;

    @NotNull(message = "{Content should not be null}")
    @NotEmpty(message = "{Content should not be empty}")
    @NotBlank(message = "{Content should not be blank}")
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
