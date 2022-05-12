package com.test.catalog.sales.client.response.ingredient;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class StoreFrontIngredientResponse {

    private Set<StoreFrontIngredient> storeFrontIngredientList;
}
