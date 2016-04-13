package com.ikuchko.world_population.models;

import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ikuchko.world_population.activities.MainActivity;
import com.ikuchko.world_population.services.WorldBankService;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by iliak on 3/20/16.
 */
@Parcel
@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {
    String countryUId;
    String index;
    String name;
    String capital;
    String region;
    Integer population;
    Integer area;
    @Nullable ArrayList<String> timezones;
    @Nullable ArrayList<String> borders;
    String nativeName;
    String alpha2Code;
    String alpha3Code;
    ArrayList<String> currencies;
    ArrayList<String> languages;
    String flagImage;
    ArrayList<Indicator> gdpIndicators = new ArrayList<>();
    ArrayList<Indicator> inflationIndicators = new ArrayList<>();
    static ArrayList<Country> countryList = new ArrayList<>();


    public Country() {

    }

    public ArrayList<Indicator> getGdpIndicators() {
        return gdpIndicators;
    }

    public ArrayList<Indicator> getInflationIndicators() {
        return inflationIndicators;
    }

    public Country(String name, String capital, String region, Integer population, Integer area, ArrayList<String> timezones, ArrayList<String> borders, String nativeName, String alpha2Code, String alpha3Code, ArrayList<String> currencies, ArrayList<String> languages) {
        this.name = name;
        this.capital = capital;
        this.region = region;
        this.population = population;
        this.area = area;
        this.timezones = timezones;
        this.borders = borders;
        this.nativeName = nativeName;
        this.alpha2Code = alpha2Code;
        this.alpha3Code = alpha3Code;
        this.currencies = currencies;
        this.languages = languages;
        this.flagImage = "http://www.geonames.org/flags/x/" + alpha2Code.toLowerCase() + ".gif";
        this.countryList.add(this);
        this.index = "not_specified";
    }

    public String getIndex() {
        return index;
    }

    public String getCountryUId() {
        return countryUId;
    }


    public String getFlagImage() {
        return flagImage;
    }

    public String getName() {
        return name;
    }

    public String getCapital() {
        return capital;
    }

    public String getRegion() {
        return region;
    }

    public Integer getPopulation() {
        return population;
    }

    public Integer getArea() {
        return area;
    }

    public ArrayList<String> getTimezones() {
        return timezones;
    }

    public ArrayList<String> getBorders() {
        try {
            if (borders.size() > 0) {
                int count = 0;
                outerloop:
                for (int i=0; i<countryList.size(); i++) {
                    for (int j=0; j<borders.size(); j++) {
                        if (countryList.get(i).getAlpha3Code().equals(borders.get(j))) {
                            borders.set(j, countryList.get(i).getName());
                            count ++;
                            if (count == borders.size()) {
                                break outerloop;
                            }
                        }
                    }
                }
            }
        } finally {
            return borders;
        }
    }

    public String getNativeName() {
        return nativeName;
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public ArrayList<String> getCurrencies() {
        return currencies;
    }

    public ArrayList<String> getLanguages() {
        return languages;
    }

    public static ArrayList<Country> getCountryList() {
        return countryList;
    }

    public String getShareContent() {
        String country = "Country: " + getName();
        String capital = "Capital: " + getCapital();
        String region = "Region: " + getRegion();
        String nativeName = "Native name: " + getNativeName();
        String alpha2code = "2 leters code: " + getAlpha2Code();
        String alpha3code = "3 leters code: " + getAlpha3Code();
        String population = "Population: " + getPopulation().toString();
        String area = "Area: " + getArea();
        String borders = "Borders: " + getBorders();
        String currenceis = "Currencies: " + getCurrencies();
        String languages = "Languages: " + getLanguages();
        return country + "\n" + capital + "\n" + region + "\n" + nativeName + "\n" + alpha2code + "\n" + alpha3code + "\n"
                + population  + "\n" + area + "\n" + borders + "\n" + currenceis + "\n" + languages;
    }

    public static Country getCountryByCode(String code) {
        for (int i=0; i<countryList.size(); i++) {
            if (countryList.get(i).getAlpha2Code().equals(code)) {
                return countryList.get(i);
            }
        }
        return null;
    }

    public void setIndicatorGDP(String indicatorId, String indicatorName, String value, String date) {
        Indicator indicator = new Indicator(indicatorName, value, date);
        if (indicatorId.equals(WorldBankService.INDICATOR_GDP)) {
            gdpIndicators.add(indicator);
        } else if (indicatorId.equals(WorldBankService.INDICATOR_INFLATION)) {
            inflationIndicators.add(indicator);
        }
    }

    public void setCountryUId(String countryUId) {
        this.countryUId = countryUId;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Country) {
            Country country = (Country) o;
            return (country.getName().equals(this.getName())) &&
                    (country.getCapital().equals(this.getCapital()));
        }
        return false;
    }
}
