package com.test.catalog.sales.api.price;

import com.test.catalog.sales.client.request.price.SapArticlePriceRequest;
import com.test.catalog.sales.client.response.HttpResponseEntity;
import com.test.catalog.sales.domain.service.price.SapArticlePriceService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.text.ParseException;

@RunWith(MockitoJUnitRunner.class)
public class CreateSapArticlePriceApiTest {

    @InjectMocks
    CreateSapArticlePriceApi createSapArticlePriceApi;

    @Mock
    SapArticlePriceService sapArticlePriceService;

    SapArticlePriceRequest sapArticlePriceRequest;

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
        HttpResponseEntity<SapArticlePriceRequest> responseEntity = createSapArticlePriceApi.createSapArticlePrice(sapArticlePriceRequest);
        Assert.assertNotNull(responseEntity);
        Assert.assertNotNull(responseEntity.getData());
        Assert.assertEquals("mdm-p-56_sap_id",responseEntity.getData().getArticleId());
    }
}
