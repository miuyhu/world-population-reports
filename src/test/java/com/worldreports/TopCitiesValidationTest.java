package com.worldreports;

import com.worldreports.repository.WorldRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TopCitiesValidationTest {

    @Test
    void rejectsZero() {
        WorldRepository repository = new WorldRepository();

        assertThrows(
                IllegalArgumentException.class,
                () -> repository.getTopCities(0)
        );
    }

    @Test
    void rejectsNegativeNumber() {
        WorldRepository repository = new WorldRepository();

        assertThrows(
                IllegalArgumentException.class,
                () -> repository.getTopCities(-5)
        );
    }
}