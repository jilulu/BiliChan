package xyz.jilulu.bilichan.helpers.db;

import android.provider.BaseColumns;

/**
 * Created by jamesji on 11/3/2016.
 */
public class CpsvContract {
    public CpsvContract() {
    }

    public static abstract class FavoritePost implements BaseColumns {
        public static final String TABLE_NAME = "post";
        public static final String COLUMN_NAME_TAG = "tags";
        public static final String COLUMN_NAME_IMAGE_URL_PREVIEW = "previewurl";
        public static final String COLUMN_NAME_IMAGE_URL = "url";
        public static final String COLUMN_NAME_TITLE = "title";
    }

    public static abstract class KonaPost implements BaseColumns {
        public static final String TABLE_NAME = "indexPost";
        public static final String COLUMN_NAME_TAGNAME = "tag_name";
        public static final String COLUMN_NAME_COUNT = "count";
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

    private static final String SQL_CREATE_ENTRIES_KONA_POST =
            "CREATE TABLE " + KonaPost.TABLE_NAME + " (" +
                    KonaPost._ID + " INTEGER PRIMARY KEY," +
                    KonaPost.COLUMN_NAME_TAGNAME + TEXT_TYPE + COMMA_SEP +
                    KonaPost.COLUMN_NAME_COUNT + " INTEGER" + COMMA_SEP +
                    ")";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + FavoritePost.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_KONA_POST = "DROP TABLE IF EXISTS " + KonaPost.TABLE_NAME;

    public static String favCreateEntries() {
        return SQL_CREATE_ENTRIES;
    }

    public static String favDeleteEntries() {
        return SQL_DELETE_ENTRIES;
    }

    public static String konaCreateEntries() {
        return SQL_CREATE_ENTRIES_KONA_POST;
    }

    public static String konaDeleteEntries() {
        return SQL_DELETE_ENTRIES_KONA_POST;
    }
}
