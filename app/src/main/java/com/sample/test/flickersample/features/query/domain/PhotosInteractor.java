package com.sample.test.flickersample.features.query.domain;

import com.sample.test.flickersample.features.query.data.model.Photo;
import com.sample.test.flickersample.features.query.data.repository.PhotosRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Business logic for photo loading
 */
public class PhotosInteractor {
    private final PhotosRepository repository;
    private final Executor executor;
    private AtomicInteger currentPage = new AtomicInteger(1);
    private List<Photo> currentData = new ArrayList<>();
    private SearchListener listener;
    private String lastQuery;

    public PhotosInteractor(PhotosRepository repository, Executor executor) {
        this.repository = repository;
        this.executor = executor;
    }

    public void setSearchResultListener(SearchListener listener) {
        this.listener = listener;
    }

    /**
     * Loads first page of the given query
     *
     * @param query query string
     */
    public void query(final String query) {
        if (query.equals(lastQuery)) {
            listener.onSearchResult(currentData);
        } else {
            if (query.isEmpty()) {
                listener.onSearchResult(Collections.<Photo>emptyList());
            } else {
                lastQuery = query;
                currentPage.set(1);
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        currentData = repository.query(query, currentPage.get()).photos;
                        listener.onSearchResult(currentData);
                    }
                });
            }
        }
    }

    /**
     * Loads next page of the current query
     *
     * @param query query string
     */
    public void loadNext(final String query) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                currentData.addAll(
                        repository.query(query, currentPage.incrementAndGet()).photos
                );

                listener.onSearchResult(
                        currentData
                );
            }
        });
    }

    /**
     * Listener for search result
     */
    public interface SearchListener {

        /**
         * On new query result
         *
         * @param result list of photos associated with the result.
         */
        void onSearchResult(List<Photo> result);
    }

}
