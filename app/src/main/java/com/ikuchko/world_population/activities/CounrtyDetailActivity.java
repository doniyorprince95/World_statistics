package com.ikuchko.world_population.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.ikuchko.world_population.R;
import com.ikuchko.world_population.apapters.CountryPagerAdapter;
import com.ikuchko.world_population.models.Country;
import com.ikuchko.world_population.models.User;

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
        final MenuItem visited = menu.findItem(R.id.favorite);
        if (User.getUser() != null) {
            getVisitedCountries(visited);
        }
        return true;
    }

    //Determine if actionBar item was selected. If true then do corresponding actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.share:
                Country currentCountry = countries.get(mViewPager.getCurrentItem());
                Intent impIntent = new Intent();
                impIntent.setAction(Intent.ACTION_SEND);
                impIntent.putExtra(Intent.EXTRA_TEXT, currentCountry.getShareContent());
                impIntent.setType("text/plain");
                if (impIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(impIntent.createChooser(impIntent, getResources().getString(R.string.intent_title)));
                }
                return true;

            case R.id.favorite:
                Intent favoriteIntent = new Intent(CounrtyDetailActivity.this, WishListActivity.class);
                startActivity(favoriteIntent);
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getVisitedCountries(final MenuItem item) {
        Firebase ref = new Firebase(getResources().getString(R.string.firebase_url) + "/visited_countries/" +User.getUser().getuId());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                item.setTitle("Visited: " + dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
