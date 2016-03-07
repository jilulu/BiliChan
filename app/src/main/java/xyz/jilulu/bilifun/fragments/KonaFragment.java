package xyz.jilulu.bilifun.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import xyz.jilulu.bilifun.R;
import xyz.jilulu.bilifun.activities.GalleryActivity;
import xyz.jilulu.bilifun.activities.MainActivity;
import xyz.jilulu.bilifun.adapters.KonaTagAdapter;
import xyz.jilulu.bilifun.helpers.KonaTag;

/**
 * Created by jamesji on 4/3/2016.
 */
public class KonaFragment extends Fragment {
    KonaTagAdapter adapter;

    @Bind(R.id.tagListView)
    ListView listView;
    @Bind(R.id.search_button)
    Button searchButton;
    @Bind(R.id.searchBar)
    EditText mySearchBox;
    @Bind(R.id.listViewDescriptor)
    TextView hintText;
    @Bind(R.id.kona_search_fragment_progress_bar)
    FrameLayout loadingIndicatorProgressBar;

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
//        adapter = new ArrayAdapter<String>(rootView.getContext(), R.layout.fragment_kona_search_tag, data);
//        ListView listView = (ListView) rootView.findViewById(R.id.tagListView);
//        listView.setAdapter(adapter);
//        listView = (ListView) rootView.findViewById(R.id.tagListView);
        FetchTagTask myFetchTagTask = new FetchTagTask();
        myFetchTagTask.execute();
//        Button searchButton = (Button) rootView.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String userInput = mySearchBox.getText().toString();
                FetchTagTask myFetchTagTask = new FetchTagTask();
                myFetchTagTask.execute("*" + userInput + "*");
                hintText.setText("Search Results");
                InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
            }
        });

        return rootView;
    }

    private class FetchTagTask extends AsyncTask<String, Void, Void> {
        private ArrayList<KonaTag> data = new ArrayList<>();
        private final String[] url_json = {"http://konachan.net/tag.json?name=", "&type=4&order=count"};
        private String json_String;

        @Override
        protected Void doInBackground(String... params) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingIndicatorProgressBar.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.INVISIBLE);
                }
            });

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

        @Override
        protected void onPostExecute(Void aVoid) {

            loadingIndicatorProgressBar.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            JsonParser parser = new JsonParser();
            final JsonArray tagArray = parser.parse(json_String).getAsJsonArray();
            for (int i = 0; i < tagArray.size(); i++) {
                KonaTag tempTag = new KonaTag(tagArray.get(i).getAsJsonObject().get("name").getAsString(),
                        tagArray.get(i).getAsJsonObject().get("count").getAsString());
                data.add(tempTag);
            }
            adapter = new KonaTagAdapter(getActivity(), R.layout.fragment_kona_search_tag, data);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    KonaTag currentTag = data.get(position);
                    String[] url = {currentTag.getTagName(), currentTag.getTagName()};
                    Intent konaIntent = new Intent(getActivity(), GalleryActivity.class);
                    konaIntent.putExtra(Intent.EXTRA_TEXT, url);
                    startActivity(konaIntent);
                }
            });

        }
    }
}
