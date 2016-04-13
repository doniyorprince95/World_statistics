package com.ikuchko.world_population.util;

import com.ikuchko.world_population.models.Country;

import java.util.ArrayList;

/**
 * Created by iliak on 4/12/16.
 */
public interface OnCountrySelectedListener {
    public void onCountrySelected(Integer position, ArrayList<Country> countries);
}
