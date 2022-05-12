package com.test.catalog.sales.client.price;

import com.test.catalog.sales.client.request.price.SapArticlePriceRequest;
import com.test.catalog.sales.client.request.price.UpdatePriceHistoryStatusRequest;
import com.test.catalog.sales.client.response.HttpResponseEntity;
import com.test.catalog.sales.client.response.price.UpdatePriceHistoryStatusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

/**
 * this is the client for creation of Sap Article Price
 */
public class SapArticlePriceClient {

    private static final Logger LOG = LoggerFactory.getLogger(SapArticlePriceClient.class);

    private final RestTemplate template;

    @Autowired
    public SapArticlePriceClient(RestTemplate template) {
        this.template = template;
    }

    /**
     * this method is to create Sap Article Price for given request
     *
     * @param sapArticlePriceRequest
     * @return HttpResponseEntity<SapArticlePriceRequest>
     */
    public HttpResponseEntity<SapArticlePriceRequest> createSapArticlePrice(SapArticlePriceRequest sapArticlePriceRequest) {
        LOG.debug("Calling createStoreLocation method of SapArticlePriceClient");
        final Map<String, String> pathParam = new HashMap<>();
        return template.exchange("/prices",
                HttpMethod.POST,
                new HttpEntity(sapArticlePriceRequest),
                new ParameterizedTypeReference<HttpResponseEntity<SapArticlePriceRequest>>() {
                }).getBody();
    }

    /**
     * this method is to update Price status history for given request
     *
     * @param updatePriceHistoryStatusRequest
     * @return HttpResponseEntity<UpdatePriceHistoryStatusResponse>
     */
    public HttpResponseEntity<UpdatePriceHistoryStatusResponse> updatePriceStatusHistory(UpdatePriceHistoryStatusRequest updatePriceHistoryStatusRequest) {
        LOG.debug("Calling updatePriceStatusHistory method of SapArticlePriceClient");
        final Map<String, String> pathParam = new HashMap<>();
        return template.exchange("/prices/state",
                HttpMethod.POST,
                new HttpEntity(updatePriceHistoryStatusRequest),
                new ParameterizedTypeReference<HttpResponseEntity<UpdatePriceHistoryStatusResponse>>() {
                }).getBody();
    }
}
