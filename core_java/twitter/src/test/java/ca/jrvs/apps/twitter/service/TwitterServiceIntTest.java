package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.TweetMaker;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TwitterServiceIntTest {

    static  TwitterService service;

    @BeforeClass
    public static void setUp() throws Exception {
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");

        System.out.println("CONSUMER_KEY: "+CONSUMER_KEY);
        System.out.println("CONSUMER_SECRET: "+CONSUMER_SECRET);
        System.out.println("ACCESS_TOKEN: "+ACCESS_TOKEN);
        System.out.println("TOKEN_SECRET: "+TOKEN_SECRET);

        TwitterHttpHelper helper=new TwitterHttpHelper(CONSUMER_KEY,CONSUMER_SECRET,ACCESS_TOKEN,TOKEN_SECRET);
        TwitterDao dao=new TwitterDao(helper);
        service=new TwitterService(dao);
    }

    @Test
    public void postTweet() {
        String hashtag="#Coding";
        String text="Tweet for CLI Testing..... "+hashtag+" at "+java.time.LocalTime.now();
        Double lat=1d;
        Double lon=-1d;
        Tweet postTweet= TweetMaker.build(text,lon,lat);
        Tweet response=service.postTweet(postTweet);
        assertEquals(postTweet.getText(),response.getText());
        assertArrayEquals(postTweet.getCoordinates().getCoordinates(),response.getCoordinates().getCoordinates());
    }

    @Test
    public void showTweet() {
        String hashtag="#Coding";
        String text="Tweet for CLI Testing..... "+hashtag+" at "+java.time.LocalTime.now();
        Double lat=1d;
        Double lon=-1d;
        Tweet postTweet= TweetMaker.build(text,lon,lat);
        Tweet response=service.postTweet(postTweet);
        Tweet getTweet=service.showTweet(response.getId_str(),new String[]{"text"});
        assertEquals(text,getTweet.getText());

    }

    @Test
    public void deleteTweets() {
        String hashtag="#Coding";
        String text="Tweet for CLI Testing..... "+hashtag+" at "+java.time.LocalTime.now();
        Double lat=1d;
        Double lon=-1d;
        Tweet postTweet= TweetMaker.build(text,lon,lat);
        Tweet response=service.postTweet(postTweet);
        List<Tweet> deletedTweets =service.deleteTweets(new String[]{response.getId_str()});
        assertEquals(deletedTweets.get(0).getText(),response.getText());

    }
}