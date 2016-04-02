package xyz.jilulu.bilichan.helpers.data;

/**
 * Created by jamesji on 4/3/2016.
 */
public class KonaTag {
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
