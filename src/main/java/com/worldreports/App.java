package com.worldreports;

import com.worldreports.model.City;
import com.worldreports.repository.WorldRepository;
import com.worldreports.service.ReportService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        WorldRepository repository = new WorldRepository();
        ReportService reportService = new ReportService();
        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while (running) {
            printMainMenu();

            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" ->
                            reportService.printCountries(
                                    repository.getAllCountries()
                            );

                    case "2" ->
                            reportService.printCities(
                                    repository.getAllCities()
                            );

                    case "3" ->
                            reportService.printCapitalCities(
                                    repository.getAllCapitalCities()
                            );

                    case "4" ->
                            runTopCities(
                                    scanner,
                                    repository,
                                    reportService
                            );

                    case "5" ->
                            reportService.printPopulationReports(
                                    repository.getCountryPopulationReports()
                            );

                    case "6" ->
                            runAreaPopulation(
                                    scanner,
                                    repository,
                                    reportService
                            );

                    case "7" ->
                            runLocationPopulation(
                                    scanner,
                                    repository,
                                    reportService
                            );

                    case "8" ->
                            reportService.printLanguageReports(
                                    repository.getLanguageReports()
                            );

                    case "0" -> {
                        running = false;
                        System.out.println("Application closed.");
                    }

                    default ->
                            System.out.println(
                                    "Enter a number from 0 to 8."
                            );
                }

            } catch (IllegalArgumentException exception) {
                System.out.println(
                        "Input error: " + exception.getMessage()
                );

            } catch (IllegalStateException exception) {
                System.out.println(
                        "Configuration error: "
                                + exception.getMessage()
                );

            } catch (SQLException exception) {
                System.out.println(
                        "Database error: " + exception.getMessage()
                );
            }
        }

        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println();
        System.out.println("WORLD POPULATION REPORTS");
        System.out.println();
        System.out.println("1. All countries");
        System.out.println("2. All cities");
        System.out.println("3. All capital cities");
        System.out.println("4. Top N populated cities");
        System.out.println(
                "5. Country city and non-city populations"
        );
        System.out.println(
                "6. World, continent or region population"
        );
        System.out.println(
                "7. Country, district or city population"
        );
        System.out.println("8. Language report");
        System.out.println("0. Exit");
        System.out.print("\nChoose an option: ");
    }

    private static void runTopCities(
            Scanner scanner,
            WorldRepository repository,
            ReportService reportService
    ) throws SQLException {

        System.out.print(
                "Enter the number of cities to display: "
        );

        String input = scanner.nextLine().trim();

        try {
            int limit = Integer.parseInt(input);

            if (limit < 1) {
                System.out.println(
                        "Enter a number greater than zero."
                );
                return;
            }

            reportService.printCities(
                    repository.getTopCities(limit)
            );

        } catch (NumberFormatException exception) {
            System.out.println(
                    "Enter a valid whole number."
            );
        }
    }

    private static void runAreaPopulation(
            Scanner scanner,
            WorldRepository repository,
            ReportService reportService
    ) throws SQLException {

        System.out.println();
        System.out.println("1. World population");
        System.out.println("2. Continent population");
        System.out.println("3. Region population");
        System.out.print("Choose an option: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> reportService.printPopulation(
                    "the world",
                    repository.getWorldPopulation()
            );

            case "2" -> {
                System.out.print("Enter continent name: ");
                String continent = scanner.nextLine().trim();

                reportService.printPopulation(
                        continent,
                        repository.getContinentPopulation(continent)
                );
            }

            case "3" -> {
                System.out.print("Enter region name: ");
                String region = scanner.nextLine().trim();

                reportService.printPopulation(
                        region,
                        repository.getRegionPopulation(region)
                );
            }

            default -> System.out.println(
                    "Enter 1, 2 or 3."
            );
        }
    }

    private static void runLocationPopulation(
            Scanner scanner,
            WorldRepository repository,
            ReportService reportService
    ) throws SQLException {

        System.out.println();
        System.out.println("1. Country population");
        System.out.println("2. District population");
        System.out.println("3. City population");
        System.out.print("Choose an option: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> {
                System.out.print("Enter country name: ");
                String country = scanner.nextLine().trim();

                reportService.printPopulation(
                        country,
                        repository.getCountryPopulation(country)
                );
            }

            case "2" -> {
                System.out.print("Enter district name: ");
                String district = scanner.nextLine().trim();

                reportService.printPopulation(
                        district,
                        repository.getDistrictPopulation(district)
                );
            }

            case "3" -> {
                System.out.print("Enter city name: ");
                String cityName = scanner.nextLine().trim();

                List<City> cities =
                        repository.getCityPopulation(cityName);

                if (cities.isEmpty()) {
                    System.out.println(
                            "No matching city was found."
                    );
                } else {
                    reportService.printCities(cities);
                }
            }

            default -> System.out.println(
                    "Enter 1, 2 or 3."
            );
        }
    }
}