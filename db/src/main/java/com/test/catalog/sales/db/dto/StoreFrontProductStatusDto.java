package com.test.catalog.sales.db.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class StoreFrontProductStatusDto {
    /**
     * The releaseDate .
     */
    String releaseDate;
    /**
     * The product id.
     */
    private UUID productId;

    /**
     * The itemSku .
     */
    private String itemSku;

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
     * The recordStatus.
     */
    private Integer recordStatus;

    /**
     * The prices.
     */
    List<StoreFrontProductDto> prices;
    /**
     * The status.
     */
    private boolean isSuccess;
}
