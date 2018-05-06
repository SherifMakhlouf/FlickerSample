package com.sample.test.flickersample.query.data.repository.network;

import com.sample.test.flickersample.query.data.model.Photo;
import com.sample.test.flickersample.query.data.model.PhotosList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Deserialize Flicker's API response
 */
public class PhotoParser {

    /**
     * Parse flicker's search response
     *
     * @param response Json string response
     * @return {@link PhotosList} containing parsed response
     * @throws JSONException
     */
    public PhotosList parseResponse(String response) throws JSONException {
        JSONObject root = new JSONObject(response);
        JSONObject paginatedPhotos = root.getJSONObject("photos");
        JSONArray photos = paginatedPhotos.getJSONArray("photo");

        List<Photo> photosList = new ArrayList<>();

        for (int i = 0; i < photos.length(); i++) {
            JSONObject singlePhoto = photos.getJSONObject(i);

            String photoUrl = buildPhotoUrl(
                    singlePhoto.getInt("farm"),
                    singlePhoto.getString("server"),
                    singlePhoto.getString("id"),
                    singlePhoto.getString("secret")
            );

            photosList.add(new Photo(photoUrl));
        }

        return new PhotosList(
                paginatedPhotos.getInt("page"),
                photosList
        );
    }

    private String buildPhotoUrl(int farm, String server, String id, String secret) {
        return String.format(
                Locale.getDefault(),
                "http://farm%d.static.flickr.com/%s/%s_%s.jpg",
                farm,
                server,
                id,
                secret
        );
    }
}
