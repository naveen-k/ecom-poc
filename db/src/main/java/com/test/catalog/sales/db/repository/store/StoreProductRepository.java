package com.test.catalog.sales.db.repository.store;

import com.test.catalog.sales.db.entity.product.StoreProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface StoreProductRepository extends JpaRepository<StoreProductEntity, Integer> {

  @Query(
      value =
          "SELECT cat.* FROM {h-schema}store_product cat INNER JOIN (SELECT store_id,product_id, MAX(create_datetime) AS MaxDateTime "
              + "FROM {h-schema}store_product GROUP BY store_id,product_id) resultset on cat.product_id  = resultset.product_id "
              + "and cat.product_id = :productId "
              + "and cat.record_status_id in (:subRecordStatusId) and cat.release_date  =:releaseDate and cat.create_datetime = resultset.MaxDateTime",
      nativeQuery = true)
  List<StoreProductEntity> findByReleaseDateAndProductIdInAndRecordStatusIdIn(
          @Param("releaseDate") Timestamp releaseDate,
          @Param("productId") String productId,
      @Param("subRecordStatusId") List<Integer> subRecordStatusId);

  @Query(value = "SELECT sp.* FROM {h-schema}store_product sp " +
          "INNER JOIN (SELECT store_id ,product_id,record_status_id, MAX(create_datetime) AS maxdate,release_datetime from {h-schema}store_product " +
          "group by  store_id, record_status_id, product_id,release_datetime) " +
          "resultset on sp.store_id  = resultset.store_id " +
          "and sp.product_id  = resultset.product_id " +
          "and sp.record_status_id  = resultset.record_status_id " +
          "and sp.release_datetime  = resultset.release_datetime " +
          "and sp.store_id = :storeId " +
          "and sp.product_id = :productId " +
          "and sp.record_status_id = :recordStatusId " +
          "and sp.release_datetime = :releaseDate " +
          "and sp.create_datetime = resultset.maxdate;", nativeQuery = true)
  StoreProductEntity findLatest(@Param("releaseDate") Date releaseDate,
             @Param("storeId") String  storeId,
             @Param("productId") UUID productId,
             @Param("recordStatusId") Integer recordStatusId);


  @Query(value = "SELECT sp.* FROM {h-schema}store_product sp " +
          "INNER JOIN (SELECT store_id ,product_id, MAX(create_datetime) AS maxdate from {h-schema}store_product group by store_id ,product_id) " +
          "resultset on sp.store_id  = resultset.store_id " +
          "and sp.product_id  = resultset.product_id " +
          "and sp.store_id = :storeId   " +
          "and sp.product_id = :productId " +
          "INNER JOIN {h-schema}store_offering_channel soc " +
          "ON soc.store_id=sp.store_id " +
          "and soc.channel_id = :channelId  " +
          "INNER JOIN {h-schema}product_offering po " +
          "ON soc.offering_id=po.offering_id and sp.product_id=po.product_id " +
          "and po.offering_id = :offeringId "+
          "INNER JOIN {h-schema}product p  " +
          "ON sp.product_id=p.product_id and p.record_status_id in (4) " +
          "and sp.record_status_id = :recordStatusId " +
          "and sp.create_datetime = resultset.maxdate;",nativeQuery = true)
  StoreProductEntity findLatest(@Param("storeId") String storeId,
                                @Param("channelId") String channelId,
                                @Param("offeringId") String offeringId,
                                @Param("productId") UUID productId,
                                @Param("recordStatusId") Integer recordStatusId);


  /*  According to Naveen Kumar the relevant tables to get the products of a storefront are:
     A store_product,product_offering,store_offering_channel  and product
      The store_offering_channel_product table is to be ignored.
  */
  @Query(value = "SELECT sp.* FROM {h-schema}store_product sp " +
          "INNER JOIN (SELECT store_id ,product_id, MAX(create_datetime) AS maxdate from {h-schema}store_product group by store_id ,product_id) " +
          "resultset on sp.store_id  = resultset.store_id " +
          "and sp.product_id  = resultset.product_id " +
          "and sp.store_id = :storeId   " +
          "INNER JOIN {h-schema}store_offering_channel soc " +
          "ON soc.store_id=sp.store_id " +
          "and soc.channel_id = :channelId  " +
          "INNER JOIN {h-schema}product_offering po " +
          "ON soc.offering_id=po.offering_id and sp.product_id=po.product_id " +
          "and po.offering_id = :offeringId "+
          "INNER JOIN {h-schema}product p  " +
          "ON sp.product_id=p.product_id and p.record_status_id in (:recordStatusId) " +
          "and sp.record_status_id = :recordStatusId " +
          "and sp.create_datetime = resultset.maxdate;",nativeQuery = true)
  List<StoreProductEntity> findProductsOfStoreFront(
          @Param("storeId") String storeId,
          @Param("channelId") String channelId,
          @Param("offeringId") String offeringId,
          @Param("recordStatusId") int recordStatusId);


}
