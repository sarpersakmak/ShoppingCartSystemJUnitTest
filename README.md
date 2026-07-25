# Shopping Cart System — JUnit 5

A compact Java project that models products, shopping-cart operations, stock reservation, and final order calculations. Its main purpose is to demonstrate readable unit tests with **JUnit 5**, parameterized tests, and JUnit Platform test suites.

## Features

- Create products with validated names, prices, and stock levels.
- Update a product's price.
- Reserve stock when a product is added to a cart.
- Restore stock when an item is removed or the cart is cleared.
- Accumulate quantities when the same product instance is added more than once.
- Calculate a cart subtotal.
- Apply percentage-based discount and tax values to an order subtotal.
- Run focused domain and business-logic test suites.

## Technologies

- Java 11+
- Maven
- JUnit Jupiter 5
- JUnit Platform Suite
- PlantUML

## Project structure

```text
ShoppingCartSystemJUnitTest-refactored/
├── docs/
│   └── TESTING_NOTES.md
├── src/
│   ├── main/java/com/example/shopping/
│   │   ├── OrderCalculator.java
│   │   ├── Product.java
│   │   └── ShoppingCart.java
│   └── test/java/com/example/shopping/
│       ├── BusinessLogicSuite.java
│       ├── CoreDomainSuite.java
│       ├── OrderCalculatorTest.java
│       ├── ProductTest.java
│       └── ShoppingCartTest.java
├── .gitignore
├── pom.xml
├── REFACTORING_NOTES.md
├── README.md
└── shopping_cart_uml.puml
```

## Domain overview

### `Product`

Stores an identifier, name, unit price, and available stock. It validates constructor values and provides controlled stock operations.

### `ShoppingCart`

Maps product instances to cart quantities. Adding an item reserves stock; removing an item or clearing the cart returns it. `getItems()` exposes an unmodifiable live view so callers can inspect, but not directly edit, cart contents.

### `OrderCalculator`

Applies a discount first and tax second:

```text
discounted subtotal = subtotal × (1 − discount / 100)
final total         = discounted subtotal × (1 + tax / 100)
```

For example, a subtotal of `100`, a `10%` discount, and `10%` tax produces `99`.

## Requirements

Install the following tools:

- JDK 11 or newer
- Apache Maven 3.8 or newer

Confirm the installation:

```bash
java -version
mvn -version
```

## Build and test

From the project root, run:

```bash
mvn clean test
```

Maven compiles the application and executes all classes whose names end with `Test`.

## Running individual tests

Run one test class:

```bash
mvn -Dtest=ProductTest test
mvn -Dtest=ShoppingCartTest test
mvn -Dtest=OrderCalculatorTest test
```

Run selected methods:

```bash
mvn -Dtest=ShoppingCartTest#clear_multipleProducts_restoresStockAndEmptiesCart test
```

The two suite classes can also be launched directly from an IDE with JUnit Platform support:

- `CoreDomainSuite`
- `BusinessLogicSuite`

## Test coverage areas

The tests cover:

- Valid and invalid product construction
- Zero-value boundaries
- Price updates
- Stock increase and reduction
- Failed operations preserving state
- Valid, null, zero, negative, and excessive cart quantities
- Stock reservation and restoration
- Multiple products and repeated additions
- Read-only cart access
- Discount and tax range validation
- Parameterized total calculations

See [`docs/TESTING_NOTES.md`](docs/TESTING_NOTES.md) for more detail.

## UML diagram

The source diagram is stored in [`shopping_cart_uml.puml`](shopping_cart_uml.puml). Render it with a PlantUML-compatible IDE extension or command-line installation.

Example command:

```bash
plantuml shopping_cart_uml.puml
```

## Design notes

- Products are used directly as map keys. Therefore, repeated additions accumulate when the same product instance is used.
- Cart totals use each product's current price at calculation time.
- This educational project uses `double` for prices and calculations. A production shopping system should normally use `BigDecimal` or a dedicated money type.
- `getItems()` returns an unmodifiable live view rather than a separate snapshot.

## Troubleshooting

### `mvn: command not found`

Install Apache Maven and ensure its `bin` directory is available on your system `PATH`.

### Tests are not visible in the IDE

Import the project as a Maven project, reload Maven dependencies, and confirm the IDE is using JDK 11 or newer.

### PlantUML does not render

Install a PlantUML extension and Graphviz when required by that extension, or use the PlantUML command-line tool.

## Possible future improvements

- Replace `double` with `BigDecimal` for monetary values.
- Introduce an immutable cart-line type instead of exposing a map view.
- Define product identity explicitly with `equals()` and `hashCode()`.
- Add JaCoCo code-coverage reporting.
- Add mutation testing with PIT.
- Add continuous integration with GitHub Actions.


