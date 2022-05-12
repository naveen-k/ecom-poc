package com.test.catalog.sales.db.repository.store;

import com.test.catalog.sales.db.entity.price.PriceStatusHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceStatusHistoryRepository extends JpaRepository<PriceStatusHistoryEntity, Integer> {
    @Query(value = "SELECT ROW_NUMBER () OVER (ORDER BY psh.*) as price_status_history_key, psh.* FROM {h-schema}price_status_history psh " +
            "INNER JOIN (SELECT store_offering_channel_prd_key,sales_catalog_price_status_key,record_status_id, MAX(create_datetime) AS maxdate from {h-schema}price_status_history " +
            "group by  store_offering_channel_prd_key,sales_catalog_price_status_key,record_status_id) " +
            "resultset on psh.store_offering_channel_prd_key  = resultset.store_offering_channel_prd_key " +
            "and psh.sales_catalog_price_status_key  = resultset.sales_catalog_price_status_key " +
            "and psh.record_status_id  = resultset.record_status_id " +
            "and psh.store_offering_channel_prd_key = :store_offering_channel_prd_key " +
            "and psh.sales_catalog_price_status_key = :sales_catalog_price_status_key " +
            "and psh.record_status_id =:recordStatusId " +
            "and psh.create_datetime = resultset.maxdate;", nativeQuery = true)
    PriceStatusHistoryEntity findLatest(@Param("store_offering_channel_prd_key") Integer store_offering_channel_prd_key,
                                        @Param("sales_catalog_price_status_key") Integer sales_catalog_price_status_key,
                                        @Param("recordStatusId") Integer recordStatusId);


   //TODO:Dennis Mathew: TODO: We will soon move to UTC hence the current time is in UTC.
    @Query(value = "select psh.* from  {h-schema}price_status_history psh       " +
            "                       INNER JOIN        " +
            "                                    ( SELECT offering_id,channel_id,store_id ,product_id,effective_start_datetime,        " +
            "                                               MAX(create_datetime) AS maxdate from  {h-schema}price_status_history group by        " +
            "                                               offering_id,channel_id,store_id ,product_id,effective_start_datetime) resultset1        " +
            "                                               on psh.offering_id  = resultset1.offering_id  and psh.channel_id  = resultset1.channel_id   AND psh.effective_start_datetime=resultset1.effective_start_datetime     " +
            "                                               and psh.store_id  = resultset1.store_id and psh.product_id  = resultset1.product_id and psh.record_status_id  in(:recordStatusId )        " +
            "                                               and psh.product_id in       " +
            "                               (select prod.product_id from  {h-schema}product prod INNER JOIN (SELECT product_id,        " + //select published prices
            "                               MAX(create_datetime) AS maxdate from  {h-schema}product group by        " +
            "                               product_id)  resultset2     " +
            "                               on  prod.product_id  = resultset2.product_id  and prod.record_status_id In (:recordStatusId )        " +
            "                               and   prod.create_datetime = resultset2.maxdate    " +
            "                                and (prod.release_datetime IS NULL OR prod.release_datetime>=(select timezone('UTC', now()))))       " +
            "                              and  psh.create_datetime = resultset1.maxdate       " +
            "                        INNER JOIN  {h-schema}store_offering_channel as soc       " +
            "                                        ON psh.store_id=soc.store_id       " +
            "                             WHERE psh.store_id=:storeId       " +
            "                                         AND psh.channel_id=:channelId        " +
            "                                         AND psh.offering_id=:offeringId        " +
            "                                         AND psh.record_status_id=:recordStatusId   "
            , nativeQuery = true)
    List<PriceStatusHistoryEntity> findLatest(
            @Param("storeId") String storeId,
            @Param("channelId") String channelId, @Param("offeringId") String offeringId, @Param("recordStatusId") int recordStatusId);


}
