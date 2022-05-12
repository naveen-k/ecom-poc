package com.test.catalog.sales.db.dao.store;

import com.test.catalog.sales.db.dto.*;
import com.test.catalog.sales.db.dto.wcm.StoreChannelOfferingEventWcm;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface StoreDao {

    List<StoreOfferingChannelProductDto> getStoreOfferingsChannelByProductId(UUID productId, Date releaseDate, List<Integer> subRecordStatusId);

    StoreDto createStore(StoreDto storeDto);

    List<StoreOfferingChannelDto> createStoreOfferingChannel(List<StoreOfferingChannelDto> storeOfferingChannelDtos);

    StoreDto getLatestStore(String storeId);

    StoreOfferingChannelDto getLatestStoreOfferingChannel(String storeId, String offeringId, String channelId);

    List<StoreOfferingChannelProductDto> getStoreOfferingsChannelByReleaseDate(Date releaseDate, List<Integer> subRecordStatusId, Integer recordsSize);

    boolean populateStorefrontProductPrice(Date releaseDate, List<Integer> subRecordStatusId);

    List<StoreOfferingChannelProductDto> getStoreOfferingsChannelBySapArticleIdAndStoreId(String storeId, String sapArticleId, String uom, Integer recordStatusId);

    List<StoreOfferingChannelComponentDto> getStoreComponentItem(Date releaseDate, Integer subRecordStatusId, Integer recordActStatusId, Integer publishedStatusId, Integer recordsSize);

    StoreComponentDto updateStoreFrontComponent(StoreComponentDto storeComponentDto);

    StoreChannelOfferingEventWcm createStoreChannelOfferingEventObject(String storeId, String channelId, String offeringId);

    String mdcGet(String key);



}
