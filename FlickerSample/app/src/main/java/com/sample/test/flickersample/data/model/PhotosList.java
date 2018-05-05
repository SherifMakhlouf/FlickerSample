package com.sample.test.flickersample.data.model;

import java.util.List;

/**
 * Photo list model
 */
public class PhotosList {
    public final int page;
    public final int perPage;
    public final List<Photo> photos;

    public PhotosList(int page, int perPage, List<Photo> photos) {
        this.page = page;
        this.perPage = perPage;
        this.photos = photos;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + page;
        result = 31 * result + perPage;
        result = 31 * result + (photos == null ? 0 : photos.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || !(o instanceof PhotosList)) return false;

        PhotosList given = (PhotosList) o;
        return given.page == page
                && given.perPage == perPage
                && given.photos.equals(photos);
    }
}
