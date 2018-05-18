package com.sample.test.flickersample.features.query.data.repository;

import com.sample.test.flickersample.features.query.data.model.PhotosList;

/**
 * Repository for images
 */
public interface PhotosRepository {

    /**
     * Queries photos with the given query string and page number
     *
     * @param query search query
     * @param page  page number to be queried
     * @return {@link PhotosList} corresponding to the query
     */
    PhotosList query(String query, int page);
}
