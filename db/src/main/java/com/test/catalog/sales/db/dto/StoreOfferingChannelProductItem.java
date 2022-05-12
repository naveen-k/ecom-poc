package com.test.catalog.sales.db.dto;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;

public interface StoreOfferingChannelProductItem {
    @Value("#{target.store_id}")
    String getStoreId();

    @Value("#{target.offering_id}")
    String getOfferingId();

    @Value("#{target.channel_id}")
    String getChannelId();

    @Value("#{target.product_id}")
    String getProductId();

    @Value("#{target.item_sku}")
    String getItemSku();

    @Value("#{target.sku_type}")
    String getSkuType();

    @Value("#{target.release_datetime}")
    Timestamp getReleaseDate();
}
