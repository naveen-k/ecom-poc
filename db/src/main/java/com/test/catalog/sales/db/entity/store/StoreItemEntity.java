package com.test.catalog.sales.db.entity.store;

import com.test.catalog.sales.db.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "store_item")
@NoArgsConstructor
public class StoreItemEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_item_key")
    private Integer storeItemKey;

    @Column(name = "store_id", nullable = false)
    private String storeId;

    @Column(name = "item_id", nullable = false)
    private UUID itemId;

    @Column(name = "price", nullable = false)
    private Double  price;

}
