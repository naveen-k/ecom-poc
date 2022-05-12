package com.test.catalog.sales.db.entity.store;

import com.test.catalog.sales.db.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "store_offering_channel_component")
@NoArgsConstructor
public class StoreOfferingChannelComponentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_offering_channel_comp_key")
    private Integer storeOfferingChannelCompKey;

    @Column(name = "store_id", nullable = false)
    private String storeId;

    @Column(name = "offering_id", nullable = false)
    private String offeringId;

    @Column(name = "channel_id", nullable = false)
    private String channelId;

    @Column(name = "component_id", nullable = false)
    private UUID componentId;
}
