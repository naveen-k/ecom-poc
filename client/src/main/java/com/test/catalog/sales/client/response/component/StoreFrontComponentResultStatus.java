package com.test.catalog.sales.client.response.component;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@JsonInclude()
public class StoreFrontComponentResultStatus {

    String releaseDate;
    private UUID componentId;
    private String storeId;
    private String offeringId;
    private String channelId;
    private String recordStatus;
    private boolean isSuccess;
}
