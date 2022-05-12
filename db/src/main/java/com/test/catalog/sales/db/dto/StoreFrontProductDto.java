package com.test.catalog.sales.db.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreFrontProductDto {

    private Number price;

    private String beginEffectiveDate;

    private String endEffectiveDate;

    private String currencyCode;

}
