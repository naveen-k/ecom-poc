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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class GetStoreFrontPublishedProductApiTest {

    StoreFrontProductResponse productStoreResponse;
    List<StoreFrontProduct> storeFrontProductList;
    StoreFrontProduct storeFrontProduct;
    @InjectMocks
    private GetStoreFrontPublishedProductApi getStoreFrontPublishedProductApi;
    @Mock
    private ProductStoreService productStoreService;
    private List<Integer> recordStatusId = new ArrayList<Integer>();
    private String storeId = "279";
    private String sapArticleId = "sap-1234";

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
     * Test to validate get getStoreProducts
     */
    @Test
    public void getStoreProducts() throws ParseException {
        HttpResponseEntity<StoreFrontProductResponse> responseEntity =
                getStoreFrontPublishedProductApi.getStoreProducts(storeId,sapArticleId, "EA");
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatus().getHttpStatus());
    }

}
