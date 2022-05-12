package com.test.catalog.sales.api.product;

import com.test.catalog.sales.client.request.product.StoreFrontProductStatus;
import com.test.catalog.sales.client.request.product.StoreFrontProductStatusRequest;
import com.test.catalog.sales.client.response.HttpResponseEntity;
import com.test.catalog.sales.db.constant.RecordStatusEnum;
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
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class UpdateStoreFrontProductApiTest {
    @InjectMocks
    UpdateStoreFrontProductApi updateStoreFrontProductApi;
    @Mock
    private  StoreLocationService storeLocationService;
    private StoreFrontProductStatusRequest request;

    @Before
    public void setUp() throws Exception {
        UUID productId = UUID.randomUUID();
        request= new StoreFrontProductStatusRequest();
        StoreFrontProductStatus item= new StoreFrontProductStatus();
        item.setProductId(productId);
        item.setStoreId("9999");
        item.setOfferingId("inStore");
        item.setChannelId("web");
        item.setRecordStatus(RecordStatusEnum.PUBLISHED.getStatusName());
        List<StoreFrontProductStatus> stateList = new ArrayList<>();
        stateList.add(item);
        request.setStateList(stateList);
    }

    @Test
    public void updateStoreFrontProducts() {
        Mockito.when(storeLocationService.updateStoreFrontStatus(request)).thenReturn(request);
        HttpResponseEntity<StoreFrontProductStatusRequest> response =updateStoreFrontProductApi.updateStoreFrontProducts(request);
        Assert.assertNotNull(response);
    }
}