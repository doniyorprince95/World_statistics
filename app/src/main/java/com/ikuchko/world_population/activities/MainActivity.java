package com.ikuchko.world_population.activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.ikuchko.world_population.R;
import com.ikuchko.world_population.apapters.CountryListAdapter;
import com.ikuchko.world_population.models.Country;
import com.ikuchko.world_population.services.populationService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    TypedArray countriesStorage;
    Country country;
    ArrayList<Country> countryArrayTemp = new ArrayList<>();
    private CountryListAdapter adapter;

    @Bind(R.id.countryRecyclerView) RecyclerView countryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getCountries();
    }

    private void getCountries() {
        final populationService movieService = new populationService(this);

        if (Country.getCountryList().size() == 0) {
            movieService.findCountries(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    populationService.processCountries(response);

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            populateRecycleView();
                        }
                    });
                }
            });
        } else {
            populateRecycleView();
        }
    }

    private void populateRecycleView() {
        adapter = new CountryListAdapter(Country.getCountryList(), getApplicationContext());
        countryRecyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        countryRecyclerView.setLayoutManager(layoutManager);
        countryRecyclerView.setHasFixedSize(true);
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
