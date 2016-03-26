package com.ikuchko.world_population.services;

import android.content.Context;

import com.ikuchko.world_population.R;
import com.ikuchko.world_population.models.Country;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by iliak on 3/25/16.
 */
public class populationService {
    private Context context;

    public populationService(Context context) {
        this.context = context;
    }

    public void findCountries (Callback callback) {
        final String URL = context.getString(R.string.countryRequest);

        OkHttpClient client = new OkHttpClient.Builder().build();

        Request request = new Request.Builder().url(URL).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static void processCountries (Response response) {
        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONArray resultJSON = new JSONArray(jsonData);
                for (int i=0; i<resultJSON.length(); i++) {
                    JSONObject countryJSON = resultJSON.getJSONObject(i);
                    String name = countryJSON.getString("name");
                    String capital = countryJSON.getString("capital");
                    JSONArray altSpellingsJSON = countryJSON.getJSONArray("altSpellings");
                    String region = countryJSON.getString("region");
                    Integer population = countryJSON.getInt("population");
                    Integer area = countryJSON.getInt("area");
                    JSONArray timezonesJSON = countryJSON.getJSONArray("timezones");
                    ArrayList<String> timezones = new ArrayList<>();
                    for (int j=0; j<timezonesJSON.length(); j++) {
                        timezones.add(timezonesJSON.getString(j));
                    }
                    JSONArray bordersJSON = countryJSON.getJSONArray("borders");
                    ArrayList<String> borders = new ArrayList<>();
                    for (int j=0; j<bordersJSON.length(); j++) {
                        borders.add(bordersJSON.getString(j));
                    }
                    String nativeName = countryJSON.getString("nativeName");
                    String alpha2Code = countryJSON.getString("alpha2Code");
                    String alpha3Code = countryJSON.getString("alpha3Code");
                    JSONArray currenciesJSON = countryJSON.getJSONArray("currencies");
                    ArrayList<String> currencies = new ArrayList<>();
                    for (int j=0; j<currenciesJSON.length(); j++) {
                        currencies.add(currenciesJSON.getString(j));
                    }
                    JSONArray languagesJSON = countryJSON.getJSONArray("languages");
                    ArrayList<String> languages = new ArrayList<>();
                    for (int j=0; j<languagesJSON.length(); j++) {
                        languages.add(languagesJSON.getString(j));
                    }
                    new Country(name, capital, region, population, area, timezones, borders, nativeName, alpha2Code, alpha3Code, currencies, languages);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (JSONException jsone) {
            jsone.printStackTrace();
        }
    }
}
