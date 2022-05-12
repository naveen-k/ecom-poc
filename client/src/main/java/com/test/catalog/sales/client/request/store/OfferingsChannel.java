package com.test.catalog.sales.client.request.store;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class OfferingsChannel {
    @NotBlank(message = "Mandatory attribute offeringId is missing")
    String offeringId;

    @Valid
    @NotNull
    @NotEmpty(message = "Mandatory attribute salesChannelList is empty")
    List<StoreChannel> salesChannelList;
}
