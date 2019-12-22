package com.mygdx.game.Entities.Functional.Maps;

public class CityCoordinate {
    private int country;
    private int region;
    private int city;

    public CityCoordinate(int country, int region, int city) {
        this.country = country;
        this.region = region;
        this.city = city;
    }
    public int getCountry() {
        return country;
    }
    public int getRegion() {
        return region;
    }
    public int getCity() {
        return city;
    }
}
