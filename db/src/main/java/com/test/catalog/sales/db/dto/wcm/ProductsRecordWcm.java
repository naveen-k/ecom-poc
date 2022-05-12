package com.test.catalog.sales.db.dto.wcm;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductsRecordWcm {
   String productId;
   List<ProductListPricesRecordWcm> listPrices;
   List<ProductCategoriesRecordWcm> productCategories;
}










