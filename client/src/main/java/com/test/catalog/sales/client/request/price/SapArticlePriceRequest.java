package com.test.catalog.sales.client.request.price;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.test.catalog.sales.client.util.ValidDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SapArticlePriceRequest {

    /**
     * storeId
     */
    @NotBlank
    String storeId;

    /**
     * channelId
     */
    @Value("Web")
    String channelId;


    /**
     * uom
     */
    @NotBlank
    String uom;

    /**
     * itemSku
     */
    @NotBlank
    String itemSku;

    /**
     * price
     */
    @NotNull
    BigDecimal price;

    /**
     *
     */
    @NotBlank
    String articleId;
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
}
