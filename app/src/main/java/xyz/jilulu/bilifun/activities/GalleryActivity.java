package xyz.jilulu.bilifun.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import xyz.jilulu.bilifun.R;
import xyz.jilulu.bilifun.adapters.KonaAdapter;
import xyz.jilulu.bilifun.helpers.KonaObject;

/**
 * Created by jamesji on 27/2/2016.
 */
public class GalleryActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private int pageNumber = 1;

    private ArrayList<KonaObject> konaObjectArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        final String[] origActivityInfo = getIntent().getStringArrayExtra(Intent.EXTRA_TEXT);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        else {
            Toast.makeText(GalleryActivity.this, "Where's my ActionBar? ", Toast.LENGTH_SHORT).show();
        }
        //http://konachan.net/post?tags=kousaka_honoka%20order:fav%20rating:safe
        String url = "http://konachan.net/post.json?tags=" + origActivityInfo[1] +
                "%20order:score%20rating:safe" + "&page=" + pageNumber;
        Log.d("OKHTTP", url);
        parser mParser = new parser();
        mParser.execute(url);

        mRecyclerView = (RecyclerView) findViewById(R.id.gallery_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                final int NUM_ITEMS_BEFORE_REQUESTING = 2;

                if (mLayoutManager.findLastCompletelyVisibleItemPosition() ==
                        mLayoutManager.getItemCount() - NUM_ITEMS_BEFORE_REQUESTING
                        && konaObjectArrayList.get(konaObjectArrayList.size()-1) != null) {
                    konaObjectArrayList.add(null);
                    mAdapter.notifyDataSetChanged();
                    System.out.println(konaObjectArrayList.toString());
                    pageNumber += 1;
                    String url = "http://konachan.net/post.json?tags=" + origActivityInfo[1] +
                            "%20order:score%20rating:safe" + "&page=" + pageNumber;
                    Log.d("OKHTTP", url);
                    parser mParser = new parser();
                    mParser.execute(url);
                }
            }
        });
    }

    class parser extends AsyncTask<String, Void, Void> {
        private OkHttpClient client = new OkHttpClient();
        private String json;
        JsonParser parser = new JsonParser();

        String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                json = run(params[0]);
            } catch (IOException e) {
                Log.e("OKHTTP/ERROR", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            JsonArray array = (JsonArray) parser.parse(json);

            if (mAdapter == null) {
                konaObjectArrayList = new ArrayList<>(parseKonaObjects(array));
                mAdapter = new KonaAdapter(konaObjectArrayList);
                mRecyclerView.setAdapter(mAdapter);
                findViewById(R.id.activity_gallery_progress_bar).setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            } else {
                konaObjectArrayList.remove(konaObjectArrayList.size() - 1);
                konaObjectArrayList.addAll(parseKonaObjects(array));
                mAdapter.notifyDataSetChanged();
                System.out.println(konaObjectArrayList.toString());
            }
        }
    }

    private ArrayList<KonaObject> parseKonaObjects (JsonArray ja) {
        ArrayList<KonaObject> konaObjects = new ArrayList<KonaObject>();
        for (int i = 0; i < ja.size(); i++) {
            konaObjects.add(new KonaObject(ja.get(i).getAsJsonObject()));
        }
        return konaObjects;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }
}
