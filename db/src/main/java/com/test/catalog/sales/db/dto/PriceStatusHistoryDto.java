package com.test.catalog.sales.db.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
public class PriceStatusHistoryDto {

    private String storeId;
    private String channelId;
    private String offeringId;
    private UUID productId;
    private String itemSku;
    private BigDecimal price;
    private Timestamp effectiveStartDatetime;
    private Timestamp effectiveEndDatetime;
    private Integer recordStatusId;
    private Timestamp createDatetime;
    private boolean success;
}
