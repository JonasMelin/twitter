package com.twitterstuff.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import twitter4j.PagableResponseList;
import twitter4j.User;

import java.util.LinkedList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class FollowerListDTO extends BaseResponseDTO {

    public FollowerListDTO(String message, int httpStatus) {
        super(message, httpStatus);
    }

    public FollowerListDTO(PagableResponseList<User> userList) {
        super();
        userList.forEach(u -> followers.add(new UserData(u.getName(), u.getScreenName(), u.getId())));
    }

    @NonNull
    private List<UserData> followers = new LinkedList<>();

    @Data
    @AllArgsConstructor
    private static class UserData {
        @ApiModelProperty(example = "Financial Times")
        private String userName;
        @ApiModelProperty(example = "@financialtimes")
        private String screenName;
        @ApiModelProperty(example = "1252488423")
        private Long userId;
    }
}
