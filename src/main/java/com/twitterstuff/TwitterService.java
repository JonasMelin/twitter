package com.twitterstuff;

import com.twitterstuff.model.BaseResponseDTO;
import com.twitterstuff.model.FollowerListDTO;
import com.twitterstuff.model.TweetsListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TwitterService {

    @Autowired
    private TwitterAccess twitterAccess;

    public FollowerListDTO getFollowersOfUser(String user) {
        return twitterAccess.getFollowersOfUser(user);
    }

    public BaseResponseDTO followUser(String user) {
        return twitterAccess.followUser(user);
    }

    public BaseResponseDTO unfollowUser(String user) {
        return twitterAccess.unfollowUser(user);
    }

    public TweetsListDTO getTweets(String user, int limit) {
        return twitterAccess.getTweets(user, limit);
    }
}
