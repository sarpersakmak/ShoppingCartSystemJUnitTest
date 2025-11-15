package com.example.shopping;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * BusinessLogicSuite (İş Mantığı Test Takımı)
 * ----------------------------------------------------------
 * Projenin iş mantığı ve operasyonel sınıflarına ait testleri çalıştırır.
 * * İçerdiği Testler:
 * - ShoppingCartTest
 * - OrderCalculatorTest
 */
@Suite
@SelectClasses({ 
    ShoppingCartTest.class, 
    OrderCalculatorTest.class 
})
public class BusinessLogicSuite {
   
}