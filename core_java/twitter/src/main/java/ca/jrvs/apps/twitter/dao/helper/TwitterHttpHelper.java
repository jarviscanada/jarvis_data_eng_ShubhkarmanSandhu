package ca.jrvs.apps.twitter.dao.helper;

import com.google.gdata.util.common.base.PercentEscaper;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class TwitterHttpHelper implements HttpHelper{
    /**
     * Dependencies are specified as private member variable
     */
    private OAuthConsumer consumer;
    private HttpClient httpClient;

    /**
     * Dependencies are setup in constructor
     * @param CONSUMER_KEY
     * @param CONSUMER_SECRET
     * @param ACCESS_TOKEN
     * @param TOKEN_SECRET
     */
    public TwitterHttpHelper(String CONSUMER_KEY,String CONSUMER_SECRET,String ACCESS_TOKEN,String TOKEN_SECRET) {
        consumer= new CommonsHttpOAuthConsumer(CONSUMER_KEY,CONSUMER_SECRET);
        consumer.setTokenWithSecret(ACCESS_TOKEN,TOKEN_SECRET);

        httpClient=new DefaultHttpClient();
    }

    @Override
    public HttpResponse httpPost(URI uri) {
        try{
             return excuteHttp(HttpMethod.POST,uri,null);
        }
        catch (OAuthException | IOException e){
              throw new RuntimeException(e);
        }

    }

    @Override
    public HttpResponse httpGet(URI uri) {
        try{
            return excuteHttp(HttpMethod.GET,uri,null);
        }
        catch (OAuthException | IOException e){
              throw new RuntimeException(e);
        }

    }

    private HttpResponse excuteHttp(HttpMethod method, URI url, StringEntity stringEntity)
    throws OAuthException,IOException{
        if(method==HttpMethod.GET){
            HttpGet request=new HttpGet(url);
            consumer.sign(request);
            return httpClient.execute(request);

        }else if(method==HttpMethod.POST){
            HttpPost request=new HttpPost(url);
            if(stringEntity!=null){
                request.setEntity(stringEntity);
            }
            consumer.sign(request);
            return httpClient.execute(request);

        }
        else{
            throw new IllegalArgumentException("Unknown HTTP method: " +method.name());
        }

    }

    public static void main(String[] args) throws Exception{
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
}
