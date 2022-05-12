package com.test.catalog.sales.api.component;

import com.test.catalog.sales.api.constant.HttpMessageEnum;
import com.test.catalog.sales.client.request.component.StoreFrontComponentStatusRequest;
import com.test.catalog.sales.client.response.HttpResponseEntity;
import com.test.catalog.sales.client.response.Status;
import com.test.catalog.sales.client.response.component.StoreFrontComponentStatusResponse;
import com.test.catalog.sales.domain.service.store.StoreIngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "${salescatalog.relative.path}", produces = "application/json")
public class UpdateStoreFrontComponentApi {

    private final StoreIngredientService storeIngredientService;

    @Autowired
    public UpdateStoreFrontComponentApi(StoreIngredientService storeIngredientService) {
        this.storeIngredientService = storeIngredientService;
    }

    /**
     * This method call the update the status of StoreFront Component
     *
     * @param request
     * @return HttpResponseEntity
     */
    @PostMapping(path = "/component/state")
    public HttpResponseEntity<StoreFrontComponentStatusResponse> updateStoreFrontComponent(@Valid @RequestBody final StoreFrontComponentStatusRequest request) {
        log.debug("UpdateStoreFrontComponentApi.updateStoreFrontComponent {}", request);
        StoreFrontComponentStatusResponse storeFrontComponentStatusResponse = storeIngredientService.updateStoreFrontComponent(request.getStateList());
        final Status status = new Status(HttpStatus.OK, HttpStatus.OK.value(), HttpMessageEnum.UPDATED_SUCCESS.getMessage());
        return new HttpResponseEntity<>(status, storeFrontComponentStatusResponse);
    }
}
