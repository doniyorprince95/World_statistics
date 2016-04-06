package com.ikuchko.world_population.models;

import java.util.ArrayList;

/**
 * Created by iliak on 4/5/16.
 */
public class User {
    private String uId;
    private String name;
    private String dob;
    private String profileUrl;
    private ArrayList<Country> visitedCountries = new ArrayList<>();
    private static User user;

    public User() {

    }

    public User(String uId) {
        this.uId = uId;
        user = this;
    }

    public String getuId() {
        return uId;
    }

    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public static User getUser() {
        return user;
    }

    public void setProfileUrl(String profileUrl) {
        user.profileUrl = profileUrl;
    }

    public void setName(String name) {
        user.name = name;
    }

    public void setDob(String dob) {
        user.dob = dob;
    }

    public ArrayList<Country> getVisitedCountries() {
        return visitedCountries;
    }

    public void addCountry(Country country) {
        visitedCountries.add(country);
    }

    public Boolean isCountryVisited(Country country) {
        for (int i=0; i<visitedCountries.size(); i++) {
            if (country.equals(visitedCountries.get(i))) {
                return true;
            }
        }
        return false;
    }

    public static void destroy() {
        user = null;
    }
}
