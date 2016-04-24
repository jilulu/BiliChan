package xyz.jilulu.bilichan.Helpers;

import java.io.Serializable;

/**
 * Created by jamesji on 24/4/2016.
 */
public class UserFavObject implements Serializable {
    private int postID;
    private String tag, prevURL, fullURL, title;

    public UserFavObject (int postID, String tag, String prevURL, String fullURL, String title) {
        this.postID = postID;
        this.tag = tag;
        this.prevURL = prevURL;
        this.fullURL = fullURL;
        this.title = title;
    }

    public int getPostID() {
        return postID;
    }

    public String getTag() {
        return tag;
    }

    public String getPrevURL() {
        return prevURL;
    }

    public String getFullURL() {
        return fullURL;
    }

    public String getTitle() {
        return title;
    }
}
