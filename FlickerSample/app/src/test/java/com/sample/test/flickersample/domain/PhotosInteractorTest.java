package com.sample.test.flickersample.domain;

import android.support.annotation.NonNull;

import com.sample.test.flickersample.data.model.Photo;
import com.sample.test.flickersample.data.model.PhotosList;
import com.sample.test.flickersample.data.repository.PhotosRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.concurrent.Executor;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;

/**
 * Photos Interactor tests
 */
@RunWith(MockitoJUnitRunner.class)
public class PhotosInteractorTest {
    @Mock
    PhotosRepository repository;
    @Mock
    PhotosInteractor.SearchListener listener;
    private FakeExecutor executor = new FakeExecutor();
    private PhotosInteractor testee;


    @Before
    public void setup() {
        testee = new PhotosInteractor(repository, executor);
        testee.setSearchResultListener(listener);
    }

    @Test
    public void testQuery() throws Exception {
        // Given
        String query = "Kittens";
        int page = 1;
        PhotosList result = createPhotosList(page);

        given(repository.query(anyString(), anyInt()))
                .willReturn(result);

        // When
        testee.query(query);

        // Then
        verify(listener).onSearchResult(result);
    }

    @Test
    public void testLoadNext() throws Exception {
        // Given
        String query = "Kittens";
        int page = 1;
        PhotosList result = createPhotosList(page);

        given(repository.query(anyString(), anyInt()))
                .willReturn(result);

        // When
        testee.query(query);
        testee.loadNext(query);

        // Then
        verify(repository).query(query, 2);
        verify(listener).onNextLoaded(result);
    }


    private PhotosList createPhotosList(int page) {
        return new PhotosList(
                page,
                1,
                Collections.singletonList(new Photo("www.cat.com")));
    }

    static class FakeExecutor implements Executor {

        @Override
        public void execute(@NonNull Runnable r) {
            r.run();
        }
    }
}