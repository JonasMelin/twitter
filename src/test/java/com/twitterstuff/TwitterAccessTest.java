package com.twitterstuff;

import com.twitterstuff.config.TwitterConfig;
import com.twitterstuff.model.FollowerListDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

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
    public void testTwitterHelloWorld() throws Exception{
        FollowerListDTO followersOfUser = twitterAccess.getFollowersOfUser("@melonjonas");

    }
}
