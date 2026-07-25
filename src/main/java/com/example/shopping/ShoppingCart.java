package com.example.shopping;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Stores products and quantities selected by a customer.
 *
 * <p>Adding an item reserves stock from the related product. Removing an item
 * or clearing the cart returns the reserved stock.</p>
 */
public class ShoppingCart {

    private final Map<Product, Integer> items = new HashMap<>();

    /**
     * Adds a product quantity to the cart and reserves the same amount of stock.
     * Repeated additions of the same product instance accumulate the quantity.
     *
     * @param product product to add
     * @param quantity positive quantity not exceeding available stock
     * @throws IllegalArgumentException if the product or quantity is invalid
     */
    public void addItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("Insufficient stock for " + product.getName());
        }

        product.reduceStock(quantity);
        items.merge(product, quantity, Integer::sum);
    }

    /**
     * Removes a product quantity from the cart and restores its stock.
     *
     * @param product product already present in the cart
     * @param quantity positive quantity not exceeding the cart quantity
     * @throws IllegalArgumentException if the request is invalid
     */
    public void removeItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (!items.containsKey(product)) {
            throw new IllegalArgumentException("Product not found in cart");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        int currentQuantity = items.get(product);
        if (quantity > currentQuantity) {
            throw new IllegalArgumentException("Cannot remove more items than the cart contains");
        }

        product.increaseStock(quantity);

        if (quantity == currentQuantity) {
            items.remove(product);
        } else {
            items.put(product, currentQuantity - quantity);
        }
    }

    /**
     * Calculates the current cart subtotal using each product's current price.
     *
     * @return sum of unit price multiplied by cart quantity
     */
    public double getTotalPrice() {
        double total = 0.0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    /**
     * @return {@code true} when the cart contains no products
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Returns an unmodifiable view of the current cart contents.
     *
     * <p>The returned map cannot be edited by callers, but it reflects later
     * changes made through this cart.</p>
     *
     * @return read-only live view of products and quantities
     */
    public Map<Product, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }

    /**
     * Removes every cart item and restores all reserved stock.
     */
    public void clear() {
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            entry.getKey().increaseStock(entry.getValue());
        }
        items.clear();
    }
}
