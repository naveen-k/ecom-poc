package com.test.catalog.sales.db.dto;

import org.springframework.beans.factory.annotation.Value;
import java.sql.Timestamp;
import java.util.UUID;

public interface ProductCategory {


    @Value("#{target.product_category_key}")
    Integer getProductCategoryKey();

    @Value("#{target.category_id}")
    String getCategoryId();

    @Value("#{target.product_id}")
    String getProductId();

    @Value("#{target.record_status_id}")
    Integer getRecordStatusId();

    @Value("#{target.display_sequence_number}")
    Integer getDisplaySequenceNumber();

    @Value("#{target.create_datetime}")
    Timestamp getCreateDatetime();

    @Value("#{target.version_id}")
    String getVersionId();


}

