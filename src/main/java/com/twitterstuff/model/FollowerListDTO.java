package com.twitterstuff.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import twitter4j.PagableResponseList;
import twitter4j.User;

import java.util.LinkedList;
import java.util.List;

@Data
public class FollowerListDTO {
    public FollowerListDTO(PagableResponseList<User> userList){
        userList.forEach(u -> screen.add(new UserData(u.getName(), u.getScreenName(), u.getId())));
    }

    @NonNull
    private List<UserData> screen = new LinkedList<>();

    @Data
    @AllArgsConstructor
    private static class UserData{
        private String userName;
        private String screenName;
        private Long userId;
    }
}
