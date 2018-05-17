package com.sample.test.flickersample;

import android.app.Application;

import com.sample.test.flickersample.di.DependencyProvider;

/**
 * Application class used to resolve dependencies.
 */
public class FlickerApplication extends Application {
    private DependencyProvider dependencyProvider;

    @Override
    public void onCreate() {
        super.onCreate();
        dependencyProvider = new DependencyProvider(this);
    }

    public DependencyProvider getDependencyProvider() {
        return dependencyProvider;
    }
}
