package com.test.catalog.sales.db.dao.price.impl;

import com.test.catalog.sales.db.constant.ExceptionCodeEnum;
import com.test.catalog.sales.db.constant.RecordStatusEnum;
import com.test.catalog.sales.db.dao.price.SapArticlePriceDao;
import com.test.catalog.sales.db.dao.store.StoreDao;
import com.test.catalog.sales.db.dto.PriceStatusHistoryDto;
import com.test.catalog.sales.db.dto.SapArticlePriceDto;
import com.test.catalog.sales.db.entity.price.PriceStatusHistoryEntity;
import com.test.catalog.sales.db.entity.price.SapArticlePriceEntity;
import com.test.catalog.sales.db.entity.store.StoreOfferingChannelProductEntity;
import com.test.catalog.sales.db.mapper.SapArticlePriceMapper;
import com.test.catalog.sales.db.repository.store.PriceStatusHistoryRepository;
import com.test.catalog.sales.db.repository.store.SapArticlePriceRepository;
import com.test.catalog.sales.db.repository.store.StoreOfferingChannelComponentRepository;
import com.test.catalog.sales.db.repository.store.StoreOfferingChannelProductRepository;
import com.test.catalog.sales.exception.handler.SalesCatalogDBException;
import com.test.catalog.sales.exception.handler.SalesCatalogGenericException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * this class is to perform CURD operations on Sap Article Price related business
 */
@Slf4j
@Service
public class SapArticlePriceDaoImpl implements SapArticlePriceDao {

    private static final Logger LOG = LoggerFactory.getLogger(SapArticlePriceDaoImpl.class);

    final private SapArticlePriceMapper sapArticlePriceMapper;
    final private SapArticlePriceRepository sapArticlePriceRepository;
    private final PriceStatusHistoryRepository priceStatusHistoryRepository;
    private StoreOfferingChannelProductRepository storeOfferingChannelProductRepository;
    private StoreDao storeDao;

    @Autowired
    public SapArticlePriceDaoImpl(SapArticlePriceMapper sapArticlePriceMapper, SapArticlePriceRepository sapArticlePriceRepository,
                                  PriceStatusHistoryRepository priceStatusHistoryRepository,
                                  StoreOfferingChannelProductRepository storeOfferingChannelProductRepository,
                                  StoreOfferingChannelComponentRepository storeOfferingChannelComponentRepository,
                                  StoreDao storeDao) {
        this.sapArticlePriceMapper = sapArticlePriceMapper;
        this.sapArticlePriceRepository = sapArticlePriceRepository;
        this.priceStatusHistoryRepository = priceStatusHistoryRepository;
        this.storeOfferingChannelProductRepository = storeOfferingChannelProductRepository;
        this.storeDao=storeDao;
    }

    @Override
    public List<SapArticlePriceDto> getStorefrontProductPrice(UUID productId, String storeId, String channelId, String offeringId, String itemSku, List<Integer> recordStatusId) {
        return sapArticlePriceMapper.sapArticlePriceEntityListToSapArticlePriceDtoList(sapArticlePriceRepository.findByProductIdnAndStoreIdAndChannelId(productId, storeId, channelId,offeringId,itemSku,recordStatusId), new ArrayList<SapArticlePriceDto>());
    }

    /**
     * this method will persist the list of sapArticlePrice into dataBase and
     * creating new entry in price status history table
     *
     * @param sapArticlePriceDto
     * @return SapArticlePriceDto
     */
    @Override
    public SapArticlePriceDto createSapArticlePrice(SapArticlePriceDto sapArticlePriceDto) {
        SapArticlePriceEntity sapArticlePriceEntity = sapArticlePriceMapper
                .sapArticlePriceDtoListToSapArticlePriceEntity(sapArticlePriceDto, new SapArticlePriceEntity());
        sapArticlePriceEntity.setCreateDatetime(new Timestamp(System.currentTimeMillis()));
        SapArticlePriceDto sapArticlePriceResult = sapArticlePriceMapper.sapArticlePriceEntityToSapArticlePriceDto(sapArticlePriceRepository
                .save(sapArticlePriceEntity), new SapArticlePriceDto());
        List<Integer> recordStatusIds = new ArrayList<>();
        recordStatusIds.add(RecordStatusEnum.ACTIVE.getStatusId());
        recordStatusIds.add(RecordStatusEnum.PUBLISHED.getStatusId());
        if (!ObjectUtils.isEmpty(sapArticlePriceResult)) {
            List<StoreOfferingChannelProductEntity> storeOfferingChannelProductEntities = null;
            try {
                storeOfferingChannelProductEntities =
                        storeOfferingChannelProductRepository.findAllByStoreIdAndChannelIdAndSapArticleIdAndPublishAndActiveState(
                                sapArticlePriceResult.getStoreId(), sapArticlePriceResult.getChannelId(),
                                sapArticlePriceResult.getSapArticleId(), sapArticlePriceResult.getUnitOfMeasureId(), sapArticlePriceResult.getItemSku(),recordStatusIds);
            } catch (SalesCatalogDBException e) {
                throw new SalesCatalogDBException(new Throwable("Exception occurred while creating the Sap Article Price"),
                        ExceptionCodeEnum.DB_ERROR.getExceptionMessage(), ExceptionCodeEnum.DB_ERROR.getExceptionCode());
            } catch (Exception e) {
                throw new SalesCatalogGenericException(
                        new Throwable("Some exception occurred in create Sap Article Price"),
                        ExceptionCodeEnum.GENERIC_DB_ERROR.getExceptionMessage(), ExceptionCodeEnum.GENERIC_DB_ERROR.getExceptionCode());
            }
            if (!CollectionUtils.isEmpty(storeOfferingChannelProductEntities)) {
                List<PriceStatusHistoryEntity> priceStatusHistoryEntityList = new ArrayList<>();
                for (StoreOfferingChannelProductEntity storeOfferingChannelProductEntity : storeOfferingChannelProductEntities) {
                    PriceStatusHistoryEntity priceStatusHistoryEntity = new PriceStatusHistoryEntity();
                    priceStatusHistoryEntity.setPrice(sapArticlePriceResult.getPrice());
                    priceStatusHistoryEntity.setChannelId(storeOfferingChannelProductEntity.getChannelId());
                    priceStatusHistoryEntity.setEffectiveEndDatetime(sapArticlePriceResult.getEffectiveEndDatetime());
                    priceStatusHistoryEntity.setEffectiveStartDatetime(sapArticlePriceResult.getEffectiveStartDatetime());
                    priceStatusHistoryEntity.setStoreId(storeOfferingChannelProductEntity.getStoreId());
                    priceStatusHistoryEntity.setOfferingId(storeOfferingChannelProductEntity.getOfferingId());
                    priceStatusHistoryEntity.setRecordStatusId(RecordStatusEnum.ACTIVE.getStatusId());
                    priceStatusHistoryEntity.setProductId(storeOfferingChannelProductEntity.getProductId());
                    priceStatusHistoryEntity.setItemSku(sapArticlePriceDto.getItemSku());
                    priceStatusHistoryEntity.setCreateDatetime(new Timestamp(System.currentTimeMillis()));
                    priceStatusHistoryEntityList.add(priceStatusHistoryEntity);
                }
                priceStatusHistoryRepository.saveAll(priceStatusHistoryEntityList);

            }
        }
        return sapArticlePriceResult;
    }

    /**
     * this method persist price status history record
     *
     * @param priceStatusHistoryDto
     * @return PriceStatusHistoryDto
     */
    @Override
    public PriceStatusHistoryDto updatePricingHistory(PriceStatusHistoryDto priceStatusHistoryDto) {
        PriceStatusHistoryEntity priceStatusHistoryEntity = new PriceStatusHistoryEntity();
        priceStatusHistoryEntity.setPrice(priceStatusHistoryDto.getPrice());
        priceStatusHistoryEntity.setChannelId(priceStatusHistoryDto.getChannelId());
        priceStatusHistoryEntity.setEffectiveEndDatetime(priceStatusHistoryDto.getEffectiveEndDatetime());
        priceStatusHistoryEntity.setEffectiveStartDatetime(priceStatusHistoryDto.getEffectiveStartDatetime());
        priceStatusHistoryEntity.setStoreId(priceStatusHistoryDto.getStoreId());
        priceStatusHistoryEntity.setItemSku(priceStatusHistoryDto.getItemSku());
        priceStatusHistoryEntity.setOfferingId(priceStatusHistoryDto.getOfferingId());
        priceStatusHistoryEntity.setRecordStatusId(priceStatusHistoryDto.getRecordStatusId());
        priceStatusHistoryEntity.setProductId(priceStatusHistoryDto.getProductId());
        priceStatusHistoryEntity.setCreateDatetime(new Timestamp(System.currentTimeMillis()));
        try {
            priceStatusHistoryRepository.save(priceStatusHistoryEntity);
            priceStatusHistoryDto.setSuccess(true);

        } catch (Exception e) {
            LOG.error("Error on persisting Price status history {} {}", priceStatusHistoryDto, e.getLocalizedMessage());
            priceStatusHistoryDto.setSuccess(false);
        }
        return priceStatusHistoryDto;
    }


}
