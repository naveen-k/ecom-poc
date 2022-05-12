package com.test.catalog.sales.db.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StoreOfferingChannelDto extends BaseDto {

    private Integer storeOfferingChannelKey;
    private String storeId;
    private String offeringId;
    private String channelId;
}
