package com.twitterstuff;

import com.twitterstuff.model.BaseResponseDTO;
import com.twitterstuff.model.FollowerListDTO;
import com.twitterstuff.model.TweetsListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TwitterService {

    @Autowired
    private TwitterRepository twitterRepository;

    public FollowerListDTO getFollowersOfUser(String user) {
        return twitterRepository.getFollowersOfUser(user);
    }

    public BaseResponseDTO followUser(String user) {
        return twitterRepository.followUser(user);
    }

    public BaseResponseDTO unfollowUser(String user) {
        return twitterRepository.unfollowUser(user);
    }

    public TweetsListDTO getTweets(String user, int limit) {
        return twitterRepository.getTweets(user, limit);
    }
}
