package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TwitterControllerIntTest {

    TwitterController twitterController;
    @Before
    public void setUp() throws Exception {
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
        Service service=new TwitterService(dao);
        twitterController=new TwitterController(service);
    }

    @Test
    public void postTweet() {
        String hashtag="#Coding";
        String text="Tweet for CLI Testing..... "+hashtag+" at "+java.time.LocalTime.now();
        Double lat=1d;
        Double lon=-1d;
        String coords=lat+":"+lon;
        System.out.println(coords);
        String  args[]=new String[]{"post",text,coords};
        Tweet postTweet= twitterController.postTweet(args);
        assertEquals(text,postTweet.getText());
        assertEquals(lon,postTweet.getCoordinates().getCoordinates()[0]);
        assertEquals(lat,postTweet.getCoordinates().getCoordinates()[1]);
    }

    @Test
    public void showTweet() {
        String hashtag="#Coding";
        String text="Tweet for CLI Testing..... "+hashtag+" at "+java.time.LocalTime.now();
        Double lat=1d;
        Double lon=-1d;
        String coords=lat+":"+lon;
        System.out.println(coords);
        String  argsPost[]=new String[]{"post",text,coords};
        Tweet postTweet= twitterController.postTweet(argsPost);

        String argsGet[]=new String[]{"get", postTweet.getId_str()};
        Tweet getTweet= twitterController.showTweet(argsGet);
        assertEquals(getTweet.getText(),postTweet.getText());
        assertEquals(getTweet.getCoordinates().getCoordinates()[0],postTweet.getCoordinates().getCoordinates()[0]);
        assertEquals(getTweet.getCoordinates().getCoordinates()[1],postTweet.getCoordinates().getCoordinates()[1]);

        String argsGetWithFields[]=new String[]{"get", postTweet.getId_str(),"text"};
        getTweet= twitterController.showTweet(argsGetWithFields);
        assertNull(getTweet.getText());
    }

    @Test
    public void deleteTweet() {
        String hashtag="#Coding";
        String text="Tweet for CLI Testing..... "+hashtag+" at "+java.time.LocalTime.now();
        Double lat=1d;
        Double lon=-1d;
        String coords=lat+":"+lon;
        System.out.println(coords);
        String  argsPost[]=new String[]{"post",text,coords};
        Tweet postTweet= twitterController.postTweet(argsPost);

        String argsGet[]=new String[]{"get", postTweet.getId_str()};
        Tweet getTweet1= twitterController.showTweet(argsGet);
        String ids=getTweet1.getId_str();
        String args[]=new String[]{"delete",ids};
        List<Tweet> deletedTweets= twitterController.deleteTweet(args);
    }
}