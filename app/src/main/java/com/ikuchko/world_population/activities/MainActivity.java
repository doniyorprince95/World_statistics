package com.ikuchko.world_population.activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.ikuchko.world_population.apapters.CountriesAdapter;
import com.ikuchko.world_population.R;
import com.ikuchko.world_population.models.Country;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    TypedArray countriesStorage;
    Country country;
    ArrayList<Country> countryArrayTemp = new ArrayList<>();

    @Bind(R.id.countryGridView) GridView countryGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        countryGridView.setAdapter(new CountriesAdapter(MainActivity.this, prepareArrayOfCountries()));
    }

    public Country[] prepareArrayOfCountries () {
        TypedArray specificCountry;
        Integer population;
        Integer imageId;
        String countryName;
        countriesStorage = getResources().obtainTypedArray(R.array.countries);
        for (int i=0; i<countriesStorage.length(); i++) {
            specificCountry = getResources().obtainTypedArray(countriesStorage.getResourceId(i, -1));
            countryName = specificCountry.getString(0);
            population = Integer.parseInt(specificCountry.getString(1));
            imageId = specificCountry.getResourceId(2, -1);
            country = new Country(countryName, population, imageId);
            countryArrayTemp.add(country);
        }
        Country[] countryArray = new Country[countryArrayTemp.size()];
        return countryArrayTemp.toArray(countryArray);
    }

    //inflate the menu
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    //Determine if actionBar item was selected. If true then do corresponding actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.signin:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
