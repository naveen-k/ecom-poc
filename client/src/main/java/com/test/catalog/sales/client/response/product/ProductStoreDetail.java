package com.test.catalog.sales.client.response.product;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ProductStoreDetail {

private UUID productId;
private Date releaseDate;
private List<StoreOfferingResponse> locations;

}
