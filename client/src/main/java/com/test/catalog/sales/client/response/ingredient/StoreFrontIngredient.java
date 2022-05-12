package com.test.catalog.sales.client.response.ingredient;

import com.test.catalog.sales.client.response.product.StoreFrontProductPrice;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class StoreFrontIngredient {

    private UUID ingredientId;

    /**
     * The storeId  .
     */
    private String storeId;

    /**
     * The offeringId  .
     */
    private String offeringId;

    /**
     * The channelId  .
     */
    private String channelId;

    /**
     * The releaseDate  .
     */
    private Timestamp releaseDate;

//    /**
//     * The itemId  .
//     */
//    @EqualsAndHashCode.Exclude private String itemId;

    /**
     * The product price.
     */
    @EqualsAndHashCode.Exclude  private List<StoreFrontProductPrice> priceList;
}
