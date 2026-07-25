package com.example.shopping;

/**
 * Provides order-total calculations after a percentage discount and tax are
 * applied.
 *
 * <p>The calculation order is:</p>
 * <ol>
 *   <li>Apply the discount to the subtotal.</li>
 *   <li>Apply the tax to the discounted amount.</li>
 * </ol>
 */
public final class OrderCalculator {

    private OrderCalculator() {
        // Utility class; instances are not required.
    }

    /**
     * Calculates the final order total.
     *
     * @param subtotal non-negative order subtotal
     * @param discountPercent discount percentage from 0 to 100, inclusive
     * @param taxPercent tax percentage from 0 to 100, inclusive
     * @return the final total after discount and tax
     * @throws IllegalArgumentException if any argument is outside its valid range
     */
    public static double calculate(double subtotal, double discountPercent, double taxPercent) {
        if (subtotal < 0) {
            throw new IllegalArgumentException("Subtotal cannot be negative");
        }
        validatePercentage(discountPercent, "Discount");
        validatePercentage(taxPercent, "Tax");

        double discountedSubtotal = subtotal * (1 - discountPercent / 100.0);
        return discountedSubtotal * (1 + taxPercent / 100.0);
    }

    private static void validatePercentage(double percentage, String fieldName) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException(fieldName + " percent must be between 0 and 100");
        }
    }
}
