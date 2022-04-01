package ca.jrvs.apps.twitter.dao.helper;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Entites;
import ca.jrvs.apps.twitter.model.Tweet;

import java.util.ArrayList;
import java.util.List;

public class TweetMaker {
    public static Tweet build(String text, Double lon, Double lat) {
        Entites entities = new Entites();
        entities.setHashtags(new ArrayList<>());
        entities.setUserMention(new ArrayList<>());

        Coordinates coordinates = new Coordinates();
        Double coords[] = new Double[2];
        coords[0]=lon;
        coords[1]=lat;
        coordinates.setCoordinates(coords);
        coordinates.setType("Point");

        Tweet tweet = new Tweet();
        tweet.setText(text);
        tweet.setCoordinates(coordinates);
        tweet.setEntites(entities);
        tweet.setRetweet_count(0);
        tweet.setRetweet_count(0);
        tweet.setFavorited(false);
        tweet.setRetweeted(false);


        return tweet;
    }
}
