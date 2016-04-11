package com.ikuchko.world_population.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import com.ikuchko.world_population.util.OnStartDragListener;
import com.ikuchko.world_population.util.SimpleItemTouchHelperCallback;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WishlistActivity extends AppCompatActivity implements OnStartDragListener {
    private static final String TAG = WishlistActivity.class.getSimpleName();
    private Query query;
    FirebaseWishlistAdapter adapter;
    private ItemTouchHelper itemTouchHelper;


    @Bind(R.id.wishlistRecyclerView) RecyclerView wishlistRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        ButterKnife.bind(this);
        Firebase.setAndroidContext(this);

        query = new Firebase(getResources().getString(R.string.firebase_url) + "/favorite_countries/" +User.getUser().getuId()).orderByChild("index");
        setupRecyclerView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (Country country : adapter.getItems()) {
            country.setIndex(Integer.toString(adapter.getItems().indexOf(country)));
            Firebase ref = new Firebase(getResources().getString(R.string.firebase_url)+"/favorite_countries/" + User.getUser().getuId() + "/" + country.getCountryUId());
            ref.setValue(country);
        }
    }

    private void setupRecyclerView() {
        adapter = new FirebaseWishlistAdapter(query, Country.class, this);
        wishlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        wishlistRecyclerView.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(wishlistRecyclerView);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        itemTouchHelper.startDrag(viewHolder);
    }
}
