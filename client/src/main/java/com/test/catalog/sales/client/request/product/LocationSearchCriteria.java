package com.test.catalog.sales.client.request.product;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Component
public class LocationSearchCriteria {

    private UUID productId;
    private Date releasteDate;
    private List<Integer> subRecordStatusId;
}
