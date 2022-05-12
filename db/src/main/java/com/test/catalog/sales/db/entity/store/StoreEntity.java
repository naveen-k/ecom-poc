package com.test.catalog.sales.db.entity.store;

import com.test.catalog.sales.db.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "store")
@NoArgsConstructor
public class StoreEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_key")
    private String storeKey;

    @Column(name = "store_id", nullable = false)
    private String storeId;

    @Column(name = "store_name", nullable = false)
    private String storeName;
}
