package com.test.catalog.sales.client.component;

import com.test.catalog.sales.client.request.component.StoreFrontComponentStatusRequest;
import com.test.catalog.sales.client.response.HttpResponseEntity;
import com.test.catalog.sales.client.response.Status;
import com.test.catalog.sales.client.response.component.StoreFrontComponentResultStatus;
import com.test.catalog.sales.client.response.component.StoreFrontComponentStatusResponse;
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
public class StoreFrontComponentClientTest {

    @InjectMocks
    StoreFrontComponentClient storeFrontComponentClient;

    @Mock
    private RestTemplate template;

    StoreFrontComponentStatusRequest request = new StoreFrontComponentStatusRequest();
    StoreFrontComponentStatusResponse storeFrontComponentStatusResponse = new StoreFrontComponentStatusResponse();
    HttpResponseEntity<StoreFrontComponentStatusResponse> storeFrontComponentStatusResponseHttpEntity = null;
    ResponseEntity<HttpResponseEntity<StoreFrontComponentStatusResponse>> storeFrontComponentStatusResponseHttpResponseEntity = null;

    @Before
    public void setUp(){
        List<StoreFrontComponentResultStatus> resultList = new ArrayList<>();
        StoreFrontComponentResultStatus storeFrontComponentResultStatus = new StoreFrontComponentResultStatus();
        storeFrontComponentResultStatus.setChannelId("Channel1");
        storeFrontComponentResultStatus.setComponentId(UUID.randomUUID());
        storeFrontComponentResultStatus.setRecordStatus("Published");
        storeFrontComponentResultStatus.setStoreId("279");
        storeFrontComponentResultStatus.setReleaseDate("2020-17-9");
        storeFrontComponentResultStatus.setSuccess(true);
        resultList.add(storeFrontComponentResultStatus);
        storeFrontComponentStatusResponse.setStateList(resultList);
        Status status = new Status(HttpStatus.OK, HttpStatus.OK.value(), "");
        storeFrontComponentStatusResponseHttpEntity = new HttpResponseEntity(status,storeFrontComponentStatusResponse );
        storeFrontComponentStatusResponseHttpResponseEntity = new ResponseEntity(storeFrontComponentStatusResponseHttpEntity, HttpStatus.OK);
    }

    @Test
    public void createStoreLocationClientSuccees(){
        final Map<String, String> pathParam = new HashMap<>();
        Mockito.when(template.exchange("/component/state",
                HttpMethod.POST,
                new HttpEntity(request),
                new ParameterizedTypeReference<HttpResponseEntity<StoreFrontComponentStatusResponse>>() {
                } )
        ).thenReturn(storeFrontComponentStatusResponseHttpResponseEntity);

        HttpResponseEntity<StoreFrontComponentStatusResponse> res = storeFrontComponentClient.updateStoreFrontComponent(request);
        StoreFrontComponentStatusResponse storeLocationResponse = res.getData();
        Assert.assertNotNull(storeLocationResponse);
    }
}
