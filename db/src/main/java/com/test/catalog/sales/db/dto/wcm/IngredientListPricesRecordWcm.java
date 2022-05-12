package com.test.catalog.sales.db.dto.wcm;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IngredientListPricesRecordWcm {
   String listPrice;
   String beginEffectiveDate;
   String endEffectiveDate;
}










