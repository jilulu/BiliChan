package xyz.jilulu.bilichan.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import xyz.jilulu.bilichan.R;
import xyz.jilulu.bilichan.activities.MainActivity;
import xyz.jilulu.bilichan.adapters.KonaTagAdapter;
import xyz.jilulu.bilichan.helpers.data.KonaTag;
import xyz.jilulu.bilichan.helpers.db.CpsvContract;

/**
 * Created by jamesji on 4/3/2016.
 */
public class KonaFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.search_button) Button searchButton;
    @Bind(R.id.searchBar) EditText mySearchBox;
    @Bind(R.id.listViewDescriptor) TextView hintText;
    @Bind(R.id.kona_search_fragment_progress_bar) FrameLayout loadingIndicatorProgressBar;
    @Bind(R.id.copyright_notice) TextView copyright;
    @Bind(R.id.konaChanFragmentRelativeLayout) RelativeLayout relativeLayout;

    @Bind(R.id.tagRecycler) RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private IBinder ib;

    public KonaFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_kona_search, container, false);

        ButterKnife.bind(this, rootView);

        int i = getArguments().getInt(MainActivity.ARG_FRAGMENT_POSITION);
        String fragmentTitle = getResources().getStringArray(R.array.fragment_title_array)[i];
        getActivity().setTitle(fragmentTitle);

        FetchTagTask myFetchTagTask = new FetchTagTask();
        myFetchTagTask.execute();

        ib = rootView.getWindowToken();

        searchButton.setOnClickListener(this);
        copyright.setOnClickListener(this);

        mLayoutManager = new LinearLayoutManager(rootView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        return rootView;
    }

    private class FetchTagTask extends AsyncTask<String, Void, Void> {
        private ArrayList<KonaTag> data = new ArrayList<>();
        private final String[] url_json = {"http://konachan.net/tag.json?name=", "&type=4&order=count"};
        private String json_String;
        private Cursor cacheCursor;

        @Override
        protected Void doInBackground(String... params) {

            // Disable the search/recommendation results and show a rotation wheel to pacify users.
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingIndicatorProgressBar.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.INVISIBLE);
                }
            });

            Cursor posts = getContext().getContentResolver().query(CpsvContract.KonaPost.CONTENT_URI, null, null, null, null);
            if (posts != null && posts.getCount() != 0) {

                cacheCursor = posts;

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Query Successful! ", Toast.LENGTH_SHORT).show();
                    }
                });
                return null;
            } else {
                OkHttpClient client = new OkHttpClient();
                String param;
                try {
                    param = params[0];
                } catch (ArrayIndexOutOfBoundsException e) {
                    param = "";
                }
                String url = url_json[0] + param + url_json[1];
                Log.d("OKHTTP", url);
                Request request = new Request.Builder().url(url).build();

                try {
                    Response response = client.newCall(request).execute();
                    json_String = response.body().string();
                } catch (IOException e) {
                    Log.e("OKHTTP", e.toString());
                }
                return null;
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            // Disable the rotation wheel, enable search/recommendation results.
            loadingIndicatorProgressBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);

            if ((json_String == null || json_String.length() == 0) && cacheCursor == null) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Warning")
                        .setMessage("BiliChan requires an active Internet connection to function normally. ")
                        .setPositiveButton("OK", null)
                        .show();
                return;
            }

            // This is an Async task, which means user might have switched to another task as task finishes
            try {
                boolean userDidntMoveAway = getActivity().getFragmentManager().findFragmentById(R.id.content_frame) instanceof KonaFragment;
                if (!userDidntMoveAway) return;
            } catch (NullPointerException e) {
                return;
            }

            if (cacheCursor == null && json_String != null) {
                ContentResolver res = getContext().getContentResolver();
                JsonParser parser = new JsonParser();
                final JsonArray tagArray = parser.parse(json_String).getAsJsonArray();
                for (int i = 0; i < tagArray.size(); i++) {
                    KonaTag tempTag = new KonaTag(tagArray.get(i).getAsJsonObject().get("name").getAsString(),
                            tagArray.get(i).getAsJsonObject().get("count").getAsString());
                    data.add(tempTag);
                    ContentValues cv = new ContentValues();
                    cv.put(CpsvContract.KonaPost.COLUMN_NAME_TAGNAME, tagArray.get(i).getAsJsonObject().get("name").getAsString());
                    cv.put(CpsvContract.KonaPost.COLUMN_NAME_COUNT, tagArray.get(i).getAsJsonObject().get("count").getAsInt());
                    res.insert(CpsvContract.KonaPost.CONTENT_URI, cv);
                }
            } else if (cacheCursor != null && json_String == null) {
                while (cacheCursor.moveToNext()) {
                    int tag = cacheCursor.getColumnIndex(CpsvContract.KonaPost.COLUMN_NAME_TAGNAME);
                    int cnt = cacheCursor.getColumnIndex(CpsvContract.KonaPost.COLUMN_NAME_COUNT);
                    data.add(new KonaTag(cacheCursor.getString(tag), Integer.toString(cacheCursor.getInt(cnt))));
                }
                cacheCursor.close();
            }

            mAdapter = new KonaTagAdapter(data, getContext());
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == searchButton) {
            String userInput = mySearchBox.getText().toString();
            FetchTagTask myFetchTagTask = new FetchTagTask();
            myFetchTagTask.execute("*" + userInput + "*");
            hintText.setText(R.string.search_results);
            InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(ib, 0);
        } else if (v == copyright) {
            Intent visitKona = new Intent(Intent.ACTION_VIEW);
            visitKona.setData(Uri.parse("http://konachan.net/help/api"));
            startActivity(visitKona);
        }
    }
}
