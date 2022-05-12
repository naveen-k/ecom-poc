package com.test.catalog.sales.client.response.price;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UpdatePriceHistoryStatusResponse {

    List<PriceHistoryStatusResponse> statusList;
}
