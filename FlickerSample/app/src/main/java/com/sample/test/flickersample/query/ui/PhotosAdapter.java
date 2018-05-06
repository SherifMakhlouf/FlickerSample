package com.sample.test.flickersample.query.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sample.test.flickersample.R;
import com.sample.test.flickersample.query.data.model.Photo;
import com.sample.test.flickersample.util.PhotoLoader;

import java.util.Collections;
import java.util.List;

/**
 * Photos recyclerView adapter
 */
public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder> {
    private final PhotoLoader photoLoader;
    private List<Photo> photosList = Collections.emptyList();

    public PhotosAdapter(PhotoLoader photoLoader) {
        this.photoLoader = photoLoader;
    }

    public void setData(List<Photo> photosList) {
        this.photosList = photosList;
        notifyDataSetChanged();
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView v = (ImageView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_list_item, parent, false);

        return new PhotoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        photoLoader.loadPhoto(photosList.get(position).url, holder.imageView);
    }

    @Override
    public int getItemCount() {
        return photosList.size();
    }

    static class PhotoViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;

        PhotoViewHolder(ImageView itemView) {
            super(itemView);
            imageView = itemView;
        }
    }
}
