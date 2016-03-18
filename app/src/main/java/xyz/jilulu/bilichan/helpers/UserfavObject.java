package xyz.jilulu.bilichan.helpers;

/**
 * Created by jamesji on 18/3/2016.
 */
public class UserfavObject {
    private int postID;
    private String title, prevURL, fullURL;

    public UserfavObject(int postID, String title, String prevURL, String fullURL) {
        this.postID = postID;
        this.title = title;
        this.prevURL = prevURL;
        this.fullURL = fullURL;
    }

    public int getPostID() {
        return postID;
    }

    public String getTitle() {
        return title;
    }

    public String getPrevURL() {
        return prevURL;
    }

    public String getFullURL() {
        return fullURL;
    }
}
