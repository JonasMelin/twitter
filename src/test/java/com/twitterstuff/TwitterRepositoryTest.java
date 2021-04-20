package com.twitterstuff;

import com.twitterstuff.model.FollowerListDTO;
import com.twitterstuff.model.TweetsListDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;

@SpringBootTest
public class TwitterRepositoryTest {

    @InjectMocks
    private TwitterRepository twitterRepository;

    @Mock
    private Twitter twitter;


    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Warning! Launches a live Twitter client! Use carfully!
        //ReflectionTestUtils.setField(twitterAccess, "twitter", new TwitterConfig().getTwitterSingleton());
    }

    @Test
    public void testTwitterHelloWorld() throws TwitterException {
        FollowerListDTO followersOfUser = twitterRepository.getFollowersOfUser("@melonjonas");

        assertNotNull(followersOfUser);
    }

    @Test
    public void testFollowUser() throws TwitterException {

        twitterRepository.followUser("@financialtimes");
    }

    @Test
    public void testUnfollowUser() throws TwitterException {

        twitterRepository.unfollowUser("@financialtimes");
    }

    @Test
    public void testgetTweetsOK() throws TwitterException {

        final int PAGE_SIZE = 10;
        final int LIMIT = 17;
        String user = "@financialtimes";
        List<Status> mockedTwitterResultList1 = new ArrayList<>();
        List<Status> mockedTwitterResultList2 = new ArrayList<>();

        Status status1 = mock(Status.class);
        when(status1.getText()).thenReturn("Nice little tweets in page 1!");
        Status status2 = mock(Status.class);
        when(status2.getText()).thenReturn("Nice little tweets in page 2!");

        for (int a = 0; a < PAGE_SIZE; a++) {
            mockedTwitterResultList1.add(status1);
            mockedTwitterResultList2.add(status2);
        }

        ResponseList<Status> userTimelinePage1 = mock(ResponseList.class);
        when(userTimelinePage1.stream()).thenReturn(mockedTwitterResultList1.stream());
        when(userTimelinePage1.size()).thenReturn(mockedTwitterResultList1.size());
        ResponseList<Status> userTimelinePage2 = mock(ResponseList.class);
        when(userTimelinePage2.stream()).thenReturn(mockedTwitterResultList2.stream());
        when(userTimelinePage2.size()).thenReturn(mockedTwitterResultList2.size());

        when(twitter.getUserTimeline(eq(user), argThat(page -> page.getPage() == 1))).thenReturn(userTimelinePage1);
        when(twitter.getUserTimeline(eq(user), argThat(page -> page.getPage() == 2))).thenReturn(userTimelinePage2);

        TweetsListDTO retVal = twitterRepository.getTweets(user, LIMIT);

        assertNotNull(retVal);
        assertTrue(retVal.isSuccess());
        assertEquals(LIMIT, retVal.getTweets().size());
        assertTrue(retVal.getTweets().containsAll(mockedTwitterResultList1
                .stream().map(Status::getText).collect(Collectors.toSet())));
    }
}
