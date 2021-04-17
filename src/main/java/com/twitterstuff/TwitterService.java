package com.twitterstuff;

import com.twitterstuff.model.FollowerListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.TwitterException;

@Service
public class TwitterService {

    @Autowired
    private TwitterAccess twitterAccess;

    public FollowerListDTO getFollowersOfUser(String user) throws TwitterException {
        return twitterAccess.getFollowersOfUser(user);
    }
}
