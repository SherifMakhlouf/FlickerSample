package com.sample.test.flickersample.data.repository.network;

import com.sample.test.flickersample.data.model.PhotosList;
import com.sample.test.flickersample.data.repository.PhotosRepository;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import static java.lang.String.format;

/**
 * Repository for flicker api.
 */
public class FlickerRepository implements PhotosRepository {

    public static final String API_KEY = "3e7cc266ae2b0e0d78e279ce8e361736";
    public static final String URL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=%s&format=json&nojsoncallback=1&safe_search=1&text=%s&page=%d";

    @Override
    public PhotosList query(String query, int page) {
        try {
            String response = requestPhotos(buildUrl(query, page));
            PhotoParser parser = new PhotoParser(); //todo inject this
            return parser.parseResponse(response);
        } catch (IOException | JSONException e) {
            e.printStackTrace(); //todo handle errors
            return null;
        }
    }

    private URL buildUrl(String query, int page) throws MalformedURLException, UnsupportedEncodingException {
        return new URL(
                format(
                        Locale.getDefault(),
                        URL,
                        API_KEY,
                        query,
                        page
                )
        );
    }

    private String requestPhotos(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            return in.toString();
        } finally {
            urlConnection.disconnect();
        }
    }

}
