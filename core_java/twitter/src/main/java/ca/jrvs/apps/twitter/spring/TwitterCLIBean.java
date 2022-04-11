package ca.jrvs.apps.twitter.spring;

import ca.jrvs.apps.twitter.TwitterCLIApp;
import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.service.TwitterService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class TwitterCLIBean {
    public static void main(String[] args) {
        ApplicationContext context=new AnnotationConfigApplicationContext(TwitterCLIBean.class);
        TwitterCLIApp app=context.getBean(TwitterCLIApp.class);
        app.run(args);

    }
    @Bean
    public TwitterCLIApp twitterCLIApp(TwitterController controller){
        return new TwitterCLIApp(controller);
    }
    @Bean
    public TwitterController controller(TwitterService service){
        return new TwitterController(service);
    }
    @Bean
    public TwitterService service(TwitterDao dao){
        return new TwitterService(dao);
    }
    @Bean
    public TwitterDao dao(HttpHelper helper){
        return new TwitterDao(helper);
    }
    @Bean
    HttpHelper helper(){
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");

        return new TwitterHttpHelper(CONSUMER_KEY,CONSUMER_SECRET,ACCESS_TOKEN,TOKEN_SECRET);

    }
}
