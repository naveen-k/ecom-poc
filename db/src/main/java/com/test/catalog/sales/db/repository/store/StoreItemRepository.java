package com.test.catalog.sales.db.repository.store;

import com.test.catalog.sales.db.entity.store.StoreItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreItemRepository  extends JpaRepository<StoreItemEntity, Integer> {
}
