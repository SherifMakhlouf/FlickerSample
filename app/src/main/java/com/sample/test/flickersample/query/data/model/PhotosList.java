package com.sample.test.flickersample.query.data.model;

import java.util.List;

/**
 * Photo list model
 */
public class PhotosList {
    public final int page;
    public final List<Photo> photos;

    public PhotosList(int page, List<Photo> photos) {
        this.page = page;
        this.photos = photos;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + page;
        result = 31 * result + (photos == null ? 0 : photos.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || !(o instanceof PhotosList)) return false;

        PhotosList given = (PhotosList) o;
        return given.page == page
                && given.photos.equals(photos);
    }
}
