package com.sample.test.flickersample.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * AsyncTask for loading images
 */
public class PhotoLoaderAsyncTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    private final LruCache<String, Bitmap> cache;
    private  String url;

    PhotoLoaderAsyncTask(ImageView imageView, LruCache<String, Bitmap> cache) {
        imageViewReference = new WeakReference<>(imageView);
        this.cache = cache;
    }

    String getUrl() {
        return url;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        this.url = strings[0];
        Bitmap bitmap = downloadBitmap(url);
//        bitmap.getByteCount();
//        cache.put(url, bitmap);
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(isCancelled()) {
            bitmap = null;
        }

        ImageView imageView = imageViewReference.get();
        if (imageView != null) {
            PhotoLoaderAsyncTask associatedTask = getBitmapDownloaderTask(imageView);
            // Change bitmap only if this asyncTask is still associated with it
            if (this == associatedTask) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    private PhotoLoaderAsyncTask getBitmapDownloaderTask(ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof TaskHolderDrawable) {
                TaskHolderDrawable downloadedDrawable = (TaskHolderDrawable) drawable;
                return downloadedDrawable.getBitmapDownloaderTask();
            }
        }

        return null;
    }

    private Bitmap downloadBitmap(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

            try {
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } finally {
                connection.disconnect();
            }
        } catch (IOException e) {
            // Do nothing, should handle error
        }

        return null;
    }
}
