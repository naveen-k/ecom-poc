package com.test.catalog.sales.client.response.store;

import com.test.catalog.sales.client.request.store.OfferingsChannel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StoreLocationDetail {

    private String storeId;
    private String storename;
    private List<OfferingsChannel> offerings;
    private Integer recordStatusId;
}
