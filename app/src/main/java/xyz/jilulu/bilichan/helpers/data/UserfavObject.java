package xyz.jilulu.bilichan.helpers.data;

/**
 * Created by jamesji on 18/3/2016.
 */
public class UserfavObject {
    private int postID;
    private String tag, prevURL, fullURL, title;

    public UserfavObject(int postID, String tag, String prevURL, String fullURL, String title) {
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
