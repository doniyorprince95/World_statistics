package com.ikuchko.world_population.activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ikuchko.world_population.R;
import com.ikuchko.world_population.apapters.CountryPagerAdapter;
import com.ikuchko.world_population.models.Country;

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

    //inflate the menu
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.country_detail_menu, menu);
        return true;
    }

    //Determine if actionBar item was selected. If true then do corresponding actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.share:
                Country currentCountry = countries.get(mViewPager.getCurrentItem());
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, currentCountry.getShareContent());
                intent.setType("text/plain");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent.createChooser(intent, getResources().getString(R.string.intent_title)));
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
