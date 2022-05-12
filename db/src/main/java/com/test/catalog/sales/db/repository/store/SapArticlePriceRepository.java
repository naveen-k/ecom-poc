package com.test.catalog.sales.db.repository.store;

import com.test.catalog.sales.db.entity.price.SapArticlePriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface SapArticlePriceRepository extends JpaRepository<SapArticlePriceEntity, Integer> {
    @Query(
            value = "select distinct sap.* from {h-schema}sap_article_price sap " +
                    "inner join {h-schema}product pro on (pro.sap_article_id = sap.sap_article_id and pro.item_sku = sap.item_sku)  " +
                    "left join (select * from {h-schema}price_status_history where  record_status_id in (:recordStatusId) " +
                    "and product_id=:productId " +
                    "and store_id =:storeId " +
                    "and channel_id = :channelId " +
                    "and offering_id = :offeringId) psh " +
                    "on  psh.product_id =pro.product_id " +
                    "and psh.store_id=sap.store_id " +
                    "and psh.channel_id=sap.channel_id " +
                    "and psh.effective_start_datetime =sap.effective_start_datetime " +
                    "and psh.effective_end_datetime =sap.effective_end_datetime " +
                    "where  pro.product_id =:productId and  sap.store_id=:storeId and sap.item_sku=:itemSku " +
                    "and  sap.channel_id=:channelId and psh.record_status_id IS NULL",
            nativeQuery = true)
    List<SapArticlePriceEntity> findByProductIdnAndStoreIdAndChannelId(@Param("productId") UUID productId,
                                                                       @Param("storeId") String storeId,
                                                                       @Param("channelId") String channelId,
                                                                       @Param("offeringId") String offeringId,
                                                                       @Param("itemSku") String itemSku,
                                                                       @Param("recordStatusId") List<Integer> recordStatusId);

    @Query(value = "select distinct sap.* from {h-schema}sap_article_price sap " +
                    "inner join {h-schema}product pro on (pro.sap_article_id = sap.sap_article_id)  " +
                    "where pro.product_id =:productId " +
                    "and  sap.store_id=:storeId " +
                    "and  sap.item_sku=:item_sku " +
                    "and  sap.effective_start_datetime=:startDate " +
                    "and  sap.effective_end_datetime=:endDate " +
                    "and  sap.channel_id=:channelId",
            nativeQuery = true)
    SapArticlePriceEntity findByProductIdnAndStoreIdAndChannelIdAndDates(@Param("productId") UUID productId,
                                                                         @Param("storeId") String storeId,
                                                                         @Param("channelId") String channelId,
                                                                         @Param("item_sku") String item_sku,
                                                                         @Param("startDate") Date startDate,
                                                                         @Param("endDate") Date endDate);
}
