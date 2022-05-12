package com.test.catalog.sales.db.dao.price;

import com.test.catalog.sales.db.dto.PriceStatusHistoryDto;
import com.test.catalog.sales.db.dto.SapArticlePriceDto;

import java.util.List;
import java.util.UUID;

public interface SapArticlePriceDao {
     List<SapArticlePriceDto> getStorefrontProductPrice(UUID productId, String  storeId, String channelId, String offeringId, String itemSku, List<Integer> recordStatusId);

     SapArticlePriceDto createSapArticlePrice(SapArticlePriceDto sapArticlePriceDto);

     PriceStatusHistoryDto updatePricingHistory(PriceStatusHistoryDto priceStatusHistoryDto);
}
