package xyz.jilulu.bilichan.helpers.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import xyz.jilulu.bilichan.helpers.data.UserfavObject;

/**
 * Created by jamesji on 18/3/2016.
 */
public class FavoriteDBOperator {
    private SQLiteDatabase db;
    private Cursor userFavCursor;

    public FavoriteDBOperator(Context context) {
        FavoriteDBHelper dbHelper = new FavoriteDBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertEntry(int postID, String tags, String previewURL, String fullResURL, String title) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CpsvContract.FavoritePost._ID, postID);
        contentValues.put(CpsvContract.FavoritePost.COLUMN_NAME_TAG, tags);
        contentValues.put(CpsvContract.FavoritePost.COLUMN_NAME_IMAGE_URL_PREVIEW, previewURL);
        contentValues.put(CpsvContract.FavoritePost.COLUMN_NAME_IMAGE_URL, fullResURL);
        contentValues.put(CpsvContract.FavoritePost.COLUMN_NAME_TITLE, title);
        return db.insert(CpsvContract.FavoritePost.TABLE_NAME, null, contentValues);
    }

    public ArrayList<UserfavObject> queryDB() {
        userFavCursor = db.query(CpsvContract.FavoritePost.TABLE_NAME, null, null, null, null, null, null);
        ArrayList<UserfavObject> favList = new ArrayList<>();
        while (userFavCursor.moveToNext()) {
            UserfavObject fav = new UserfavObject(userFavCursor.getInt(0), userFavCursor.getString(1), userFavCursor.getString(2), userFavCursor.getString(3), userFavCursor.getString(4));
            favList.add(fav);
        }
        return favList;
    }

    public void closeDB() {
        db.close();
    }

}
