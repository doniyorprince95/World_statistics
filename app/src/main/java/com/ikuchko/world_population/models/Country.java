package com.ikuchko.world_population.models;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by iliak on 3/20/16.
 */
@Parcel
public class Country {
    String name;
    String capital;
    String region;
    Integer population;
    Integer area;
    ArrayList<String> timezones;
    ArrayList<String> borders;
    String nativeName;
    String alpha2Code;
    String alpha3Code;
    ArrayList<String> currencies;
    ArrayList<String> languages;
    String flagImage;
    static ArrayList<Country> countryList = new ArrayList<>();

    public Country() {

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
        return borders;
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
}
