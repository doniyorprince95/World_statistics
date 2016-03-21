package com.ikuchko.world_population;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;

/**
 * Created by iliak on 3/18/16.
 */
public class CountriesAdapter extends ArrayAdapter {
    private Country[] countryList;
    private Context context;
    private LayoutInflater inflater;

    public CountriesAdapter (Context context, Country[] countries) {
        super(context, R.layout.country_tile, countries);

        this.countryList = countries;
        this.context = context;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.country_tile, parent, false);
        }

        Picasso
                .with(context)
                .load(countryList[position].getImageId())
                .fit() // will explain later
                .into((ImageView) convertView.findViewById(R.id.flagView));

        TextView countryName = (TextView) convertView.findViewById(R.id.countryName);
        countryName.setText(countryList[position].getName());

        TextView population = (TextView) convertView.findViewById(R.id.population);
        population.setText(countryList[position].getPopulation().toString());

        return convertView;
    }

}
