package com.test.catalog.sales.db.repository.store;

import com.test.catalog.sales.db.entity.store.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Integer> {

    StoreEntity  findFirstByStoreIdOrderByCreateDatetimeDesc(String storeId);




}
