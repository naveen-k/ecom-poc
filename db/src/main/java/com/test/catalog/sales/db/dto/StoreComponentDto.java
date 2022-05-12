package com.test.catalog.sales.db.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class StoreComponentDto extends BaseDto {

    String releaseDate;

    private UUID componentId;

    private String storeId;

    private String offeringId;

    private String channelId;

    private boolean isSuccess;

}
