package com.ikuchko.world_population.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ikuchko.world_population.fragments.CountryDetailFragment;
import com.ikuchko.world_population.models.Country;

import java.util.ArrayList;

/**
 * Created by iliak on 3/27/16.
 */
public class CountryPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Country> country;

    public CountryPagerAdapter (FragmentManager fm, ArrayList<Country> country) {
        super (fm);
        this.country = country;
    }

    @Override
    public Fragment getItem (int position) {
        return CountryDetailFragment.newInstance(country.get(position));
    }

    @Override
    public int getCount () {
        return country.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return country.get(position).getName();
    }
}
