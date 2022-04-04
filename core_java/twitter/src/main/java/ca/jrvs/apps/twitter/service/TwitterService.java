package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.model.Tweet;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TwitterService implements Service{
    private TwitterDao dao;

    @Autowired
    public TwitterService(TwitterDao dao) {
        this.dao = dao;
    }

    @Override
    public Tweet postTweet(Tweet tweet) {
        this.validatePostTweet(tweet);
        return (Tweet)dao.create(tweet);
    }

    @Override
    public Tweet showTweet(String id, String[] fields) {
        validateID(id);
        Tweet response= dao.findById(id);

        if(fields!=null)
        response=filterTweet(response,fields);

        return response;
    }

    private Tweet filterTweet(Tweet response, String[] fields) {
        Class tweetClass = Tweet.class;
        Field f[] = tweetClass.getDeclaredFields();
        HashSet<String> toSet=new HashSet<String>();
        Collections.addAll(toSet,fields);

        try{
            for(Field field:f){
                if (toSet.contains(field.getName())) {

                char[] getAccess = field.getName().toCharArray();
                getAccess[0] = Character.toUpperCase(getAccess[0]);
                String setMethodName = "set" + String.valueOf(getAccess);
                Method method = tweetClass.getDeclaredMethod(setMethodName,new Class[]{field.getType()});
                Object obj=null;
                method.invoke(response,obj);
                }
            }
           }
        catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException("Invalid Field at Filtering", e);
        }
        return response;
    }

    @Override
    public List<Tweet> deleteTweets(String[] ids) {
        List<Tweet> delTweets=new ArrayList<>();
        for(String id:ids){
            validateID(id);
            delTweets.add((Tweet)dao.deleteById(id));
        }
        return delTweets;
    }
    private void validatePostTweet(Tweet tweet){
        int len=tweet.getText().length();
        if( len==0 || len>140 ){
               throw new IllegalArgumentException("Tweet Size more than 140 characters or nothing");
           }

        if( tweet.getCoordinates()!=null && tweet.getCoordinates().getCoordinates()!=null ) {
               Double lon = tweet.getCoordinates().getCoordinates()[0];
               Double lat = tweet.getCoordinates().getCoordinates()[1];

               if (lon < -180.0 || lon > 180.0) {
                   throw new IllegalArgumentException("Longitude out of Range");
               }

               if (lat < -90.0 || lat > 90.0) {
                   throw new IllegalArgumentException("Latitude out of Range");
               }
           }


    }
    private void validateID(String id){
        try{
           Long.parseLong(id);
        }
        catch(Exception e){
            throw new IllegalArgumentException("Invalid ID format");
        }
    }
}
