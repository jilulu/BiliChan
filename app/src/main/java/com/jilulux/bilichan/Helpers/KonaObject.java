package com.jilulux.bilichan.Helpers;

import com.google.gson.JsonObject;

/**
 * Created by jamesji on 23/4/2016.
 */
public class KonaObject {
    private String tags, author, fullSizeURL, previewURL, largeSizeURL;
    private int fileSize, id;

    public KonaObject(JsonObject jo) {
        tags = jo.get("tags").getAsString();
        id = jo.get("id").getAsInt();
        author = jo.get("author").getAsString();
        fullSizeURL = jo.get("file_url").getAsString();
        previewURL = jo.get("preview_url").getAsString();
        largeSizeURL = jo.get("sample_url").getAsString();
        fileSize = jo.get("file_size").getAsInt();
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
