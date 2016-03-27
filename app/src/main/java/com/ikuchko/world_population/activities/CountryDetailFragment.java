package com.ikuchko.world_population.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ikuchko.world_population.R;
import com.ikuchko.world_population.models.Country;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountryDetailFragment extends Fragment {
    @Bind(R.id.countryTextView) TextView countryTextView;
    @Bind(R.id.flagImageView) ImageView flagImageView;
    @Bind(R.id.capitalTextView) TextView capitalTextView;
    @Bind(R.id.regionTextView) TextView regionTextView;
    @Bind(R.id.nativeNameTextView) TextView nativeNameTextView;
    @Bind(R.id.alpha2CodeTextView) TextView alpha2CodeTextView;
    @Bind(R.id.alpha3CodeTextView) TextView alpha3CodeTextView;
    @Bind(R.id.populationTextView) TextView populationTextView;
    @Bind(R.id.areaTextView) TextView areaTextView;
    @Bind(R.id.bordersTextView) TextView bordersTextView;
    @Bind(R.id.currenciesTextView) TextView currenciesTextView;
    @Bind(R.id.languagesTextView) TextView languagesTextView;
    private Country country;


    public static CountryDetailFragment newInstance(Country country) {
        CountryDetailFragment countryDetailFragment = new CountryDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("country", Parcels.wrap(country));
        countryDetailFragment.setArguments(args);
        return countryDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        country = Parcels.unwrap(getArguments().getParcelable("country"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_country_detail, container, false);
        ButterKnife.bind(this, view);

        Picasso.with(view.getContext()).load(country.getFlagImage()).fit().into(flagImageView);
        countryTextView.setText(country.getName());
        capitalTextView.setText(country.getCapital());
        regionTextView.setText(country.getRegion());
        nativeNameTextView.setText(country.getNativeName());
        alpha2CodeTextView.setText(country.getAlpha2Code());
        alpha3CodeTextView.setText(country.getAlpha3Code());
        populationTextView.setText(country.getPopulation().toString());
        areaTextView.setText(country.getArea().toString());
        bordersTextView.setText(android.text.TextUtils.join(", ", country.getBorders()));
        currenciesTextView.setText(android.text.TextUtils.join(", ", country.getCurrencies()));
        languagesTextView.setText(android.text.TextUtils.join(", ", country.getLanguages()));
        return view;
    }

}
