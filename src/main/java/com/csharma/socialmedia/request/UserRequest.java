package com.csharma.socialmedia.request;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class UserRequest {

    @NotNull(message = "{FollowerId should not be null}")
    @NotBlank(message = "{FollowerId should not be blank}")
    @NotEmpty(message = "{FollowerId should not be empty}")
    private final String followerId;


    @NotNull(message = "FolloweeId should not be null")
    @NotBlank(message = "FolloweeId should not be blank")
    @NotEmpty(message = "FolloweeId should not be empty")
    private final String followeeId;

    @SuppressWarnings("unused")
    private UserRequest() {
        this(null, null);
    }

    public UserRequest(final String followerId, final String followeeId) {
        this.followerId = followerId;
        this.followeeId = followeeId;
    }

    public String getFollowerId() {
        return followerId;
    }

    public String getFolloweeId() {
        return followeeId;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "followerId='" + followerId + '\'' +
                ", followeeId='" + followeeId + '\'' +
                '}';
    }
}
