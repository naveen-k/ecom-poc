package com.test.catalog.sales.db.entity.price;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "price_status_history")
@NoArgsConstructor
public class PriceStatusHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "price_status_hist_key", columnDefinition = "serial")
    private Integer priceStatusHistory;

    @Column(name = "store_id")
    private String storeId;

    @Column(name = "channel_id")
    private String channelId;

    @Column(name = "offering_id")
    private String offeringId;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "item_sku")
    private String itemSku;

    @CreatedDate
    @Column(name = "effective_start_datetime")
    private Timestamp effectiveStartDatetime;

    @CreatedDate
    @Column(name = "effective_end_datetime")
    private Timestamp effectiveEndDatetime;

    @Column(name = "record_status_id")
    private Integer recordStatusId;

    @CreatedDate
    @Column(name = "create_datetime")
    private Timestamp createDatetime;
}
