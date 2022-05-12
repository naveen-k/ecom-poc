package com.test.catalog.sales.client.product;

import com.test.catalog.sales.client.response.HttpResponseEntity;
import com.test.catalog.sales.client.response.product.ProductStoreResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SalesCatalogProductLocationsApiClient {
    private static final Logger LOG = LoggerFactory.getLogger(SalesCatalogProductLocationsApiClient.class);

    private final RestTemplate template;
    private String locationsUri = "/locations";
    private String releaseDateStr = "releaseDate";

    public SalesCatalogProductLocationsApiClient(RestTemplate template) {
        this.template = template;
    }

    /**
     * This method to get ingredient in master catalog service
     *
     * @param releaseDate
     * @param productId
     * @return
     */
    public HttpResponseEntity<ProductStoreResponse> getProductStores(UUID productId , String releaseDate) {
        LOG.debug("Calling getProductStores method of ProductLocationsClient for productId {} ", productId);
            final Map<String, String> pathParam = new HashMap<>();
            return template.exchange("/products/"+productId+ locationsUri + "?" +releaseDateStr+ "=" +releaseDate,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<HttpResponseEntity<ProductStoreResponse>>() {
                    }, pathParam).getBody();

    }
}
