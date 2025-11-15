package com.example.shopping;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * ProductTest
 * ----------------------------------------------------------
 * Contains 20 test cases that validate the Product class.
 * Covers constructor validation, stock management,
 * setter methods, and edge cases.
 *
 * Uses 5+ different assertions:
 *   - assertEquals
 *   - assertTrue
 *   - assertNotNull
 *   - assertThrows
 *   - assertDoesNotThrow
 *
 * Each test checks a unique behavior or boundary condition.
 */
public class ProductTest {

    // --- Constructor validation tests ---

    /** Valid product creation should correctly store all fields. */
    @Test
    public void validProductCreation_shouldStoreFields() {
        Product p = new Product(1, "Lamp", 10.0, 5);
        assertNotNull(p);                              // Object created successfully
        assertEquals("Lamp", p.getName());             // Correct name
        assertEquals(10.0, p.getPrice(), 0.001);       // Correct price
        assertEquals(5, p.getStock());                 // Correct stock
    }

    /** Product name cannot be null. */
    @Test
    public void nullName_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new Product(1, null, 10, 5));
    }

    /** Product name cannot be empty or whitespace. */
    @Test
    public void emptyName_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new Product(1, "   ", 10, 5));
    }

    /** Price cannot be negative. */
    @Test
    public void negativePrice_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new Product(1, "TV", -5, 2));
    }

    /** Stock cannot be negative. */
    @Test
    public void negativeStock_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new Product(1, "TV", 5, -1));
    }

    // --- Stock operation tests ---

    /** Reducing stock by a valid amount decreases available stock. */
    @Test
    public void reduceStock_validAmount_decreasesStock() {
        Product p = new Product(1, "TV", 10, 5);
        p.reduceStock(3);
        assertEquals(2, p.getStock()); // 5 - 3 = 2
    }

    /** Reducing stock beyond available amount throws exception. */
    @Test
    public void reduceStock_moreThanAvailable_throws() {
        Product p = new Product(1, "TV", 10, 1);
        assertThrows(IllegalArgumentException.class, () -> p.reduceStock(2));
    }

    /** Reducing stock with a negative number is invalid. */
    @Test
    public void reduceStock_negativeAmount_throws() {
        Product p = new Product(1, "TV", 10, 5);
        assertThrows(IllegalArgumentException.class, () -> p.reduceStock(-1));
    }

    /** Increasing stock by a valid amount should increase it correctly. */
    @Test
    public void increaseStock_valid_increasesValue() {
        Product p = new Product(1, "Book", 5, 5);
        p.increaseStock(3);
        assertEquals(8, p.getStock());
    }

    /** Increasing stock by a negative number should throw an error. */
    @Test
    public void increaseStock_negative_throws() {
        Product p = new Product(1, "Book", 5, 5);
        assertThrows(IllegalArgumentException.class, () -> p.increaseStock(-2));
    }

    // --- Price setter and validations ---

    /** Setting a new valid price should update the price field. */
    @Test
    public void setPrice_valid_updatesPrice() {
        Product p = new Product(1, "Pen", 1.0, 10);
        p.setPrice(2.5);
        assertEquals(2.5, p.getPrice(), 0.001);
    }

    /** Setting a negative price should fail validation. */
    @Test
    public void setPrice_negative_throws() {
        Product p = new Product(1, "Pen", 1.0, 10);
        assertThrows(IllegalArgumentException.class, () -> p.setPrice(-3));
    }

    // --- Miscellaneous / boundary tests ---

    /** The toString() method should never return null. */
    @Test
    public void toString_notNull() {
        Product p = new Product(1, "Phone", 100, 2);
        assertNotNull(p.toString());
    }

    /** Reducing stock to zero should set stock exactly to zero. */
    @Test
    public void reduceToZero_stockBecomesZero() {
        Product p = new Product(1, "Mouse", 20, 2);
        p.reduceStock(2);
        assertEquals(0, p.getStock());
    }

    /** Product with zero stock can exist (allowed boundary). */
    @Test
    public void zeroStock_creationAllowed() {
        Product p = new Product(1, "Game", 50, 0);
        assertEquals(0, p.getStock());
    }

    /** Product with zero price can exist (e.g., free item). */
    @Test
    public void zeroPrice_creationAllowed() {
        Product p = new Product(1, "Gift", 0, 5);
        assertEquals(0, p.getPrice(), 0.001);
    }

    /** ID should match the constructor argument. */
    @Test
    public void getId_returnsCorrectValue() {
        Product p = new Product(99, "X", 1.0, 1);
        assertEquals(99, p.getId());
    }

    /** After invalid reduction, stock should still remain non-negative. */
    @Test
    public void stockNeverNegativeAfterOperations() {
        Product p = new Product(1, "Cable", 2, 1);
        assertThrows(IllegalArgumentException.class, () -> p.reduceStock(2));
        assertTrue(p.getStock() >= 0);
    }

    /** Reducing when stock = 0 should throw an exception. */
    @Test
    public void reduceFromZero_throws() {
        Product p = new Product(1, "Item", 1, 0);
        assertThrows(IllegalArgumentException.class, () -> p.reduceStock(1));
    }
    @Test
    public void validProduct_doesNotThrow() {
        assertDoesNotThrow(() -> new Product(1, "Valid", 1.0, 1));
    }

    @Test
    public void toString_containsProductName() {
        Product p = new Product(1, "Lamp", 5.0, 2);
        assertTrue(p.toString().contains("Lamp"));
    }
}