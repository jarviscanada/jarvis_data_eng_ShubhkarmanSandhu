package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.JsonPraser;
import ca.jrvs.apps.twitter.dao.helper.TweetMaker;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class TwitterDaoIntTest {

    private static TwitterDao dao;
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
        dao=new TwitterDao(helper);
    }

    @Test
    public void create() throws JsonProcessingException {
        String hashtag="#Coding";
        String text="Tweet for CLI Testing..... "+hashtag+" at "+java.time.LocalTime.now();
        Double lat=1d;
        Double lon=-1d;
        Tweet postTweet= TweetMaker.build(text,lon,lat);
        System.out.println(JsonPraser.toJson(postTweet));
        Tweet tweet= dao.create(postTweet);

        assertEquals(text,tweet.getText());

        assertNotNull(tweet.getCoordinates().getCoordinates());
        assertEquals(lon,tweet.getCoordinates().getCoordinates()[0]);
        assertEquals(lat,tweet.getCoordinates().getCoordinates()[1]);
    }

    @Test
    public void findById() throws JsonProcessingException {
        String hashtag="#Coding";
        String text="Tweet for CLI Testing......"+hashtag+" at "+java.time.LocalTime.now();
        Double lat=1d;
        Double lon=-1d;
        Tweet postTweet= TweetMaker.build(text,lon,lat);
        System.out.println(JsonPraser.toJson(postTweet));
        Tweet tweet= dao.create(postTweet);
        Tweet foundTweet= dao.findById(tweet.getId_str());

        assertEquals(foundTweet.getText(),tweet.getText());
        assertEquals(foundTweet.getId(),tweet.getId());
        assertEquals(lon,foundTweet.getCoordinates().getCoordinates()[0]);
        assertEquals(lat,foundTweet.getCoordinates().getCoordinates()[1]);

    }

    @Test
    public void deleteById() throws JsonProcessingException {
        String hashtag="#Coding";
        String text="Tweet for CLI Testing......"+hashtag+" at "+java.time.LocalTime.now();
        Double lat=1d;
        Double lon=-1d;
        Tweet postTweet= TweetMaker.build(text,lon,lat);
        System.out.println(JsonPraser.toJson(postTweet));
        Tweet tweet= dao.create(postTweet);
        Tweet foundTweet= dao.deleteById(tweet.getId_str());


    }
}