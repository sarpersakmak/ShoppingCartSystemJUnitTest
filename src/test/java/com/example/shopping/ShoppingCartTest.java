package com.example.shopping;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * ShoppingCartTest
 * ----------------------------------------------------------
 * Tests for ShoppingCart class.
 *
 * - 11 unique tests in total.
 * - Covers valid and invalid operations,
 *   stock updates, and percentage-based calculations.
 * - Uses assertEquals, assertTrue,  assertThrows.
 */
public class ShoppingCartTest {

    // --- Cart creation and adding items ---

    /** A new cart should be empty. */
    @Test
    public void newCart_isInitiallyEmpty() {
        ShoppingCart cart = new ShoppingCart();
        assertTrue(cart.isEmpty());
    }

    /** Adding valid items should update stock and total price. */
    @Test
    public void addItem_validProduct_reducesStockAndAdds() {
        Product p = new Product(1, "Book", 10, 5);
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(p, 2);
        assertEquals(3, p.getStock());                 // 5 - 2 = 3
        assertEquals(20, cart.getTotalPrice(), 0.001); // 2 * 10
    }

    /** Null product should cause exception. */
    @Test
    public void addItem_nullProduct_throws() {
        ShoppingCart cart = new ShoppingCart();
        assertThrows(IllegalArgumentException.class, () -> cart.addItem(null, 1));
    }

    /** Zero quantity should not be allowed. */
    @Test
    public void addItem_zeroQuantity_throws() {
        Product p = new Product(1, "Pen", 1.0, 5);
        ShoppingCart cart = new ShoppingCart();
        assertThrows(IllegalArgumentException.class, () -> cart.addItem(p, 0));
    }

    /** Adding more than available stock should fail. */
    @Test
    public void addItem_exceedStock_throws() {
        Product p = new Product(1, "Laptop", 1000, 1);
        ShoppingCart cart = new ShoppingCart();
        assertThrows(IllegalArgumentException.class, () -> cart.addItem(p, 2));
    }

    // --- Removing items from cart ---

    /** Removing item should restore stock. */
    @Test
    public void removeItem_valid_returnsStock() {
        Product p = new Product(1, "Lamp", 5, 5);
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(p, 2);
        cart.removeItem(p, 1);
        assertEquals(4, p.getStock()); // one returned
    }

    /** Removing all quantity removes the item completely from cart. */
    @Test
    public void removeItem_allQuantity_removesFromCart() {
        Product p = new Product(1, "Chair", 50, 2);
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(p, 2);
        cart.removeItem(p, 2);
        assertTrue(cart.isEmpty());
    }

    /** Removing item not in the cart should throw. */
    @Test
    public void removeItem_notInCart_throws() {
        ShoppingCart cart = new ShoppingCart();
        Product p = new Product(1, "Desk", 100, 5);
        assertThrows(IllegalArgumentException.class, () -> cart.removeItem(p, 1));
    }

    /** Removing more than quantity in cart should fail. */
    @Test
    public void removeItem_moreThanInCart_throws() {
        Product p = new Product(1, "Toy", 10, 5);
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(p, 2);
        assertThrows(IllegalArgumentException.class, () -> cart.removeItem(p, 3));
    }

    // --- Totals and clearing ---

    /** Total price should sum multiple products correctly. */
    @Test
    public void getTotalPrice_multipleProducts_correctSum() {
        Product a = new Product(1, "A", 10, 10);
        Product b = new Product(2, "B", 20, 10);
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(a, 2);
        cart.addItem(b, 3);
        assertEquals(2 * 10 + 3 * 20, cart.getTotalPrice(), 0.001);
    }

    /** Clearing the cart should return all stock. */
    @Test
    public void clear_returnsStock() {
        Product p = new Product(1, "TV", 100, 3);
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(p, 2);
        cart.clear();
        assertEquals(3, p.getStock()); // stock restored
        assertTrue(cart.isEmpty());
    }
    /** Floating point tolerance: small rounding differences accepted. */
    @Test
    public void floatingPointTotals_toleranceCheck() {
        Product p = new Product(1, "Cable", 0.3333, 10);
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(p, 3);
        assertEquals(0.9999, cart.getTotalPrice(), 0.001);
    }

    /** Adding the same product twice should accumulate quantities. */
    @Test
    public void addSameProductTwice_accumulatesQuantities() {
        Product p = new Product(1, "Book", 10, 10);
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(p, 2);
        cart.addItem(p, 3);
        assertEquals(5, cart.getItems().get(p));
    }


}