package com.worldreports.model;

public class PopulationReport {

    private final String name;
    private final long totalPopulation;
    private final long cityPopulation;
    private final double cityPercentage;
    private final long nonCityPopulation;
    private final double nonCityPercentage;

    public PopulationReport(
            String name,
            long totalPopulation,
            long cityPopulation,
            double cityPercentage,
            long nonCityPopulation,
            double nonCityPercentage
    ) {
        this.name = name;
        this.totalPopulation = totalPopulation;
        this.cityPopulation = cityPopulation;
        this.cityPercentage = cityPercentage;
        this.nonCityPopulation = nonCityPopulation;
        this.nonCityPercentage = nonCityPercentage;
    }

    public String getName() {
        return name;
    }

    public long getTotalPopulation() {
        return totalPopulation;
    }

    public long getCityPopulation() {
        return cityPopulation;
    }

    public double getCityPercentage() {
        return cityPercentage;
    }

    public long getNonCityPopulation() {
        return nonCityPopulation;
    }

    public double getNonCityPercentage() {
        return nonCityPercentage;
    }
}