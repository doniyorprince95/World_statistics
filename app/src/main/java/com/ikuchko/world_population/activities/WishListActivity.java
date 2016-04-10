package com.ikuchko.world_population.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.client.realtime.util.StringListReader;
import com.ikuchko.world_population.R;
import com.ikuchko.world_population.WorldPopulationApplication;
import com.ikuchko.world_population.apapters.FirebaseWishlistAdapter;
import com.ikuchko.world_population.models.Country;
import com.ikuchko.world_population.models.User;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WishlistActivity extends AppCompatActivity {
    private static final String TAG = WishlistActivity.class.getSimpleName();
    private Query query;
    private Firebase firebaseRef;


    @Bind(R.id.wishlistRecyclerView) RecyclerView wishlistRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        ButterKnife.bind(this);
        Firebase.setAndroidContext(this);
        firebaseRef = WorldPopulationApplication.getAppInstance().getFirebaseRef();

        query = new Firebase(getResources().getString(R.string.firebase_url) + "/favorite_countries/" +User.getUser().getuId());
        setupRecyclerView();
    }


    private void setupRecyclerView() {
        FirebaseWishlistAdapter adapter = new FirebaseWishlistAdapter(query, Country.class);
        wishlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        wishlistRecyclerView.setAdapter(adapter);
    }
}
