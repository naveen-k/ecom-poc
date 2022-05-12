package com.test.catalog.sales.api.product;

import com.test.catalog.sales.api.constant.HttpMessageEnum;
import com.test.catalog.sales.client.request.product.StoreFrontProductStatusRequest;
import com.test.catalog.sales.client.response.HttpResponseEntity;
import com.test.catalog.sales.client.response.Status;
import com.test.catalog.sales.domain.service.store.StoreLocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "${salescatalog.relative.path}", produces = "application/json")
public class UpdateStoreFrontProductApi {
    private  final StoreLocationService storeLocationService;
    @Autowired
    public UpdateStoreFrontProductApi(StoreLocationService storeLocationService) {
        this.storeLocationService = storeLocationService;
    }

    /**
     * This method call the --
     *
     * @param request
     * @return HttpResponseEntity
     */
    @PostMapping(path = "/products/state")
    public HttpResponseEntity<StoreFrontProductStatusRequest> updateStoreFrontProducts(@Valid @RequestBody final StoreFrontProductStatusRequest request) {
        log.debug("updateStoreFrontProducts {}", request);

        final Status status = new Status(HttpStatus.OK, HttpStatus.OK.value(), HttpMessageEnum.UPDATED_SUCCESS.getMessage());
        return new HttpResponseEntity<>(status, storeLocationService.updateStoreFrontStatus(request));
    }
}
