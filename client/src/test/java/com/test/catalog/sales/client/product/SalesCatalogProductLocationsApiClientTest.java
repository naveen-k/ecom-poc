package com.test.catalog.sales.client.product;

import com.test.catalog.sales.client.response.HttpResponseEntity;
import com.test.catalog.sales.client.response.Status;
import com.test.catalog.sales.client.response.product.ProductStoreDetail;
import com.test.catalog.sales.client.response.product.ProductStoreResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class SalesCatalogProductLocationsApiClientTest {

    @InjectMocks
    SalesCatalogProductLocationsApiClient salesCatalogProductLocationsApiClient;

    @Mock
    private RestTemplate restTemplate;

    ProductStoreResponse productStoreResponse;

    private final String releaseDate = "2020-07-11 12:30:00-0000";
    private final String locationsUri = "/locations";
    private final String releaseDateStr = "releaseDate";
    private final UUID productId = UUID.fromString("849a3ffa-364d-4847-b9d2-85313f0d271b");


    @Before
    public void setUp() {
        ProductStoreDetail productStoreDetail = new ProductStoreDetail();
        productStoreDetail.setProductId(productId);
        productStoreDetail.setReleaseDate(new Date());
        productStoreResponse = new ProductStoreResponse();
        productStoreResponse.setProductStoreDetail(productStoreDetail);
    }

    @Test
    public void getProductStoresTest(){
        Status status = new Status(HttpStatus.OK, HttpStatus.OK.value(), "");
        ResponseEntity<HttpResponseEntity<ProductStoreResponse>> responseEntity = new ResponseEntity(new HttpResponseEntity(status, productStoreResponse), HttpStatus.OK);

        final Map<String, String> pathParam = new HashMap<>();

        Mockito.when(restTemplate.exchange("/products/"+productId+ locationsUri + "?" +releaseDateStr+ "=" +releaseDate,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<HttpResponseEntity<ProductStoreResponse>>() {
                }, pathParam)).thenReturn(responseEntity);

        HttpResponseEntity<ProductStoreResponse> response = salesCatalogProductLocationsApiClient.getProductStores(productId , releaseDate);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus().getHttpStatus(), HttpStatus.OK);
        Assert.assertNotNull(response.getData());
        Assert.assertEquals(response.getData().getProductStoreDetail().getProductId(), UUID.fromString("849a3ffa-364d-4847-b9d2-85313f0d271b"));

    }
}