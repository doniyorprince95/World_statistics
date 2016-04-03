package com.ikuchko.world_population.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.ikuchko.world_population.R;
import com.ikuchko.world_population.apapters.CountryListAdapter;
import com.ikuchko.world_population.models.Country;
import com.ikuchko.world_population.services.CountriesService;
import com.ikuchko.world_population.services.WorldBankService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String INDICATOR_GDP = "NY.GDP.PCAP.CD";
    public static final String INDICATOR_INFLATION = "FP.CPI.TOTL.ZG";
    TypedArray countriesStorage;
    Country country;
    ArrayList<Country> countryArrayTemp = new ArrayList<>();
    private CountryListAdapter adapter;
    public static ProgressDialog loadingDialog;

    @Bind(R.id.countryRecyclerView) RecyclerView countryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initializeProgressDialog();
        loadingDialog.show();

        getCountries();
    }

    private void initializeProgressDialog() {
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setTitle("loading...");
        loadingDialog.setMessage("Preparing data...");
        loadingDialog.setCancelable(false);
    }

    private void getCountries() {
        final CountriesService countryService = new CountriesService(this);

        if (Country.getCountryList().size() == 0) {
            countryService.findCountries(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    CountriesService.processCountries(response);
                    getIndicators(INDICATOR_GDP);
//                    getIndicators(INDICATOR_INFLATION);

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

    private void getIndicators (String indicator) {
        for (Country country : Country.getCountryList()) {
            final WorldBankService worldBankService = new WorldBankService(MainActivity.this, country.getAlpha2Code());
            worldBankService.findIndicator(indicator, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    WorldBankService.processIndicator(response);
                }
            });
        }
    }


    private void populateRecycleView() {
        adapter = new CountryListAdapter(Country.getCountryList(), getApplicationContext());
        countryRecyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        countryRecyclerView.setLayoutManager(layoutManager);
        countryRecyclerView.setHasFixedSize(true);
        loadingDialog.hide();
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
