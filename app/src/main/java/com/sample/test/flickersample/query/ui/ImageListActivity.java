package com.sample.test.flickersample.query.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.sample.test.flickersample.FlickerApplication;
import com.sample.test.flickersample.R;
import com.sample.test.flickersample.di.DependencyProvider;
import com.sample.test.flickersample.query.data.model.Photo;
import com.sample.test.flickersample.query.domain.PhotosInteractor;
import com.sample.test.flickersample.query.ui.PhotosPresenter.PhotosViewModel;
import com.sample.test.flickersample.util.PhotoLoader;

import java.util.List;

public class ImageListActivity extends AppCompatActivity implements PhotosPresenter.PhotosStateView {
    private PhotosPresenter presenter;
    private PhotosAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private EditText searchText;
    private DependencyProvider dependencyProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        dependencyProvider = ((FlickerApplication) getApplication()).getDependencyProvider();
        presenter = dependencyProvider.providePhotosPresenter();
        progressBar = findViewById(R.id.progress_bar);
        setupRecyclerView();
        setupSearchText();


    }

    private void setupSearchText() {
        searchText = findViewById(R.id.search_box);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.onUserSearch(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Do nothing
            }
        });
    }

    private void setupRecyclerView() {
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        adapter = new PhotosAdapter(new PhotoLoader(dependencyProvider.provideCache()));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (shouldLoadMoreImages(newState, layoutManager)) {
                    presenter.onScrollEnd(searchText.getText().toString());
                }
            }
        });
    }

    private boolean shouldLoadMoreImages(int newState, GridLayoutManager layoutManager) {
        return newState == RecyclerView.SCROLL_STATE_IDLE
                && layoutManager.findLastVisibleItemPosition() == adapter.getItemCount() - 1;
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void updateState(final PhotosViewModel state) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateStateOnUiThread(state);
            }
        });
    }

    private void updateStateOnUiThread(PhotosViewModel state) {
        switch (state.state) {
            case Data:
                setDataState(state.data);
                break;
            case LoadingAll:
                setLoadingAll(state.data);
                break;
        }

    }

    private void setLoadingAll(List<Photo> data) {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        adapter.setData(data);
    }

    private void setDataState(List<Photo> data) {
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        adapter.setData(data);
    }
}
