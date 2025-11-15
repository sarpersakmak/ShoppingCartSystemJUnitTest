package com.example.shopping;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class OrderCalculatorTest {

    /**
     * OrderCalculatorTest
     * ----------------------------------------------------------
     * Contains tests for OrderCalculator class logic and edge cases.
     * Includes one parameterized test for multiple discount/tax inputs.
     */

    /** Invalid discount rate should throw exception. */
    @Test
    public void invalidDiscountRate_throws() {
        assertThrows(IllegalArgumentException.class,() -> OrderCalculator.calculate(100, -1, 10));
    }

    /** Invalid tax rate should throw exception. */
    @Test
    public void invalidTaxRate_throws() {
        assertThrows(IllegalArgumentException.class, () -> OrderCalculator.calculate(100, 10, 200));
    }

    /** Valid calculation: 100 with 10% discount and 10% tax = 99. */
    @Test
    public void orderCalculation_validValues_returnsExpectedTotal() {
        double total = OrderCalculator.calculate(100, 10, 10);
        assertEquals(99, total, 0.001);
    }


    /** Subtotal can be zero without causing errors. */
    @Test
    public void subtotal_zeroAllowed() {
        double total = OrderCalculator.calculate(0, 0, 10);
        assertEquals(0, total, 0.001);
    }

    /** Negative subtotal should throw an exception. */
    @Test
    public void subtotal_negative_throws() {
        assertThrows(IllegalArgumentException.class, () -> OrderCalculator.calculate(-1, 0, 0));
    }
    // --- Parameterized test (multiple discount/tax combinations) ---

    /**
     * Parameterized test: runs multiple discount/tax combinations.
     * Uses @CsvSource to feed input/output values.
     */
    @ParameterizedTest
    @CsvSource({
            "100, 0, 0, 100",
            "100, 10, 0, 90",
            "200, 25, 10, 165",
            "50, 5, 5, 49.875"
    })
    public void parameterizedTotals(double subtotal, double discount, double tax, double expected) {
        double result = OrderCalculator.calculate(subtotal, discount, tax);
        assertEquals(expected, result, 0.001);
    }
}