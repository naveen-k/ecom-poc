package com.test.catalog.sales.api.product;

import com.test.catalog.sales.api.constant.HttpMessageEnum;
import com.test.catalog.sales.client.response.HttpResponseEntity;
import com.test.catalog.sales.client.response.Status;
import com.test.catalog.sales.client.response.product.StoreFrontProductResponse;
import com.test.catalog.sales.domain.service.store.ProductStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequestMapping(value = "${salescatalog.relative.path}", produces = "application/json")
@Validated
public class GetStoreFrontPublishedProductApi {

   private ProductStoreService productStoreService;

   @Autowired
    public GetStoreFrontPublishedProductApi(ProductStoreService productStoreService) {
       this.productStoreService = productStoreService;
    }

    /**
     * This method call the ProductStore service impl to get the Products
     *
     * @param storeId
     * @param sapArticleId
     * @return StoreFrontProductResponse
     */
    @GetMapping("/products")
    public HttpResponseEntity<StoreFrontProductResponse> getStoreProducts( @NotNull @NotEmpty @RequestParam(value = "storeId") final String storeId,
                                                                           @NotNull @NotEmpty @RequestParam(value = "sapArticleId") final String sapArticleId,
                                                                           @NotNull @NotEmpty @RequestParam(value = "uom") final String uom) {
       log.debug("getStoreFrontProducts storeId {}",storeId);
       StoreFrontProductResponse productStoreResponse =
               productStoreService.getStorePublishedProducts(storeId, sapArticleId, uom);
       final Status status =
               new Status(
                       HttpStatus.OK, HttpStatus.OK.value(), HttpMessageEnum.RETRIEVED_SUCCESS.getMessage());
       return new HttpResponseEntity<>(status, productStoreResponse);
    }
}
