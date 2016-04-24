package xyz.jilulu.bilichan.Helpers;

import java.io.Serializable;

/**
 * Created by jamesji on 23/4/2016.
 */
public class KonaTag implements Serializable {
    private String tagName;
    private String tagCount;

    public KonaTag(String tagName, String tagCount) {
        this.tagName = tagName;
        this.tagCount = tagCount;
    }

    public String getTagName() {
        return tagName;
    }

    public String getTagCount() {
        return tagCount;
    }
}
