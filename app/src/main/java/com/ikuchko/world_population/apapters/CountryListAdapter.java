package com.ikuchko.world_population.apapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ikuchko.world_population.R;
import com.ikuchko.world_population.activities.CounrtyDetailActivity;
import com.ikuchko.world_population.activities.MainActivity;
import com.ikuchko.world_population.fragments.CountryListFragment;
import com.ikuchko.world_population.models.Country;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by iliak on 3/18/16.
 */
public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>{
    private final static String TAG = CountryListAdapter.class.getSimpleName();
    private ArrayList<Country> countries = new ArrayList<>();
    private Context context;

    public CountryListAdapter(ArrayList<Country> countries, Context context) {
        this.countries = countries;
        this.context = context;
    }

    @Override
    public CountryListAdapter.CountryViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.country_list_item, parent, false);
        CountryViewHolder viewHolder = new CountryViewHolder (view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder (CountryListAdapter.CountryViewHolder holder, int position) {
        holder.bindCountry(countries.get(position));
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public class CountryViewHolder  extends RecyclerView.ViewHolder{
        @Bind(R.id.flagView) ImageView flagView;
        @Bind(R.id.countryName) TextView countryName;
        @Bind(R.id.population) TextView population;
        @Bind(R.id.capital) TextView capital;

        public CountryViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CountryListFragment.loadingDialog.show();
                    int itemPosition = getLayoutPosition();
                    Intent intent = new Intent(context, CounrtyDetailActivity.class);
                    intent.putExtra("position", itemPosition + "");
                    intent.putExtra("country", Parcels.wrap(countries));
                    context.startActivity(intent);
                }
            });
        }

        public void bindCountry(Country country) {
            try {
                String str = country.getFlagImage();
                Picasso.with(context).load(country.getFlagImage()).resize(300, 156).centerCrop().into(flagView);
                countryName.setText(country.getName());
                capital.setText(country.getCapital());
                population.setText(country.getPopulation().toString());
            } catch (NullPointerException npe) {
                Log.e(TAG, "nullPointException on: " + npe.getMessage());
                npe.printStackTrace();
            }
        }
    }
}
