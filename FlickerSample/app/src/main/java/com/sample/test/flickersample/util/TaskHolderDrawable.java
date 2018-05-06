package com.sample.test.flickersample.util;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import java.lang.ref.WeakReference;

/**
 * Drawable to hold current running task.
 * note: This can be used to show loading indication
 */
class TaskHolderDrawable extends ColorDrawable {

    private final WeakReference<PhotoLoaderAsyncTask> bitmapLoaderTaskReference;

    TaskHolderDrawable(PhotoLoaderAsyncTask bitmapDownloaderTask) {
        super(Color.GRAY);
        bitmapLoaderTaskReference =
                new WeakReference<>(bitmapDownloaderTask);
    }

    PhotoLoaderAsyncTask getBitmapDownloaderTask() {
        return bitmapLoaderTaskReference.get();
    }
}
