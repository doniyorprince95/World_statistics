package com.ikuchko.world_population.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ikuchko.world_population.R;
import com.ikuchko.world_population.activities.CounrtyDetailActivity;
import com.ikuchko.world_population.fragments.CountryDetailFragment;
import com.ikuchko.world_population.fragments.CountryListFragment;
import com.ikuchko.world_population.models.Country;
import com.ikuchko.world_population.util.OnCountrySelectedListener;
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
    private OnCountrySelectedListener countrySelectedListener;
    private ArrayList<Country> countries = new ArrayList<>();
    private Context context;
    private int orientation;
    private  Integer position;

    public CountryListAdapter(ArrayList<Country> countries, Context context, OnCountrySelectedListener onCountrySelectedListener) {
        this.countries = countries;
        this.context = context;
        this.countrySelectedListener =  onCountrySelectedListener;
    }

    @Override
    public CountryListAdapter.CountryViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.country_list_item, parent, false);
        CountryViewHolder viewHolder = new CountryViewHolder (view, countries, countrySelectedListener);
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

        public CountryViewHolder(View itemView, ArrayList<Country> countryList, OnCountrySelectedListener onCountrySelectedListener) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this, itemView);
            countries = countryList;
            countrySelectedListener = onCountrySelectedListener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int currentPosition = getLayoutPosition();
                    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        countrySelectedListener.onCountrySelected(currentPosition, countries);
                        CountryDetailFragment countryDetailFragment = CountryDetailFragment.newInstance(countries.get(currentPosition));
                        FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.countryDetailContainer, countryDetailFragment);
                        fragmentTransaction.commit();
                    } else {
                        CountryListFragment.loadingDialog.show();
                        int itemPosition = getLayoutPosition();
                        Intent intent = new Intent(context, CounrtyDetailActivity.class);
                        intent.putExtra("position", itemPosition + "");
                        intent.putExtra("countries", Parcels.wrap(countries));
                        context.startActivity(intent);
                    }
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
                orientation = itemView.getResources().getConfiguration().orientation;
            } catch (NullPointerException npe) {
                Log.e(TAG, "nullPointException on: " + npe.getMessage());
                npe.printStackTrace();
            }
        }
    }
}
