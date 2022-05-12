package com.test.catalog.sales.client.price;

import com.test.catalog.sales.client.request.price.SapArticlePriceRequest;
import com.test.catalog.sales.client.request.price.UpdatePriceHistoryStatus;
import com.test.catalog.sales.client.request.price.UpdatePriceHistoryStatusRequest;
import com.test.catalog.sales.client.response.HttpResponseEntity;
import com.test.catalog.sales.client.response.Status;
import com.test.catalog.sales.client.response.price.PriceHistoryStatusResponse;
import com.test.catalog.sales.client.response.price.UpdatePriceHistoryStatusResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class SapArticlePriceClientTest {

    @InjectMocks
    SapArticlePriceClient sapArticlePriceClient;

    @Mock
    private RestTemplate restTemplate;
    SapArticlePriceRequest sapArticlePriceRequest;

    UpdatePriceHistoryStatusRequest updatePriceHistoryStatusRequest;
    List<UpdatePriceHistoryStatus> statusList = new ArrayList<>();
    UpdatePriceHistoryStatusResponse updatePriceHistoryStatusResponse;
    List<PriceHistoryStatusResponse> statusResponseList = new ArrayList<>();
    UUID productId = UUID.randomUUID();

    @Before
    public void setup(){
        updatePriceHistoryStatusRequest = new UpdatePriceHistoryStatusRequest();
        UpdatePriceHistoryStatus updatePriceHistoryStatus = new UpdatePriceHistoryStatus();
        updatePriceHistoryStatus.setBeginEffectiveDate("2020-10-11");
        updatePriceHistoryStatus.setEndEffectiveDate("2020-11-11");
        updatePriceHistoryStatus.setChannelId("channelId1");
        updatePriceHistoryStatus.setStoreId("storeId1");
        updatePriceHistoryStatus.setOfferingId("offeringId1");
        updatePriceHistoryStatus.setPrice(new BigDecimal(1.0));
        updatePriceHistoryStatus.setProductId(productId);
        statusList.add(updatePriceHistoryStatus);
        updatePriceHistoryStatusRequest.setStatusList(statusList);

        updatePriceHistoryStatusResponse = new UpdatePriceHistoryStatusResponse();
        PriceHistoryStatusResponse statusResponse = new PriceHistoryStatusResponse();
        statusResponse.setBeginEffectiveDate("2020-10-11");
        statusResponse.setEndEffectiveDate("2020-11-11");
        statusResponse.setChannelId("channelId1");
        statusResponse.setStoreId("storeId1");
        statusResponse.setOfferingId("offeringId1");
        statusResponse.setPrice(new BigDecimal(1.0));
        statusResponse.setProductId(productId);
        statusResponseList.add(statusResponse);
        updatePriceHistoryStatusResponse.setStatusList(statusResponseList);
    }

    @Test
    public void test() throws ParseException {

        sapArticlePriceRequest = new SapArticlePriceRequest();
        sapArticlePriceRequest.setStoreId("279");
        sapArticlePriceRequest.setUom("UOM2");
        sapArticlePriceRequest.setArticleId("mdm-p-56_sap_id");
        sapArticlePriceRequest.setChannelId("kiosk");
        sapArticlePriceRequest.setPrice(new BigDecimal(10.00));
        sapArticlePriceRequest.setBeginEffectiveDate("2020-05-05");
        sapArticlePriceRequest.setEndEffectiveDate("2020-05-07");
        Status status = new Status(HttpStatus.CREATED, HttpStatus.CREATED.value(), "");
        ResponseEntity<HttpResponseEntity<SapArticlePriceRequest>> responseEntity = new ResponseEntity(new HttpResponseEntity(status, sapArticlePriceRequest), HttpStatus.CREATED);

        final Map<String, String> pathParam = new HashMap<>();

        Mockito.when(restTemplate.exchange("/prices",
                HttpMethod.POST,
                new HttpEntity(sapArticlePriceRequest),
                new ParameterizedTypeReference<HttpResponseEntity<SapArticlePriceRequest>>() {
                } ) ).thenReturn(responseEntity);

        HttpResponseEntity<SapArticlePriceRequest> response = sapArticlePriceClient.createSapArticlePrice(sapArticlePriceRequest);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getData());
        Assert.assertEquals("mdm-p-56_sap_id",response.getData().getArticleId());
    }

    @Test
    public void testUpdatePriceStatusHistory() throws ParseException {

        Status status = new Status(HttpStatus.OK, HttpStatus.OK.value(), "");

        ResponseEntity<HttpResponseEntity<UpdatePriceHistoryStatusResponse>> responseEntity = new ResponseEntity(new HttpResponseEntity(status, updatePriceHistoryStatusResponse), HttpStatus.OK);

        final Map<String, String> pathParam = new HashMap<>();

        Mockito.when(restTemplate.exchange("/prices/state",
                HttpMethod.POST,
                new HttpEntity(updatePriceHistoryStatusRequest ),
                new ParameterizedTypeReference<HttpResponseEntity<UpdatePriceHistoryStatusResponse>>() {
                } ) ).thenReturn(responseEntity);

        HttpResponseEntity<UpdatePriceHistoryStatusResponse> response = sapArticlePriceClient.updatePriceStatusHistory(updatePriceHistoryStatusRequest);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getData());
        Assert.assertNotNull(response.getData().getStatusList());
    }
}
