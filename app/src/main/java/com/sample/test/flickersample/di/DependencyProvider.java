package com.sample.test.flickersample.di;

import android.app.ActivityManager;
import android.content.Context;

import com.sample.test.flickersample.features.query.data.repository.PhotosRepository;
import com.sample.test.flickersample.features.query.data.repository.network.FlickerRepository;
import com.sample.test.flickersample.features.query.data.repository.network.PhotoParser;
import com.sample.test.flickersample.features.query.domain.PhotosInteractor;
import com.sample.test.flickersample.features.query.ui.PhotosPresenter;
import com.sample.test.flickersample.util.PhotosCache;
import com.sample.test.flickersample.util.ScheduledExecutor;

import java.util.concurrent.Executor;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Provides dependencies
 */
public class DependencyProvider {
    private final PhotosInteractor photosInteractor;
    private final PhotosCache photosCache;


    public DependencyProvider(Context context) {
        photosInteractor = new PhotosInteractor(providePhotosRepository(), provideExecutor());
        photosCache = createCache(context);
    }

    private PhotosCache createCache(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        int memoryClassByte = activityManager.getMemoryClass() * 1024 * 1024;
        return new PhotosCache(memoryClassByte / 8);
    }

    public PhotosCache provideCache() {
        return photosCache;
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

    private Executor provideExecutor() {
        return new ScheduledExecutor();
    }
}
