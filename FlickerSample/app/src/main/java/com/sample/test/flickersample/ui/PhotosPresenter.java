package com.sample.test.flickersample.ui;

import com.sample.test.flickersample.data.model.Photo;
import com.sample.test.flickersample.domain.PhotosInteractor;

import java.util.List;

import static com.sample.test.flickersample.ui.PhotosPresenter.PhotosViewModel.State.Data;
import static com.sample.test.flickersample.ui.PhotosPresenter.PhotosViewModel.State.LoadingAll;
import static com.sample.test.flickersample.ui.PhotosPresenter.PhotosViewModel.data;
import static com.sample.test.flickersample.ui.PhotosPresenter.PhotosViewModel.loadingAll;
import static com.sample.test.flickersample.ui.PhotosPresenter.PhotosViewModel.loadingMore;

/**
 * Photo search presenter
 */
public class PhotosPresenter implements PhotosInteractor.SearchListener {
    private PhotosInteractor interactor;
    private PhotosStateView view;

    PhotosPresenter(PhotosInteractor interactor) {
        this.interactor = interactor;
    }

    void onStart(PhotosStateView view) {
        this.view = view;
        interactor.setSearchResultListener(this);
    }

    void onStop() {
        interactor.setSearchResultListener(null);
    }

    void onUserSearch(String searchText) {
        view.updateState(loadingAll());
        interactor.query(searchText);
    }

    void onScrollEnd(String searchText) {
        view.updateState(loadingMore());
        interactor.loadNext(searchText);
    }

    @Override
    public void onSearchResult(List<Photo> result) {
        view.updateState(data(result));
    }

    public interface PhotosStateView {
        void updateState(PhotosViewModel state);
    }

    public static class PhotosViewModel {
        public final State state;
        public final List<Photo> data;

        private PhotosViewModel(State state, List<Photo> data) {
            this.state = state;
            this.data = data;
        }

        static PhotosViewModel loadingAll() {
            return new PhotosViewModel(LoadingAll, null);
        }

        static PhotosViewModel loadingMore() {
            return new PhotosViewModel(State.LoadingMore, null);
        }

        static PhotosViewModel error() {
            return new PhotosViewModel(State.Error, null);
        }

        static PhotosViewModel data(List<Photo> list) {
            return new PhotosViewModel(Data, list);
        }

        public enum State {
            LoadingAll,
            LoadingMore,
            Error,
            Data
        }
    }
}
