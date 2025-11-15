package com.example.shopping;

/**
 * Represents a single product in the online shopping system.
 * Each product has:
 *   - id (unique identifier)
 *   - name (non-null, non-empty)
 *   - price (>= 0)
 *   - stock (>= 0)
 *
 * It provides methods to read details and to modify stock levels safely.
 */
public class Product {

    // --- Fields ---
    private final int id;
    private final String name;
    private double price;
    private int stock;

    /**
     * Constructor that validates all input fields.
     * Throws IllegalArgumentException if any validation fails.
     */
    public Product(int id, String name, double price, int stock) {
        if (name == null)
            throw new IllegalArgumentException("Product name cannot be null");
        if (name.trim().isEmpty())
            throw new IllegalArgumentException("Product name cannot be empty");
        if (price < 0)
            throw new IllegalArgumentException("Price cannot be negative");
        if (stock < 0)
            throw new IllegalArgumentException("Stock cannot be negative");

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    

    public int getId() { return id; }

    public String getName() { return name; }

    public double getPrice() { return price; }

    public int getStock() { return stock; }

    // --- Setters and Operations ---

    /**
     * Updates product price with validation.
     * @param price new price (must be >= 0)
     */
    public void setPrice(double price) {
        if (price < 0)
            throw new IllegalArgumentException("Price cannot be negative");
        this.price = price;
    }

    /**
     * Reduces available stock by the given amount.
     * Throws IllegalArgumentException if amount < 0 or exceeds available stock.
     */
    public void reduceStock(int amount) {
        if (amount < 0)
            throw new IllegalArgumentException("Amount to reduce cannot be negative");
        if (amount > stock)
            throw new IllegalArgumentException("Insufficient stock");
        this.stock -= amount;
    }

    /**
     * Increases available stock by the given amount.
     * Useful when an item is removed from cart.
     */
    public void increaseStock(int amount) {
        if (amount < 0)
            throw new IllegalArgumentException("Amount to increase cannot be negative");
        this.stock += amount;
    }

    @Override
    public String toString() {
        return "Product{id=" + id +
               ", name='" + name + '\'' +
               ", price=" + price +
               ", stock=" + stock + '}';
    }
    
}
