package com.example.shopping;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Product")
class ProductTest {

    private static final double TOLERANCE = 0.001;

    @Test
    @DisplayName("stores valid constructor values")
    void constructor_validValues_storesFields() {
        Product product = new Product(1, "Lamp", 10.0, 5);

        assertAll(
                () -> assertNotNull(product),
                () -> assertEquals(1, product.getId()),
                () -> assertEquals("Lamp", product.getName()),
                () -> assertEquals(10.0, product.getPrice(), TOLERANCE),
                () -> assertEquals(5, product.getStock()));
    }

    @Test
    @DisplayName("accepts zero price and zero stock")
    void constructor_zeroPriceAndStock_isAllowed() {
        Product product = new Product(1, "Free sample", 0, 0);

        assertAll(
                () -> assertEquals(0, product.getPrice(), TOLERANCE),
                () -> assertEquals(0, product.getStock()));
    }

    @Test
    @DisplayName("rejects a null name")
    void constructor_nullName_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product(1, null, 10, 5));
    }

    @Test
    @DisplayName("rejects a blank name")
    void constructor_blankName_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product(1, "   ", 10, 5));
    }

    @Test
    @DisplayName("rejects a negative price")
    void constructor_negativePrice_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product(1, "TV", -5, 2));
    }

    @Test
    @DisplayName("rejects negative stock")
    void constructor_negativeStock_throwsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product(1, "TV", 5, -1));
    }

    @Test
    @DisplayName("updates the price")
    void setPrice_validPrice_updatesValue() {
        Product product = new Product(1, "Pen", 1.0, 10);

        product.setPrice(2.5);

        assertEquals(2.5, product.getPrice(), TOLERANCE);
    }

    @Test
    @DisplayName("rejects a negative updated price")
    void setPrice_negativePrice_throwsException() {
        Product product = new Product(1, "Pen", 1.0, 10);

        assertThrows(IllegalArgumentException.class, () -> product.setPrice(-3));
    }

    @Test
    @DisplayName("reduces stock by a valid amount")
    void reduceStock_validAmount_decreasesStock() {
        Product product = new Product(1, "TV", 10, 5);

        product.reduceStock(3);

        assertEquals(2, product.getStock());
    }

    @Test
    @DisplayName("can reduce stock exactly to zero")
    void reduceStock_fullAmount_setsStockToZero() {
        Product product = new Product(1, "Mouse", 20, 2);

        product.reduceStock(2);

        assertEquals(0, product.getStock());
    }

    @Test
    @DisplayName("rejects reducing more stock than available")
    void reduceStock_excessiveAmount_throwsWithoutChangingStock() {
        Product product = new Product(1, "Cable", 2, 1);

        assertThrows(IllegalArgumentException.class, () -> product.reduceStock(2));
        assertEquals(1, product.getStock());
    }

    @Test
    @DisplayName("rejects a negative stock reduction")
    void reduceStock_negativeAmount_throwsException() {
        Product product = new Product(1, "TV", 10, 5);

        assertThrows(IllegalArgumentException.class, () -> product.reduceStock(-1));
    }

    @Test
    @DisplayName("increases stock by a valid amount")
    void increaseStock_validAmount_increasesStock() {
        Product product = new Product(1, "Book", 5, 5);

        product.increaseStock(3);

        assertEquals(8, product.getStock());
    }

    @Test
    @DisplayName("rejects a negative stock increase")
    void increaseStock_negativeAmount_throwsException() {
        Product product = new Product(1, "Book", 5, 5);

        assertThrows(IllegalArgumentException.class, () -> product.increaseStock(-2));
    }

    @Test
    @DisplayName("creates a valid product without throwing")
    void constructor_validValues_doesNotThrow() {
        assertDoesNotThrow(() -> new Product(1, "Valid", 1.0, 1));
    }

    @Test
    @DisplayName("includes useful details in its text representation")
    void toString_containsProductDetails() {
        Product product = new Product(7, "Lamp", 5.0, 2);
        String text = product.toString();

        assertAll(
                () -> assertNotNull(text),
                () -> assertTrue(text.contains("Lamp")),
                () -> assertTrue(text.contains("id=7")),
                () -> assertTrue(text.contains("stock=2")));
    }
}
