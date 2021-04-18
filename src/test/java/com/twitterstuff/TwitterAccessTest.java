package com.twitterstuff;

import com.twitterstuff.config.TwitterConfig;
import com.twitterstuff.model.FollowerListDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TwitterAccessTest {

    @InjectMocks
    private TwitterAccess twitterAccess;

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);

        // Warning! Launches a live Twitter client! Use carfully!
        ReflectionTestUtils.setField(twitterAccess, "twitter", new TwitterConfig().getTwitterSingleton());
    }

    @Test
    public void testTwitterHelloWorld() throws TwitterException{
        FollowerListDTO followersOfUser = twitterAccess.getFollowersOfUser("@melonjonas");

        assertNotNull(followersOfUser);
    }

    @Test
    public void testFollowUser() throws TwitterException {

        twitterAccess.followUser("@financialtimes");
    }

    @Test
    public void testUnfollowUser() throws TwitterException {

        twitterAccess.unfollowUser("@financialtimes");
    }

    @Test
    public void testgetTweets() throws TwitterException {
        List<String> tweets = twitterAccess.getTweets("@financialtimes", 115);
    }
}
