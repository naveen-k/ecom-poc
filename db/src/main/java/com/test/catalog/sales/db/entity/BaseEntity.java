package com.test.catalog.sales.db.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@Data
@MappedSuperclass
public class BaseEntity {

    @Column(name = "version_id")
    private String versionId;
    @Column(name = "create_datetime", nullable = false, updatable = false)
    @CreatedDate
    private Timestamp createDatetime;
    @Column(name = "record_status_id")
    private Integer recordStatusId;
}
