package com.example.shopping;

/**
 * Represents a product that can be added to a shopping cart.
 *
 * <p>A product has an immutable identifier and name, together with a mutable
 * price and available stock level.</p>
 */
public class Product {

    private final int id;
    private final String name;
    private double price;
    private int stock;

    /**
     * Creates a product after validating its initial state.
     *
     * @param id product identifier
     * @param name non-blank product name
     * @param price non-negative unit price
     * @param stock non-negative available quantity
     * @throws IllegalArgumentException if the name is blank or a numeric value is negative
     */
    public Product(int id, String name, double price, int stock) {
        if (name == null) {
            throw new IllegalArgumentException("Product name cannot be null");
        }
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    /**
     * Updates the unit price.
     *
     * @param price new non-negative price
     * @throws IllegalArgumentException if the price is negative
     */
    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }

    /**
     * Reserves stock by subtracting the requested amount.
     *
     * @param amount quantity to subtract
     * @throws IllegalArgumentException if the amount is negative or greater than available stock
     */
    public void reduceStock(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to reduce cannot be negative");
        }
        if (amount > stock) {
            throw new IllegalArgumentException("Insufficient stock");
        }
        stock -= amount;
    }

    /**
     * Returns stock by adding the requested amount.
     *
     * @param amount quantity to add
     * @throws IllegalArgumentException if the amount is negative
     */
    public void increaseStock(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to increase cannot be negative");
        }
        stock += amount;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
