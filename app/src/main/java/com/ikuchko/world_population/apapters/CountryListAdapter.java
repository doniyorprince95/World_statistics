package com.ikuchko.world_population.apapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ikuchko.world_population.R;
import com.ikuchko.world_population.models.Country;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by iliak on 3/18/16.
 */
public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>{
    private ArrayList<Country> countries = new ArrayList<>();
    private Context context;

    public CountryListAdapter(ArrayList<Country> countries, Context context) {
        this.countries = countries;
        this.context = context;
    }

    @Override
    public CountryListAdapter.CountryViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.country_tile, parent, false);
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

        public CountryViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(context, itemView);
        }

        public void bindCountry(Country country) {
            Picasso.with(context).load(country.getFlagImage()).fit().into(flagView);
            countryName.setText(country.getName());
            population.setText(country.getPopulation());
        }
    }
}
