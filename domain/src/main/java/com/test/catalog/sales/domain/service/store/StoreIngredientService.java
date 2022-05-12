package com.test.catalog.sales.domain.service.store;

import com.test.catalog.sales.client.request.component.StoreFrontComponentStatus;
import com.test.catalog.sales.client.response.ingredient.StoreFrontIngredientResponse;
import com.test.catalog.sales.client.response.component.StoreFrontComponentStatusResponse;

import java.util.Date;
import java.util.List;

public interface StoreIngredientService {

    StoreFrontIngredientResponse getStorefrontIngredients(String nextState, Date releasteDate, Integer recordsSize);

    StoreFrontComponentStatusResponse updateStoreFrontComponent(List<StoreFrontComponentStatus> storeFrontComponentStatusList);

}
