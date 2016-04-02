package com.ikuchko.world_population.services;

import android.content.Context;
import android.util.Log;

import com.ikuchko.world_population.R;
import com.ikuchko.world_population.models.Country;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by iliak on 4/1/16.
 */
public class WorldBankService {
    private final static String TAG = WorldBankService.class.getSimpleName();
    private final static String GDP_INDICATOR = "/indicators/NY.GDP.PCAP.CD";
    private String countryCode;
    private Context context;

    public WorldBankService(Context context, String countryCode) {
        this.context = context;
        this.countryCode = countryCode;
    }

    public void findIndicator (Callback callback) {
        String url = context.getString(R.string.worldBankURL) + countryCode + GDP_INDICATOR + "format=json&date=1995:2014";

        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static void processIndicator (Response response) {
        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONArray resultJSON = new JSONArray(jsonData);
                JSONArray resultArayJSON = resultJSON.getJSONArray(1);
                for (int i=0; i<resultArayJSON.length(); i++) {
                    JSONObject indicatorsJSON = resultArayJSON.getJSONObject(i);
                    JSONObject indicatorJSON = indicatorsJSON.getJSONObject("indicator");
                    String indicatorName = indicatorJSON.getString("value");
                    JSONObject coutryJSON = indicatorsJSON.getJSONObject("country");
                    String countryCode = coutryJSON.getString("id");
                    String value = indicatorsJSON.getString("value");
                    String date = indicatorsJSON.getString("date");

                    Country country = Country.getCountryByCode(countryCode);
                    if (country != null) {
                        country.setIndicatorGDP(indicatorName, value, date);
                    }
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (JSONException jsone) {
            Log.d(TAG, "error stack bellow");
            jsone.printStackTrace();
        }
    }
}
