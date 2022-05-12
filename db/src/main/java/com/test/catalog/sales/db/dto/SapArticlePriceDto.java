package com.test.catalog.sales.db.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class SapArticlePriceDto {

    private Integer salesCatalogPriceStatusKey;

    private String storeId;

    private String channelId;

    private String unitOfMeasureId;

    private String itemSku;

    private BigDecimal price;

    private String sapArticleId;

    private Timestamp effectiveStartDatetime;

    private Timestamp effectiveEndDatetime;

    private Timestamp createDatetime;
}
