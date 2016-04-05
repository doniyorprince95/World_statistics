package com.ikuchko.world_population.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.ui.auth.core.AuthProviderType;
import com.firebase.ui.auth.core.FirebaseLoginBaseActivity;
import com.firebase.ui.auth.core.FirebaseLoginError;
import com.ikuchko.world_population.R;
import com.ikuchko.world_population.WorldPopulationApplication;
import com.ikuchko.world_population.apapters.CountryListAdapter;
import com.ikuchko.world_population.models.Country;
import com.ikuchko.world_population.services.CountriesService;
import com.ikuchko.world_population.services.WorldBankService;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends FirebaseLoginBaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String INDICATOR_GDP = "NY.GDP.PCAP.CD";
    public static final String INDICATOR_INFLATION = "FP.CPI.TOTL.ZG";
    private CountryListAdapter adapter;
    private MenuItem signOption;
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
//                    getIndicators(INDICATOR_GDP);
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

    private void getIndicators (final String indicator) {
        for (final Country country : Country.getCountryList()) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
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
            };

            Thread thread = new Thread(runnable);
            thread.setName("indicatorRequest");
            thread.start();
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
        signOption = menu.findItem(R.id.signOption);
        menuBuilder();
        return true;
    }

    private void menuBuilder() {
        if (signOption != null) {
            if (getFirebaseRef().getAuth() != null) {
                signOption.setTitle("sign out");
                Log.d(TAG, getFirebaseRef().getAuth().getToken().toString());
            } else {
                signOption.setTitle("sign in");
            }
        }
    }

    //Determine if actionBar item was selected. If true then do corresponding actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.signOption:
                if (getFirebaseRef().getAuth() == null) {
                    showFirebaseLoginPrompt();
                } else {

//                    To logout from each Provider use logout();
//                    To keep connection with Provider, but unAuthorized form Firebase
//                    use ref.unauth();

                    logout();
//                    getFirebaseRef().unauth();
                }
//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Firebase getFirebaseRef() {
        return WorldPopulationApplication.getAppInstance().getFirebaseRef();
    }

    @Override
    protected void onFirebaseLoginProviderError(FirebaseLoginError firebaseLoginError) {

    }

    @Override
    protected void onFirebaseLoginUserError(FirebaseLoginError firebaseLoginError) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        setEnabledAuthProvider(AuthProviderType.PASSWORD);
        setEnabledAuthProvider(AuthProviderType.FACEBOOK);
    }

    @Override
    protected void onFirebaseLoggedIn(AuthData authData) {
        menuBuilder();
        Map<String, Object> providerData = authData.getProviderData();
        String url =((Map) ((Map) ((Map) providerData.get("cachedUserProfile")).get("picture")).get("data")).get("url").toString();
        Log.d(TAG, url);
    }

    @Override
    protected void onFirebaseLoggedOut() {
        menuBuilder();
    }


}
