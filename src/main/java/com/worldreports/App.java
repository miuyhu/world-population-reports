package com.worldreports;

import com.worldreports.model.Country;
import com.worldreports.repository.WorldRepository;
import com.worldreports.service.ReportService;

import java.sql.SQLException;
import java.util.List;

public class App {

    public static void main(String[] args) {
        WorldRepository repository = new WorldRepository();
        ReportService reportService = new ReportService();

        try {
            List<Country> countries =
                    repository.getAllCountries();

            reportService.printCountries(countries);

        } catch (IllegalStateException exception) {
            System.err.println(
                    "Configuration error: " + exception.getMessage()
            );

        } catch (SQLException exception) {
            System.err.println(
                    "Database error: " + exception.getMessage()
            );
        }
    }
}