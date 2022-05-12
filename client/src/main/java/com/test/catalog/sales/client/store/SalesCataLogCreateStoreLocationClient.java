package com.test.catalog.sales.client.store;

import com.test.catalog.sales.client.request.store.StoreLocationRequest;
import com.test.catalog.sales.client.response.HttpResponseEntity;
import com.test.catalog.sales.client.response.store.StoreLocationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class SalesCataLogCreateStoreLocationClient {

    private static final Logger LOG = LoggerFactory.getLogger(SalesCataLogCreateStoreLocationClient.class);

    private final RestTemplate template;

    @Autowired
    public SalesCataLogCreateStoreLocationClient(RestTemplate template) {
        this.template = template;
    }

    /**
     * This method to create storeLocation in SalesCatalog
     *
     * @param storeLocationRequest
     * @return
     */
    public HttpResponseEntity<StoreLocationResponse> createStoreLocation(StoreLocationRequest storeLocationRequest) {
        LOG.debug("Calling createStoreLocation method of SalesCataLogCreateStoreLocationClient");
        final Map<String, String> pathParam = new HashMap<>();
        return template.exchange("/locations",
                HttpMethod.POST,
                new HttpEntity(storeLocationRequest),
                new ParameterizedTypeReference<HttpResponseEntity<StoreLocationResponse>>() {
                }).getBody();
    }
}
