package com.ikuchko.world_population.activities;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ikuchko.world_population.R;
import com.ikuchko.world_population.WorldPopulationApplication;
import com.ikuchko.world_population.apapters.CountryListAdapter;
import com.ikuchko.world_population.models.Country;
import com.ikuchko.world_population.models.Indicator;
import com.ikuchko.world_population.models.User;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountryDetailFragment extends Fragment implements View.OnClickListener {
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
    @Bind(R.id.gdpPerCapitaTextView) TextView gdpPerCapitaTextView;
    @Bind(R.id.inflationTextView) TextView inflationTextView;
    @Bind(R.id.visitedButton) FloatingActionButton visitedButton;
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

        visitedButton.setOnClickListener(this);
        Picasso.with(view.getContext()).load(country.getFlagImage()).resize(300, 156).centerCrop().into(flagImageView);
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
        gdpPerCapitaTextView.setText(getIndicatorValue(MainActivity.INDICATOR_GDP));
        inflationTextView.setText(getIndicatorValue(MainActivity.INDICATOR_INFLATION));
        changeVisitedButton();
        MainActivity.loadingDialog.hide();
        return view;
    }

    private String getIndicatorValue (String indicatorId) {
        ArrayList<Indicator> indicators = new ArrayList<>();
        if (indicatorId.equals(MainActivity.INDICATOR_GDP)) {
            indicators = country.getGdpIndicators();
        } else if (indicatorId.equals(MainActivity.INDICATOR_INFLATION)) {
            indicators = country.getInflationIndicators();
        }
        for (Indicator indicator : indicators) {
            if (!(indicator.getValue().equals(""))) {
                switch (indicatorId) {
                    case MainActivity.INDICATOR_GDP : return "$" + indicator.getValue();
                    case MainActivity.INDICATOR_INFLATION : return indicator.getValue() + " %";
                }

            }
        }
        return "";
    }

    @Override
    public void onClick(View v) {
        if (!User.getUser().isCountryVisited(country)) {
            WorldPopulationApplication.getAppInstance().getFirebaseRef()
                    .child("visited_countries/" + User.getUser().getuId())
                    .push().setValue(country);
            User.getUser().addCountry(country);
            changeVisitedButton();
        } else {
            Toast.makeText(v.getContext(), country.getName() + " have been already added to your wishlist.", Toast.LENGTH_SHORT).show();
        }
    }

    private void changeVisitedButton() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        User user = User.getUser();
        if (user != null) {
            if (user.isCountryVisited(country)) {
                map.put("star", android.R.drawable.btn_star_big_on);
            } else {
                map.put("star", android.R.drawable.btn_star_big_off);
            }
            visitedButton.setImageResource(map.get("star"));
        }
    }
}
