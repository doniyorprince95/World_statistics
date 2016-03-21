package com.ikuchko.world_population;

/**
 * Created by iliak on 3/20/16.
 */
public class Country {
    private String mName;
    private Integer mPopulation;
    private Integer mImgUrl;

    public Country (String countryName, Integer population, Integer image) {
        this.mName = countryName;
        this.mPopulation = population;
        this.mImgUrl = image;
    }

    public String getName() {
        return mName;
    }

    public Integer getPopulation() {
        return mPopulation;
    }

    public Integer getImageId () {
        return mImgUrl;
    }
}
