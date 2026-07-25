package com.example.shopping;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Shopping cart")
class ShoppingCartTest {

    private static final double TOLERANCE = 0.001;

    @Test
    @DisplayName("starts empty")
    void newCart_isEmpty() {
        ShoppingCart cart = new ShoppingCart();

        assertAll(
                () -> assertTrue(cart.isEmpty()),
                () -> assertEquals(0, cart.getTotalPrice(), TOLERANCE));
    }

    @Test
    @DisplayName("adds a product and reserves stock")
    void addItem_validProduct_addsQuantityAndReducesStock() {
        Product product = new Product(1, "Book", 10, 5);
        ShoppingCart cart = new ShoppingCart();

        cart.addItem(product, 2);

        assertAll(
                () -> assertFalse(cart.isEmpty()),
                () -> assertEquals(2, cart.getItems().get(product).intValue()),
                () -> assertEquals(3, product.getStock()),
                () -> assertEquals(20, cart.getTotalPrice(), TOLERANCE));
    }

    @Test
    @DisplayName("accumulates repeated additions of the same product")
    void addItem_sameProductTwice_accumulatesQuantity() {
        Product product = new Product(1, "Book", 10, 10);
        ShoppingCart cart = new ShoppingCart();

        cart.addItem(product, 2);
        cart.addItem(product, 3);

        assertAll(
                () -> assertEquals(5, cart.getItems().get(product).intValue()),
                () -> assertEquals(5, product.getStock()));
    }

    @Test
    @DisplayName("rejects a null product")
    void addItem_nullProduct_throwsException() {
        ShoppingCart cart = new ShoppingCart();

        assertThrows(IllegalArgumentException.class, () -> cart.addItem(null, 1));
    }

    @Test
    @DisplayName("rejects non-positive quantities")
    void addItem_nonPositiveQuantity_throwsException() {
        Product product = new Product(1, "Pen", 1.0, 5);
        ShoppingCart cart = new ShoppingCart();

        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> cart.addItem(product, 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> cart.addItem(product, -1)));
    }

    @Test
    @DisplayName("rejects a quantity greater than available stock")
    void addItem_excessiveQuantity_throwsWithoutChangingState() {
        Product product = new Product(1, "Laptop", 1000, 1);
        ShoppingCart cart = new ShoppingCart();

        assertThrows(IllegalArgumentException.class, () -> cart.addItem(product, 2));
        assertAll(
                () -> assertTrue(cart.isEmpty()),
                () -> assertEquals(1, product.getStock()));
    }

    @Test
    @DisplayName("removes part of a quantity and restores stock")
    void removeItem_partialQuantity_updatesCartAndStock() {
        Product product = new Product(1, "Lamp", 5, 5);
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(product, 2);

        cart.removeItem(product, 1);

        assertAll(
                () -> assertEquals(1, cart.getItems().get(product).intValue()),
                () -> assertEquals(4, product.getStock()));
    }

    @Test
    @DisplayName("removes a product when its full cart quantity is returned")
    void removeItem_fullQuantity_removesProduct() {
        Product product = new Product(1, "Chair", 50, 2);
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(product, 2);

        cart.removeItem(product, 2);

        assertAll(
                () -> assertTrue(cart.isEmpty()),
                () -> assertEquals(2, product.getStock()));
    }

    @Test
    @DisplayName("rejects removing a null product")
    void removeItem_nullProduct_throwsException() {
        ShoppingCart cart = new ShoppingCart();

        assertThrows(IllegalArgumentException.class, () -> cart.removeItem(null, 1));
    }

    @Test
    @DisplayName("rejects removing a product not in the cart")
    void removeItem_missingProduct_throwsException() {
        ShoppingCart cart = new ShoppingCart();
        Product product = new Product(1, "Desk", 100, 5);

        assertThrows(IllegalArgumentException.class, () -> cart.removeItem(product, 1));
    }

    @Test
    @DisplayName("rejects non-positive removal quantities")
    void removeItem_nonPositiveQuantity_throwsException() {
        Product product = new Product(1, "Toy", 10, 5);
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(product, 2);

        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> cart.removeItem(product, 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> cart.removeItem(product, -1)));
    }

    @Test
    @DisplayName("rejects removing more than the cart contains")
    void removeItem_excessiveQuantity_throwsWithoutChangingState() {
        Product product = new Product(1, "Toy", 10, 5);
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(product, 2);

        assertThrows(IllegalArgumentException.class, () -> cart.removeItem(product, 3));
        assertAll(
                () -> assertEquals(2, cart.getItems().get(product).intValue()),
                () -> assertEquals(3, product.getStock()));
    }

    @Test
    @DisplayName("sums multiple products")
    void getTotalPrice_multipleProducts_returnsCorrectSubtotal() {
        Product first = new Product(1, "A", 10, 10);
        Product second = new Product(2, "B", 20, 10);
        ShoppingCart cart = new ShoppingCart();

        cart.addItem(first, 2);
        cart.addItem(second, 3);

        assertEquals(80, cart.getTotalPrice(), TOLERANCE);
    }

    @Test
    @DisplayName("supports floating-point totals with a tolerance")
    void getTotalPrice_fractionalPrice_returnsExpectedSubtotal() {
        Product product = new Product(1, "Cable", 0.3333, 10);
        ShoppingCart cart = new ShoppingCart();

        cart.addItem(product, 3);

        assertEquals(0.9999, cart.getTotalPrice(), TOLERANCE);
    }

    @Test
    @DisplayName("exposes cart contents as read-only")
    void getItems_externalModification_throwsException() {
        Product product = new Product(1, "Book", 10, 5);
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(product, 1);
        Map<Product, Integer> readOnlyItems = cart.getItems();

        assertThrows(UnsupportedOperationException.class, () -> readOnlyItems.put(product, 99));
        assertEquals(1, cart.getItems().get(product).intValue());
    }

    @Test
    @DisplayName("clears all products and restores their stock")
    void clear_multipleProducts_restoresStockAndEmptiesCart() {
        Product first = new Product(1, "TV", 100, 3);
        Product second = new Product(2, "Cable", 10, 8);
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(first, 2);
        cart.addItem(second, 5);

        cart.clear();

        assertAll(
                () -> assertTrue(cart.isEmpty()),
                () -> assertEquals(3, first.getStock()),
                () -> assertEquals(8, second.getStock()),
                () -> assertEquals(0, cart.getTotalPrice(), TOLERANCE));
    }
}
