package com.test.catalog.sales.db.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableConfigurationProperties
@SpringBootApplication
@EnableJpaRepositories("com.test.catalog.sales")
@ComponentScan(basePackages = {"com.test.catalog.sales","java.store.db.util"})
@EntityScan("com.test.catalog.sales.db.entity")
public class SalesCatalogApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalesCatalogApplication.class, args);
    }

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("${salescatalog.default.timezone}"));
    }
}
