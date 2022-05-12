package com.test.catalog.sales.domain.service.price;

import com.test.catalog.sales.client.request.price.SapArticlePriceRequest;
import com.test.catalog.sales.client.request.price.UpdatePriceHistoryStatus;
import com.test.catalog.sales.client.response.price.UpdatePriceHistoryStatusResponse;
import com.test.catalog.sales.db.dto.SapArticlePriceDto;

import java.text.ParseException;
import java.util.List;

public interface SapArticlePriceService {

    SapArticlePriceDto createSapArticlePrice(SapArticlePriceRequest sapArticlePriceRequest) throws ParseException;

    UpdatePriceHistoryStatusResponse updatePricingHistory(List<UpdatePriceHistoryStatus> updatePriceHistoryStatusList);
}
