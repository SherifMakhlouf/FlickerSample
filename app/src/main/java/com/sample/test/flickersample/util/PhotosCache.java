package com.sample.test.flickersample.util;

import android.graphics.Bitmap;
import android.util.LruCache;

public class PhotosCache extends LruCache<String, Bitmap>{
    public PhotosCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getByteCount();
    }
}
