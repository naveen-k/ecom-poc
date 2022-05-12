package com.test.catalog.sales.client.product;

import com.test.catalog.sales.client.request.product.StoreFrontProductStatusRequest;
import com.test.catalog.sales.client.response.HttpResponseEntity;
import com.test.catalog.sales.client.response.product.StoreFrontProductResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class StoreFrontProductApiClient {
    private final RestTemplate restTemplate;

    public StoreFrontProductApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * This method to get StoreFrontProducts
     *
     * @param releaseDate
     * @return
     */
    public HttpResponseEntity<StoreFrontProductResponse> getStoreFrontProducts(String releaseDate,String nextState, Integer recordsSize) {
        log.info("Calling getStoreFrontProducts method of StoreFrontProductApiClient for releaseDate {}  nextState {} ", releaseDate,nextState);
        final Map<String, String> pathParam = new HashMap<>();
        return restTemplate.exchange("/products/locations?releaseDate=" + releaseDate +"&nextState="+nextState +"&recordsSize="+ recordsSize,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<HttpResponseEntity<StoreFrontProductResponse>>() {
                }, pathParam).getBody();
    }

    /**
     * This method to get StoreFrontProducts
     *
     * @param storeId,sapArticleId
     * @return
     */
    public HttpResponseEntity<StoreFrontProductResponse> getStoreProducts(String storeId,String sapArticleId, String uom) {
        log.info("Calling getStoreFrontProducts method of StoreFrontProductApiClient for storeId {} ,sapArticleId{}", storeId,sapArticleId);
        final Map<String, String> pathParam = new HashMap<>();
        return restTemplate.exchange("/products?storeId=" + storeId+"&sapArticleId="+sapArticleId+"&uom="+uom,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<HttpResponseEntity<StoreFrontProductResponse>>() {
                }, pathParam).getBody();
    }

    /**
     * This method to get updateStoreFrontProducts
     *
     * @param request
     * @return
     */
    public HttpResponseEntity<StoreFrontProductStatusRequest> updateStoreFrontProductStatus(StoreFrontProductStatusRequest request) {
        log.info("Calling getStoreFrontProducts method of StoreFrontProductApiClient for releaseDate {} ", request);
        final Map<String, String> pathParam = new HashMap<>();
        return restTemplate.exchange("/products/state",
                HttpMethod.POST,
                new HttpEntity(request),
                new ParameterizedTypeReference<HttpResponseEntity<StoreFrontProductStatusRequest>>() {
                }, pathParam).getBody();
    }
}
