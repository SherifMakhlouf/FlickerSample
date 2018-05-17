package com.sample.test.flickersample.di;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.sample.test.flickersample.features.query.data.repository.PhotosRepository;
import com.sample.test.flickersample.features.query.data.repository.network.FlickerRepository;
import com.sample.test.flickersample.features.query.data.repository.network.PhotoParser;
import com.sample.test.flickersample.features.query.domain.PhotosInteractor;
import com.sample.test.flickersample.features.query.ui.PhotosPresenter;
import com.sample.test.flickersample.util.ScheduledExecutor;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Provides dependencies
 */
public class DependencyProvider {
    private final PhotosInteractor photosInteractor;
    private final LruCache<String, Bitmap> lruCache;


    public DependencyProvider(Context context) {
        photosInteractor = new PhotosInteractor(providePhotosRepository(), new ScheduledExecutor());
        lruCache = createCache(context);
    }

    private LruCache<String, Bitmap> createCache(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        int memoryClassByte = activityManager.getMemoryClass() * 1024 * 1024;
        return new LruCache<>(memoryClassByte);
    }

    public LruCache<String, Bitmap> provideCache() {
        return lruCache;
    }

    public PhotosPresenter providePhotosPresenter() {
        return new PhotosPresenter(providePhotosInteractor());
    }

    private PhotosInteractor providePhotosInteractor() {
        return photosInteractor;
    }

    private PhotosRepository providePhotosRepository() {
        return new FlickerRepository(new PhotoParser());
    }
}
