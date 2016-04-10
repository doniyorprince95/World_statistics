package com.ikuchko.world_population.apapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Query;
import com.ikuchko.world_population.R;
import com.ikuchko.world_population.models.Country;
import com.ikuchko.world_population.util.FirebaseRecyclerAdapter;

/**
 * Created by iliak on 4/9/16.
 */
public class FirebaseWishlistAdapter extends FirebaseRecyclerAdapter<WishlistViewHolder, Country> {

    public FirebaseWishlistAdapter (Query query, Class<Country> itemClass) {
        super(query, itemClass);
    }

    @Override
    public WishlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.country_list_item, parent, false);
        return new WishlistViewHolder(view, getItems());
    }

    @Override
    public void onBindViewHolder(WishlistViewHolder holder, int position) {
        holder.bindCountry(getItem(position));
    }

    @Override
    protected void itemAdded(Country item, String key, int position) {

    }

    @Override
    protected void itemChanged(Country oldItem, Country newItem, String key, int position) {

    }

    @Override
    protected void itemRemoved(Country item, String key, int position) {

    }

    @Override
    protected void itemMoved(Country item, String key, int oldPosition, int newPosition) {

    }


}
