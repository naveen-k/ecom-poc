package com.test.catalog.sales.db.dto;

import lombok.Data;

@Data
public class StoreDto extends BaseDto {

    private String storeKey;

    private String storeId;

    private String storeName;
}
