package com.worldreports.model;

public class CapitalCity {

    private final String name;
    private final String country;
    private final long population;

    public CapitalCity(
            String name,
            String country,
            long population
    ) {
        this.name = name;
        this.country = country;
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public long getPopulation() {
        return population;
    }
}