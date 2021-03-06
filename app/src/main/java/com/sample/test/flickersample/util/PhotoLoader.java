package com.sample.test.flickersample.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.LruCache;
import android.widget.ImageView;

/**
 * A helper class for loading remote images into image views
 */
public class PhotoLoader {

    private final LruCache<String, Bitmap> cache;

    public PhotoLoader(LruCache<String, Bitmap> cache) {
        this.cache = cache;
    }

    /**
     * Loads photo with given url into the given ImageView. If image is already cached
     * it will directly load it into the view, otherwise it will asynchronously load
     * the image from remote
     *
     * @param url       remote url.
     * @param imageView image view to load image in.
     */
    public void loadPhoto(String url, ImageView imageView) {
        if (cache.get(url) != null) {
            imageView.setImageBitmap(cache.get(url));
        } else if (cancelPotentialDownload(url, imageView)) {
            PhotoLoaderAsyncTask task = new PhotoLoaderAsyncTask(imageView, cache);
            TaskHolderDrawable downloadedDrawable = new TaskHolderDrawable(task);
            imageView.setImageDrawable(downloadedDrawable);
            task.execute(url);
        }
    }

    private boolean cancelPotentialDownload(String url, ImageView imageView) {
        PhotoLoaderAsyncTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);

        if (bitmapDownloaderTask != null) {
            String bitmapUrl = bitmapDownloaderTask.getUrl();
            if ((bitmapUrl == null) || (!bitmapUrl.equals(url))) {
                bitmapDownloaderTask.cancel(true);
            } else {
                // The same URL is already being downloaded.
                return false;
            }
        }

        return true;
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


}
