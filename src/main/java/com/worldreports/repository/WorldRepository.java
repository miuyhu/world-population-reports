package com.worldreports.repository;

import com.worldreports.database.DatabaseConnection;
import com.worldreports.model.CapitalCity;
import com.worldreports.model.City;
import com.worldreports.model.Country;
import com.worldreports.model.LanguageReport;
import com.worldreports.model.PopulationReport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorldRepository {

    public List<Country> getAllCountries() throws SQLException {
        String sql = """
                SELECT
                    country.Code,
                    country.Name,
                    country.Continent,
                    country.Region,
                    country.Population,
                    city.Name AS Capital
                FROM country
                LEFT JOIN city
                    ON country.Capital = city.ID
                ORDER BY country.Population DESC
                """;

        List<Country> countries = new ArrayList<>();

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet results = statement.executeQuery()
        ) {
            while (results.next()) {
                countries.add(new Country(
                        results.getString("Code"),
                        results.getString("Name"),
                        results.getString("Continent"),
                        results.getString("Region"),
                        results.getLong("Population"),
                        results.getString("Capital")
                ));
            }
        }

        return countries;
    }

    public List<City> getAllCities() throws SQLException {
        String sql = """
                SELECT
                    city.Name,
                    country.Name AS Country,
                    city.District,
                    city.Population
                FROM city
                JOIN country
                    ON city.CountryCode = country.Code
                ORDER BY city.Population DESC
                """;

        List<City> cities = new ArrayList<>();

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet results = statement.executeQuery()
        ) {
            while (results.next()) {
                cities.add(new City(
                        results.getString("Name"),
                        results.getString("Country"),
                        results.getString("District"),
                        results.getLong("Population")
                ));
            }
        }

        return cities;
    }

    public List<CapitalCity> getAllCapitalCities() throws SQLException {
        String sql = """
                SELECT
                    city.Name,
                    country.Name AS Country,
                    city.Population
                FROM country
                JOIN city
                    ON country.Capital = city.ID
                ORDER BY city.Population DESC
                """;

        List<CapitalCity> capitalCities = new ArrayList<>();

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet results = statement.executeQuery()
        ) {
            while (results.next()) {
                capitalCities.add(new CapitalCity(
                        results.getString("Name"),
                        results.getString("Country"),
                        results.getLong("Population")
                ));
            }
        }

        return capitalCities;
    }

    public List<City> getTopCities(int limit) throws SQLException {
        if (limit < 1) {
            throw new IllegalArgumentException(
                    "The number of cities must be greater than zero."
            );
        }

        String sql = """
                SELECT
                    city.Name,
                    country.Name AS Country,
                    city.District,
                    city.Population
                FROM city
                JOIN country
                    ON city.CountryCode = country.Code
                ORDER BY city.Population DESC
                LIMIT ?
                """;

        List<City> cities = new ArrayList<>();

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, limit);

            try (ResultSet results = statement.executeQuery()) {
                while (results.next()) {
                    cities.add(new City(
                            results.getString("Name"),
                            results.getString("Country"),
                            results.getString("District"),
                            results.getLong("Population")
                    ));
                }
            }
        }

        return cities;
    }

    public List<PopulationReport> getCountryPopulationReports()
            throws SQLException {

        String sql = """
                SELECT
                    country.Name,
                    country.Population AS TotalPopulation,
                    COALESCE(SUM(city.Population), 0) AS CityPopulation
                FROM country
                LEFT JOIN city
                    ON country.Code = city.CountryCode
                GROUP BY
                    country.Code,
                    country.Name,
                    country.Population
                ORDER BY country.Population DESC
                """;

        List<PopulationReport> reports = new ArrayList<>();

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet results = statement.executeQuery()
        ) {
            while (results.next()) {
                String name = results.getString("Name");
                long totalPopulation =
                        results.getLong("TotalPopulation");
                long cityPopulation =
                        results.getLong("CityPopulation");

                long nonCityPopulation =
                        totalPopulation - cityPopulation;

                double cityPercentage = totalPopulation == 0
                        ? 0
                        : cityPopulation * 100.0 / totalPopulation;

                double nonCityPercentage = totalPopulation == 0
                        ? 0
                        : nonCityPopulation * 100.0 / totalPopulation;

                reports.add(new PopulationReport(
                        name,
                        totalPopulation,
                        cityPopulation,
                        cityPercentage,
                        nonCityPopulation,
                        nonCityPercentage
                ));
            }
        }

        return reports;
    }

    public long getWorldPopulation() throws SQLException {
        String sql = """
                SELECT COALESCE(SUM(Population), 0) AS Population
                FROM country
                """;

        return getPopulationWithoutParameter(sql);
    }

    public long getContinentPopulation(String continent)
            throws SQLException {

        String sql = """
                SELECT COALESCE(SUM(Population), 0) AS Population
                FROM country
                WHERE Continent = ?
                """;

        return getPopulationWithParameter(sql, continent);
    }

    public long getRegionPopulation(String region)
            throws SQLException {

        String sql = """
                SELECT COALESCE(SUM(Population), 0) AS Population
                FROM country
                WHERE Region = ?
                """;

        return getPopulationWithParameter(sql, region);
    }

    public long getCountryPopulation(String country)
            throws SQLException {

        String sql = """
                SELECT COALESCE(Population, 0) AS Population
                FROM country
                WHERE Name = ?
                """;

        return getPopulationWithParameter(sql, country);
    }

    public long getDistrictPopulation(String district)
            throws SQLException {

        String sql = """
                SELECT COALESCE(SUM(Population), 0) AS Population
                FROM city
                WHERE District = ?
                """;

        return getPopulationWithParameter(sql, district);
    }

    public List<City> getCityPopulation(String cityName)
            throws SQLException {

        String sql = """
                SELECT
                    city.Name,
                    country.Name AS Country,
                    city.District,
                    city.Population
                FROM city
                JOIN country
                    ON city.CountryCode = country.Code
                WHERE city.Name = ?
                ORDER BY city.Population DESC
                """;

        List<City> cities = new ArrayList<>();

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, cityName);

            try (ResultSet results = statement.executeQuery()) {
                while (results.next()) {
                    cities.add(new City(
                            results.getString("Name"),
                            results.getString("Country"),
                            results.getString("District"),
                            results.getLong("Population")
                    ));
                }
            }
        }

        return cities;
    }

    public List<LanguageReport> getLanguageReports()
            throws SQLException {

        long worldPopulation = getWorldPopulation();

        String sql = """
                SELECT
                    countrylanguage.Language,
                    ROUND(
                        SUM(
                            country.Population
                            * countrylanguage.Percentage
                            / 100
                        )
                    ) AS Speakers
                FROM countrylanguage
                JOIN country
                    ON countrylanguage.CountryCode = country.Code
                WHERE countrylanguage.Language IN (
                    'Chinese',
                    'English',
                    'Spanish'
                )
                GROUP BY countrylanguage.Language
                ORDER BY Speakers DESC
                """;

        List<LanguageReport> reports = new ArrayList<>();

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet results = statement.executeQuery()
        ) {
            while (results.next()) {
                String language =
                        results.getString("Language");
                long speakers =
                        results.getLong("Speakers");

                double worldPercentage = worldPopulation == 0
                        ? 0
                        : speakers * 100.0 / worldPopulation;

                reports.add(new LanguageReport(
                        language,
                        speakers,
                        worldPercentage
                ));
            }
        }

        return reports;
    }

    private long getPopulationWithoutParameter(String sql)
            throws SQLException {

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet results = statement.executeQuery()
        ) {
            if (results.next()) {
                return results.getLong("Population");
            }
        }

        return 0;
    }

    private long getPopulationWithParameter(
            String sql,
            String value
    ) throws SQLException {

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, value);

            try (ResultSet results = statement.executeQuery()) {
                if (results.next()) {
                    return results.getLong("Population");
                }
            }
        }

        return 0;
    }
}