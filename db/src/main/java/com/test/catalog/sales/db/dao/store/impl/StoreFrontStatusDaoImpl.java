package com.test.catalog.sales.db.dao.store.impl;

import com.test.catalog.sales.db.constant.RecordStatusEnum;
import com.test.catalog.sales.db.dto.PriceStatusHistoryDto;
import com.test.catalog.sales.db.dto.StoreFrontProductStatusDto;
import com.test.catalog.sales.db.dto.StoreProductDto;
import com.test.catalog.sales.db.entity.price.PriceStatusHistoryEntity;
import com.test.catalog.sales.db.entity.price.SapArticlePriceEntity;
import com.test.catalog.sales.db.entity.product.StoreProductEntity;
import com.test.catalog.sales.db.entity.store.StoreOfferingChannelProductEntity;
import com.test.catalog.sales.db.mapper.StoreEntityMapper;
import com.test.catalog.sales.db.repository.store.PriceStatusHistoryRepository;
import com.test.catalog.sales.db.repository.store.SapArticlePriceRepository;
import com.test.catalog.sales.db.repository.store.StoreOfferingChannelProductRepository;
import com.test.catalog.sales.db.repository.store.StoreProductRepository;
import com.test.catalog.sales.db.util.CatalogUtilities;
import com.test.catalog.sales.db.dao.store.StoreDao;
import com.test.catalog.sales.db.dao.store.StoreFrontStatusDao;
import com.test.catalog.sales.db.dto.StoreOfferingChannelProductDto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;



@Slf4j
@Service
public class StoreFrontStatusDaoImpl implements StoreFrontStatusDao {
    private final StoreOfferingChannelProductRepository storeOfferingChannelProductRepository;
    private final PriceStatusHistoryRepository priceStatusHistoryRepository;
    private final SapArticlePriceRepository sapArticlePriceRepository;
    private final StoreProductRepository storeProductRepository;
    private final StoreEntityMapper storeEntityMapper;

    @Autowired
    StoreDao storeDao;

    @Autowired
    public StoreFrontStatusDaoImpl(StoreOfferingChannelProductRepository storeOfferingChannelProductRepository, PriceStatusHistoryRepository priceStatusHistoryRepository,
                                   SapArticlePriceRepository sapArticlePriceRepository,
                                   StoreProductRepository storeProductRepository,
                                   StoreEntityMapper storeEntityMapper) {
        this.storeOfferingChannelProductRepository = storeOfferingChannelProductRepository;
        this.priceStatusHistoryRepository = priceStatusHistoryRepository;
        this.sapArticlePriceRepository = sapArticlePriceRepository;
        this.storeProductRepository = storeProductRepository;
        this.storeEntityMapper = storeEntityMapper;
    }

    /**
     * This method updateStoreFrontStatus
     *
     * @param request
     * @return StoreFrontProductStatusDto
     */
    @Override
    public StoreFrontProductStatusDto updateStoreFrontStatus(final StoreFrontProductStatusDto request) {
        log.info("updateStoreFrontStatus {}", request);
        try {
            int statusId= RecordStatusEnum.ACTIVE.getStatusId();
            if(request.getRecordStatus()==RecordStatusEnum.UNPUBLISHED.getStatusId()||
                    request.getRecordStatus()==RecordStatusEnum.UNPUBLISHED_FAILED.getStatusId()){
                statusId= RecordStatusEnum.PUBLISHED.getStatusId();
            }
            request.setSuccess(false);
            //1. Update StoreFrontProduct
            StoreOfferingChannelProductDto storeOfferingChannelProductDto = findLatestStoreOfferingChannelProduct(request,statusId);
            log.debug("updateStoreFrontStatus -> storeOfferingChannelProductEntity {}", storeOfferingChannelProductDto);
            if (!ObjectUtils.isEmpty(storeOfferingChannelProductDto)) {
                storeOfferingChannelProductDto.setStoreOfferChannelPrdKey(null);
                storeOfferingChannelProductDto.setCreateDatetime(new Timestamp(System.currentTimeMillis()));
                storeOfferingChannelProductDto.setRecordStatusId(request.getRecordStatus());
                storeOfferingChannelProductRepository.save(storeEntityMapper.storeOfferingChannelProductDtoListToEntity(storeOfferingChannelProductDto, new StoreOfferingChannelProductEntity()));

                //2. Update StoreProduct
                StoreProductDto storeProductDto = findLatestStoreProduct(request,statusId);
                log.debug("updateStoreFrontStatus -> storeProductDto {}", storeProductDto);
                if (!ObjectUtils.isEmpty(storeProductDto)) {
                    storeProductDto.setStoreProductKey(null);
                    storeProductDto.setCreateDatetime(new Timestamp(System.currentTimeMillis()));
                    storeProductDto.setRecordStatusId(request.getRecordStatus());
                    storeProductDto.setReleaseDate(null);
                    storeProductRepository.save(storeEntityMapper.storeProductDtoToStoreProductEntity(storeProductDto, new StoreProductEntity()));

                    //3. Update  PriceStatusHistory
                    request.getPrices().stream().forEach(storeFrontProductDto -> {
                        SapArticlePriceEntity sapArticlePrice = null;
                        try {
                            sapArticlePrice = sapArticlePriceRepository.findByProductIdnAndStoreIdAndChannelIdAndDates(
                                    request.getProductId(),
                                    request.getStoreId(),
                                    request.getChannelId(),
                                    request.getItemSku(),
                                    CatalogUtilities.getTimestampDate(storeFrontProductDto.getBeginEffectiveDate(), "yyyy-MM-dd"),
                                    CatalogUtilities.getTimestampDate(storeFrontProductDto.getEndEffectiveDate(), "yyyy-MM-dd"));
                        } catch (ParseException e) {
                            log.error("Error on occurred while parsing timestamp {}", e.getLocalizedMessage());
                        }
                        log.debug("updateStoreFrontStatus -> sapArticlePrice {}", sapArticlePrice);

                        if (!ObjectUtils.isEmpty(sapArticlePrice)) {
                            PriceStatusHistoryDto priceHistoryDto = new PriceStatusHistoryDto();
                            priceHistoryDto.setStoreId(request.getStoreId());
                            priceHistoryDto.setChannelId(request.getChannelId());
                            priceHistoryDto.setOfferingId(request.getOfferingId());
                            priceHistoryDto.setProductId(request.getProductId());
                            priceHistoryDto.setPrice(BigDecimal.valueOf(sapArticlePrice.getPrice()));
                            priceHistoryDto.setItemSku(request.getItemSku());
                            priceHistoryDto.setEffectiveEndDatetime(sapArticlePrice.getEffectiveEndDatetime());
                            priceHistoryDto.setEffectiveStartDatetime(sapArticlePrice.getEffectiveStartDatetime());
                            priceHistoryDto.setRecordStatusId(request.getRecordStatus());
                            priceHistoryDto.setCreateDatetime(new Timestamp(System.currentTimeMillis()));
                            priceStatusHistoryRepository.save(storeEntityMapper.priceStatusHistoryDtoToPriceStatusHistoryEntity(priceHistoryDto, new PriceStatusHistoryEntity()));
                            request.setSuccess(true);
                            log.debug("priceHistoryDto trigger storeFront event for {}", priceHistoryDto);

                        }
                    });
                }
            }
        } catch (Exception e) {
            request.setSuccess(false);
            log.error("Error on occurred while fetching detail {}", e.getLocalizedMessage());
        }
        return request;
    }

    /**
     * This method findLatestStoreOfferingChannelProduct
     *
     * @param request
     * @return StoreOfferingChannelProductDto
     */
    public StoreOfferingChannelProductDto findLatestStoreOfferingChannelProduct(StoreFrontProductStatusDto request,int statusId)  {
        StoreProductEntity entity = storeProductRepository.findLatest(
                request.getStoreId(),
                request.getChannelId(),
                request.getOfferingId(),
                request.getProductId(),
                statusId);
        if(!ObjectUtils.isEmpty(entity)){
            StoreOfferingChannelProductDto  storeOfferingChannelProductDto = new StoreOfferingChannelProductDto();
            storeOfferingChannelProductDto.setRecordStatusId(request.getRecordStatus());
            storeOfferingChannelProductDto.setStoreId(entity.getStoreId());
            storeOfferingChannelProductDto.setProductId(entity.getProductId());
            storeOfferingChannelProductDto.setOfferingId(request.getOfferingId());
            storeOfferingChannelProductDto.setChannelId(request.getChannelId());
            return storeOfferingChannelProductDto;
        }else{
            return null;
        }
    }

    /**
     * This method findLatestStoreProduct
     *
     * @param request
     * @return StoreProductDto
     */
    public StoreProductDto findLatestStoreProduct(StoreFrontProductStatusDto request,int statusId) throws ParseException {
        return storeEntityMapper.storeProductEntityToStoreProductDto(storeProductRepository.findLatest(
                CatalogUtilities.getTimestampDate(request.getReleaseDate()),
                request.getStoreId(),
                request.getProductId(),
                statusId), new StoreProductDto());
    }






}
