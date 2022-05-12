package com.test.catalog.sales.client.response.price;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@JsonInclude()
public class PriceHistoryStatusResponse {

    String storeId;

    String channelId;

    String offeringId;

    String itemSku;

    UUID productId;

    private String beginEffectiveDate;

    private String endEffectiveDate;

    BigDecimal price;

    private String recordStatus;

    boolean isSuccess;







}
