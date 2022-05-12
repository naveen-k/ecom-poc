package com.test.catalog.sales.db.entity.store;

import com.test.catalog.sales.db.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "store_component")
@NoArgsConstructor
public class StoreComponentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_component_key")
    private Integer storeComponentKey;

    @Column(name = "store_id", nullable = false)
    private String storeId;

    @Column(name = "component_id", nullable = false)
    private UUID componentId;

    @Column(name = "release_datetime")
    private Timestamp releaseDate;
}
