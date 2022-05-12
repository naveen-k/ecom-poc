package com.test.catalog.sales.domain.service.store;

import com.test.catalog.sales.client.request.product.LocationSearchCriteria;
import com.test.catalog.sales.client.response.product.ProductStoreResponse;
import com.test.catalog.sales.client.response.product.StoreFrontProductResponse;

import java.util.Date;
import java.util.List;

public interface ProductStoreService {

    public ProductStoreResponse getStores(LocationSearchCriteria locationSearchCriteria);

    public StoreFrontProductResponse getStorefrontProducts(Date releasteDate, List<Integer> subRecordStatusId,Integer recordsSize);

    StoreFrontProductResponse getStorePublishedProducts(String storeId, String sapArticleId, String uom);

}
