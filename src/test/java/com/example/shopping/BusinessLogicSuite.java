package com.example.shopping;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Runs tests for cart operations and order-total calculations.
 */
@Suite
@SelectClasses({ShoppingCartTest.class, OrderCalculatorTest.class})
public class BusinessLogicSuite {
    // JUnit Platform discovers the selected test classes through annotations.
}
