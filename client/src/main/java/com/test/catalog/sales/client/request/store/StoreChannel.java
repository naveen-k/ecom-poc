package com.test.catalog.sales.client.request.store;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class StoreChannel {

    @NotBlank(message = "Mandatory attribute channelId is missing")
    private String channelId;
}
