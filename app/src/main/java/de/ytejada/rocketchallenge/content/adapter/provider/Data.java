package de.ytejada.rocketchallenge.content.adapter.provider;


import java.util.List;

import de.ytejada.rocketchallenge.content.adapter.ClothesItem;
import de.ytejada.rocketchallenge.content.adapter.Image;

/**
 * Represent the a data element retrieved from the Rest api. Methods of this class are public so that
 * GSON can access them
 */
class Data implements ClothesItem {

    private String name;
    private String brand;
    private String price;
    private List<Images> images;


    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public List<? extends Image> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }
}
