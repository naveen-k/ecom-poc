package com.test.catalog.sales.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class StoreDaoHelper {
    @Transactional
    public boolean populateStorefrontProductPrice(EntityManager entityManager, Date releasedDate, List<Integer> subRecordStatusId) {
        String query = "insert into {h-schema}store_offering_channel_product(store_id,product_id,channel_id,offering_id,record_status_id,release_datetime) " +
                "select sp.store_id,sp.product_id,soc.channel_id, soc.offering_id ,sp.record_status_id,sp.release_datetime  from {h-schema}store_product sp " +
                "INNER JOIN {h-schema}store_offering_channel soc " +
                "ON soc.store_id=sp.store_id  " +
                "INNER JOIN {h-schema}product_offering po " +
                "ON soc.offering_id=po.offering_id and sp.product_id=po.product_id " +
                "INNER JOIN {h-schema}product p " +
                "ON sp.product_id=p.product_id and p.record_status_id in (:record_status_id) " +
                "and sp.record_status_id in (:record_status_id) and sp.release_datetime<=:releasedDate";
        int count = entityManager.createNativeQuery(query)
                .setParameter("record_status_id", subRecordStatusId)
                .setParameter("releasedDate", releasedDate)
                .executeUpdate();
        entityManager.close();
        return count > 0;
    }
}
