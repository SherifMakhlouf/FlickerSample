package com.sample.test.flickersample.util;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import java.lang.ref.WeakReference;

/**
 * Todo
 */
public class TaskHolderDrawable extends ColorDrawable {

    private final WeakReference<PhotoLoaderAsyncTask> bitmapLoaderTaskReference;

    public TaskHolderDrawable(PhotoLoaderAsyncTask bitmapDownloaderTask) {
        super(Color.BLACK);
        bitmapLoaderTaskReference =
                new WeakReference<>(bitmapDownloaderTask);
    }

    public PhotoLoaderAsyncTask getBitmapDownloaderTask() {
        return bitmapLoaderTaskReference.get();
    }
}
