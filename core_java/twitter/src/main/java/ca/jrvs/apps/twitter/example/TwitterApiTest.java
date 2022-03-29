package ca.jrvs.apps.twitter.example;

import com.google.gdata.util.common.base.PercentEscaper;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.swing.text.html.parser.Entity;
import java.util.Arrays;

public class TwitterApiTest {
    private static String CONSUMER_KEY = System.getenv("consumerKey");
    private static String CONSUMER_SECRET = System.getenv("consumerSecret");
    private static String ACCESS_TOKEN = System.getenv("accessToken");
    private static String TOKEN_SECRET = System.getenv("tokenSecret");

    public static void main(String[] args) throws Exception {

        //Setup OAuth
        OAuthConsumer consumer= new CommonsHttpOAuthConsumer(CONSUMER_KEY,CONSUMER_SECRET);
        consumer.setTokenWithSecret(ACCESS_TOKEN,TOKEN_SECRET);

        //Create hhtp req
        String Status="Hello World";
        PercentEscaper percentEscaper=new PercentEscaper("",false);
        HttpPost request=new HttpPost("https://api.twitter.com/1.1/statuses/update.json?status="+percentEscaper.escape(Status));

        //sign req
        consumer.sign(request);
        System.out.println("Http Request Header:");
        Arrays.stream(request.getAllHeaders()).forEach(System.out::println);

        //Send req
        HttpClient httpClient= HttpClientBuilder.create().build();
        HttpResponse httpResponse= httpClient.execute(request);
        System.out.println(EntityUtils.toString(httpResponse.getEntity()));
    }



}
