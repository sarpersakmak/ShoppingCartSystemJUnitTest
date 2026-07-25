# Testing Notes

## Test design

The test suite is divided by responsibility:

- `ProductTest` checks constructor validation, price updates, stock operations, and text output.
- `ShoppingCartTest` checks stock reservation and restoration, quantity validation, totals, repeated additions, clearing, and read-only access.
- `OrderCalculatorTest` checks the discount/tax formula, invalid inputs, representative values, and percentage boundaries.

## JUnit features demonstrated

- `@Test`
- `@ParameterizedTest` with `@CsvSource`
- `@DisplayName`
- `assertAll`
- `assertEquals`
- `assertThrows`
- `assertDoesNotThrow`
- JUnit Platform suites with `@Suite` and `@SelectClasses`

## Suite classes

`CoreDomainSuite` groups the product tests. `BusinessLogicSuite` groups shopping-cart and order-calculation tests. Maven's normal test discovery executes the `*Test` classes directly; suites are primarily useful for targeted IDE runs or explicit suite execution.

## Floating-point comparisons

The project intentionally uses `double` because it is a compact educational example. Tests compare calculated values with a small tolerance. Production commerce software should generally represent money with `BigDecimal` or a dedicated money type.
