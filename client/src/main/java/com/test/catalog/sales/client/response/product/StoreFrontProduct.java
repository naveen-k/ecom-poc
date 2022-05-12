package com.test.catalog.sales.client.response.product;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class StoreFrontProduct {
    /**
     * The product id.
     */
    private UUID productId;

    /**
     * The storeId  .
     */
    private String storeId;

    /**
     * The offeringId  .
     */
    private String offeringId;

    /**
     * The itemSku  .
     */
    private String itemSku;

    /**
     * The channelId  .
     */
    private String channelId;

    /**
     * The skuType  .
     */
    private String skuType;

    /**
     * The channelId  .
     */
    private Timestamp releaseDate;

    /**
     * The product price.
     */
    private List<StoreFrontProductPrice> priceList;

}
