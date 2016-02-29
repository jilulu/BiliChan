package xyz.jilulu.jamesji.bilifun.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import xyz.jilulu.jamesji.bilifun.R;
import xyz.jilulu.jamesji.bilifun.adapters.KonaAdapter;
import xyz.jilulu.jamesji.bilifun.helpers.KonaObject;

/**
 * Created by jamesji on 27/2/2016.
 */
public class GalleryActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        parser mParser = new parser();
        mParser.execute("http://konachan.com/post.json?tags=nishikino_maki");

        mRecyclerView = (RecyclerView) findViewById(R.id.gallery_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

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
                Log.i("OKHTTP/Success", "GET successful");
            } catch (IOException e) {
                Log.e("OKHTTP/ERROR", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            JsonArray array = (JsonArray) parser.parse(json);
            System.out.println(array.get(0).getAsJsonObject().get("preview_url"));
            ArrayList<KonaObject> konaObjectArrayList = parseKonaObjects(array);
            mAdapter = new KonaAdapter(konaObjectArrayList);
            mRecyclerView.setAdapter(mAdapter);
            findViewById(R.id.activity_gallery_progress_bar).setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }



    private ArrayList<KonaObject> parseKonaObjects (JsonArray ja) {
        ArrayList<KonaObject> konaObjects = new ArrayList<KonaObject>();
        for (int i = 0; i < ja.size(); i++) {
            konaObjects.add(new KonaObject(ja.get(i).getAsJsonObject()));
        }
        return konaObjects;
    }
}
