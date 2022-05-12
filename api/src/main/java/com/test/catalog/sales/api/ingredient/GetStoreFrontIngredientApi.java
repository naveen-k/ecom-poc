package com.test.catalog.sales.api.ingredient;

import com.test.catalog.sales.api.validator.ValuesAllowed;
import com.test.catalog.sales.api.constant.HttpMessageEnum;
import com.test.catalog.sales.client.response.HttpResponseEntity;
import com.test.catalog.sales.client.response.Status;
import com.test.catalog.sales.client.response.ingredient.StoreFrontIngredientResponse;
import com.test.catalog.sales.domain.service.store.StoreIngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "${salescatalog.relative.path}", produces = "application/json")
public class GetStoreFrontIngredientApi {

    private StoreIngredientService storeIngredientService;

    @Autowired
    public GetStoreFrontIngredientApi(StoreIngredientService storeIngredientService){
        this.storeIngredientService = storeIngredientService;
    }

    /**
     * This method call the StoreIngredient service impl to get the StoreIngredient list for storefront
     * @param releaseDate
     * @return HttpResponseEntity
     */
    @GetMapping(path = "/ingredients/locations")
    public HttpResponseEntity<StoreFrontIngredientResponse> getStoreFrontIngredients( @ValuesAllowed(propName = "nextState", values = {"UNPUBLISHED", "PUBLISHED", "Published", "UnPublished"})
                                                                                         @RequestParam("nextState") final String nextState
                                                                                        , @Valid @RequestParam(value = "releaseDate")
                                                                                          @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ssZZZZ") final Date releaseDate,
                                                                                     @NotNull @RequestParam(value = "recordsSize")  Integer recordsSize) {
        log.debug(" GetStoreFrontIngredientApi getStoreFrontIngredients releaseDate: {}, recordsSize: {} ",releaseDate, recordsSize);
        StoreFrontIngredientResponse storeFrontIngredientResponse =
                storeIngredientService.getStorefrontIngredients(nextState, releaseDate, recordsSize);
        final Status status =
                new Status(
                        HttpStatus.OK, HttpStatus.OK.value(), HttpMessageEnum.RETRIEVED_SUCCESS.getMessage());
        return new HttpResponseEntity<>(status, storeFrontIngredientResponse);
    }
}
