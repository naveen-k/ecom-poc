package com.test.catalog.sales.domain.service.store;

import com.test.catalog.sales.client.request.product.StoreFrontProductStatusRequest;
import com.test.catalog.sales.client.request.store.StoreLocationRequest;
import com.test.catalog.sales.client.response.store.StoreLocationResponse;

public interface StoreLocationService {

    StoreLocationResponse createStoreLocation(StoreLocationRequest storeLocationRequest);

    StoreFrontProductStatusRequest updateStoreFrontStatus(StoreFrontProductStatusRequest request);
}
