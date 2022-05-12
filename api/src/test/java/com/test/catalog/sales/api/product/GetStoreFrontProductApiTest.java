package com.test.catalog.sales.api.product;

import com.test.catalog.sales.client.response.HttpResponseEntity;
import com.test.catalog.sales.client.response.product.StoreFrontProduct;
import com.test.catalog.sales.client.response.product.StoreFrontProductResponse;
import com.test.catalog.sales.domain.service.store.ProductStoreService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class GetStoreFrontProductApiTest {
    StoreFrontProductResponse productStoreResponse;
    List<StoreFrontProduct> storeFrontProductList;
    StoreFrontProduct storeFrontProduct;
    @InjectMocks
    private GetStoreFrontProductApi getStoreFrontProductApi;
    @Mock
    private ProductStoreService productStoreService;
    private List<Integer> recordStatusId = new ArrayList<Integer>();
    private String sDate = "2020-07-11 12:30:00-0000";
    private String dateFormat = "yyyy-MM-dd HH:mm:ssZZZZ";
    private final Integer recordsSize=4;

    @Before
    public void setUp() throws Exception {
        UUID productId = UUID.randomUUID();
        recordStatusId.add(2);
        storeFrontProductList = new ArrayList<>();
        storeFrontProduct = new StoreFrontProduct();
        storeFrontProduct.setStoreId("006");
        storeFrontProduct.setChannelId("catering");
        storeFrontProduct.setOfferingId("web");
        storeFrontProduct.setProductId(productId);
        storeFrontProductList.add(storeFrontProduct);
        productStoreResponse = new StoreFrontProductResponse();
        productStoreResponse.setStoreFrontProductList(storeFrontProductList);
    }

    /**
     * Test to validate get storeFrontProducts
     */
    @Test
    public void getStoreFrontProducts() throws ParseException {
        Date releaseDate = new SimpleDateFormat(dateFormat).parse(sDate);
        //Mockito.when(productStoreService.getStorefrontProducts(releaseDate, recordStatusId)).thenReturn(productStoreResponse);
        HttpResponseEntity<StoreFrontProductResponse> responseEntity =
                getStoreFrontProductApi.getStoreFrontProducts(sDate,"PUBLISHED",5);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatus().getHttpStatus());

        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatus().getHttpStatus());
    }

    /**
     * Test to validate get of toreFrontProducts incorrect date format
     */
    @Test(expected = ParseException.class)
    public void validateDateExceptionResponse() throws ParseException {
        String sDate1 = "2020-06-11 12";
        Date releaseDate = new SimpleDateFormat(dateFormat).parse(sDate1);
    }



}