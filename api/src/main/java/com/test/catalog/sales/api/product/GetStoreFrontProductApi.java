package com.test.catalog.sales.api.product;

import com.test.catalog.sales.api.constant.HttpMessageEnum;
import com.test.catalog.sales.api.validator.ValuesAllowed;
import com.test.catalog.sales.client.response.HttpResponseEntity;
import com.test.catalog.sales.client.response.Status;
import com.test.catalog.sales.client.response.product.StoreFrontProductResponse;
import com.test.catalog.sales.client.util.ValidDate;
import com.test.catalog.sales.db.constant.RecordStatusEnum;
import com.test.catalog.sales.domain.service.store.ProductStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "${salescatalog.relative.path}", produces = "application/json")
@Validated
public class GetStoreFrontProductApi {

    private ProductStoreService productStoreService;

    @Value("#{'${salescatalog.statusQueryParam.value}'.split(',')}")
    private List<Integer> recordStatusId;
    @Value("#{'${salescatalog.unpublished.eligible.status.ids}'.split(',')}")
    private List<Integer> unPublishedProductsStatus;

    @Autowired
    public GetStoreFrontProductApi(
            ProductStoreService productStoreService) {
        this.productStoreService = productStoreService;
    }

    /**
     * This method call the ProductStore service impl to get the ProductStore list for storefront
     *
     * @param releaseDate
     * @param nextState
     * @param recordsSize
     * @return HttpResponseEntity
     */
    @GetMapping(path = "/products/locations")
    public HttpResponseEntity<StoreFrontProductResponse> getStoreFrontProducts(@ValidDate(format = "yyyy-MM-dd HH:mm:ssZZZZ") @RequestParam final String releaseDate,
                                                                               @ValuesAllowed(propName = "nextState", values = {"UNPUBLISHED", "PUBLISHED"}) @RequestParam String nextState,
                                                                               @NotNull @RequestParam(value = "recordsSize") Integer recordsSize) throws ParseException {
        log.debug("getStoreFrontProducts {}", releaseDate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZZZZ");
        Date date = sdf.parse(releaseDate);
        StoreFrontProductResponse productStoreResponse =
                productStoreService.getStorefrontProducts(date, nextState.equalsIgnoreCase(RecordStatusEnum.PUBLISHED.getStatusName()) ? recordStatusId : unPublishedProductsStatus ,recordsSize);
        final Status status =
                new Status(
                        HttpStatus.OK, HttpStatus.OK.value(), HttpMessageEnum.RETRIEVED_SUCCESS.getMessage());
        return new HttpResponseEntity<>(status, productStoreResponse);
    }
}
