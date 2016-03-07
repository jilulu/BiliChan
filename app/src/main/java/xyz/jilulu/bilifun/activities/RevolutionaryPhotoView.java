package xyz.jilulu.bilifun.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;
import uk.co.senab.photoview.PhotoViewAttacher;
import xyz.jilulu.bilifun.R;

/**
 * Created by jamesji on 3/3/2016.
 */
public class RevolutionaryPhotoView extends AppCompatActivity {
    static final String PHOTO_TAP_TOAST_STRING = "Photo Tap! X: %.2f %% Y:%.2f %% ID: %d";

    private TextView mCurrMatrixTv;

    private PhotoViewAttacher mAttacher;

    private Toast mCurrentToast;

    private Matrix mCurrentDisplayMatrix = null;

    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.revolutionary_photo);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final ImageView mImageView = (ImageView) findViewById(R.id.iv_photo);
        mCurrMatrixTv = (TextView) findViewById(R.id.tv_current_matrix);
        url = getIntent().getStringExtra(Intent.EXTRA_TEXT);

        imageDownload dl = new imageDownload();

        dl.execute(url);

        // The MAGIC happens here!
        mAttacher = new PhotoViewAttacher(mImageView);

        // Lets attach some listeners, not required though!
        mAttacher.setOnMatrixChangeListener(new MatrixChangeListener());
        mAttacher.setOnPhotoTapListener(new PhotoTapListener());
        Toast.makeText(RevolutionaryPhotoView.this, "Beginning download. ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.awesome_photo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.save:
                try {
                    saveToDisk();
                } catch (IOException e) {
                    Log.e("OKIO", e.toString());
                }
                break;
            default:
                break;
        }
        return true;
    }

    private boolean saveToDisk() throws IOException {
        if (bmpBytes == null) {
            Toast.makeText(RevolutionaryPhotoView.this, "Please wait until download finished", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (ActivityCompat.checkSelfPermission(RevolutionaryPhotoView.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RevolutionaryPhotoView.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            return false;
        }
        // Permission granted.
        Calendar cal = Calendar.getInstance();
        File path = Environment.getExternalStorageDirectory();
        File file = new File(path, "Download/" + cal.get(Calendar.YEAR)
                + (cal.get(Calendar.MONTH) <= 9 ? "0" + cal.get(Calendar.MONTH) : "" + cal.get(Calendar.MONTH))
                + (cal.get(Calendar.DATE) <= 9 ? "0" + cal.get(Calendar.DATE) : "" + cal.get(Calendar.DATE))
                + "." + url.hashCode() + ".jpg");
        BufferedSink bufferedSink = Okio.buffer(Okio.sink(file));
        bufferedSink.write(bmpBytes);
        bufferedSink.close();
        Toast.makeText(RevolutionaryPhotoView.this, "Saved to the Download folder", Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (ActivityCompat.checkSelfPermission(RevolutionaryPhotoView.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            try {
                saveToDisk();
            } catch (IOException e) {
                Log.e("OKIO", e.toString());
            }
        } else
            Toast.makeText(RevolutionaryPhotoView.this, "Permission denied. Unable to write to device storage. ", Toast.LENGTH_SHORT).show();
    }

    private class PhotoTapListener implements PhotoViewAttacher.OnPhotoTapListener {

        @Override
        public void onPhotoTap(View view, float x, float y) {
            float xPercentage = x * 100f;
            float yPercentage = y * 100f;

            showToast(String.format(PHOTO_TAP_TOAST_STRING, xPercentage, yPercentage, view == null ? 0 : view.getId()));
        }
    }

    private void showToast(CharSequence text) {
        if (null != mCurrentToast) {
            mCurrentToast.cancel();
        }

        mCurrentToast = Toast.makeText(RevolutionaryPhotoView.this, text, Toast.LENGTH_SHORT);
        mCurrentToast.show();
    }

    private class MatrixChangeListener implements PhotoViewAttacher.OnMatrixChangedListener {

        @Override
        public void onMatrixChanged(RectF rect) {
            mCurrMatrixTv.setText(rect.toString());
        }
    }

    private Bitmap bmp;
    private byte[] bmpBytes;

    private class imageDownload extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                bmp = run(params[0]);
            } catch (Exception e) {
                Log.e("imageDownoad", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ProgressBar pb = ((ProgressBar) findViewById(R.id.downloadProgressBar));
                    pb.setVisibility(View.GONE);
                    ImageView mImageView = (ImageView) findViewById(R.id.iv_photo);
                    mImageView.setVisibility(View.VISIBLE);
                    mImageView.setImageBitmap(bmp);
                }
            });
        }
    }

    public Bitmap run(String url) throws Exception {
        Request request = new Request.Builder()
                .url(url)
                .build();

        final ProgressListener progressListener = new ProgressListener() {
            @Override
            public void update(long bytesRead, long contentLength, boolean done) {
//                System.out.println(bytesRead);
//                System.out.println(contentLength);
//                System.out.println(done);
//                System.out.format("%d%% done\n", (100 * bytesRead) / contentLength);
                final int progress = (int) ((100 * bytesRead) / contentLength);
                final ProgressBar pb = ((ProgressBar) findViewById(R.id.downloadProgressBar));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb.setProgress(progress);
                    }
                });
            }
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder()
                                .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                                .build();
                    }
                })
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        bmpBytes = response.body().bytes();
        Bitmap bmp = BitmapFactory.decodeByteArray(bmpBytes, 0, bmpBytes.length);
        return bmp;
    }

    private static class ProgressResponseBody extends ResponseBody {

        private final ResponseBody responseBody;
        private final ProgressListener progressListener;
        private BufferedSource bufferedSource;

        public ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener) {
            this.responseBody = responseBody;
            this.progressListener = progressListener;
        }

        @Override
        public MediaType contentType() {
            return responseBody.contentType();
        }

        @Override
        public long contentLength() {
            return responseBody.contentLength();
        }

        @Override
        public BufferedSource source() {
            if (bufferedSource == null) {
                bufferedSource = Okio.buffer(source(responseBody.source()));
            }
            return bufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) {
                long totalBytesRead = 0L;

                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    // read() returns the number of bytes read, or -1 if this source is exhausted.
                    totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                    progressListener.update(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                    return bytesRead;
                }
            };
        }
    }

    interface ProgressListener {
        void update(long bytesRead, long contentLength, boolean done);
    }

}