package com.test.catalog.sales.client;

import com.test.catalog.sales.client.price.SapArticlePriceClient;
import com.test.catalog.sales.client.product.SalesCatalogProductLocationsApiClient;
import com.test.catalog.sales.client.product.StoreFrontIngredientApiClient;
import com.test.catalog.sales.client.product.StoreFrontProductApiClient;
import com.test.catalog.sales.client.store.SalesCataLogCreateStoreLocationClient;
import com.test.catalog.sales.client.component.StoreFrontComponentClient;
import com.test.catalog.sales.client.exception.SalesCatalogApiClientException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

@Configuration
@PropertySource("classpath:api-catalog-sales-client.properties")
@AutoConfigureAfter(RestTemplateAutoConfiguration.class)
public class SalesCatalogClientAutoConfiguration {

    /**
     * this method create ProductLocationsClient object to consume to call sales catalog service API's
     * @param restTemplateBuilder
     * @param baseUrl
     * @param relativePath
     * @return SalesCatalogProductLocationsApiClient
     */
    @Bean
    @ConditionalOnBean(RestTemplateBuilder.class)
    @ConditionalOnMissingBean
    public SalesCatalogProductLocationsApiClient salesCatalogProductLocationsApiClient(RestTemplateBuilder restTemplateBuilder,
                                                                                       @Value("${salescatalog.base.url}") final String baseUrl,
                                                                                       @Value("${salescatalog.relative.path}") final String relativePath) {

        return new SalesCatalogProductLocationsApiClient(
                restTemplateBuilder.rootUri(baseUrl+relativePath).errorHandler(new DefaultResponseErrorHandler() {
                    @Override
                    public void handleError(ClientHttpResponse response) {
                        try {
                            super.handleError(response);
                        } catch (Throwable t) {
                            throw new SalesCatalogApiClientException(t);
                        }
                    }
                }).build());
    }

    /**
     * this method create CreateStoreLocationClient object to consume to call sales catalog service API's
     * @param restTemplateBuilder
     * @param baseUrl
     * @param relativePath
     * @return SalesCataLogCreateStoreLocationClient
     */
    @Bean
    @ConditionalOnBean(RestTemplateBuilder.class)
    @ConditionalOnMissingBean
    public SalesCataLogCreateStoreLocationClient salesCatalogCreateLocationApiClient(RestTemplateBuilder restTemplateBuilder,
                                                                                     @Value("${salescatalog.base.url}") final String baseUrl,
                                                                                     @Value("${salescatalog.relative.path}") final String relativePath) {

        return new SalesCataLogCreateStoreLocationClient(
                restTemplateBuilder.rootUri(baseUrl+relativePath).errorHandler(new DefaultResponseErrorHandler() {
                    @Override
                    public void handleError(ClientHttpResponse response) {
                        try {
                            super.handleError(response);
                        } catch (Throwable t) {
                            throw new SalesCatalogApiClientException(t);
                        }
                    }
                }).build());
    }

    /**
     * this method create StoreFrontProductApiClient object to consume to call sales catalog service API's
     * @param restTemplateBuilder
     * @param baseUrl
     * @param relativePath
     * @return SalesCataLogCreateStoreLocationClient
     */
    @Bean
    @ConditionalOnBean(RestTemplateBuilder.class)
    @ConditionalOnMissingBean
    public StoreFrontProductApiClient storeFrontProductApiClientApiClient(RestTemplateBuilder restTemplateBuilder,
                                                                          @Value("${salescatalog.base.url}") final String baseUrl,
                                                                          @Value("${salescatalog.relative.path}") final String relativePath) {

        return new StoreFrontProductApiClient(
                restTemplateBuilder.rootUri(baseUrl+relativePath).errorHandler(new DefaultResponseErrorHandler() {
                    @Override
                    public void handleError(ClientHttpResponse response) {
                        try {
                            super.handleError(response);
                        } catch (Throwable t) {
                            throw new SalesCatalogApiClientException(t);
                        }
                    }
                }).build());
    }

    /**
     * this method create StoreFrontProductApiClient object to consume to call sales catalog service API's
     * @param restTemplateBuilder
     * @param baseUrl
     * @param relativePath
     * @return SalesCataLogCreateStoreLocationClient
     */
    @Bean
    @ConditionalOnBean(RestTemplateBuilder.class)
    @ConditionalOnMissingBean
    public SapArticlePriceClient sapArticlePriceClient(RestTemplateBuilder restTemplateBuilder,
                                                       @Value("${salescatalog.base.url}") final String baseUrl,
                                                       @Value("${salescatalog.relative.path}") final String relativePath) {

        return new SapArticlePriceClient(
                restTemplateBuilder.rootUri(baseUrl+relativePath).errorHandler(new DefaultResponseErrorHandler() {
                    @Override
                    public void handleError(ClientHttpResponse response) {
                        try {
                            super.handleError(response);
                        } catch (Throwable t) {
                            throw new SalesCatalogApiClientException(t);
                        }
                    }
                }).build());
    }

    /**
     * this method create StoreFrontComponentClient object to consume to call sales catalog service API's
     * @param restTemplateBuilder
     * @param baseUrl
     * @param relativePath
     * @return SalesCataLogCreateStoreLocationClient
     */
    @Bean
    @ConditionalOnBean(RestTemplateBuilder.class)
    @ConditionalOnMissingBean
    public StoreFrontComponentClient storeFrontComponentClient(RestTemplateBuilder restTemplateBuilder,
                                                               @Value("${salescatalog.base.url}") final String baseUrl,
                                                               @Value("${salescatalog.relative.path}") final String relativePath) {

        return new StoreFrontComponentClient(
                restTemplateBuilder.rootUri(baseUrl+relativePath).errorHandler(new DefaultResponseErrorHandler() {
                    @Override
                    public void handleError(ClientHttpResponse response) {
                        try {
                            super.handleError(response);
                        } catch (Throwable t) {
                            throw new SalesCatalogApiClientException(t);
                        }
                    }
                }).build());
    }

    /**
     * this method create StoreFrontIngredientApiClient object to consume to call sales catalog service API's
     * @param restTemplateBuilder
     * @param baseUrl
     * @param relativePath
     * @return SalesCataLogCreateStoreLocationClient
     */
    @Bean
    @ConditionalOnBean(RestTemplateBuilder.class)
    @ConditionalOnMissingBean
    public StoreFrontIngredientApiClient storeFrontIngredientComponentClient(RestTemplateBuilder restTemplateBuilder,
                                                                             @Value("${salescatalog.base.url}") final String baseUrl,
                                                                             @Value("${salescatalog.relative.path}") final String relativePath) {

        return new StoreFrontIngredientApiClient(
                restTemplateBuilder.rootUri(baseUrl+relativePath).errorHandler(new DefaultResponseErrorHandler() {
                    @Override
                    public void handleError(ClientHttpResponse response) {
                        try {
                            super.handleError(response);
                        } catch (Throwable t) {
                            throw new SalesCatalogApiClientException(t);
                        }
                    }
                }).build());
    }
}
