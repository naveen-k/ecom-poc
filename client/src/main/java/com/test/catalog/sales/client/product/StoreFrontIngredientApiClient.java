package com.test.catalog.sales.client.product;

import com.test.catalog.sales.client.response.HttpResponseEntity;
import com.test.catalog.sales.client.response.ingredient.StoreFrontIngredientResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class StoreFrontIngredientApiClient {

    private final RestTemplate restTemplate;

    public StoreFrontIngredientApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * This method to get StoreFrontProducts
     *
     * @param releaseDate
     * @return
     */
    public HttpResponseEntity<StoreFrontIngredientResponse> getStoreFrontIngredients(String nextState, String releaseDate, Integer recordsSize) {
        log.info("Calling getStoreFrontIngredients method of StoreFrontIngredientApiClient for releaseDate {},  recordsSize: {}", releaseDate, recordsSize);
        final Map<String, String> pathParam = new HashMap<>();
        return restTemplate.exchange("/ingredients/locations?nextState="+ nextState +"&releaseDate=" + releaseDate +"&recordsSize=" +recordsSize,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<HttpResponseEntity<StoreFrontIngredientResponse>>() {
                }, pathParam).getBody();
    }
}
