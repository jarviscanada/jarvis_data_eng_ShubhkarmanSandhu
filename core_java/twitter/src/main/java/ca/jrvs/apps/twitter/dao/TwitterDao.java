package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.JsonPraser;
import ca.jrvs.apps.twitter.model.Tweet;
import com.google.gdata.util.common.base.PercentEscaper;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class TwitterDao implements CrdDao<Tweet,String>{

    //URI constants
    private static final String API_BASE_URI = "https://api.twitter.com";
    private static final String POST_PATH = "/1.1/statuses/update.json";
    private static final String SHOW_PATH = "/1.1/statuses/show.json";
    private static final String DELETE_PATH = "/1.1/statuses/destroy";

    //URI symbols
    private static final String QUERY_SYM = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUAL = "=";

    //Response code
    private static final int HTTP_OK = 200;

    private HttpHelper httpHelper;

    @Autowired
    public TwitterDao(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }
    @Override
    public Tweet create(Tweet entity) {
        URI uri;
        try {
            //Getting uri from helper function
            uri = getPOSTURI(entity);

        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Tweet Pattern wrong",e);
        }
        //Execute http post req
        HttpResponse response=httpHelper.httpPost(uri);

        return parseResponse(response,HTTP_OK);
    }

    @Override
    public Tweet findById(String id) {
        URI uri;
        try {
            String uri_str = API_BASE_URI + SHOW_PATH + QUERY_SYM + "id" + EQUAL + id;
            uri=new URI(uri_str);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Error in URI Construction",e);
        }
        HttpResponse response= httpHelper.httpGet(uri);

        return parseResponse(response,HTTP_OK);
    }

    @Override
    public Tweet deleteById(String id) {
        URI uri;
        try {
            String uri_str = API_BASE_URI + DELETE_PATH + "/" + id + ".json";
            uri=new URI(uri_str);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Error in URI Construction",e);
        }

        HttpResponse response= httpHelper.httpPost(uri);

        return parseResponse(response,HTTP_OK);
    }

    /**
     * Helper function to parse the Response into twitter Object
     * @param response
     * @param expectedCode
     * @return
     */
    public Tweet parseResponse(HttpResponse response, int expectedCode) {

        //Check for wrong Status
        int responseCode=response.getStatusLine().getStatusCode();
        if(expectedCode!=responseCode){
            throw new RuntimeException("Unexpected HTTP status: "+responseCode);
        }

        //Check for no response
        if(response.getEntity()==null){
            throw new RuntimeException("Response has no Entity");
        }

        //Convert Response Entity to String
        String json_str;
        try {
            json_str = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException("Error Converting Response to String", e );
        }
        //Convert String to Tweet object
        Tweet tweet;
        try {
            tweet = JsonPraser.toObjectFromJson(json_str, Tweet.class);
        } catch (IOException e) {
            throw new RuntimeException("Error Json to Tweet Object", e );
        }
        return tweet;

    }

    /**
     * Helper function to get post uri
     * @param entity
     * @return
     * @throws URISyntaxException
     */
    private URI getPOSTURI(Tweet entity) throws URISyntaxException {

        //getting text from tweet model
        String text=entity.getText();

        //Creating URI for post
        PercentEscaper percentEscaper=new PercentEscaper("",false);
        String uri_text=API_BASE_URI+POST_PATH+QUERY_SYM+"status"+EQUAL+percentEscaper.escape(text);

        //Adding Coordinates to tweet
        if(entity.getCoordinates()!=null){
            uri_text=uri_text
                    +AMPERSAND+"long"+EQUAL+entity.getCoordinates().getCoordinates()[0]
                    +AMPERSAND+"lat"+EQUAL+entity.getCoordinates().getCoordinates()[1];
        }
        return new URI(uri_text);
    }
}
