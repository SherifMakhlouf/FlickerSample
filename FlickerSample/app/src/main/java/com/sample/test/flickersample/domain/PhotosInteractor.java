package com.sample.test.flickersample.domain;

import com.sample.test.flickersample.data.model.Photo;
import com.sample.test.flickersample.data.repository.PhotosRepository;

import java.util.ArrayList;
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
    private SearchListener listener;
    private List<Photo> currentData = new ArrayList<>();

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
        currentPage.set(1);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                currentData = repository.query(query, currentPage.get()).photos;
                listener.onSearchResult(currentData);
            }
        });
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

        void onSearchResult(List<Photo> result);
    }

}
