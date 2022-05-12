package com.test.catalog.sales.api;

import com.test.catalog.sales.domain.SalesCatalogDomainAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.test.catalog")
@AutoConfigureAfter(SalesCatalogDomainAutoConfiguration.class)
public class SalesCatalogAutoConfiguration {

}
