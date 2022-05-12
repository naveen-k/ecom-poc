package com.test.catalog.sales.client.request.product;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
public class StoreFrontProductStatusRequest {
    @Valid
    @Size(min = 1)
    List<StoreFrontProductStatus> stateList;
}
