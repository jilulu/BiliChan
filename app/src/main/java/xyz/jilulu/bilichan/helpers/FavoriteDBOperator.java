package xyz.jilulu.bilichan.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by jamesji on 24/4/2016.
 */
public class FavoriteDBOperator {
    private SQLiteDatabase db;

    public FavoriteDBOperator (Context context) {
        FavoriteDBHelper dbHelper = new FavoriteDBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertEntry(int postID, String tags, String previewURL, String fullResURL, String title) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoritePostContract.FavoritePost._ID, postID);
        contentValues.put(FavoritePostContract.FavoritePost.COLUMN_NAME_TAG, tags);
        contentValues.put(FavoritePostContract.FavoritePost.COLUMN_NAME_IMAGE_URL_PREVIEW, previewURL);
        contentValues.put(FavoritePostContract.FavoritePost.COLUMN_NAME_IMAGE_URL, fullResURL);
        contentValues.put(FavoritePostContract.FavoritePost.COLUMN_NAME_TITLE, title);
        return db.insert(FavoritePostContract.FavoritePost.TABLE_NAME, null, contentValues);
    }

    public ArrayList<UserFavObject> queryDB() {
        Cursor userFavCursor = db.query(FavoritePostContract.FavoritePost.TABLE_NAME, null, null, null, null, null, null);
        ArrayList<UserFavObject> favList = new ArrayList<>();
        while (userFavCursor.moveToNext()) {
            UserFavObject fav = new UserFavObject(userFavCursor.getInt(0), userFavCursor.getString(1), userFavCursor.getString(2), userFavCursor.getString(3), userFavCursor.getString(4));
            favList.add(fav);
        }
        userFavCursor.close();
        return favList;
    }

    public void closeDB() {
        db.close();
    }

}
