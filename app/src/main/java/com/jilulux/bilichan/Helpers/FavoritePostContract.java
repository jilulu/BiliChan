package com.jilulux.bilichan.Helpers;

import android.provider.BaseColumns;

/**
 * Created by jamesji on 24/4/2016.
 */
public class FavoritePostContract {
    public FavoritePostContract() {
    }

    public static abstract class FavoritePost implements BaseColumns {
        public static final String TABLE_NAME = "post";
        public static final String COLUMN_NAME_TAG = "tags";
        public static final String COLUMN_NAME_IMAGE_URL_PREVIEW = "previewurl";
        public static final String COLUMN_NAME_IMAGE_URL = "url";
        public static final String COLUMN_NAME_TITLE = "title";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ", ";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FavoritePost.TABLE_NAME + " (" +
                    FavoritePost._ID + " INTEGER PRIMARY KEY," +
                    FavoritePost.COLUMN_NAME_TAG + TEXT_TYPE + COMMA_SEP +
                    FavoritePost.COLUMN_NAME_IMAGE_URL_PREVIEW + TEXT_TYPE + COMMA_SEP +
                    FavoritePost.COLUMN_NAME_IMAGE_URL + TEXT_TYPE + COMMA_SEP +
                    FavoritePost.COLUMN_NAME_TITLE + TEXT_TYPE +
                    ")";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + FavoritePost.TABLE_NAME;

    public static String getSqlCreateEntries() {
        return SQL_CREATE_ENTRIES;
    }

    public static String getSqlDeleteEntries() {
        return SQL_DELETE_ENTRIES;
    }
}
