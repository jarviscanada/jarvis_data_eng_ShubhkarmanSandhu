package ca.jrvs.apps.twitter.dao.helper;

import com.google.gdata.util.common.base.PercentEscaper;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class TwitterHttpHelperTest {

    @Test
    public void httpPost() throws URISyntaxException {
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");

        System.out.println("CONSUMER_KEY: "+CONSUMER_KEY);
        System.out.println("CONSUMER_SECRET: "+CONSUMER_SECRET);
        System.out.println("ACCESS_TOKEN: "+ACCESS_TOKEN);
        System.out.println("TOKEN_SECRET: "+TOKEN_SECRET);

        TwitterHttpHelper helper=new TwitterHttpHelper(CONSUMER_KEY,CONSUMER_SECRET,ACCESS_TOKEN,TOKEN_SECRET);

        String Status="Test tweet from CLI";
        PercentEscaper percentEscaper=new PercentEscaper("",false);
        URI uri=new URI("https://api.twitter.com/1.1/statuses/update.json?status="+percentEscaper.escape(Status));
        HttpResponse response= helper.httpPost(uri);
    }

    @Test
    public void httpGet() throws Exception {
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");

        System.out.println("CONSUMER_KEY: "+CONSUMER_KEY);
        System.out.println("CONSUMER_SECRET: "+CONSUMER_SECRET);
        System.out.println("ACCESS_TOKEN: "+ACCESS_TOKEN);
        System.out.println("TOKEN_SECRET: "+TOKEN_SECRET);

        TwitterHttpHelper helper=new TwitterHttpHelper(CONSUMER_KEY,CONSUMER_SECRET,ACCESS_TOKEN,TOKEN_SECRET);

        URI uri=new URI("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=IGN");
        HttpResponse response=helper.httpGet(uri);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }
}