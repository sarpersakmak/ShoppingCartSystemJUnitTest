package com.example.shopping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Order calculator")
class OrderCalculatorTest {

    private static final double TOLERANCE = 0.001;

    @Test
    @DisplayName("calculates a total after discount and tax")
    void calculate_validValues_returnsExpectedTotal() {
        double total = OrderCalculator.calculate(100, 10, 10);

        assertEquals(99, total, TOLERANCE);
    }

    @Test
    @DisplayName("allows a zero subtotal")
    void calculate_zeroSubtotal_returnsZero() {
        assertEquals(0, OrderCalculator.calculate(0, 0, 10), TOLERANCE);
    }

    @Test
    @DisplayName("rejects a negative subtotal")
    void calculate_negativeSubtotal_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> OrderCalculator.calculate(-1, 0, 0));
    }

    @ParameterizedTest(name = "discount={0}%")
    @CsvSource({"-1", "100.01"})
    @DisplayName("rejects a discount outside the allowed range")
    void calculate_invalidDiscount_throwsException(double discount) {
        assertThrows(
                IllegalArgumentException.class,
                () -> OrderCalculator.calculate(100, discount, 10));
    }

    @ParameterizedTest(name = "tax={0}%")
    @CsvSource({"-1", "100.01"})
    @DisplayName("rejects a tax rate outside the allowed range")
    void calculate_invalidTax_throwsException(double tax) {
        assertThrows(
                IllegalArgumentException.class,
                () -> OrderCalculator.calculate(100, 10, tax));
    }

    @ParameterizedTest(name = "subtotal={0}, discount={1}%, tax={2}%")
    @CsvSource({
            "100, 0, 0, 100",
            "100, 10, 0, 90",
            "200, 25, 10, 165",
            "50, 5, 5, 49.875",
            "100, 100, 100, 0"
    })
    @DisplayName("handles representative and boundary percentages")
    void calculate_multipleValues_returnsExpectedTotal(
            double subtotal,
            double discount,
            double tax,
            double expected) {
        double result = OrderCalculator.calculate(subtotal, discount, tax);

        assertEquals(expected, result, TOLERANCE);
    }
}
