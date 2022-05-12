package com.test.catalog.sales.domain.service.store.impl;

import com.test.catalog.sales.client.request.component.StoreFrontComponentStatus;
import com.test.catalog.sales.client.response.component.StoreFrontComponentResultStatus;
import com.test.catalog.sales.client.response.ingredient.StoreFrontIngredient;
import com.test.catalog.sales.client.response.ingredient.StoreFrontIngredientResponse;
import com.test.catalog.sales.client.response.component.StoreFrontComponentStatusResponse;
import com.test.catalog.sales.client.response.product.StoreFrontProductPrice;
import com.test.catalog.sales.db.constant.RecordStatusEnum;
import com.test.catalog.sales.db.dao.store.StoreDao;
import com.test.catalog.sales.db.dto.StoreComponentDto;
import com.test.catalog.sales.db.dto.StoreOfferingChannelComponentDto;
import com.test.catalog.sales.domain.service.store.StoreIngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
public class StoreIngredientServiceImpl implements StoreIngredientService {

    private StoreDao storeDao;

    @Autowired
    public StoreIngredientServiceImpl(StoreDao storeDao) {
        this.storeDao = storeDao;
    }

    /**
     * This method call the StoreDao impl to get the StoreIngredient list for store
     *
     * @param releaseDate
     * @return StoreFrontIngredientResponse
     */
    @Override
    public StoreFrontIngredientResponse getStorefrontIngredients(String nextState, Date releaseDate, Integer recordsSize) {
        log.debug(" StoreIngredientServiceImpl getStoreFrontIngredients  nextState{},recordsSize {}, releaseDate {}", nextState,recordsSize, releaseDate);
        StoreFrontIngredientResponse storeFrontIngredientResponse = null;
        List<StoreFrontIngredient> storeFrontIngredients = null;
        List<StoreOfferingChannelComponentDto> storeOfferingChannelComponentDtos = null;
        if(RecordStatusEnum.UNPUBLISHED.getStatusName().equalsIgnoreCase(nextState)) {
            storeOfferingChannelComponentDtos = storeDao.getStoreComponentItem(releaseDate, RecordStatusEnum.PUBLISHED.getStatusId(), RecordStatusEnum.ACTIVE.getStatusId(), RecordStatusEnum.PUBLISHED.getStatusId(),  recordsSize);
        } else {
            storeOfferingChannelComponentDtos = storeDao.getStoreComponentItem(releaseDate, RecordStatusEnum.ACTIVE.getStatusId(), RecordStatusEnum.ACTIVE.getStatusId(), RecordStatusEnum.PUBLISHED.getStatusId(), recordsSize);
        }
        if (!ObjectUtils.isEmpty(storeOfferingChannelComponentDtos)) {
            storeFrontIngredientResponse = new StoreFrontIngredientResponse();
            storeFrontIngredients = new ArrayList<>();
            for (StoreOfferingChannelComponentDto storeOfferingChannelComponentDto : storeOfferingChannelComponentDtos) {
                List<StoreFrontProductPrice> storeFrontProductPrices = new ArrayList<>();
                StoreFrontProductPrice storeFrontProductPrice = new StoreFrontProductPrice();
                storeFrontProductPrice.setPrice(new BigDecimal(0));
                storeFrontProductPrices.add(storeFrontProductPrice);

                StoreFrontIngredient storeFrontIngredient = new StoreFrontIngredient();
                storeFrontIngredient.setStoreId(storeOfferingChannelComponentDto.getStoreId());
                storeFrontIngredient.setChannelId(storeOfferingChannelComponentDto.getChannelId());
                storeFrontIngredient.setOfferingId(storeOfferingChannelComponentDto.getOfferingId());
                storeFrontIngredient.setIngredientId(storeOfferingChannelComponentDto.getComponentId());
                storeFrontIngredient.setPriceList(storeFrontProductPrices);
                storeFrontIngredient.setReleaseDate(storeOfferingChannelComponentDto.getReleaseDate());
                storeFrontIngredients.add(storeFrontIngredient);
            }
            Set<StoreFrontIngredient> frontIngredients = new HashSet<>();
            frontIngredients.addAll(storeFrontIngredients);
            storeFrontIngredientResponse.setStoreFrontIngredientList(frontIngredients);
        }
        return storeFrontIngredientResponse;
    }

    /**
     * this method is to update the status of store front component
     * @param storeFrontComponentStatusList
     * @return StoreFrontIngredientStatusResponse
     */
    public StoreFrontComponentStatusResponse updateStoreFrontComponent(List<StoreFrontComponentStatus> storeFrontComponentStatusList) {
        StoreFrontComponentStatusResponse storeFrontComponentStatusResponse = new StoreFrontComponentStatusResponse();
        storeFrontComponentStatusResponse.setStateList(new ArrayList<>(storeFrontComponentStatusList.size()));
        storeFrontComponentStatusList.stream().forEach(storeFrontIngredientStatus -> {
            StoreComponentDto storeComponentDto = storeDao.updateStoreFrontComponent(getStoreComponentDto(storeFrontIngredientStatus));
            StoreFrontComponentResultStatus storeFrontComponentResultStatus = getComponentStatus(storeComponentDto);
            storeFrontComponentResultStatus.setRecordStatus(storeFrontIngredientStatus.getRecordStatus());
            storeFrontComponentStatusResponse.getStateList().add(storeFrontComponentResultStatus);
        });
        return storeFrontComponentStatusResponse;
    }

    /**
     * this method to populate  StoreComponentDto from storeFrontIngredientStatus object
     * @return StoreComponentDto
     */
    private StoreComponentDto getStoreComponentDto(StoreFrontComponentStatus storeFrontComponentStatus) {
        StoreComponentDto storeComponentDto = new StoreComponentDto();
        storeComponentDto.setChannelId(storeFrontComponentStatus.getChannelId());
        storeComponentDto.setComponentId(storeFrontComponentStatus.getComponentId());
        storeComponentDto.setOfferingId(storeFrontComponentStatus.getOfferingId());
        storeComponentDto.setReleaseDate(storeFrontComponentStatus.getReleaseDate());
        storeComponentDto.setStoreId(storeFrontComponentStatus.getStoreId());
        storeComponentDto.setRecordStatusId(RecordStatusEnum.valueOfLabel(storeFrontComponentStatus.getRecordStatus()).getStatusId());
        return storeComponentDto;
    }

    /**
     * this method to populate  StoreFrontComponentStatus from StoreComponentDto object
     * @return StoreFrontComponentStatus
     */
    private StoreFrontComponentResultStatus getComponentStatus(StoreComponentDto storeComponentDto) {
        StoreFrontComponentResultStatus storeFrontComponentResultStatus = new StoreFrontComponentResultStatus();
        storeFrontComponentResultStatus.setChannelId(storeComponentDto.getChannelId());
        storeFrontComponentResultStatus.setComponentId(storeComponentDto.getComponentId());
        storeFrontComponentResultStatus.setOfferingId(storeComponentDto.getOfferingId());
        storeFrontComponentResultStatus.setReleaseDate(storeComponentDto.getReleaseDate());
        storeFrontComponentResultStatus.setStoreId(storeComponentDto.getStoreId());
        storeFrontComponentResultStatus.setSuccess(storeComponentDto.isSuccess());
        return storeFrontComponentResultStatus;
    }
}
