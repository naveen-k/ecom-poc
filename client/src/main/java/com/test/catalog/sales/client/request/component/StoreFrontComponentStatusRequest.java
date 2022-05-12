package com.test.catalog.sales.client.request.component;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
public class StoreFrontComponentStatusRequest {
    @Valid
    @Size(min = 1)
    List<StoreFrontComponentStatus> stateList;
}
