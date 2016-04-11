package com.ikuchko.world_population.apapters;

import android.support.v4.view.MotionEventCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.ikuchko.world_population.R;
import com.ikuchko.world_population.WorldPopulationApplication;
import com.ikuchko.world_population.models.Country;
import com.ikuchko.world_population.util.FirebaseRecyclerAdapter;
import com.ikuchko.world_population.util.ItemTouchHelperAdapter;
import com.ikuchko.world_population.util.OnStartDragListener;

import java.util.Collections;

/**
 * Created by iliak on 4/9/16.
 */
public class FirebaseWishlistAdapter extends FirebaseRecyclerAdapter<WishlistViewHolder, Country> implements ItemTouchHelperAdapter {
    private final OnStartDragListener dragStartListener;

    public FirebaseWishlistAdapter (Query query, Class<Country> itemClass, OnStartDragListener dragStartListener) {
        super(query, itemClass);
        this.dragStartListener = dragStartListener;
    }

    @Override
    public WishlistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_list_item_drag, parent, false);
        return new WishlistViewHolder(view, getItems());
    }

    @Override
    public void onBindViewHolder(final WishlistViewHolder holder, int position) {
        holder.bindCountry(getItem(position));
        holder.flagView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    dragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
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


    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(getItems(), fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        Firebase ref = WorldPopulationApplication.getAppInstance().getFirebaseRef();
        ref.child("favorite_countries/" + ref.getAuth().getUid() + "/" + getItem(position).getCountryUId()).removeValue();
    }

    @Override
    public int getItemCount() {
        return getItems().size();
    }
}
