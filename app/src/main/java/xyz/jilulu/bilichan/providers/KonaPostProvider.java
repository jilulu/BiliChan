package xyz.jilulu.bilichan.providers;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import xyz.jilulu.bilichan.helpers.db.CpsvContract;
import xyz.jilulu.bilichan.helpers.db.KonaPostDBHelper;
import xyz.jilulu.bilichan.helpers.db.KonaPostDBOperator;

/**
 * Created by jamesji on 2/4/2016.
 */
public class KonaPostProvider extends ContentProvider {
    public static final int KONA_ITEM = 0;
    public static final int KONA_DIR = 1;
    public static UriMatcher uriMatcher;
    private KonaPostDBHelper dbHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(CpsvContract.CONTENT_AUTHORITY, CpsvContract.KONA_POST, KONA_DIR);
        uriMatcher.addURI(CpsvContract.CONTENT_AUTHORITY, CpsvContract.KONA_POST + "/*", KONA_ITEM);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new KonaPostDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int queryType = uriMatcher.match(uri);
        switch (queryType) {
            case KONA_DIR:
                return dbHelper.getReadableDatabase().query(CpsvContract.KonaPost.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case KONA_ITEM:
                return ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CpsvContract.CONTENT_AUTHORITY + "/" + CpsvContract.KONA_POST;
            case KONA_DIR:
                return ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CpsvContract.CONTENT_AUTHORITY + "/" + CpsvContract.KONA_POST;
            default:
                break;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        KonaPostDBOperator op;
        switch (uriMatcher.match(uri)) {
            case KONA_DIR:
                op = new KonaPostDBOperator(getContext());
                op.insertEntry(
                        values.getAsString(CpsvContract.KonaPost.COLUMN_NAME_TAGNAME),
                        values.getAsInteger(CpsvContract.KonaPost.COLUMN_NAME_COUNT)
                );
                op.closeDB();
                break;
            default:
                break;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
