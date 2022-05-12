package com.test.catalog.sales.db.entity.price;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "sap_article_price")
@NoArgsConstructor
public class SapArticlePriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_catalog_price_status_key", columnDefinition = "serial")
    private Integer salesCatalogPriceStatusKey;
    @Column(name = "store_id")
    private String storeId;
    @Column(name = "channel_id")
    private String channelId;
    @Column(name = "unit_of_measure_id")
    private String unitOfMeasureId;
    @Column(name = "price")
    private Double price;
    @Column(name = "sap_article_id")
    private String sapArticleId;
    @Column(name = "item_sku")
    private String itemSku;

    @Column(name = "effective_start_datetime")
    @CreatedDate
    private Timestamp effectiveStartDatetime;

    @Column(name = "effective_end_datetime")
    @CreatedDate
    private Timestamp effectiveEndDatetime;

    @Column(name = "create_datetime")
    @CreatedDate
    private Timestamp createDatetime;
}
