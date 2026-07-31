package com.worldreports.service;

import com.worldreports.model.Country;

import java.util.List;

public class ReportService {

    public void printCountries(List<Country> countries) {
        System.out.println();
        System.out.println("COUNTRY POPULATION REPORT");
        System.out.println();

        System.out.printf(
                "%-5s %-35s %-15s %-30s %15s %-25s%n",
                "Code",
                "Country",
                "Continent",
                "Region",
                "Population",
                "Capital"
        );

        System.out.println(
                "----------------------------------------------------------------" +
                        "----------------------------------------------------------------"
        );

        for (Country country : countries) {
            String capital = country.getCapital();

            if (capital == null || capital.isBlank()) {
                capital = "Not recorded";
            }

            System.out.printf(
                    "%-5s %-35s %-15s %-30s %,15d %-25s%n",
                    country.getCode(),
                    country.getName(),
                    country.getContinent(),
                    country.getRegion(),
                    country.getPopulation(),
                    capital
            );
        }

        System.out.println();
        System.out.println(
                "Total countries displayed: " + countries.size()
        );
    }
}