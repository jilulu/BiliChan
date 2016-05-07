package com.jilulux.bilichan.Models;

import java.util.List;

/**
 * Created by jamesji on 28/4/2016.
 */
public class ExploreItems {
    public List<ExploreItem> posts;

    public class ExploreItem {
        //        private int postID;
//        private String tag, prevURL, fullURL, title;
        public int id;
        public int jpeg_file_size;
        public String tags;
        public String author;
        public String preview_url;
        public String jpeg_url;

    }
}
