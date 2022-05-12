package com.test.catalog.sales.db.repository.store;

import com.test.catalog.sales.db.dto.StoreComponentItem;
import com.test.catalog.sales.db.entity.store.StoreComponentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface StoreComponentRepository extends JpaRepository<StoreComponentEntity, Integer> {

    @Query(value = "SELECT DISTINCT sc.store_id, CAST(sc.component_id AS VARCHAR), resultset3.channel_id, resultset3.offering_id, sc.release_datetime FROM   {h-schema}store_component sc " +
            "  INNER JOIN (SELECT ing.ingredient_id " +
            "  FROM   {h-schema}ingredient ing  INNER JOIN(SELECT ingredient_id, Max(create_datetime) AS maxdate FROM   {h-schema}ingredient " +
            "  WHERE  record_status_id =:publishedStatusId GROUP  BY ingredient_id) resultset  ON ing.ingredient_id = resultset.ingredient_id AND ing.record_status_id = :publishedStatusId " +
            "  AND ing.create_datetime = resultset.maxdate) resultset2 ON sc.component_id = resultset2.ingredient_id INNER JOIN (SELECT sfc.*  FROM   {h-schema}store_offering_channel sfc " +
            "    INNER JOIN (SELECT store_id,  Max(create_datetime) AS maxdate FROM {h-schema}store_offering_channel WHERE  record_status_id =:recordActStatusId " +
            "   GROUP  BY store_id) resultset ON sfc.store_id = resultset.store_id AND sfc.create_datetime = resultset.maxdate ) resultset3 ON sc.store_id = resultset3.store_id " +
            "   INNER JOIN(SELECT it.* FROM   {h-schema}item it INNER JOIN(SELECT item_id, ingredient_id, Max(create_datetime) AS maxdate FROM   {h-schema}item " +
            "   WHERE  record_status_id =:recordActStatusId GROUP  BY item_id, ingredient_id) resultset ON it.ingredient_id = resultset.ingredient_id AND it.record_status_id =:recordActStatusId " +
            "    AND it.create_datetime = resultset.maxdate) resultset4  ON resultset2.ingredient_id = resultset4.ingredient_id INNER JOIN (SELECT it.* FROM   {h-schema}store_item it " +
            "   INNER JOIN(SELECT item_id, store_id,Max(create_datetime) AS maxdate FROM   {h-schema}store_item WHERE  record_status_id = :recordActStatusId GROUP  BY item_id, " +
            "   store_id) resultset ON it.item_id = resultset.item_id AND it.store_id = resultset.store_id AND it.create_datetime = resultset.maxdate)  resultset5  ON resultset4.item_id = resultset5.item_id " +
            "   AND sc.store_id = resultset5.store_id AND sc.store_component_key in (:storeComponentKeys) ", nativeQuery = true)
    public List<StoreComponentItem> getStoreComponentItemList(@Param("storeComponentKeys") List<Integer> storeComponentKeys,
                                                              @Param("recordActStatusId") Integer recordActStatusId,
                                                              @Param("publishedStatusId") Integer publishedStatusId
    );

    @Query(value = "SELECT store_component_key FROM {h-schema}store_component sc  INNER JOIN " +
            " (SELECT component_id,  store_id,  Max(create_datetime) AS maxdate FROM   {h-schema}store_component  GROUP  BY component_id," +
            " store_id) resultset ON sc.component_id = resultset.component_id AND sc.store_id = resultset.store_id AND " +
            "  sc.release_datetime <= :releaseDate  AND sc.record_status_id =:recordStatusId " +
            " and sc.component_id in (SELECT ing.ingredient_id   FROM   mastercatalogschema.ingredient ing " +
            " INNER JOIN(SELECT ingredient_id, Max(create_datetime) AS maxdate FROM   mastercatalogschema.ingredient " +
            " WHERE  record_status_id = :publishedRecordStatusId GROUP  BY ingredient_id) resultset_ing  ON ing.ingredient_id = resultset_ing.ingredient_id AND ing.record_status_id = :publishedRecordStatusId " +
            " AND ing.create_datetime = resultset_ing.maxdate) " +
            "   AND sc.create_datetime = resultset.maxdate Limit(:recordSize)", nativeQuery = true)
    List<Integer> getStoreComponentItemKeyList(@Param("releaseDate") Date releaseDate,
                                               @Param("recordStatusId") Integer recordStatusId,
                                               @Param("publishedRecordStatusId") Integer publishedRecordStatusId,
                                               @Param("recordSize") Integer recordSize
    );

    @Query(value = "SELECT sc.* FROM {h-schema}store_component sc INNER JOIN (SELECT store_id ,component_id, " +
            " MAX(create_datetime) AS maxdate,release_datetime from {h-schema}store_component group by  store_id," +
            " component_id,release_datetime) resultset on sc.store_id  = resultset.store_id" +
            " and sc.component_id  = resultset.component_id  and sc.release_datetime  = resultset.release_datetime and " +
            " sc.store_id =:storeId and sc.component_id =:componentId and  sc.record_status_id =:recordStatusId and " +
            " sc.release_datetime =:releaseDate " +
            " and sc.create_datetime = resultset.maxdate;", nativeQuery = true)
    StoreComponentEntity findLatest(@Param("releaseDate") Date releaseDate,
                                    @Param("storeId") String storeId,
                                    @Param("componentId") UUID componentId,
                                    @Param("recordStatusId") Integer recordStatusId);


    /* According to Naveen Kumar the relevant tables to get the components of a storefront are:
   A store_component,item, ingredient, store_offering_channel  and store_item
    The store_offering_channel_component table is to be ignored.
    Notice that there is a different criteria for the record status id (ie recordStatusIdActive)  in some tables item, store_item, store_offering_channel
*/
    @Query(value = "SELECT DISTINCT sc.store_id, CAST(sc.component_id AS VARCHAR) , resultset3.channel_id, resultset3.offering_id, sc.release_datetime FROM   {h-schema}store_component sc " +
            "       INNER JOIN (SELECT component_id,  store_id,  Max(create_datetime) AS maxdate FROM   {h-schema}store_component  GROUP  BY component_id, " +
            "     store_id) resultset ON sc.component_id = resultset.component_id AND sc.store_id = resultset.store_id INNER JOIN (SELECT ing.ingredient_id " +
            "  FROM   {h-schema}ingredient ing  INNER JOIN(SELECT ingredient_id, Max(create_datetime) AS maxdate FROM   {h-schema}ingredient " +
            "  WHERE  record_status_id =:recordStatusIdPublished GROUP  BY ingredient_id) resultset  ON ing.ingredient_id = resultset.ingredient_id AND ing.record_status_id = :recordStatusIdPublished " +
            "  AND ing.create_datetime = resultset.maxdate) resultset2 ON sc.component_id = resultset2.ingredient_id INNER JOIN (SELECT sfc.*  FROM   {h-schema}store_offering_channel sfc " +
            "    INNER JOIN (SELECT store_id,  Max(create_datetime) AS maxdate FROM {h-schema}store_offering_channel WHERE  record_status_id =:recordStatusIdActive " +
            "   GROUP  BY store_id) resultset ON sfc.store_id = resultset.store_id AND sfc.create_datetime = resultset.maxdate ) resultset3 ON sc.store_id = resultset3.store_id " +
            "   INNER JOIN(SELECT it.* FROM   {h-schema}item it INNER JOIN(SELECT item_id, ingredient_id, Max(create_datetime) AS maxdate FROM   {h-schema}item " +
            "   WHERE  record_status_id =:recordStatusIdActive GROUP  BY item_id, ingredient_id) resultset ON it.ingredient_id = resultset.ingredient_id AND it.record_status_id =:recordStatusIdActive " +
            "    AND it.create_datetime = resultset.maxdate) resultset4  ON resultset2.ingredient_id = resultset4.ingredient_id INNER JOIN (SELECT it.* FROM   {h-schema}store_item it " +
            "   INNER JOIN(SELECT item_id, store_id,Max(create_datetime) AS maxdate FROM   {h-schema}store_item WHERE  record_status_id = :recordStatusIdActive GROUP  BY item_id, " +
            "   store_id) resultset ON it.item_id = resultset.item_id AND it.store_id = resultset.store_id AND it.create_datetime = resultset.maxdate)  resultset5  ON resultset4.item_id = resultset5.item_id " +
            "   AND sc.store_id = resultset5.store_id  AND sc.record_status_id =:recordStatusIdPublished  AND sc.create_datetime = resultset.maxdate " +
            "   AND sc.store_id=:storeId  AND resultset3.channel_id=:channelId AND resultset3.offering_id=:offeringId"
            , nativeQuery = true)
    List<StoreComponentItem> findComponentsOfStoreFront(
            @Param("storeId") String storeId,
            @Param("channelId") String channelId,
            @Param("offeringId") String offeringId,
            @Param("recordStatusIdPublished") int recordStatusIdPublished,
            @Param("recordStatusIdActive") int recordStatusIdActive);

}