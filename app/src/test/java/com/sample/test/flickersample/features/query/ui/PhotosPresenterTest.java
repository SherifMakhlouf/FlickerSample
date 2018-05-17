package com.sample.test.flickersample.features.query.ui;

import com.sample.test.flickersample.features.query.data.model.Photo;
import com.sample.test.flickersample.features.query.domain.PhotosInteractor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PhotosPresenterTest {
    @Mock
    PhotosInteractor photosInteractor;
    @Mock
    PhotosPresenter.PhotosStateView view;

    PhotosPresenter testee;

    @Before
    public void setup() {
        testee = new PhotosPresenter(photosInteractor);
        testee.onStart(view);
    }

    @Test
    public void test_onUserSearch_setLoading() throws Exception {
        // Given

        // When
        testee.onUserSearch("Anything");

        // Then
        verify(view).updateState(PhotosPresenter.PhotosViewModel.loadingAll());
    }

    @Test
    public void test_onUserSearch_queryInteractor() throws Exception {
        // Given
        String something = "Something";

        // When
        testee.onUserSearch(something);

        // Then
        verify(photosInteractor).query(something);
    }

    @Test
    public void onScrollEnd() throws Exception {
        // Given
        String something = "Something";

        // When
        testee.onScrollEnd(something);

        // Then
        verify(photosInteractor).loadNext(something);
    }

    @Test
    public void onSearchResult() throws Exception {
        // Given
        List<Photo> photos =
                Collections.singletonList(new Photo("www.cat.com"));
        // When
        testee.onSearchResult(photos);

        //Then
        verify(view).updateState(PhotosPresenter.PhotosViewModel.data(photos));
    }

}