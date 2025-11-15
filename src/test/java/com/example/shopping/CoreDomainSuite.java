package com.example.shopping;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * CoreDomainSuite (Ana Varlıklar Test Takımı)
 * ----------------------------------------------------------
 * Projenin temel varlık (entity) sınıflarına ait testleri çalıştırır.
 * * İçerdiği Testler:
 * - ProductTest
 */
@Suite
@SelectClasses({ 
    ProductTest.class 
})
public class CoreDomainSuite {
    
}