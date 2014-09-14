package de.ytejada.rocketchallenge.content.adapter.provider;

import de.ytejada.rocketchallenge.content.adapter.Image;

/**
 * Bean representing an Image resource in the REST api. Methods of this class are public so that
 * GSON can access them
 */
class Images implements Image {

    private String path;
    private String format;
    private String width;
    private String height;

    @Override
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    @Override
    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
