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
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.auth.core.AuthProviderType;
import com.firebase.ui.auth.core.FirebaseLoginBaseActivity;
import com.firebase.ui.auth.core.FirebaseLoginError;
import com.ikuchko.world_population.R;
import com.ikuchko.world_population.WorldPopulationApplication;
import com.ikuchko.world_population.apapters.CountryListAdapter;
import com.ikuchko.world_population.models.Country;
import com.ikuchko.world_population.models.User;
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
    private MenuItem signOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //inflate the menu
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        signOption = menu.findItem(R.id.signOption);
        menuBuilder();
        return true;
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

//                    logout();
                    getFirebaseRef().unauth();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void menuBuilder() {
        if (signOption != null) {
            if (getFirebaseRef().getAuth() != null) {
                signOption.setTitle("sign out");
            } else {
                signOption.setTitle("sign in");

            }
        }
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
        String userName = providerData.get("displayName").toString();
        String profileUrl = providerData.get("profileImageURL").toString();
        new User(authData.getUid());
        User.getUser().setName(userName);
        User.getUser().setProfileUrl(profileUrl);
        getVisitedCountries();

        Log.d(TAG, User.getUser().getName());
        Log.d(TAG, User.getUser().getProfileUrl());
    }

    @Override
    protected void onFirebaseLoggedOut() {
        menuBuilder();
        User.destroy();
    }

    public void getVisitedCountries() {
        Firebase ref = new Firebase(getResources().getString(R.string.firebase_url) + "/favorite_countries/" +User.getUser().getuId());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot countrySnapshot : dataSnapshot.getChildren()) {
                    Country country = countrySnapshot.getValue(Country.class);
                    User.getUser().addCountry(country);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

}
