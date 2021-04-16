package com.twitterstuff;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

 /*
1. Follow a user
2. Unfollow a user
3. Get a list of people following a user
4. Get a list of tweets of a user (including self-tweets and replies by followers)
 */

@RestController()
@Slf4j
public class Controller {

    @PutMapping("/user/{user}/follow")
    public ResponseEntity<String> followUser(@PathVariable String user){

        log.info(String.format("PUT user/follow/{%s}", user));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/user/{user}/unfollow")
    public ResponseEntity<String> unfollowUser(@PathVariable String user){

        log.info(String.format("PUT user/unfollow/{%s}", user));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user/{user}/followers")
    public ResponseEntity<List<String>> listFollowers(@PathVariable String user){

        log.info(String.format("GET /user/{%s}/followers", user));
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/tweets/{user}")
    public ResponseEntity<List<String>> getTweetsFromUser(@PathVariable String user){

        log.info(String.format("GET /tweets/{%s}", user));
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
