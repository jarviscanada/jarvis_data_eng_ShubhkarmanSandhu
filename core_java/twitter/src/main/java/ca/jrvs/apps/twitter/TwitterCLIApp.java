package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.JsonPraser;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;
@Component
public class TwitterCLIApp {
    public static final String USAGE = "USAGE: TwitterCLIApp post|show|delete [options]";
    private TwitterController twitterController;

    @Autowired
    public TwitterCLIApp(TwitterController twitterController) {
        this.twitterController = twitterController;
    }
    public void run(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException(USAGE);
        }

        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "post":
                printTweet(twitterController.postTweet(args));
                break;
            case "show":
                printTweet(twitterController.showTweet(args));
                break;
            case "delete":
                twitterController.deleteTweet(args).forEach(this::printTweet);
                break;
            default:
                throw new IllegalArgumentException(USAGE);
        }
    }
    private void printTweet(Tweet tweet) {
        try {
            System.out.println(JsonPraser.toJson(tweet));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to convert tweet object to string", e);
        }

    }

    public static void main(String[] args) {
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");

        TwitterHttpHelper helper=new TwitterHttpHelper(CONSUMER_KEY,CONSUMER_SECRET,ACCESS_TOKEN,TOKEN_SECRET);
        TwitterDao dao=new TwitterDao(helper);
        Service service=new TwitterService(dao);
        TwitterController controller=new TwitterController(service);
        TwitterCLIApp twitterCLIApp=new TwitterCLIApp(controller);

        twitterCLIApp.run(args);

    }
}
