package com.test.catalog.sales.db.dto;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;
import java.util.UUID;

public interface StoreComponentItem {

    @Value("#{target.store_id}")
    String getStoreId();

    @Value("#{target.component_id}")
    String getComponentId();

    @Value("#{target.channel_id}")
    String getChannelId();

    @Value("#{target.offering_id}")
    String getOfferingId();

    @Value("#{target.release_datetime}")
    Timestamp getReleaseDate();
}
