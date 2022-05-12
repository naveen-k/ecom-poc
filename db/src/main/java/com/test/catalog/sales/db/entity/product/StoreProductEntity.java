package com.test.catalog.sales.db.entity.product;

import com.test.catalog.sales.db.entity.BaseEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "store_product")
@NoArgsConstructor
public class StoreProductEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "store_product_key", columnDefinition = "serial")
  private Integer storeProductKey;

  @Column(name = "store_id", nullable = false)
  private String storeId;

  @Column(name = "product_id", nullable = false)
  private UUID productId;

  @Column(name = "price")
  private Double price;

  @Column(name = "release_datetime")
  private Timestamp releaseDate;
}
