package com.ikuchko.world_population.models;

import org.parceler.Parcel;

/**
 * Created by iliak on 4/1/16.
 */
@Parcel
public class Indicator {
    private String indicatorName;
    private String value;
    private String date;

    public Indicator() {

    }

    public Indicator(String indicatorName, String value, String date) {
        this.indicatorName = indicatorName;
        this.value = value;
        this.date = date;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public String getValue() {
        return value;
    }

    public String getDate() {
        return date;
    }
}
