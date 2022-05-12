package com.test.catalog.sales.db.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreOfferingChannelComponentDto extends BaseDto{

    private Integer storeOfferingChannelCompKey;

    private String storeId;

    private String offeringId;

    private String channelId;

    private UUID componentId;

    private String itemId;

    private Timestamp releaseDate;
}
