package com.worldreports;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PopulationCalculationTest {

    @Test
    void calculatesNonCityPopulation() {
        long totalPopulation = 1000;
        long cityPopulation = 600;

        long nonCityPopulation =
                totalPopulation - cityPopulation;

        assertEquals(400, nonCityPopulation);
    }

    @Test
    void calculatesCityPercentage() {
        long totalPopulation = 1000;
        long cityPopulation = 600;

        double percentage =
                cityPopulation * 100.0 / totalPopulation;

        assertEquals(60.0, percentage);
    }

    @Test
    void calculatesNonCityPercentage() {
        long totalPopulation = 1000;
        long nonCityPopulation = 400;

        double percentage =
                nonCityPopulation * 100.0 / totalPopulation;

        assertEquals(40.0, percentage);
    }

    @Test
    void handlesZeroPopulation() {
        long totalPopulation = 0;

        double percentage = totalPopulation == 0
                ? 0
                : 500 * 100.0 / totalPopulation;

        assertEquals(0.0, percentage);
    }
}