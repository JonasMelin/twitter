package com.twitterstuff;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.TwitterException;

@RestController()
@Slf4j
public class Controller {

    @Autowired TwitterService twitterService;

    @PutMapping("/user/{user}/follow")
    public ResponseEntity<String> followUser(@PathVariable String user){

        log.info(String.format("followUser: %s", user));
        try {
            twitterService.followUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (TwitterException twEx){
            log.warn(String.format("followUser: %s, %s", user, twEx.toString()));
            return new ResponseEntity<>(twEx.toString(), HttpStatus.valueOf(twEx.getStatusCode()));
        }catch (Exception ex){
            log.error(String.format("followUser: %s, %s", user, ex.toString()));
            return new ResponseEntity<>(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/user/{user}/unfollow")
    public ResponseEntity<String> unfollowUser(@PathVariable String user){

        log.info(String.format("unfollowUser: %s", user));
        try {
            twitterService.unfollowUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (TwitterException twEx){
            log.warn(String.format("unfollowUser: %s, %s", user, twEx.toString()));
            return new ResponseEntity<>(twEx.toString(), HttpStatus.valueOf(twEx.getStatusCode()));
        }catch (Exception ex){
            log.error(String.format("unfollowUser: %s, %s", user, ex.toString()));
            return new ResponseEntity<>(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{user}/followers")
    public ResponseEntity<Object> listFollowers(@PathVariable String user) throws Exception{

        log.info(String.format("listFollowers: %s", user));
        try {
            return new ResponseEntity<>(twitterService.getFollowersOfUser(user), HttpStatus.OK);
        }catch (TwitterException twEx){
            log.warn(String.format("listFollowers: %s, %s", user, twEx.toString()));
            return new ResponseEntity<>(twEx.toString(), HttpStatus.valueOf(twEx.getStatusCode()));
        }catch (Exception ex){
            log.warn(String.format("listFollowers: %s, %s", user, ex.toString()));
            return new ResponseEntity<>(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tweets/{user}/{limit}")
    public ResponseEntity<Object> getTweetsFromUser(@PathVariable String user, @PathVariable int limit) {

        log.info(String.format("getTweetsFromUser: %s, limit: %d", user, limit));

        try{
            return new ResponseEntity<>(twitterService.getTweets(user, limit), HttpStatus.OK);
        }catch (TwitterException twEx){
            log.warn(String.format("getTweetsFromUser: %s, %s", user, twEx.toString()));
            return new ResponseEntity<>(twEx.toString(), HttpStatus.valueOf(twEx.getStatusCode()));
        }catch (Exception ex){
            log.warn(String.format("getTweetsFromUser: %s, %s", user, ex.toString()));
            return new ResponseEntity<>(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
