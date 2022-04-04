package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.TweetMaker;
import ca.jrvs.apps.twitter.model.Tweet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {

    @Mock
    TwitterDao dao;

    @InjectMocks
    TwitterService service;


    @Test
    public void postTweet() {
        when(dao.create(any())).thenReturn(new Tweet());
        Tweet retTweet=service.postTweet(TweetMaker.build("testing..",2d,-2d));
        assertNull(retTweet.getText());

    }

    @Test
    public void showTweet() {
        when(dao.findById(any())).thenReturn(new Tweet());
        Tweet retTweet=service.showTweet("123",null);
        assertNull(retTweet.getText());
    }

    @Test
    public void deleteTweets() {
        List<Tweet> tweetList=new ArrayList<>();
        when(dao.deleteById(any())).thenReturn(new Tweet());
        List<Tweet> retTweet=service.deleteTweets(new String[]{"1234"});
        assertNull(retTweet.get(0).getText());
    }
}