package com.twitterstuff;

import com.twitterstuff.model.FollowerListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterException;

@Service
@Slf4j
public class TwitterAccess {

    public FollowerListDTO getFollowersOfUser(String user) throws TwitterException{
        return new FollowerListDTO(twitter.getFollowersList(user, -1));
    }

    @Autowired
    private Twitter twitter;
}
