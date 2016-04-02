package xyz.jilulu.bilichan.helpers.data;

import com.google.gson.JsonObject;

/**
 * Created by jamesji on 29/2/2016.
 */
public class KonaObject {
    private JsonObject konaJsonEntry;
    private String tags, author, fullSizeURL, previewURL, largeSizeURL;
    private int fileSize, id;

    public KonaObject(JsonObject jo) {
        konaJsonEntry = jo;
        tags = konaJsonEntry.get("tags").getAsString();
        id = konaJsonEntry.get("id").getAsInt();
        author = konaJsonEntry.get("author").getAsString();
        fullSizeURL = konaJsonEntry.get("file_url").getAsString();
        previewURL = konaJsonEntry.get("preview_url").getAsString();
        largeSizeURL = konaJsonEntry.get("sample_url").getAsString();
        fileSize = konaJsonEntry.get("file_size").getAsInt();
    }

    public String getTags() {
        return tags;
    }

    public String getAuthor() {
        return author;
    }

    public String getFullSizeURL() {
        return fullSizeURL;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public String getLargeSizeURL() {
        return largeSizeURL;
    }

    public int getFileSize() {
        return fileSize;
    }

    public int getId() {
        return id;
    }
}
