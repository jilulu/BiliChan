package com.jilulux.bilichan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jilulux.bilichan.Adapters.GalleryActivityRecyclerAdapter;
import com.jilulux.bilichan.Fragments.SearchFragment;

/**
 * Created by jamesji on 24/4/2016.
 */
public class GalleryActivity extends AppCompatActivity implements GalleryActivityRecyclerAdapter.OnDataSetLoaded {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private String title = null;
    private GalleryActivityRecyclerAdapter adapter;

    private static final int LOADING_THRESHOLD = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        title = getIntent().getStringExtra(SearchFragment.EXTRA);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title);
        } else {
            Toast.makeText(GalleryActivity.this, "Where's my ActionBar? ", Toast.LENGTH_SHORT).show();
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.gallery_recycler_view);
        assert mRecyclerView != null;
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter = new GalleryActivityRecyclerAdapter(title, this));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mLayoutManager.findLastVisibleItemPosition() == mLayoutManager.getItemCount() - LOADING_THRESHOLD && adapter.mDataset.get(adapter.mDataset.size() - 1) != null)
                    adapter.nextPage();
            }
        });
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

    @Override
    public void dataSetLoaded() {
        findViewById(R.id.activity_gallery_progress_bar).setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }
}
