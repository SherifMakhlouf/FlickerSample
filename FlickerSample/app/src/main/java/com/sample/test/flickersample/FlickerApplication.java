package com.sample.test.flickersample;

import android.app.ActivityManager;
import android.app.Application;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.sample.test.flickersample.query.data.repository.PhotosRepository;
import com.sample.test.flickersample.query.data.repository.network.FlickerRepository;
import com.sample.test.flickersample.query.data.repository.network.PhotoParser;
import com.sample.test.flickersample.query.domain.PhotosInteractor;

import java.util.concurrent.Executors;

/**
 * Application class used to resolve dependencies.
 */
public class FlickerApplication extends Application {
    private PhotosInteractor interactor;
    private LruCache<String, Bitmap> photoCache;

    @Override
    public void onCreate() {
        super.onCreate();
        PhotosRepository photosRepository = new FlickerRepository(new PhotoParser());
        interactor = new PhotosInteractor(photosRepository,
                Executors.newSingleThreadScheduledExecutor());
        createPhotosCache();
    }

    private void createPhotosCache() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        int memoryClassByte = activityManager.getMemoryClass() * 1024 * 1024;
        photoCache = new LruCache<>(memoryClassByte);
    }

    public PhotosInteractor getPhotosInteractor(){
        return interactor;
    }

    public LruCache<String, Bitmap> getPhotoCache(){
        return photoCache;
    }
}
