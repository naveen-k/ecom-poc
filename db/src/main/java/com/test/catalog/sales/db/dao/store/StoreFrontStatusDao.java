package com.test.catalog.sales.db.dao.store;

import com.test.catalog.sales.db.dto.StoreFrontProductStatusDto;

public interface StoreFrontStatusDao {
    StoreFrontProductStatusDto updateStoreFrontStatus(StoreFrontProductStatusDto request);
}
