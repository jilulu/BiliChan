package xyz.jilulu.bilichan.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import xyz.jilulu.bilichan.Adapters.SearchFragmentTagAdapter;
import xyz.jilulu.bilichan.GalleryActivity;
import xyz.jilulu.bilichan.Helpers.KonaTag;
import xyz.jilulu.bilichan.R;

public class SearchFragment extends android.app.Fragment {

    public static final String EXTRA = "This GalleryActivity fired up by Search Fragment";
    SearchFragmentTagAdapter adapter;

    @Bind(R.id.fragment_search_list_view)
    ListView listView;
    @Bind(R.id.fragment_search_box)
    EditText mySearchBox;
    @Bind(R.id.fragment_search_progress_bar)
    FrameLayout loadingIndicatorProgressBar;
    @Bind(R.id.fragment_search_root_rel_layout)
    RelativeLayout relativeLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this, rootView);

        FetchTagTask myFetchTagTask = new FetchTagTask();
        myFetchTagTask.execute();

        mySearchBox.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        String userInput = v.getText().toString();
                        FetchTagTask myFetchTagTask = new FetchTagTask();
                        myFetchTagTask.execute("*" + userInput + "*");
                        InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        manager.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
                        return true;
                    }
                }
        );

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

            if (json_String == null || json_String.length() == 0) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Warning")
                        .setMessage("BiliChan requires an active Internet connection to function normally. ")
                        .setPositiveButton("OK", null)
                        .show();
                return;
            }

            JsonParser parser = new JsonParser();
            final JsonArray tagArray = parser.parse(json_String).getAsJsonArray();
            for (int i = 0; i < tagArray.size(); i++) {
                KonaTag tempTag = new KonaTag(tagArray.get(i).getAsJsonObject().get("name").getAsString(),
                        tagArray.get(i).getAsJsonObject().get("count").getAsString());
                data.add(tempTag);
            }
            adapter = new SearchFragmentTagAdapter(getActivity(), R.layout.fragment_search_tag, data);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    KonaTag currentTag = data.get(position);
                    Intent konaIntent = new Intent(getActivity(), GalleryActivity.class);
                    konaIntent.putExtra(EXTRA, currentTag.getTagName());
                    startActivity(konaIntent);
                }
            });

        }
    }

}
