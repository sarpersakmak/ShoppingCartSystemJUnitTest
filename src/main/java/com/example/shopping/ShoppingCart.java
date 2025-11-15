package com.example.shopping;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a customer's shopping cart.
 * 
 * The cart holds products and their quantities.
 * Each time an item is added:
 *   - It checks stock availability.
 *   - It decreases the product's stock (reservation).
 * 
 * Each time an item is removed:
 *   - It increases the product's stock (return).
 * 
 * Provides total price calculation and validation checks.
 */
public class ShoppingCart {

    // Map of Product -> Quantity in the cart
    private final Map<Product, Integer> items = new HashMap<>();

    /**
     * Adds an item to the cart.
     * @param product Product to add (must not be null)
     * @param quantity Quantity (must be > 0 and <= product stock)
     */
    public void addItem(Product product, int quantity) {
        if (product == null)
            throw new IllegalArgumentException("Product cannot be null");
        if (quantity <= 0)
            throw new IllegalArgumentException("Quantity must be positive");
        if (product.getStock() < quantity)
            throw new IllegalArgumentException("Insufficient stock for " + product.getName());

        // Reserve stock
        product.reduceStock(quantity);

        // Add to cart or increase quantity
        items.merge(product, quantity, Integer::sum);
    }

    /**
     * Removes quantity of a product from the cart.
     * Returns that amount of stock back to the product.
     */
    public void removeItem(Product product, int quantity) {
        if (product == null)
            throw new IllegalArgumentException("Product cannot be null");
        if (!items.containsKey(product))
            throw new IllegalArgumentException("Product not found in cart");
        if (quantity <= 0)
            throw new IllegalArgumentException("Quantity must be positive");

        int current = items.get(product);
        if (quantity > current)
            throw new IllegalArgumentException("Trying to remove more than present in cart");

        // Return stock to product
        product.increaseStock(quantity);

        if (quantity == current)
            items.remove(product); // remove completely
        else
            items.put(product, current - quantity);
    }

    /** Calculates total price for all items (no tax/discount yet). */
    public double getTotalPrice() {
        double total = 0.0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    /** Checks whether the cart is empty. */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /** Returns a read-only copy of the cart contents. */
    public Map<Product, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }

    /** Clears all items and returns stock to products. */
    public void clear() {
        for (Map.Entry<Product, Integer> e : items.entrySet()) {
            e.getKey().increaseStock(e.getValue());
        }
        items.clear();
    }
}
