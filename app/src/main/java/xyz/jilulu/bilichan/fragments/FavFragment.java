package xyz.jilulu.bilichan.fragments;

import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;

import xyz.jilulu.bilichan.R;
import xyz.jilulu.bilichan.activities.MainActivity;
import xyz.jilulu.bilichan.helpers.FavoriteDBHelper;
import xyz.jilulu.bilichan.helpers.FavoritePostContract;

/**
 * Created by jamesji on 11/3/2016.
 */
public class FavFragment extends Fragment {
    public FavFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fav, container, false);
        int i = getArguments().getInt(MainActivity.ARG_FRAGMENT_POSITION);
        String fragmentTitle = getResources().getStringArray(R.array.fragment_title_array)[i];
        getActivity().setTitle(fragmentTitle);

        rootView.findViewById(R.id.dbPopulateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDB();
            }
        });

        return rootView;
    }

    public void initDB() {
        FavoriteDBHelper dbHelper = new FavoriteDBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FavoritePostContract.FavoritePost._ID, 213523);
        values.put(FavoritePostContract.FavoritePost.COLUMN_NAME_TITLE, "Test Title");
        values.put(FavoritePostContract.FavoritePost.COLUMN_NAME_IMAGE_URL_PREVIEW, "Test preview link");
        values.put(FavoritePostContract.FavoritePost.COLUMN_NAME_IMAGE_URL, "Test full url");
        long rowID; // Return value will be "_ID" if successfully inserted, and -1 otherwise.
        rowID = db.insert(FavoritePostContract.FavoritePost.TABLE_NAME, "null", values);
        Cursor myCursor = db.query(FavoritePostContract.FavoritePost.TABLE_NAME, null, null, null, null, null, null);
        System.out.println(myCursor.toString());
        String[] titles = myCursor.getColumnNames();
        myCursor.close();
        ((TextView)getActivity().findViewById(R.id.dbtext)).setText(Arrays.toString(titles));
    }

}
