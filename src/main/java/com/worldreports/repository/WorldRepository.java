package com.worldreports.repository;

import com.worldreports.database.DatabaseConnection;
import com.worldreports.model.Country;

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
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet results =
                        statement.executeQuery()
        ) {
            while (results.next()) {
                Country country = new Country(
                        results.getString("Code"),
                        results.getString("Name"),
                        results.getString("Continent"),
                        results.getString("Region"),
                        results.getLong("Population"),
                        results.getString("Capital")
                );

                countries.add(country);
            }
        }

        return countries;
    }
}