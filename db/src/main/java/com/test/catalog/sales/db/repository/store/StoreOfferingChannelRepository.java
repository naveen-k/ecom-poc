package com.test.catalog.sales.db.repository.store;

import com.test.catalog.sales.db.entity.store.StoreOfferingChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreOfferingChannelRepository extends JpaRepository<StoreOfferingChannelEntity,Integer> {

    @Query(
            value ="SELECT rel.* FROM {h-schema}store_offering_channel rel INNER JOIN (SELECT offering_id,channel_id , MAX(store_offering_channel_key) AS MaxItemKey "
                    + " FROM {h-schema}store_offering_channel where store_id = :storeId GROUP BY offering_id,channel_id ) resultset on rel.offering_id  = resultset.offering_id "
                    + "and rel.record_status_id in (:subRecordStatusId) and rel.store_offering_channel_key = resultset.MaxItemKey" , nativeQuery = true)
    List<StoreOfferingChannelEntity> findByStoreIdAndRecordStatusId(@Param("storeId")String storeId,@Param("subRecordStatusId") List<Integer> subRecordStatusId);

    StoreOfferingChannelEntity findFirstByStoreIdAndOfferingIdAndChannelIdOrderByCreateDatetimeDesc(String storeId,String offeringId,String channelId);
}
