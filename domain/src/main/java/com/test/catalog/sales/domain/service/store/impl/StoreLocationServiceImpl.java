package com.test.catalog.sales.domain.service.store.impl;

import com.test.catalog.sales.client.request.product.StoreFrontProductStatus;
import com.test.catalog.sales.client.request.product.StoreFrontProductStatusRequest;
import com.test.catalog.sales.client.request.store.StoreLocationRequest;
import com.test.catalog.sales.client.response.store.StoreLocationDetail;
import com.test.catalog.sales.client.response.store.StoreLocationResponse;
import com.test.catalog.sales.db.constant.RecordStatusEnum;
import com.test.catalog.sales.db.dao.store.StoreDao;
import com.test.catalog.sales.db.dao.store.StoreFrontStatusDao;
import com.test.catalog.sales.db.dto.StoreDto;
import com.test.catalog.sales.db.dto.StoreFrontProductDto;
import com.test.catalog.sales.db.dto.StoreFrontProductStatusDto;
import com.test.catalog.sales.db.dto.StoreOfferingChannelDto;
import com.test.catalog.sales.domain.helper.StoreServiceHelper;
import com.test.catalog.sales.domain.service.store.StoreLocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreLocationServiceImpl implements StoreLocationService {

    private static final Logger LOG = LoggerFactory.getLogger(StoreLocationServiceImpl.class);

    private StoreDao storeDao;
    private StoreFrontStatusDao storeFrontStatusDao;
    private StoreServiceHelper storeServiceHelper;

    @Autowired
    public StoreLocationServiceImpl(StoreDao storeDao,
                                    StoreServiceHelper storeServiceHelper,
                                    StoreFrontStatusDao storeFrontStatusDao) {
        this.storeDao = storeDao;
        this.storeServiceHelper = storeServiceHelper;
        this.storeFrontStatusDao = storeFrontStatusDao;
    }

    /**
     * This method calls storeDao to create the store and storeofferingChannel record
     *
     * @param storeLocationRequest
     * @return StoreLocationResponse
     */
    @Override
    public StoreLocationResponse createStoreLocation(StoreLocationRequest storeLocationRequest) {
        LOG.debug(" StoreLocationServiceImpl.createStoreLocation  called");
        StoreLocationDetail storeLocationDetail = null;
        StoreDto storeDto = null;
        //TODO: Exception Handling Using new Exception FrameWork
        if (!ObjectUtils.isEmpty(storeLocationRequest)) {
            storeDto = storeDao.getLatestStore(storeLocationRequest.getStoreId());
            if (ObjectUtils.isEmpty(storeDto) || (!ObjectUtils.isEmpty(storeDto) && (storeDto.getRecordStatusId() == RecordStatusEnum.ARCHIVED.getStatusId()))) {
                storeDto = new StoreDto();
                storeDto.setStoreId(storeLocationRequest.getStoreId());
                storeDto.setStoreName(storeLocationRequest.getStoreName());
                storeDto.setRecordStatusId(storeLocationRequest.getRecordStatusId());
                storeDto.setCreateDatetime(Timestamp.from(Instant.now()));
                storeDto = storeDao.createStore(storeDto);
            }

            List<StoreOfferingChannelDto> storeOfferingChannelDtos = storeServiceHelper.mapRequestToStoreOfferingChannelDto(storeLocationRequest);

            List<StoreOfferingChannelDto> finalStoreOfferingChannelDtos = new ArrayList<>();

            for (StoreOfferingChannelDto storeOfferingChannelDto : storeOfferingChannelDtos) {
                StoreOfferingChannelDto tempStoreOfferingChannelDto = storeDao.getLatestStoreOfferingChannel(storeOfferingChannelDto.getStoreId(), storeOfferingChannelDto.getOfferingId(), storeOfferingChannelDto.getChannelId());
                if (ObjectUtils.isEmpty(tempStoreOfferingChannelDto) || (!ObjectUtils.isEmpty(tempStoreOfferingChannelDto) && (tempStoreOfferingChannelDto.getRecordStatusId() == RecordStatusEnum.ARCHIVED.getStatusId()))) {
                    finalStoreOfferingChannelDtos.add(storeOfferingChannelDto);
                }
            }
            if (finalStoreOfferingChannelDtos.size() > 0) {
                storeOfferingChannelDtos = storeDao.createStoreOfferingChannel(finalStoreOfferingChannelDtos);
            }
            storeLocationDetail = storeServiceHelper.mapToStoreLocationDetail(storeDto, storeOfferingChannelDtos);
        }
        return new StoreLocationResponse(storeLocationDetail);
    }

    @Override
    public StoreFrontProductStatusRequest updateStoreFrontStatus(StoreFrontProductStatusRequest request) {
        request.getStateList().stream().forEach(storeFrontProductStatus -> {
            StoreFrontProductStatusDto rtn = storeFrontStatusDao.updateStoreFrontStatus(prepareStoreFrontProductStatusDto(storeFrontProductStatus));
            storeFrontProductStatus.setSuccess(rtn.isSuccess());
        });
        return request;
    }

    public StoreFrontProductStatusDto prepareStoreFrontProductStatusDto(StoreFrontProductStatus storeFrontProductStatus) {
        StoreFrontProductStatusDto storeFrontProductStatusDto = new StoreFrontProductStatusDto();
        storeFrontProductStatusDto.setStoreId(storeFrontProductStatus.getStoreId());
        storeFrontProductStatusDto.setChannelId(storeFrontProductStatus.getChannelId());
        storeFrontProductStatusDto.setOfferingId(storeFrontProductStatus.getOfferingId());
        storeFrontProductStatusDto.setProductId(storeFrontProductStatus.getProductId());
        storeFrontProductStatusDto.setItemSku(storeFrontProductStatus.getItemSku());
        storeFrontProductStatusDto.setRecordStatus(RecordStatusEnum.valueOfLabel(storeFrontProductStatus.getRecordStatus()).getStatusId());
        storeFrontProductStatusDto.setReleaseDate(storeFrontProductStatus.getReleaseDate());
        storeFrontProductStatusDto.setPrices(storeFrontProductStatus.getPriceList().stream().map(storeFrontProductPrice -> new StoreFrontProductDto(storeFrontProductPrice.getPrice(), storeFrontProductPrice.getBeginEffectiveDate(), storeFrontProductPrice.getEndEffectiveDate(), storeFrontProductPrice.getCurrencyCode())).collect(Collectors.toList()));
        return storeFrontProductStatusDto;
    }
}
