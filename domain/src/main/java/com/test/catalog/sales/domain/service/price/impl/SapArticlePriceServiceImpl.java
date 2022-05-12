package com.test.catalog.sales.domain.service.price.impl;

import com.test.catalog.sales.client.request.price.SapArticlePriceRequest;
import com.test.catalog.sales.client.request.price.UpdatePriceHistoryStatus;
import com.test.catalog.sales.client.response.price.PriceHistoryStatusResponse;
import com.test.catalog.sales.client.response.price.UpdatePriceHistoryStatusResponse;
import com.test.catalog.sales.db.constant.RecordStatusEnum;
import com.test.catalog.sales.db.dao.price.SapArticlePriceDao;
import com.test.catalog.sales.db.dto.PriceStatusHistoryDto;
import com.test.catalog.sales.db.dto.SapArticlePriceDto;
import com.test.catalog.sales.db.util.CatalogUtilities;
import com.test.catalog.sales.domain.service.price.SapArticlePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * this service is to handle Sap Article Price process
 */
@Service
public class SapArticlePriceServiceImpl implements SapArticlePriceService {

    SapArticlePriceDao sapArticlePriceDao;

    @Autowired
    public SapArticlePriceServiceImpl(SapArticlePriceDao sapArticlePriceDao) {
        this.sapArticlePriceDao = sapArticlePriceDao;
    }

    /**
     * This is to set default value for Channel id
     */
    private static final String DEFAULT_CHANNEL_ID = "web";

    /**
     * this method is take request object and convert into Dto to call Doa service for creation of Sap Article Price
     *
     * @param sapArticlePriceRequest
     * @return SapArticlePriceDto
     * @throws ParseException
     */
    @Override
    public SapArticlePriceDto createSapArticlePrice(@Valid SapArticlePriceRequest sapArticlePriceRequest)
            throws ParseException {
        SapArticlePriceDto sapArticlePriceDto = new SapArticlePriceDto();
        if (StringUtils.isEmpty(sapArticlePriceRequest.getChannelId())) {
            sapArticlePriceDto.setChannelId(DEFAULT_CHANNEL_ID);
        } else {
            sapArticlePriceDto.setChannelId(sapArticlePriceRequest.getChannelId());
        }
        sapArticlePriceDto.setEffectiveEndDatetime(CatalogUtilities.getTimestampDate(
                sapArticlePriceRequest.getEndEffectiveDate(), CatalogUtilities.DATE_FORMAT));
        sapArticlePriceDto.setEffectiveStartDatetime(
                CatalogUtilities.getTimestampDate(sapArticlePriceRequest.getBeginEffectiveDate(),
                        CatalogUtilities.DATE_FORMAT));
        sapArticlePriceDto.setPrice(sapArticlePriceRequest.getPrice());
        sapArticlePriceDto.setStoreId(sapArticlePriceRequest.getStoreId());
        sapArticlePriceDto.setItemSku(sapArticlePriceRequest.getItemSku());
        sapArticlePriceDto.setSapArticleId(sapArticlePriceRequest.getArticleId());
        sapArticlePriceDto.setUnitOfMeasureId(sapArticlePriceRequest.getUom());
        return sapArticlePriceDao.createSapArticlePrice(sapArticlePriceDto);
    }

    /**
     * this method is to update pricing status history by calling dao class and creating the response structure
     * @param updatePriceHistoryStatusList
     * @return UpdatePriceHistoryStatusResponse
     */
    @Override
    public UpdatePriceHistoryStatusResponse updatePricingHistory(
            @Valid List<UpdatePriceHistoryStatus> updatePriceHistoryStatusList) {
        UpdatePriceHistoryStatusResponse response = new UpdatePriceHistoryStatusResponse();
        List<PriceHistoryStatusResponse> statusList = new ArrayList<>();
        updatePriceHistoryStatusList.stream().forEach(status -> {
            PriceStatusHistoryDto dto = sapArticlePriceDao.updatePricingHistory(getUpdatedPriceDto(status));
            statusList.add(getResponse(dto, status));
        });
        response.setStatusList(statusList);
        return response;
    }

    /**
     * this method is get the PriceStatusHistoryDto object from UpdatePriceHistoryStatus request
     * @param status
     * @return PriceStatusHistoryDto
     */
    private PriceStatusHistoryDto getUpdatedPriceDto(UpdatePriceHistoryStatus status) {
        PriceStatusHistoryDto dto = new PriceStatusHistoryDto();
        dto.setPrice(status.getPrice());
        dto.setChannelId(status.getChannelId());
        try {
            dto.setEffectiveEndDatetime(CatalogUtilities.getTimestampDate(status.getEndEffectiveDate(),
                    CatalogUtilities.DATE_FORMAT));
            dto.setEffectiveStartDatetime(CatalogUtilities.getTimestampDate(status.getBeginEffectiveDate(),
                    CatalogUtilities.DATE_FORMAT));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dto.setStoreId(status.getStoreId());
        dto.setOfferingId(status.getOfferingId());
        dto.setItemSku(status.getItemSku());
        dto.setRecordStatusId(RecordStatusEnum.valueOfLabel(status.getRecordStatus()).getStatusId());
        dto.setProductId(status.getProductId());
        return dto;
    }

    /**
     * this method is get the PriceHistoryStatusResponse object fromPriceStatusHistoryDto and
     * UpdatePriceHistoryStatus request
     * @param dto
     * @param status
     * @return PriceHistoryStatusResponse
     */
    private PriceHistoryStatusResponse getResponse(PriceStatusHistoryDto dto, UpdatePriceHistoryStatus status) {
        PriceHistoryStatusResponse response = new PriceHistoryStatusResponse();
        response.setBeginEffectiveDate(status.getBeginEffectiveDate());
        response.setEndEffectiveDate(status.getEndEffectiveDate());
        response.setChannelId(dto.getChannelId());
        response.setOfferingId(dto.getOfferingId());
        response.setProductId(dto.getProductId());
        response.setPrice(dto.getPrice());
        response.setItemSku(dto.getItemSku());
        response.setStoreId(dto.getStoreId());
        response.setRecordStatus(status.getRecordStatus());
        response.setSuccess(dto.isSuccess());
        return response;
    }

}
