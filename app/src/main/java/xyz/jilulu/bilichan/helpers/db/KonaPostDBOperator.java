package xyz.jilulu.bilichan.helpers.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by jamesji on 3/4/2016.
 */
public class KonaPostDBOperator {
    private SQLiteDatabase db;
    private Cursor konaCursor;

    public KonaPostDBOperator(Context context) {
        KonaPostDBHelper helper = new KonaPostDBHelper(context);
        db = helper.getWritableDatabase();
    }

    public long insertEntry(String tag, int count) {
        ContentValues cv = new ContentValues();
        cv.put(CpsvContract.KonaPost.COLUMN_NAME_TAGNAME, tag);
        cv.put(CpsvContract.KonaPost.COLUMN_NAME_COUNT, count);
        return db.insert(CpsvContract.KonaPost.TABLE_NAME, null, cv);
    }

    public void closeDB() {
        db.close();
    }
}
