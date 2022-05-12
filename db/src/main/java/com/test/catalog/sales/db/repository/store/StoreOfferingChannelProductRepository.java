package com.test.catalog.sales.db.repository.store;

import com.test.catalog.sales.db.dto.StoreOfferingChannelProductItem;
import com.test.catalog.sales.db.entity.store.StoreOfferingChannelProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface StoreOfferingChannelProductRepository extends JpaRepository<StoreOfferingChannelProductEntity,Integer> {
    @Query(
            value ="select socp.* from {h-schema}store_offering_channel_product socp INNER JOIN (SELECT offering_id,channel_id,store_id ,product_id," +
                    " MAX(create_datetime) AS maxdate from {h-schema}store_offering_channel_product group by " +
                    " offering_id,channel_id,store_id ,product_id)  resultset on socp.offering_id  = resultset.offering_id  and socp.channel_id  = resultset.channel_id" +
                    " and socp.store_id  = resultset.store_id and socp.product_id  = resultset.product_id and socp.product_id = :productId and socp.record_status_id in (:subRecordStatusId)" +
                    " and  socp.create_datetime = resultset.maxdate;"+
                    " join {h-schema}store_product sp " +
                    " on sp.product_id = socp.product_id " +
                    " and sp.store_id = socp.store_id "+
                    " and sp.release_datetime<=:releaseDate", nativeQuery = true)
    List<StoreOfferingChannelProductEntity> findAllByProductIdAndReleaseDateAndRecordStatusIdIn(@Param("productId") UUID productId, @Param("releaseDate") Date releaseDate, @Param("subRecordStatusId") List<Integer> subRecordStatusId);

    @Query(
            value ="select socp.* from {h-schema}store_offering_channel_product socp INNER JOIN (SELECT offering_id,channel_id,store_id ,product_id, " +
                    " MAX(create_datetime) AS maxdate from {h-schema}store_offering_channel_product group by " +
                    " offering_id,channel_id,store_id ,product_id)  resultset on socp.offering_id  = resultset.offering_id  and socp.channel_id  = resultset.channel_id " +
                    " and socp.store_id  = resultset.store_id " +
                    " and socp.product_id  = resultset.product_id " +
                    " and socp.record_status_id in (:subRecordStatusId) " +
                    " and socp.create_datetime = resultset.maxdate " +
                    " join {h-schema}store_product sp " +
                    " on sp.product_id = socp.product_id " +
                    " and sp.store_id = socp.store_id "+
                    " and sp.release_datetime <= :releaseDate" , nativeQuery = true)
    List<StoreOfferingChannelProductEntity> findAllByReleaseDateAndRecordStatusIdIn(@Param("releaseDate") Date releaseDate, @Param("subRecordStatusId") List<Integer> subRecordStatusId);

    @Query(value = "select socp.* from {h-schema}store_offering_channel_product socp INNER JOIN (SELECT offering_id,channel_id,store_id ,product_id," +
            "  MAX(create_datetime) AS maxdate from {h-schema}store_offering_channel_product group by" +
            "  offering_id,channel_id,store_id ,product_id)  resultset on socp.offering_id  = resultset.offering_id  and socp.channel_id  = resultset.channel_id" +
            "  and socp.store_id  = resultset.store_id and socp.product_id  = resultset.product_id and socp.record_status_id  =:recordStatusId" +
            "  and socp.product_id in (select prod.product_id from {h-schema}product prod INNER JOIN (SELECT product_id," +
            "  MAX(create_datetime) AS maxdate from {h-schema}product group by" +
            "  product_id)  resultset on  prod.product_id  = resultset.product_id and prod.sap_article_id =:sapArticleId and prod.unit_of_measure_id =:uom and prod.record_status_id =:recordStatusId" +
            "  and   prod.create_datetime = resultset.maxdate) and socp.store_id=:storeId and  socp.create_datetime = resultset.maxdate;", nativeQuery = true)
    List<StoreOfferingChannelProductEntity> findAllByStoreIdAndSapArticleIdAndUnitOfMeasureIdPublishState(@Param("storeId") String storeId,
                                                                                                          @Param("sapArticleId") String sapArticleId,
                                                                                                          @Param("uom") String uom,
                                                                                                          @Param("recordStatusId") Integer recordStatusId);

    @Query(value = "select socp.* from {h-schema}store_offering_channel_product socp INNER JOIN" +
            " (SELECT offering_id,channel_id,store_id ,product_id," +
            "              MAX(create_datetime) AS maxdate from {h-schema}store_offering_channel_product group by" +
            "              offering_id,channel_id,store_id ,product_id)  resultset" +
            "              on socp.offering_id  = resultset.offering_id  and socp.channel_id  = resultset.channel_id" +
            "              and socp.store_id  = resultset.store_id and socp.product_id  = resultset.product_id and socp.record_status_id  in(:recordStatusIds)" +
            "              and socp.product_id in (select prod.product_id from {h-schema}product prod INNER JOIN (SELECT product_id," +
            "              MAX(create_datetime) AS maxdate from {h-schema}product group by" +
            "              product_id)  resultset on  prod.product_id  = resultset.product_id and prod.sap_article_id =:sapArticleId and prod.item_sku =:itemSku and prod.unit_of_measure_id =:unitOfMeasureId and prod.record_status_id In (:recordStatusIds)" +
            "              and   prod.create_datetime = resultset.maxdate) and socp.store_id=:storeId and  socp.create_datetime = resultset.maxdate and socp.channel_id=:channelId", nativeQuery = true)
    List<StoreOfferingChannelProductEntity> findAllByStoreIdAndChannelIdAndSapArticleIdAndPublishAndActiveState(@Param("storeId") String storeId,
                                                                                           @Param("channelId") String channelId,
                                                                                           @Param("sapArticleId") String sapArticleId,
                                                                                           @Param("unitOfMeasureId") String unitOfMeasureId,
                                                                                           @Param("itemSku") String itemSku,
                                                                                           @Param("recordStatusIds") List<Integer> recordStatusIds);

    @Query(value ="SELECT cat.store_product_key, cat.store_id,CAST(cat.product_id AS VARCHAR),soc.channel_id,soc.offering_id ," +
                         "cat.record_status_id,cat.release_datetime,  p.item_sku, p.sku_type FROM {h-schema}store_product cat " +
                         " INNER JOIN (Select store_id, offering_id, channel_id, MAX(create_datetime)  " +
                         "from {h-schema}store_offering_channel  GROUP BY store_id,offering_id,channel_id) soc ON soc.store_id=cat.store_id " +
                         "INNER JOIN (select product_id, offering_id, MAX(create_datetime) from {h-schema}product_offering " +
                         "group by  product_id,offering_id) po ON soc.offering_id=po.offering_id and cat.product_id=po.product_id " +
                         "INNER JOIN (SELECT  Product_id ,item_sku, MAX(create_datetime),sku_type from {h-schema}product p where  p.record_status_id in (:recordStatusId) " +
                         "GROUP BY product_id,item_sku,sku_type ) p ON cat.product_id=p.product_id and cat.store_product_key in (:storeProductKeys) " ,
        nativeQuery = true)
    List<StoreOfferingChannelProductItem> findAllEligibleStorefrontProducts(
            @Param("storeProductKeys") List<Integer> storeProductKeys,
            @Param("recordStatusId") Integer recordStatusId);


    @Query(value ="SELECT cat.store_product_key FROM {h-schema}store_product cat " +
            " INNER JOIN (SELECT store_id,product_id, MAX(create_datetime) AS MaxDateTime FROM " +
            " {h-schema}store_product GROUP BY store_id,product_id) resultset on cat.product_id  = resultset.product_id and cat.store_id = resultset.store_id " +
            " and cat.record_status_id in (:subRecordStatusId) and cat.release_datetime  <=:releaseDate and cat.create_datetime = resultset.MaxDateTime LIMIT(:recordsSize)",
            nativeQuery = true)
    List<Integer> findAllEligibleStorefrontProductKeys(
            @Param("releaseDate") Date releaseDate,
            @Param("subRecordStatusId") List<Integer> subRecordStatusId,
            @Param("recordsSize") Integer recordsSize);


}
