package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.JsonPraser;
import ca.jrvs.apps.twitter.dao.helper.TweetMaker;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelperTest;
import ca.jrvs.apps.twitter.model.Tweet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest {

    @Mock
    HttpHelper mockHelper;

    @InjectMocks
    TwitterDao dao;

    @Test
    public void postTweet() throws Exception {

        //fail case
        String hashTag = "#abc";
        String text = "@someone some text" + hashTag + " " + System.currentTimeMillis();
        Double lon = -1d;
        Double lat = 1d;
        when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
        try{
            dao.create(TweetMaker.build(text,lon,lat));
            fail();
        }
        catch (Exception e){
            assertTrue(true);
        }

        //pass case
        String tweetJsonStr = "{\n"
                + "   \"created_at\":\"Mon Jan 31 15:00:00 +0000 2022\",\n"
                + "   \"id\":9999,\n"
                + "   \"id_str\":\"9999\",\n"
                + "   \"text\":\"test with loc223\",\n"
                + "   \"entities\":{\n"
                + "      \"hashtags\":[],"
                + "      \"user_mentions\":[]"
                + "   },\n"
                + "   \"coordinates\":null,"
                + "   \"retweet_count\":0,\n"
                + "   \"favorite_count\":0,\n"
                + "   \"favorited\":false,\n"
                + "   \"retweeted\":false\n"
                + "}";
        when(mockHelper.httpPost(isNotNull())).thenReturn(null);
        TwitterDao spydDao= Mockito.spy(dao);
        Tweet expectedTweet= JsonPraser.toObjectFromJson(tweetJsonStr,Tweet.class);
        doReturn(expectedTweet).when(spydDao).parseResponse(any(),anyInt());
        Tweet tweet=spydDao.create(TweetMaker.build(text,lon,lat));
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
    }
    @Test
    public void showTweet() throws Exception {

        //fail case
        String hashTag = "#abc";
        String text = "@someone some text" + hashTag + " " + System.currentTimeMillis();
        Double lon = -1d;
        Double lat = 1d;
        when(mockHelper.httpGet(isNotNull())).thenThrow(new RuntimeException("mock"));
        try{
            dao.findById("9999");
            fail();
        }
        catch (Exception e){
            assertTrue(true);
        }

        //pass case
        String tweetJsonStr = "{\n"
                + "   \"created_at\":\"Mon Jan 31 15:00:00 +0000 2022\",\n"
                + "   \"id\":9999,\n"
                + "   \"id_str\":\"9999\",\n"
                + "   \"text\":\"test with loc223\",\n"
                + "   \"entities\":{\n"
                + "      \"hashtags\":[],"
                + "      \"user_mentions\":[]"
                + "   },\n"
                + "   \"coordinates\":null,"
                + "   \"retweet_count\":0,\n"
                + "   \"favorite_count\":0,\n"
                + "   \"favorited\":false,\n"
                + "   \"retweeted\":false\n"
                + "}";
        when(mockHelper.httpGet(isNotNull())).thenReturn(null);
        TwitterDao spydDao= Mockito.spy(dao);
        Tweet expectedTweet= JsonPraser.toObjectFromJson(tweetJsonStr,Tweet.class);
        doReturn(expectedTweet).when(spydDao).parseResponse(any(),anyInt());
        Tweet tweet=spydDao.findById("9999");
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
        assertEquals("9999",tweet.getId_str());
    }
    @Test
    public void deleteTweet() throws Exception {

        //fail case
        String hashTag = "#abc";
        String text = "@someone some text" + hashTag + " " + System.currentTimeMillis();
        Double lon = -1d;
        Double lat = 1d;
        when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
        try{
            dao.deleteById("9999");
            fail();
        }
        catch (Exception e){
            assertTrue(true);
        }

        //pass case
        String tweetJsonStr = "{\n"
                + "   \"created_at\":\"Mon Jan 31 15:00:00 +0000 2022\",\n"
                + "   \"id\":9999,\n"
                + "   \"id_str\":\"9999\",\n"
                + "   \"text\":\"test with loc223\",\n"
                + "   \"entities\":{\n"
                + "      \"hashtags\":[],"
                + "      \"user_mentions\":[]"
                + "   },\n"
                + "   \"coordinates\":null,"
                + "   \"retweet_count\":0,\n"
                + "   \"favorite_count\":0,\n"
                + "   \"favorited\":false,\n"
                + "   \"retweeted\":false\n"
                + "}";
        when(mockHelper.httpPost(isNotNull())).thenReturn(null);
        TwitterDao spydDao= Mockito.spy(dao);
        Tweet expectedTweet= JsonPraser.toObjectFromJson(tweetJsonStr,Tweet.class);
        doReturn(expectedTweet).when(spydDao).parseResponse(any(),anyInt());
        Tweet tweet=spydDao.deleteById("9999");
        assertNotNull(tweet);
        assertNotNull(tweet.getText());
        assertEquals("9999",tweet.getId_str());
    }
}