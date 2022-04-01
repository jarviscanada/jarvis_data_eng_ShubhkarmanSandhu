package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Entites {
    private List<Hashtags> hashtags;
    private List<UserMention> userMentions;

    public List<Hashtags> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<Hashtags> hashtags) {
        this.hashtags = hashtags;
    }

    public List<UserMention> getUserMentions() {
        return userMentions;
    }

    public void setUserMention(List<UserMention> userMentions) {
        this.userMentions = userMentions;
    }
}
