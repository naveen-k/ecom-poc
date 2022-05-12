package com.test.catalog.sales.client.product;

import com.test.catalog.sales.client.request.product.StoreFrontProductStatusRequest;
import com.test.catalog.sales.client.request.product.StoreFrontProductStatus;
import com.test.catalog.sales.client.response.HttpResponseEntity;
import com.test.catalog.sales.client.response.Status;
import com.test.catalog.sales.client.response.product.StoreFrontProduct;
import com.test.catalog.sales.client.response.product.StoreFrontProductResponse;
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

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class StoreFrontProductApiClientTest {
    @InjectMocks
    StoreFrontProductApiClient storeFrontProductApiClient;
    StoreFrontProductResponse productStoreResponse;
    List<StoreFrontProduct> storeFrontProductList;
    StoreFrontProduct storeFrontProduct;
    @Mock
    private RestTemplate restTemplate;
    private String sDate = "2020-07-11 12:30:00-0000";
    private String storeId = "279";
    String sapArticleId = "sap-1234";
    UUID productId = UUID.randomUUID();

    private StoreFrontProductStatusRequest request;
    private final Integer recordsSize=4;

    @Before
    public void setUp() throws Exception {
        storeFrontProductList = new ArrayList<>();
        storeFrontProduct = new StoreFrontProduct();
        storeFrontProduct.setStoreId("006");
        storeFrontProduct.setChannelId("catering");
        storeFrontProduct.setOfferingId("web");
        storeFrontProduct.setProductId(productId);
        storeFrontProductList.add(storeFrontProduct);
        productStoreResponse = new StoreFrontProductResponse();
        productStoreResponse.setStoreFrontProductList(storeFrontProductList);
        request= new StoreFrontProductStatusRequest();
        StoreFrontProductStatus item= new StoreFrontProductStatus();
        item.setProductId(productId);
        item.setStoreId("9999");
        item.setOfferingId("inStore");
        item.setChannelId("web");
        item.setRecordStatus("PUBLISHED");
        List<StoreFrontProductStatus> stateList = new ArrayList<>();
        stateList.add(item);
        request.setStateList(stateList);
    }

    @Test
    public void getStoreFrontProducts() {
        Status status = new Status(HttpStatus.OK, HttpStatus.OK.value(), "");
        ResponseEntity<HttpResponseEntity<StoreFrontProductResponse>> responseEntity = new ResponseEntity(new HttpResponseEntity(status, productStoreResponse), HttpStatus.OK);

        final Map<String, String> pathParam = new HashMap<>();

        Mockito.when(restTemplate.exchange("/products/locations?releaseDate=" + sDate +"&nextState=PUBLISHED"+ "&recordsSize=" + recordsSize,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<HttpResponseEntity<StoreFrontProductResponse>>() {
                }, pathParam)).thenReturn(responseEntity);

        HttpResponseEntity<StoreFrontProductResponse> response = storeFrontProductApiClient.getStoreFrontProducts(sDate,"PUBLISHED",recordsSize);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus().getHttpStatus(), HttpStatus.OK);
        Assert.assertNotNull(response.getData());
        Assert.assertEquals(response.getData().getStoreFrontProductList().get(0).getChannelId(), "catering");
    }
    @Test
    public void getStoreFrontProductsForUnpublished() {
        Status status = new Status(HttpStatus.OK, HttpStatus.OK.value(), "");
        ResponseEntity<HttpResponseEntity<StoreFrontProductResponse>> responseEntity = new ResponseEntity(new HttpResponseEntity(status, productStoreResponse), HttpStatus.OK);

        final Map<String, String> pathParam = new HashMap<>();
        Mockito.when(restTemplate.exchange("/products/locations?releaseDate=" + sDate+"&nextState=UNPUBLISHED&recordsSize="+recordsSize,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<HttpResponseEntity<StoreFrontProductResponse>>() {
                }, pathParam)).thenReturn(responseEntity);

        HttpResponseEntity<StoreFrontProductResponse> response = storeFrontProductApiClient.getStoreFrontProducts(sDate,"UNPUBLISHED",recordsSize );
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus().getHttpStatus(), HttpStatus.OK);
        Assert.assertNotNull(response.getData());
        Assert.assertEquals(response.getData().getStoreFrontProductList().get(0).getChannelId(), "catering");
    }

    @Test
    public void getStoreProducts() {
        Status status = new Status(HttpStatus.OK, HttpStatus.OK.value(), "");
        ResponseEntity<HttpResponseEntity<StoreFrontProductResponse>> responseEntity = new ResponseEntity(new HttpResponseEntity(status, productStoreResponse), HttpStatus.OK);

        final Map<String, String> pathParam = new HashMap<>();
        Mockito.when(restTemplate.exchange("/products?storeId=" + storeId+"&sapArticleId="+sapArticleId+"&uom=EA",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<HttpResponseEntity<StoreFrontProductResponse>>() {
                }, pathParam)).thenReturn(responseEntity);
        HttpResponseEntity<StoreFrontProductResponse> response = storeFrontProductApiClient.getStoreProducts(storeId,sapArticleId, "EA");
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus().getHttpStatus(), HttpStatus.OK);
        Assert.assertNotNull(response.getData());
        Assert.assertEquals(response.getData().getStoreFrontProductList().get(0).getChannelId(), "catering");
    }
    @Test
    public void storeProductsUpdate() {
        Status status = new Status(HttpStatus.OK, HttpStatus.OK.value(), "");
        ResponseEntity<HttpResponseEntity<StoreFrontProductStatusRequest>> responseEntity = new ResponseEntity(new HttpResponseEntity(status, request), HttpStatus.OK);

        final Map<String, String> pathParam = new HashMap<>();
        Mockito.when(restTemplate.exchange("/products/state",
                HttpMethod.POST,
                new HttpEntity(request),
                new ParameterizedTypeReference<HttpResponseEntity<StoreFrontProductStatusRequest>>() {
                }, pathParam)).thenReturn(responseEntity);
        HttpResponseEntity<StoreFrontProductStatusRequest> response = storeFrontProductApiClient.updateStoreFrontProductStatus(request);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus().getHttpStatus(), HttpStatus.OK);
    }
}