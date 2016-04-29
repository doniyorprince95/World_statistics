package com.ikuchko.world_population.services;

import android.content.Context;
import android.util.Log;

import com.ikuchko.world_population.R;
import com.ikuchko.world_population.models.Country;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by iliak on 4/1/16.
 */
public class WorldBankService {
    private final static String TAG = WorldBankService.class.getSimpleName();
    private final static String GDP_INDICATOR = "/indicators/NY.GDP.PCAP.CD";
    public static final String INDICATOR_GDP = "NY.GDP.PCAP.CD";
    public static final String INDICATOR_INFLATION = "FP.CPI.TOTL.ZG";
    private String countryCode;
    private Context context;

    public WorldBankService(Context context, String countryCode) {
        this.context = context;
        this.countryCode = countryCode;
    }

    public void findIndicator (String indicator, Callback callback) {
        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
        String url = context.getString(R.string.worldBank_url) + countryCode + "/indicators/" + indicator + "?format=json&date=" + (currentYear - 5) + ":" + currentYear;

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
                    String indicatorId = indicatorJSON.getString("id");
                    JSONObject countryJSON = indicatorsJSON.getJSONObject("country");
                    String countryCode = countryJSON.getString("id");
                    String value;
                    try {
                        Double indicatorValue = indicatorsJSON.getDouble("value");
                        DecimalFormat df = new DecimalFormat("#.##");
                        df.setRoundingMode(RoundingMode.CEILING);
                        value = df.format(indicatorValue);
                    } catch (JSONException jsone) {
                        value = "null";
                    }
                    String date = indicatorsJSON.getString("date");

                    if (!(value.equals("null"))) {
                        Country country = Country.getCountry(countryCode);
                        if (country != null) {
                            country.setIndicatorGDP(indicatorId, indicatorName, value, date);
                        }
                    }

                    Log.d(TAG, countryCode+" | "+indicatorName+" | "+value+" | "+date);
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
