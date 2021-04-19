package com.twitterstuff;

import com.twitterstuff.model.BaseResponseDTO;
import com.twitterstuff.model.FollowerListDTO;
import com.twitterstuff.model.TweetsListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TwitterAccess {

    final static int PAGE_SIZE = 50;

    @Autowired
    private Twitter twitter;

    public FollowerListDTO getFollowersOfUser(String user) {
        try {
            return new FollowerListDTO(twitter.getFollowersList(user, -1));
        } catch (TwitterException twEx) {
            return new FollowerListDTO(twEx.getMessage(), twEx.getStatusCode());
        }
    }

    public BaseResponseDTO followUser(String user) {
        try {
            twitter.createFriendship(user);
            return new BaseResponseDTO();
        } catch (TwitterException twEx) {
            return new BaseResponseDTO(twEx.toString(), twEx.getStatusCode());
        }
    }

    public BaseResponseDTO unfollowUser(String user) {
        try {
            twitter.destroyFriendship(user);
            return new BaseResponseDTO();
        } catch (TwitterException twEx) {
            return new BaseResponseDTO(twEx.toString(), twEx.getStatusCode());
        }
    }

    public TweetsListDTO getTweets(String user, int limit) {

        List<String> allTweets = new LinkedList<>();
        Paging paging = new Paging(1, PAGE_SIZE);

        try {

            while (true) {
                ResponseList<Status> userTimeline = twitter.getUserTimeline(user, paging);
                List<String> nextPage = userTimeline.stream().map(Status::getText).collect(Collectors.toList());

                allTweets.addAll(nextPage.subList(0, Math.min(limit - allTweets.size(), nextPage.size())));

                if (nextPage.size() == 0 || allTweets.size() >= limit) {
                    break;
                }

                paging.setPage(paging.getPage() + 1);
            }

            return new TweetsListDTO(allTweets);

        } catch (TwitterException twEx) {
            return new TweetsListDTO(twEx.getMessage(), twEx.getErrorCode());
        }
    }
}
