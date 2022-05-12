package com.test.catalog.sales.client.response.product;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StoreOfferingResponse {

    private String storeId;
   // private Double price;
    private List<StoreOfferingsChannelResponse> offeringList;
}
