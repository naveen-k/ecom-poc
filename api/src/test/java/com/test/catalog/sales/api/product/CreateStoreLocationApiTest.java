package com.test.catalog.sales.api.product;

import com.test.catalog.sales.api.store.CreateStoreLocationApi;
import com.test.catalog.sales.client.request.store.OfferingsChannel;
import com.test.catalog.sales.client.request.store.StoreChannel;
import com.test.catalog.sales.client.request.store.StoreLocationRequest;
import com.test.catalog.sales.client.response.HttpResponseEntity;
import com.test.catalog.sales.client.response.store.StoreLocationDetail;
import com.test.catalog.sales.client.response.store.StoreLocationResponse;
import com.test.catalog.sales.domain.service.store.StoreLocationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CreateStoreLocationApiTest {

    @InjectMocks
    private CreateStoreLocationApi createStoreLocationApi;

    @Mock
    private StoreLocationService storeLocationService;

    StoreLocationResponse storeLocationResponse = new StoreLocationResponse();
    StoreLocationDetail storeLocationDetail = new StoreLocationDetail();
    StoreLocationRequest storeLocationRequest = new StoreLocationRequest();

    private String storeId = "00322";
    private String storeName = "test, STORE # 0322";
    private String offeringId = "cateringLite";
//Test
    private List<OfferingsChannel> offeringsChannels = new ArrayList<>();
    private List<StoreChannel> storeChannels = new ArrayList<>();

    @Before
    public void setup() {
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
    }

    /**
     * test to validate createSalesCatalog
     */
    @Test
    public void createSalesCatalogSuccess() {
        Mockito.when(storeLocationService.createStoreLocation(storeLocationRequest)).thenReturn(storeLocationResponse);

        HttpResponseEntity<StoreLocationResponse> responseEntity = createStoreLocationApi.createSalesCatalog(storeLocationRequest);

        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(201, responseEntity.getStatus().getStatusCode().longValue());
    }

}
