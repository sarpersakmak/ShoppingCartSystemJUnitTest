package com.example.shopping;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * Runs tests for the core domain model.
 */
@Suite
@SelectClasses(ProductTest.class)
public class CoreDomainSuite {
    // JUnit Platform discovers the selected test class through annotations.
}
