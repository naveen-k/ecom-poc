package com.test.catalog.sales.client.request.store;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class StoreLocationRequest {

    @NotBlank (message = "Mandatory attribute storeId is missing")
    private String storeId;
    @NotBlank (message = "Mandatory attribute storeName is missing")
    private String storeName;

    @Valid
    @NotNull
    @NotEmpty(message = "Mandatory attribute offerings is missing")
    private List<OfferingsChannel> offerings;

    @NotNull(message = "Mandatory attribute recordStatusId is missing")
    private Integer recordStatusId;
}
