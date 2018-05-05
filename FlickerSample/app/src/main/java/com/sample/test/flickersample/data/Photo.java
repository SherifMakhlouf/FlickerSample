package com.sample.test.flickersample.data;

/**
 * Single photo model
 */
public class Photo {
    /**
     * Photo remote url
     */
    public final String url;

    public Photo(String url) {
        this.url = url;
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || !(o instanceof Photo)) {
            return false;
        }
        
        return ((Photo) o).url.equals(url);
    }

    @Override
    public String toString() {
        return url;
    }
}
