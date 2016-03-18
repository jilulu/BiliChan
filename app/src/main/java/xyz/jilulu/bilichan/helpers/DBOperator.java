package xyz.jilulu.bilichan.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by jamesji on 18/3/2016.
 */
public class DBOperator {
    private SQLiteDatabase db;
    private Cursor userFavCursor;

    public DBOperator (Context context) {
        FavoriteDBHelper dbHelper = new FavoriteDBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertEntry(int postID, String title, String previewURL, String fullResURL) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoritePostContract.FavoritePost._ID, postID);
        contentValues.put(FavoritePostContract.FavoritePost.COLUMN_NAME_TITLE, title);
        contentValues.put(FavoritePostContract.FavoritePost.COLUMN_NAME_IMAGE_URL_PREVIEW, previewURL);
        contentValues.put(FavoritePostContract.FavoritePost.COLUMN_NAME_IMAGE_URL, fullResURL);
        return db.insert(FavoritePostContract.FavoritePost.TABLE_NAME, null, contentValues);
    }

    public ArrayList<UserfavObject> queryDB() {
        userFavCursor = db.query(FavoritePostContract.FavoritePost.TABLE_NAME, null, null, null, null, null, null);
        ArrayList<UserfavObject> favList = new ArrayList<>();
        while (userFavCursor.moveToNext()) {
            UserfavObject fav = new UserfavObject(userFavCursor.getInt(0), userFavCursor.getString(1), userFavCursor.getString(2), userFavCursor.getString(3));
            favList.add(fav);
        }
        return favList;
    }

    public void closeDB() {
        db.close();
    }

}
