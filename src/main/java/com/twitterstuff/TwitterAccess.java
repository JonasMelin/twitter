package com.twitterstuff;

import com.twitterstuff.model.FollowerListDTO;
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
    final static int MAX_ALLOWED_PAGES = 20;

    @Autowired
    private Twitter twitter;

    public FollowerListDTO getFollowersOfUser(String user) throws TwitterException {
        return new FollowerListDTO(twitter.getFollowersList(user, -1));
    }

    public void followUser(String user) throws TwitterException {
        twitter.createFriendship(user);
    }

    public void unfollowUser(String user) throws TwitterException {
        twitter.destroyFriendship(user);
    }

    public List<String> getTweets(String user, int limit) throws TwitterException {

        List<String> allTweets = new LinkedList<>();
        Paging paging = new Paging(1, PAGE_SIZE);

        while(paging.getPage() <= MAX_ALLOWED_PAGES){
            ResponseList<Status> userTimeline = twitter.getUserTimeline(user, paging);
            List<String> fetechedTweets = userTimeline.stream().map(Status::getText).collect(Collectors.toList());

            allTweets.addAll(fetechedTweets.subList(0, Math.min(limit - allTweets.size(), fetechedTweets.size())));

            if (fetechedTweets.size() == 0 || allTweets.size() >= limit){
                break;
            }

            paging.setPage(paging.getPage() + 1);
        }

        return allTweets;
    }
}
