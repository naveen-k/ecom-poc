package com.test.catalog.sales.db.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
public class StoreProductDto extends BaseDto {

    private Integer storeProductKey;

    private String storeId;

    private UUID productId;

    private Double price;

    private Timestamp releaseDate;
}
