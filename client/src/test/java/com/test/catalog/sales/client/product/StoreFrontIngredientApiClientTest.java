package com.test.catalog.sales.client.product;


import com.test.catalog.sales.client.response.HttpResponseEntity;
import com.test.catalog.sales.client.response.Status;
import com.test.catalog.sales.client.response.ingredient.StoreFrontIngredient;
import com.test.catalog.sales.client.response.ingredient.StoreFrontIngredientResponse;
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class StoreFrontIngredientApiClientTest {

    @InjectMocks
    StoreFrontIngredientApiClient StoreFrontIngredientApiClient;

    @Mock
    private RestTemplate restTemplate;

    StoreFrontIngredientResponse storeFrontIngredientResponse;

    private String nextState = "PUBLISHED";
    private final Integer recordsSize=4;
    private String releaseDate = "2020-07-11 12:30:00-0000";

    @Before
    public void setUp(){

        StoreFrontIngredient storeFrontIngredient = new StoreFrontIngredient();
        storeFrontIngredient.setChannelId("catering");
        Set storeFrontAssociationList = new HashSet<StoreFrontIngredient>();
        storeFrontAssociationList.add(storeFrontIngredient);
        storeFrontIngredientResponse = new StoreFrontIngredientResponse();
        storeFrontIngredientResponse.setStoreFrontIngredientList(storeFrontAssociationList);
    }

    @Test
    public void getStoreFrontIngredientsTest(){
        Status status = new Status(HttpStatus.OK, HttpStatus.OK.value(), "");
        ResponseEntity<HttpResponseEntity<StoreFrontIngredientResponse>> responseEntity = new ResponseEntity(new HttpResponseEntity(status, storeFrontIngredientResponse), HttpStatus.OK);

        final Map<String, String> pathParam = new HashMap<>();

        Mockito.when(restTemplate.exchange("/ingredients/locations?nextState="+ nextState +"&releaseDate=" + releaseDate +"&recordsSize=" +recordsSize,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<HttpResponseEntity<StoreFrontIngredientResponse>>() {
                }, pathParam)).thenReturn(responseEntity);

        HttpResponseEntity<StoreFrontIngredientResponse> response = StoreFrontIngredientApiClient.getStoreFrontIngredients( nextState,  releaseDate,  recordsSize);
        Assert.assertNotNull(response);
        Assert.assertEquals(response.getStatus().getHttpStatus(), HttpStatus.OK);
        Assert.assertNotNull(response.getData());
        Assert.assertNotNull(response.getData().getStoreFrontIngredientList());
    }
}