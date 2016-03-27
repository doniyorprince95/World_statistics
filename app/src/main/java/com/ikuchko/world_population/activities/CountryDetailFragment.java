package com.ikuchko.world_population.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ikuchko.world_population.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountryDetailFragment extends Fragment {


    public CountryDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country_detail, container, false);
    }

}
