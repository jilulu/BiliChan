package xyz.jilulu.bilichan.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.jilulu.bilichan.API.KonaCharacter;
import xyz.jilulu.bilichan.Adapters.SearchFragmentTagAdapter;
import xyz.jilulu.bilichan.Models.KonaTag;
import xyz.jilulu.bilichan.R;


public class SearchFragment extends android.app.Fragment implements Callback<List<KonaTag>>, TextView.OnEditorActionListener {

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

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://www.konachan.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    KonaCharacter konaCharacter = retrofit.create(KonaCharacter.class);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this, rootView);

        mySearchBox.setOnEditorActionListener(this);

        Call<List<KonaTag>> listCall = konaCharacter.listChars("");
        listCall.enqueue(this);

        return rootView;
    }

    @Override
    public void onResponse(Call<List<KonaTag>> call, retrofit2.Response<List<KonaTag>> response) {
        loadingIndicatorProgressBar.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        adapter = new SearchFragmentTagAdapter(getActivity(), R.layout.fragment_search_tag, response.body());
        listView.setAdapter(adapter);
    }

    @Override
    public void onFailure(Call<List<KonaTag>> call, Throwable t) {
        t.printStackTrace();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        listView.setVisibility(View.INVISIBLE);
        loadingIndicatorProgressBar.setVisibility(View.VISIBLE);
        Call<List<KonaTag>> listCall = konaCharacter.listChars(v.getText().toString());
        listCall.enqueue(this);
        return true;
    }
}
