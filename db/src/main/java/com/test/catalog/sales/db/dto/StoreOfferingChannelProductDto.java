package com.test.catalog.sales.db.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
public class StoreOfferingChannelProductDto extends  BaseDto{

    private Integer storeOfferChannelPrdKey;
    private String storeId;
    private String offeringId;
    private String channelId;
    private UUID productId;
    private String itemSku;
    private String skuType;
    private Timestamp releaseDate;
}
