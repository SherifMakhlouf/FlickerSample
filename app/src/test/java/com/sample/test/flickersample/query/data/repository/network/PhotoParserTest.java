package com.sample.test.flickersample.query.data.repository.network;

import com.sample.test.flickersample.query.data.model.Photo;
import com.sample.test.flickersample.query.data.model.PhotosList;

import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * Photo parser test
 */
public class PhotoParserTest {
    PhotoParser testee = new PhotoParser();

    @Test
    public void parseResponseTest() throws Exception {
        // Given
        String response =  "{\n" +
                "photos: \n" +
                "{\n" +
                "page: 1,\n" +
                "pages: 2151,\n" +
                "perpage: 100,\n" +
                "total: \"215054\",\n" +
                "photo: \n" +
                "[\n" +
                "{\n" +
                "id: \"222\",\n" +
                "owner: \"156120441@N05\",\n" +
                "secret: \"333\",\n" +
                "server: \"111\",\n" +
                "farm: 1,\n" +
                "title: \"11d697e0-3c63-4e86-aa66-a214dbd45a96\",\n" +
                "ispublic: 1,\n" +
                "isfriend: 0,\n" +
                "isfamily: 0\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "stat: \"ok\"\n" +
                "}";


        PhotosList expected = new PhotosList(
                1,
                Collections.singletonList(
                        new Photo("http://farm1.static.flickr.com/111/222_333.jpg")
                )
        );

        // When
        PhotosList result = testee.parseResponse(response);

        // Then
        assertEquals(expected, result);
    }

}