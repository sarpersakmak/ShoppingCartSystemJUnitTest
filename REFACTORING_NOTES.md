# Refactoring Notes

## Preserved behavior

The project's original domain rules were retained:

- Product names cannot be null or blank.
- Prices and stock cannot be negative.
- Cart quantities must be positive.
- Adding items reserves product stock.
- Removing or clearing items restores stock.
- Discounts and tax rates must remain between 0 and 100.

## Code improvements

- Standardized formatting and braces.
- Expanded English Javadoc around public behavior.
- Converted `OrderCalculator` into a non-instantiable utility class.
- Extracted percentage validation to remove duplicate conditions.
- Clarified that `ShoppingCart#getItems()` returns an unmodifiable live view, not a defensive copy.
- Updated the UML so it includes `Product#setPrice` and matches the implementation.
- Removed generated Maven output and Eclipse-specific project metadata.

## Test improvements

- Reorganized test names around behavior and expected outcome.
- Added readable display names.
- Added missing negative and upper-bound percentage cases.
- Added negative cart quantity and removal cases.
- Added null removal, read-only map, multiple-product clear, and unchanged-state assertions.
- Consolidated related assertions with `assertAll`.

## Project documentation

- Added a complete English `README.md`.
- Added focused testing notes under `docs/TESTING_NOTES.md`.
- Added a practical `.gitignore`.
