package com.sample.test.flickersample.features.query.data.repository.network;

import com.sample.test.flickersample.features.query.data.model.PhotosList;
import com.sample.test.flickersample.features.query.data.repository.PhotosRepository;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    private static final String API_KEY = "3e7cc266ae2b0e0d78e279ce8e361736";
    private static final String URL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=%s&format=json&nojsoncallback=1&safe_search=1&text=%s&page=%d";
    private final PhotoParser parser;

    public FlickerRepository(PhotoParser parser) {
        this.parser = parser;
    }

    @Override
    public PhotosList query(String query, int page) {
        try {
            String response = requestPhotos(buildUrl(query, page));

            return parser.parseResponse(response);
        } catch (IOException | JSONException e) {
            // Do nothing, should handle error here
            return null;
        }
    }

    private URL buildUrl(String query, int page) throws MalformedURLException {
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
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            StringBuilder stringBuilder = new StringBuilder();

            while ((line = in.readLine()) != null) {
                stringBuilder.append(line);
            }

            return stringBuilder.toString();
        } finally {
            urlConnection.disconnect();
        }
    }

}
