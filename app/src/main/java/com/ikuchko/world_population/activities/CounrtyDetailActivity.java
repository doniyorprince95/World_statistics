package com.ikuchko.world_population.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ikuchko.world_population.R;
import com.ikuchko.world_population.apapters.CountryPagerAdapter;
import com.ikuchko.world_population.models.Country;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CounrtyDetailActivity extends AppCompatActivity {
    @Bind(R.id.viewPager) ViewPager mViewPager;
    private ArrayList<Country> countries = new ArrayList<>();
    private CountryPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counrty_detail);
        ButterKnife.bind(this);

        countries = Parcels.unwrap(getIntent().getParcelableExtra("country"));
        int startPosition = Integer.parseInt(getIntent().getStringExtra("position"));
        adapter = new CountryPagerAdapter(getSupportFragmentManager(), countries);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(startPosition);
    }
}
