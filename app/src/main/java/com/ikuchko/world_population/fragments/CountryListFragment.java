package com.ikuchko.world_population.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ikuchko.world_population.R;
import com.ikuchko.world_population.adapters.CountryListAdapter;
import com.ikuchko.world_population.models.Country;
import com.ikuchko.world_population.services.CountriesService;
import com.ikuchko.world_population.services.WorldBankService;
import com.ikuchko.world_population.util.OnCountrySelectedListener;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountryListFragment extends Fragment {
    private static final String TAG = CountryListFragment.class.getSimpleName();
    @Bind(R.id.countryRecyclerView) RecyclerView countryRecyclerView;
    public static ProgressDialog loadingDialog;
    OnCountrySelectedListener onCountrySelectedListener;

    public CountryListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onCountrySelectedListener = (OnCountrySelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + e.getMessage());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country_list, container, false);
        ButterKnife.bind(this, view);
        initializeProgressDialog();
        loadingDialog.show();

        getCountries();
        return view;
    }

    private void initializeProgressDialog() {
        loadingDialog = new ProgressDialog(getContext());
        loadingDialog.setTitle("loading...");
        loadingDialog.setMessage("Preparing data...");
        loadingDialog.setCancelable(false);
    }

    private void getCountries() {
        final CountriesService countryService = new CountriesService(getContext());

        if (Country.getCountryList().size() == 0) {
            countryService.findCountries(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    CountriesService.processCountries(response);
//                    getIndicators(INDICATOR_GDP);
//                    getIndicators(INDICATOR_INFLATION);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            populateRecycleView();
                        }
                    });

                }
            });
        } else {
            populateRecycleView();
        }
    }

    private void getIndicators (final String indicator) {
        for (final Country country : Country.getCountryList()) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    final WorldBankService worldBankService = new WorldBankService(getActivity(), country.getAlpha2Code());
                    worldBankService.findIndicator(indicator, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            WorldBankService.processIndicator(response);
                        }
                    });
                }
            };

            Thread thread = new Thread(runnable);
            thread.setName("indicatorRequest");
            thread.start();
        }
    }


    private void populateRecycleView() {
        CountryListAdapter adapter = new CountryListAdapter(Country.getCountryList(), getContext(), onCountrySelectedListener);
        countryRecyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        countryRecyclerView.setLayoutManager(layoutManager);
        countryRecyclerView.setHasFixedSize(true);
        loadingDialog.hide();
    }

}
