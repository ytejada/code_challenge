package de.ytejada.rocketchallenge.content.adapter;

/**
 * Representation of an image resource. Contains path to the resource as well as basic information about size and format.
 */
public interface Image {
    public String getPath();

    public String getFormat();

    public String getWidth();

    public String getHeight();

}
