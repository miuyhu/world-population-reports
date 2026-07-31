package com.worldreports.service;

import com.worldreports.model.CapitalCity;
import com.worldreports.model.City;
import com.worldreports.model.Country;
import com.worldreports.model.LanguageReport;
import com.worldreports.model.PopulationReport;

import java.util.List;

public class ReportService {

    public void printCountries(List<Country> countries) {
        System.out.println();
        System.out.println("ALL COUNTRIES BY POPULATION");
        System.out.println();

        System.out.printf(
                "%-5s %-30s %-15s %-28s %15s %-25s%n",
                "Code",
                "Country",
                "Continent",
                "Region",
                "Population",
                "Capital"
        );

        printLine();

        for (Country country : countries) {
            String capital = country.getCapital();

            if (capital == null || capital.isBlank()) {
                capital = "Not recorded";
            }

            System.out.printf(
                    "%-5s %-30s %-15s %-28s %,15d %-25s%n",
                    country.getCode(),
                    country.getName(),
                    country.getContinent(),
                    country.getRegion(),
                    country.getPopulation(),
                    capital
            );
        }

        System.out.println(
                "\nTotal countries: " + countries.size()
        );
    }

    public void printCities(List<City> cities) {
        System.out.println();
        System.out.println("CITY REPORT");
        System.out.println();

        System.out.printf(
                "%-30s %-30s %-30s %15s%n",
                "Name",
                "Country",
                "District",
                "Population"
        );

        printLine();

        for (City city : cities) {
            System.out.printf(
                    "%-30s %-30s %-30s %,15d%n",
                    city.getName(),
                    city.getCountry(),
                    city.getDistrict(),
                    city.getPopulation()
            );
        }

        System.out.println(
                "\nTotal cities displayed: " + cities.size()
        );
    }

    public void printCapitalCities(
            List<CapitalCity> capitalCities
    ) {
        System.out.println();
        System.out.println("CAPITAL CITY REPORT");
        System.out.println();

        System.out.printf(
                "%-35s %-35s %15s%n",
                "Name",
                "Country",
                "Population"
        );

        printLine();

        for (CapitalCity capital : capitalCities) {
            System.out.printf(
                    "%-35s %-35s %,15d%n",
                    capital.getName(),
                    capital.getCountry(),
                    capital.getPopulation()
            );
        }

        System.out.println(
                "\nTotal capital cities: " + capitalCities.size()
        );
    }

    public void printPopulationReports(
            List<PopulationReport> reports
    ) {
        System.out.println();
        System.out.println(
                "COUNTRY CITY AND NON-CITY POPULATION REPORT"
        );
        System.out.println();

        System.out.printf(
                "%-28s %15s %15s %10s %15s %10s%n",
                "Country",
                "Total",
                "In cities",
                "City %",
                "Not in cities",
                "Non-city %"
        );

        printLine();

        for (PopulationReport report : reports) {
            System.out.printf(
                    "%-28s %,15d %,15d %9.2f%% %,15d %9.2f%%%n",
                    report.getName(),
                    report.getTotalPopulation(),
                    report.getCityPopulation(),
                    report.getCityPercentage(),
                    report.getNonCityPopulation(),
                    report.getNonCityPercentage()
            );
        }
    }

    public void printPopulation(String name, long population) {
        System.out.println();
        System.out.printf(
                "Population of %s: %,d%n",
                name,
                population
        );
    }

    public void printLanguageReports(
            List<LanguageReport> reports
    ) {
        System.out.println();
        System.out.println("LANGUAGE REPORT");
        System.out.println();

        System.out.printf(
                "%-20s %20s %20s%n",
                "Language",
                "Speakers",
                "World percentage"
        );

        printLine();

        for (LanguageReport report : reports) {
            System.out.printf(
                    "%-20s %,20d %19.2f%%%n",
                    report.getLanguage(),
                    report.getSpeakers(),
                    report.getWorldPercentage()
            );
        }
    }

    private void printLine() {
        System.out.println(
                "------------------------------------------------------------" +
                        "------------------------------------------------------------"
        );
    }
}