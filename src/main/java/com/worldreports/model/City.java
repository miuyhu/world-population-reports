package com.worldreports.model;

public class City {

    private final String name;
    private final String country;
    private final String district;
    private final long population;

    public City(
            String name,
            String country,
            String district,
            long population
    ) {
        this.name = name;
        this.country = country;
        this.district = district;
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getDistrict() {
        return district;
    }

    public long getPopulation() {
        return population;
    }
}