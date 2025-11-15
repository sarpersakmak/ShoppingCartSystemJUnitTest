package com.example.shopping;

/**
 * Utility class for calculating the final order total
 * after applying a discount and tax rate.
 *
 * Formula:
 *   discounted = subtotal * (1 - discountPercent / 100)
 *   total = discounted * (1 + taxPercent / 100)
 */
public class OrderCalculator {

    /**
     * Calculates the final total price.
     *
     * @param subtotal Non-negative subtotal
     * @param discountPercent 0–100 inclusive
     * @param taxPercent 0–100 inclusive
     * @return final total amount
     */
    public static double calculate(double subtotal, double discountPercent, double taxPercent) {
        if (subtotal < 0)
            throw new IllegalArgumentException("Subtotal cannot be negative");
        if (discountPercent < 0 || discountPercent > 100)
            throw new IllegalArgumentException("Invalid discount percent");
        if (taxPercent < 0 || taxPercent > 100)
            throw new IllegalArgumentException("Invalid tax percent");

        double discounted = subtotal * (1 - discountPercent / 100.0);
        return discounted * (1 + taxPercent / 100.0);
    }
}
