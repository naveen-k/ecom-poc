package com.test.catalog.sales.client.store;

import com.test.catalog.sales.client.request.store.OfferingsChannel;
import com.test.catalog.sales.client.request.store.StoreChannel;
import com.test.catalog.sales.client.request.store.StoreLocationRequest;
import com.test.catalog.sales.client.response.HttpResponseEntity;
import com.test.catalog.sales.client.response.Status;
import com.test.catalog.sales.client.response.store.StoreLocationDetail;
import com.test.catalog.sales.client.response.store.StoreLocationResponse;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class SalesCataLogCreateStoreLocationClientTest {

    @InjectMocks
    SalesCataLogCreateStoreLocationClient salesCataLogCreateStoreLocationClient;

    @Mock
    private RestTemplate template;

    private ResponseEntity<HttpResponseEntity<StoreLocationResponse>> locationResponseResponseEntity = null;
    private HttpResponseEntity<StoreLocationResponse> responseHttpResponseEntity = null;
    private StoreLocationResponse storeLocationResponse = new StoreLocationResponse();
    private StoreLocationDetail storeLocationDetail = new StoreLocationDetail();
    private StoreLocationRequest storeLocationRequest = new StoreLocationRequest();
    private String storeId = "00322";
    private String storeName = "test, STORE # 0322";
    private String offeringId = "cateringLite";

    private List<OfferingsChannel> offeringsChannels = new ArrayList<>();
    private List<StoreChannel> storeChannels = new ArrayList<>();
    @Before
    public void setup(){
        OfferingsChannel offeringsChannel = new OfferingsChannel();
        offeringsChannel.setOfferingId(offeringId);
        StoreChannel storeChannel = new StoreChannel();
        storeChannel.setChannelId("WEB");
        StoreChannel storeChannel2 = new StoreChannel();
        storeChannel2.setChannelId("Store");
        storeChannels.add(storeChannel);
        storeChannels.add(storeChannel2);
        offeringsChannel.setSalesChannelList(storeChannels);
        offeringsChannels.add(offeringsChannel);

        storeLocationRequest.setStoreId(storeId);
        storeLocationRequest.setOfferings(offeringsChannels);
        storeLocationRequest.setStoreName(storeName);
        storeLocationRequest.setRecordStatusId(4);

        storeLocationDetail.setStoreId(storeId);
        storeLocationDetail.setStorename(storeName);
        storeLocationDetail.setOfferings(offeringsChannels);
        storeLocationDetail.setRecordStatusId(4);
        storeLocationResponse.setStoreLocationDetails(storeLocationDetail);
        Status status = new Status(HttpStatus.OK, HttpStatus.OK.value(), "");

        responseHttpResponseEntity = new HttpResponseEntity(status,storeLocationResponse );
        locationResponseResponseEntity = new ResponseEntity(responseHttpResponseEntity,HttpStatus.CREATED);
    }
    
    @Test
    public void createStoreLocationClientSuccees(){
        final Map<String, String> pathParam = new HashMap<>();
        Mockito.when(template.exchange("/locations",
                HttpMethod.POST,
                new HttpEntity(storeLocationRequest),
                new ParameterizedTypeReference<HttpResponseEntity<StoreLocationResponse>>() {
                } )
        ).thenReturn(locationResponseResponseEntity);

        HttpResponseEntity<StoreLocationResponse> res = salesCataLogCreateStoreLocationClient.createStoreLocation(storeLocationRequest);
        StoreLocationResponse storeLocationResponse = res.getData();
        Assert.assertNotNull(storeLocationResponse);
    }
}
