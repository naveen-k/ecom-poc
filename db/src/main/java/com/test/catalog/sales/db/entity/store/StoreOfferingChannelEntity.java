package com.test.catalog.sales.db.entity.store;

import com.test.catalog.sales.db.entity.BaseEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "store_offering_channel")
@NoArgsConstructor
public class StoreOfferingChannelEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "store_offering_channel_key")
  private Integer storeOfferingChannelKey;

  @Column(name = "store_id", nullable = false)
  private String storeId;

  @Column(name = "offering_id", nullable = false)
  private String offeringId;

  @Column(name = "channel_id", nullable = false)
  private String channelId;
}
