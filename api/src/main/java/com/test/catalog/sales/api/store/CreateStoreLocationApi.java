package com.test.catalog.sales.api.store;

import com.test.catalog.sales.api.constant.HttpMessageEnum;
import com.test.catalog.sales.client.request.store.StoreLocationRequest;
import com.test.catalog.sales.client.response.HttpResponseEntity;
import com.test.catalog.sales.client.response.Status;
import com.test.catalog.sales.client.response.store.StoreLocationResponse;
import com.test.catalog.sales.domain.service.store.StoreLocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "${salescatalog.relative.path}", produces = "application/json")
public class CreateStoreLocationApi {

    private static final Logger LOG = LoggerFactory.getLogger(CreateStoreLocationApi.class);

    private final StoreLocationService storeLocationService;

    @Autowired
    public CreateStoreLocationApi(StoreLocationService storeLocationService) {
        this.storeLocationService = storeLocationService;
    }

    /**
     * This method call the StoreLocation service impl to create StoreLocation
     *
     * @param storeLocationRequest
     * @return
     */
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(path = "/locations")
    public HttpResponseEntity<StoreLocationResponse> createSalesCatalog(@Valid @RequestBody final StoreLocationRequest storeLocationRequest) {
        LOG.debug("Calling CreateStoreLocation.createCategory ");
        final StoreLocationResponse storeLocationResponse = storeLocationService.createStoreLocation(storeLocationRequest);
        final Status status =
                new Status(
                        HttpStatus.CREATED, HttpStatus.CREATED.value(), HttpMessageEnum.CREATED_SUCCESS.getMessage());
        return new HttpResponseEntity<>(status, storeLocationResponse);
    }
}
