package com.sample.test.flickersample.domain;

import com.sample.test.flickersample.data.model.PhotosList;
import com.sample.test.flickersample.data.repository.PhotosRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Business logic for photo loading
 */
public class PhotosInteractor {
    private final PhotosRepository repository;
    private final Executor executor;
    private AtomicInteger currentPage =  new AtomicInteger(1);
    private SearchListener listener;

    public PhotosInteractor(
            PhotosRepository repository,
            Executor executor
    ) {
        this.repository = repository;
        this.executor = executor;
    }

    public void setSearchResultListener(SearchListener listener) {
        this.listener = listener;
    }

    /**
     * Loads first page of the given query
     * @param query query string
     */
    public void query(final String query) {
        currentPage.set(1);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                listener.onSearchResult(
                        repository.query(query, currentPage.get())
                );
            }
        });
    }

    /**
     * Loads next page of the current query
     * @param query query string
     */
    public void loadNext(final String query) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                listener.onNextLoaded(
                        repository.query(query, currentPage.incrementAndGet())
                );
            }
        });
    }

    /**
     * Listener for search result
     */
    public interface SearchListener {

        void onSearchResult(PhotosList result);

        void onNextLoaded(PhotosList result);
    }

}
