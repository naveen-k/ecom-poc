package com.test.catalog.sales.client.response.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.test.catalog.sales.client.util.ValidDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreFrontProductPrice {
    /**
     * The product price.
     */
    @NotNull
    private BigDecimal price;

    /**
     * The valid from.
     */
    @ValidDate(format = "yyyy-MM-dd")
    private String beginEffectiveDate;

    /**
     * The valid till.
     */
    @ValidDate(format = "yyyy-MM-dd")
    private String endEffectiveDate;

    /**
     * The currency code. "USD"
     */
    private String currencyCode;

}
