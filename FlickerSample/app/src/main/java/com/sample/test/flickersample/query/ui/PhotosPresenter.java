package com.sample.test.flickersample.query.ui;

import com.sample.test.flickersample.query.data.model.Photo;
import com.sample.test.flickersample.query.domain.PhotosInteractor;

import java.util.List;

import static com.sample.test.flickersample.query.ui.PhotosPresenter.PhotosViewModel.State.Data;
import static com.sample.test.flickersample.query.ui.PhotosPresenter.PhotosViewModel.State.LoadingAll;
import static com.sample.test.flickersample.query.ui.PhotosPresenter.PhotosViewModel.data;
import static com.sample.test.flickersample.query.ui.PhotosPresenter.PhotosViewModel.loadingAll;
import static com.sample.test.flickersample.query.ui.PhotosPresenter.PhotosViewModel.loadingMore;

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || !(o instanceof PhotosViewModel)) return false;

            PhotosViewModel given = (PhotosViewModel) o;
            return given.state.equals(state)
                    && (data == null ? given.data == null : given.data.equals(data));
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + state.hashCode();
            result = 31 * result + (data == null ? 0 : data.hashCode());
            return result;
        }
    }
}
