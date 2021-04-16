package com.twitterstuff;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.*;

@Service
@Slf4j
public class TwitterAccess {

    @Autowired
    private Twitter twitter;

    public void getFollowersOfUser(String user) throws TwitterException {

        PagableResponseList<User> followersList = twitter.getFollowersList("@melonjonas", 0);

        log.info("hello");
    }
}
