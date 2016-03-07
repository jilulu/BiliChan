package xyz.jilulu.bilifun;

import android.app.Application;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by jamesji on 29/2/2016.
 */
public class Global extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));
        Picasso built = builder.build();
        // built.setIndicatorsEnabled(true); // Uncomment this to enable image-corner squares
        // built.setLoggingEnabled(true);    // Uncomment this to enable debug logs
        Picasso.setSingletonInstance(built);
    }
}
