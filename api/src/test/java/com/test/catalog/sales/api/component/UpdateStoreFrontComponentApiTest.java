package com.test.catalog.sales.api.component;

import com.test.catalog.sales.client.request.component.StoreFrontComponentStatus;
import com.test.catalog.sales.client.request.component.StoreFrontComponentStatusRequest;
import com.test.catalog.sales.client.response.HttpResponseEntity;
import com.test.catalog.sales.client.response.component.StoreFrontComponentResultStatus;
import com.test.catalog.sales.client.response.component.StoreFrontComponentStatusResponse;
import com.test.catalog.sales.domain.service.store.StoreIngredientService;
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
public class UpdateStoreFrontComponentApiTest {

    @InjectMocks
    UpdateStoreFrontComponentApi updateStoreFrontComponentApi;

    @Mock
    StoreIngredientService storeIngredientService;

    StoreFrontComponentStatusRequest request = new StoreFrontComponentStatusRequest();
    StoreFrontComponentStatusResponse response = new StoreFrontComponentStatusResponse();

    @Before
    public void setup(){
        UUID componentId = UUID.randomUUID();
        List<StoreFrontComponentStatus> stateList = new ArrayList<>();
        StoreFrontComponentStatus storeFrontComponentStatus = new StoreFrontComponentStatus();
        storeFrontComponentStatus.setChannelId("Channel1");
        storeFrontComponentStatus.setComponentId(componentId);
        storeFrontComponentStatus.setRecordStatus("Published");
        storeFrontComponentStatus.setStoreId("279");
        storeFrontComponentStatus.setReleaseDate("2020-17-9");
        stateList.add(storeFrontComponentStatus);
        request.setStateList(stateList);

        List<StoreFrontComponentResultStatus> resultList = new ArrayList<>();
        StoreFrontComponentResultStatus status = new StoreFrontComponentResultStatus();
        status.setChannelId("Channel1");
        status.setComponentId(componentId);
        status.setRecordStatus("Published");
        status.setStoreId("279");
        status.setReleaseDate("2020-17-9");
        status.setSuccess(true);
        resultList.add(status);
        response.setStateList(resultList);
    }

    @Test
    public void test(){
        Mockito.when(storeIngredientService.updateStoreFrontComponent(request.getStateList())).thenReturn(response);
        HttpResponseEntity<StoreFrontComponentStatusResponse> responseEntity = updateStoreFrontComponentApi.updateStoreFrontComponent(request);
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(responseEntity.getData());
        Assert.assertNotNull(responseEntity.getData().getStateList());
        Assert.assertEquals(true,responseEntity.getData().getStateList().get(0).isSuccess());
    }
}
