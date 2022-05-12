package com.test.catalog.sales.db.dto;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class BaseDto {
    private String versionId;
    private Timestamp createDatetime;
    private Integer recordStatusId;
}
