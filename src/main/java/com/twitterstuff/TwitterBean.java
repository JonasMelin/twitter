package com.twitterstuff;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

@Configuration
public class TwitterBean {

    @Bean
    public Twitter getTwitterSingleton() {
        return TwitterFactory.getSingleton();
    }
}
