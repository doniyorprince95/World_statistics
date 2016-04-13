package com.ikuchko.world_population.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.ikuchko.world_population.R;
import com.ikuchko.world_population.adapters.FirebaseWishlistAdapter;
import com.ikuchko.world_population.models.Country;
import com.ikuchko.world_population.models.User;
import com.ikuchko.world_population.util.OnStartDragListener;
import com.ikuchko.world_population.util.SimpleItemTouchHelperCallback;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishlistFragment extends Fragment implements OnStartDragListener {
    private static final String TAG = WishlistFragment.class.getSimpleName();
    private Query query;
    FirebaseWishlistAdapter adapter;
    private ItemTouchHelper itemTouchHelper;

    @Bind(R.id.wishlistRecyclerView) RecyclerView wishlistRecyclerView;

//    public WishlistFragment() {
//        // Required empty public constructor
//    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onPause() {
        super.onPause();
        for (Country country : adapter.getItems()) {
            country.setIndex(Integer.toString(adapter.getItems().indexOf(country)));
            Firebase ref = new Firebase(getResources().getString(R.string.firebase_url)+"/favorite_countries/" + User.getUser().getuId() + "/" + country.getCountryUId());
            ref.setValue(country);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        ButterKnife.bind(this, view);
        Firebase.setAndroidContext(getContext());
        query = new Firebase(getResources().getString(R.string.firebase_url) + "/favorite_countries/" + User.getUser().getuId()).orderByChild("index");
        setupRecyclerView();
        return view;
    }

    private void setupRecyclerView() {
        adapter = new FirebaseWishlistAdapter(query, Country.class, this);
        wishlistRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
