package de.ytejada.rocketchallenge.content.adapter;

import java.util.List;

/**
 * Representation of a clothes item. Contains basic text info and a List of images
 * which can describe a clothes item
 */
public interface ClothesItem {
    public String getName();

    public String getBrand();

    public String getPrice();

    public List<? extends Image> getImages();

}
