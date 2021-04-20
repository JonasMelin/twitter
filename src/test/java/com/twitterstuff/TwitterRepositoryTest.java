package com.twitterstuff;

import com.twitterstuff.model.BaseResponseDTO;
import com.twitterstuff.model.FollowerListDTO;
import com.twitterstuff.model.TweetsListDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import twitter4j.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TwitterRepositoryTest {

    private final int PAGE_SIZE = 10;
    private final String USER = "@financialtimes";
    private final HttpStatus twitterHttpErrorStatus = HttpStatus.NOT_FOUND;
    private final String twitterErrorMessage = "Something went wrong";

    @InjectMocks
    private TwitterRepository twitterRepository;

    @Mock
    private Twitter twitter;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFollowUserOK() throws TwitterException {

        when(twitter.createFriendship(eq(USER))).thenReturn(null);
        BaseResponseDTO baseResponseDTO = twitterRepository.followUser(USER);

        assertNotNull(baseResponseDTO);
        assertTrue(baseResponseDTO.isSuccess());
    }

    @Test
    public void testFollowUserError() throws TwitterException {

        TwitterException twEx = new TwitterException(twitterErrorMessage, new Exception(), twitterHttpErrorStatus.value());
        when(twitter.createFriendship(eq(USER))).thenThrow(twEx);
        BaseResponseDTO baseResponseDTO = twitterRepository.followUser(USER);

        assertNotNull(baseResponseDTO);
        assertFalse(baseResponseDTO.isSuccess());
        assertFalse(baseResponseDTO.getDebugMessage().isEmpty());
        assertEquals(twitterHttpErrorStatus, baseResponseDTO.getTwitterStatus());
    }

    @Test
    public void testUnfollowUserOK() throws TwitterException {

        when(twitter.destroyFriendship(eq(USER))).thenReturn(null);
        BaseResponseDTO baseResponseDTO = twitterRepository.unfollowUser(USER);

        assertNotNull(baseResponseDTO);
        assertTrue(baseResponseDTO.isSuccess());
    }

    @Test
    public void testUnfollowUserError() throws TwitterException {

        TwitterException twEx = new TwitterException(twitterErrorMessage, new Exception(), twitterHttpErrorStatus.value());
        when(twitter.destroyFriendship(eq(USER))).thenThrow(twEx);
        BaseResponseDTO baseResponseDTO = twitterRepository.unfollowUser(USER);

        assertNotNull(baseResponseDTO);
        assertFalse(baseResponseDTO.isSuccess());
        assertFalse(baseResponseDTO.getDebugMessage().isEmpty());
        assertEquals(twitterHttpErrorStatus, baseResponseDTO.getTwitterStatus());
    }

    @Test
    public void testGetFollowersOfUserOK() throws TwitterException {

        final int USER_COUNT = 2;
        List<User> twitterResultList = new ArrayList<>();
        PagableResponseList<User> responseListMock = mock(PagableResponseList.class);

        for (int a = 0; a < USER_COUNT; a++) {
            User userMock = mock(User.class);
            when(userMock.getName()).thenReturn("Nisse " + a);
            when(userMock.getScreenName()).thenReturn("@nisse" + a);
            when(userMock.getId()).thenReturn(12345L + a);

            twitterResultList.add(userMock);
        }
        when(responseListMock.stream()).thenReturn(twitterResultList.stream());
        when(twitter.getFollowersList(eq(USER), anyLong())).thenReturn(responseListMock);

        FollowerListDTO followersOfUser = twitterRepository.getFollowersOfUser(USER);

        assertNotNull(followersOfUser);
        assertTrue(followersOfUser.isSuccess());
        assertEquals(twitterResultList.size(), followersOfUser.getFollowers().size());
        assertTrue(followersOfUser.getFollowers().stream().map(t -> t.getUserName()).collect(Collectors.toSet())
                .containsAll(twitterResultList.stream().map(u -> u.getName()).collect(Collectors.toSet())));
    }

    @Test
    public void testGetFollowersOfUserError() throws TwitterException {

        TwitterException twEx = new TwitterException(twitterErrorMessage, new Exception(), twitterHttpErrorStatus.value());
        when(twitter.getFollowersList(eq(USER), anyLong())).thenThrow(twEx);
        FollowerListDTO followersOfUser = twitterRepository.getFollowersOfUser(USER);

        assertNotNull(followersOfUser);
        assertFalse(followersOfUser.isSuccess());
        assertFalse(followersOfUser.getDebugMessage().isEmpty());
        assertEquals(twitterHttpErrorStatus, followersOfUser.getTwitterStatus());
    }

    @Test
    public void testgetTweetsOK() throws TwitterException {

        final int LIMIT = PAGE_SIZE * 2 - 3;

        List<Status> twitterResultList1 = new ArrayList<>();
        List<Status> twitterResultList2 = new ArrayList<>();

        for (int a = 0; a < PAGE_SIZE; a++) {
            Status statusMock1 = mock(Status.class);
            when(statusMock1.getText()).thenReturn(String.format("Nice little tweet %d, (Page 1) ", a + 1));
            twitterResultList1.add(statusMock1);

            Status statusMock2 = mock(Status.class);
            when(statusMock2.getText()).thenReturn(String.format("Nice little tweet %d, (Page 2) ", a + 1));
            twitterResultList2.add(statusMock2);
        }

        ResponseList<Status> userTimelinePage1Mock = mock(ResponseList.class);
        when(userTimelinePage1Mock.stream()).thenReturn(twitterResultList1.stream());
        when(userTimelinePage1Mock.size()).thenReturn(twitterResultList1.size());
        ResponseList<Status> userTimelinePage2Mock = mock(ResponseList.class);
        when(userTimelinePage2Mock.stream()).thenReturn(twitterResultList2.stream());
        when(userTimelinePage2Mock.size()).thenReturn(twitterResultList2.size());

        when(twitter.getUserTimeline(eq(USER), argThat(page -> page.getPage() == 1))).thenReturn(userTimelinePage1Mock);
        when(twitter.getUserTimeline(eq(USER), argThat(page -> page.getPage() == 2))).thenReturn(userTimelinePage2Mock);

        TweetsListDTO retVal = twitterRepository.getTweets(USER, LIMIT);

        assertNotNull(retVal);
        assertTrue(retVal.isSuccess());
        assertEquals(LIMIT, retVal.getTweets().size());
        assertTrue(retVal.getTweets().containsAll(twitterResultList1
                .stream().map(Status::getText).collect(Collectors.toSet())));
    }

    @Test
    public void testGetTweetsError() throws TwitterException {

        TwitterException twEx = new TwitterException(twitterErrorMessage, new Exception(), twitterHttpErrorStatus.value());
        when(twitter.getUserTimeline(eq(USER), argThat(page -> page.getPage() == 1))).thenThrow(twEx);
        TweetsListDTO retVal = twitterRepository.getTweets(USER, 10);

        assertNotNull(retVal);
        assertFalse(retVal.isSuccess());
        assertFalse(retVal.getDebugMessage().isEmpty());
        assertEquals(twitterHttpErrorStatus, retVal.getTwitterStatus());
    }
}
