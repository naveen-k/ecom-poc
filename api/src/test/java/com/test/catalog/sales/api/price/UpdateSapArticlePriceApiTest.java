package com.test.catalog.sales.api.price;

import com.test.catalog.sales.client.request.price.UpdatePriceHistoryStatus;
import com.test.catalog.sales.client.request.price.UpdatePriceHistoryStatusRequest;
import com.test.catalog.sales.client.response.HttpResponseEntity;
import com.test.catalog.sales.client.response.price.PriceHistoryStatusResponse;
import com.test.catalog.sales.client.response.price.UpdatePriceHistoryStatusResponse;
import com.test.catalog.sales.domain.service.price.SapArticlePriceService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class UpdateSapArticlePriceApiTest {

    @InjectMocks
    UpdateSapArticlePriceApi updateSapArticlePriceApi;

    @Mock
    SapArticlePriceService sapArticlePriceService;

    UpdatePriceHistoryStatusRequest updatePriceHistoryStatusRequest;
    List<UpdatePriceHistoryStatus> statusList = new ArrayList<>();
    UpdatePriceHistoryStatusResponse updatePriceHistoryStatusResponse;
    List<PriceHistoryStatusResponse> statusResponseList = new ArrayList<>();

    @Before
    public void setup(){
        UUID productId = UUID.randomUUID();
        updatePriceHistoryStatusRequest =new UpdatePriceHistoryStatusRequest();
        UpdatePriceHistoryStatus status = new UpdatePriceHistoryStatus();
        status.setBeginEffectiveDate("2020-10-11");
        status.setEndEffectiveDate("2020-11-11");
        status.setChannelId("channelId1");
        status.setStoreId("storeId1");
        status.setOfferingId("offeringId1");
        status.setPrice(new BigDecimal(1.0));
        status.setProductId(productId);
        statusList.add(status);
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
    public void test(){
        Mockito.when(sapArticlePriceService.updatePricingHistory(updatePriceHistoryStatusRequest.getStatusList())).thenReturn(updatePriceHistoryStatusResponse);
        HttpResponseEntity<UpdatePriceHistoryStatusResponse> responseEntity = updateSapArticlePriceApi.updatePricingHistory(updatePriceHistoryStatusRequest);
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(responseEntity.getData());
        Assert.assertNotNull(responseEntity.getData().getStatusList());
        Assert.assertEquals(responseEntity.getStatus().getStatusCode().intValue(),HttpStatus.OK.value());
    }
}
