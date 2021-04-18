package com.twitterstuff;

import com.twitterstuff.model.FollowerListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.TwitterException;

import java.util.List;

@Service
public class TwitterService {

    @Autowired
    private TwitterAccess twitterAccess;

    public FollowerListDTO getFollowersOfUser(String user) throws TwitterException {
        return twitterAccess.getFollowersOfUser(user);
    }

    public void followUser(String user) throws TwitterException{
        twitterAccess.followUser(user);
    }

    public void unfollowUser(String user) throws TwitterException{
        twitterAccess.unfollowUser(user);
    }

    public List<String> getTweets(String user, int limit) throws  TwitterException {
        return twitterAccess.getTweets(user, limit);
    }
}
