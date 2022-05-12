package com.test.catalog.sales.client.component;

import com.test.catalog.sales.client.request.component.StoreFrontComponentStatusRequest;
import com.test.catalog.sales.client.response.HttpResponseEntity;
import com.test.catalog.sales.client.response.component.StoreFrontComponentStatusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

public class StoreFrontComponentClient {

    private static final Logger LOG = LoggerFactory.getLogger(StoreFrontComponentClient.class);

    private final RestTemplate template;

    @Autowired
    public StoreFrontComponentClient(RestTemplate template) {
        this.template = template;
    }

    /**
     * This method to update StoreFrontComponent in SalesCatalog
     *
     * @param request
     * @return HttpResponseEntity<StoreFrontComponentStatusResponse>
     */
    public HttpResponseEntity<StoreFrontComponentStatusResponse> updateStoreFrontComponent(StoreFrontComponentStatusRequest request) {
        LOG.debug("Calling updateStoreFrontComponent method of StoreFrontComponentClient");
        final Map<String, String> pathParam = new HashMap<>();
        return template.exchange("/component/state",
                HttpMethod.POST,
                new HttpEntity(request),
                new ParameterizedTypeReference<HttpResponseEntity<StoreFrontComponentStatusResponse>>() {
                }).getBody();
    }
}
