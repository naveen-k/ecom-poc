package com.test.catalog.sales.domain;


import com.test.catalog.sales.client.SalesCatalogClientAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@AutoConfigureAfter(SalesCatalogClientAutoConfiguration.class)
public class SalesCatalogDomainAutoConfiguration {
}
