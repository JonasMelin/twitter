package com.twitterstuff;

import com.twitterstuff.model.BaseResponseDTO;
import com.twitterstuff.model.FollowerListDTO;
import com.twitterstuff.model.TweetsListDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Twitter demo. REST APIs related to basic twitter commands...")
@RestController()
@Slf4j
public class Controller {

    final static int MAX_LIMIT = 5000;

    @Autowired
    TwitterService twitterService;

    @ApiOperation(value = "Follow a user", response = String.class, tags = "Follow",
            notes = "user shall be the screenName, i.e. @finalcialtimes")

    @PutMapping("/user/{user}/follow")
    public ResponseEntity<BaseResponseDTO> followUser(@PathVariable String user) {

        log.info(String.format("followUser: %s", user));
        try {
            twitterService.followUser(user);
            return new ResponseEntity<>(new BaseResponseDTO(), HttpStatus.OK);
        } catch (Exception ex) {
            log.error(String.format("followUser: %s, %s", user, ex.toString()));
            return new ResponseEntity<>(new BaseResponseDTO(ex.toString(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Un-follow a user", response = String.class, tags = "Unfollow",
            notes = "user shall be the screenName, i.e. @finalcialtimes")

    @PutMapping("/user/{user}/unfollow")
    public ResponseEntity<BaseResponseDTO> unfollowUser(@PathVariable String user) {

        log.info(String.format("unfollowUser: %s", user));
        try {
            twitterService.unfollowUser(user);
            return new ResponseEntity<>(new BaseResponseDTO(), HttpStatus.OK);
        } catch (Exception ex) {
            log.error(String.format("unfollowUser: %s, %s", user, ex.toString()));
            return new ResponseEntity<>(new BaseResponseDTO(ex.toString(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "List followers of a user", response = String.class, tags = "list followers",
            notes = "user shall be the screenName, i.e. @finalcialtimes")

    @GetMapping("/user/{user}/followers")
    public ResponseEntity<FollowerListDTO> listFollowers(@PathVariable String user) {

        log.info(String.format("listFollowers: %s", user));
        try {
            return new ResponseEntity<>(twitterService.getFollowersOfUser(user), HttpStatus.OK);
        } catch (Exception ex) {
            log.warn(String.format("listFollowers: %s, %s", user, ex.toString()));
            return new ResponseEntity<>(new FollowerListDTO(
                    ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "List tweets of a user", response = String.class, tags = "list tweets",
            notes = "user shall be the screenName, i.e. @finalcialtimes")

    @GetMapping("/tweets/{user}/{limit}")
    public ResponseEntity<TweetsListDTO> getTweetsFromUser(@PathVariable String user, @PathVariable int limit) {

        log.info(String.format("getTweetsFromUser: %s, limit: %d", user, limit));

        if (limit < 1 || limit > MAX_LIMIT) {
            return new ResponseEntity<>(new TweetsListDTO(
                    String.format("limit not within range ( 1 <= range <= %d )", MAX_LIMIT),
                    HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE.value()),
                    HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
        }

        try {
            return new ResponseEntity<>(twitterService.getTweets(user, limit), HttpStatus.OK);
        } catch (Exception ex) {
            log.warn(String.format("getTweetsFromUser: %s, %s", user, ex.toString()));
            return new ResponseEntity<>(new TweetsListDTO(
                    ex.toString(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
