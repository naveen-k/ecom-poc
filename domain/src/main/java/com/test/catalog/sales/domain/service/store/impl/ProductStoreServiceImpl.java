package com.test.catalog.sales.domain.service.store.impl;

import com.test.catalog.sales.client.request.product.LocationSearchCriteria;
import com.test.catalog.sales.client.response.product.*;
import com.test.catalog.sales.db.constant.RecordStatusEnum;
import com.test.catalog.sales.db.dao.price.SapArticlePriceDao;
import com.test.catalog.sales.db.dao.store.StoreDao;
import com.test.catalog.sales.db.dto.SapArticlePriceDto;
import com.test.catalog.sales.db.dto.StoreOfferingChannelProductDto;
import com.test.catalog.sales.domain.helper.StoreServiceHelper;
import com.test.catalog.sales.domain.service.store.ProductStoreService;
import com.test.catalog.sales.exception.handler.NoContentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductStoreServiceImpl implements ProductStoreService {
    private static final Logger LOG = LoggerFactory.getLogger(ProductStoreServiceImpl.class);
    private final static String NO_PRODUCT_MSG = "No product stores found for release date ";
    @PersistenceContext
    private final EntityManager entityManager;
    private StoreServiceHelper storeServiceHelper;
    private StoreDao storeDao;
    private SapArticlePriceDao sapArticlePriceDao;

    @Autowired
    public ProductStoreServiceImpl(StoreDao storeDao, StoreServiceHelper storeServiceHelper, SapArticlePriceDao sapArticlePriceDao, EntityManager entityManager) {
        this.storeDao = storeDao;
        this.storeServiceHelper = storeServiceHelper;
        this.sapArticlePriceDao = sapArticlePriceDao;
        this.entityManager = entityManager;
    }

    /**
     * This method calls storeDao to get Store on basis locationSearchCriteria
     *
     * @param locationSearchCriteria
     * @return List<StoreOfferingChannelDto>
     */
    @Override
    public ProductStoreResponse getStores(LocationSearchCriteria locationSearchCriteria) {
        LOG.debug(
                " StoreServiceImpl.getStores  called for productId{}",
                locationSearchCriteria.getProductId());
        List<StoreOfferingResponse> storeOfferingResponses = null;
        ProductStoreDetail productStoreDetail = null;
        List<StoreOfferingChannelProductDto> storeOfferingChannelProductDtos = null;
        storeOfferingChannelProductDtos = storeDao.getStoreOfferingsChannelByProductId(
                locationSearchCriteria.getProductId(),
                locationSearchCriteria.getReleasteDate(),
                locationSearchCriteria.getSubRecordStatusId());
        if (!ObjectUtils.isEmpty(storeOfferingChannelProductDtos)) {
            storeOfferingResponses = storeServiceHelper.buildProductStoreOfferingList(storeOfferingChannelProductDtos);
            productStoreDetail = storeServiceHelper.buildResponse(storeOfferingResponses, locationSearchCriteria);
        } else {
            throw new NoContentException(NO_PRODUCT_MSG + locationSearchCriteria.getReleasteDate());
        }
        return new ProductStoreResponse(productStoreDetail);
    }

    @Override
    public StoreFrontProductResponse getStorefrontProducts(Date releasedDate, List<Integer> subRecordStatusId, Integer recordsSize) {
        LOG.debug("StoreServiceImpl.getStorefrontProducts  called for releasedDate {} subRecordStatusId {}", releasedDate,subRecordStatusId);
        StoreFrontProductResponse storeOfferingResponses = null;
        List<StoreFrontProduct> storeFrontProducts = new ArrayList<>();
        List<StoreOfferingChannelProductDto> storeOfferingChannelProductDtos = storeDao.getStoreOfferingsChannelByReleaseDate(releasedDate, subRecordStatusId,recordsSize);
        if (!ObjectUtils.isEmpty(storeOfferingChannelProductDtos)) {
            storeOfferingResponses = new StoreFrontProductResponse();
            storeOfferingChannelProductDtos.stream().forEach(storeOfferingChannelProductDto -> {
                List<Integer> nextRecordId = subRecordStatusId.stream().map(integer -> integer+2).collect(Collectors.toList());
                List<SapArticlePriceDto> priceDtos = sapArticlePriceDao.getStorefrontProductPrice(storeOfferingChannelProductDto.getProductId(), storeOfferingChannelProductDto.getStoreId(), storeOfferingChannelProductDto.getChannelId(),storeOfferingChannelProductDto.getOfferingId(),storeOfferingChannelProductDto.getItemSku(),nextRecordId);
                if(!ObjectUtils.isEmpty(priceDtos)){
                    storeFrontProducts.add(storeServiceHelper.buildStorefrontProduct(storeOfferingChannelProductDto, priceDtos));
                }
            });
            if (ObjectUtils.isEmpty(storeFrontProducts)) {
                throw new NoContentException(NO_PRODUCT_MSG + releasedDate);
            }
            storeOfferingResponses.setStoreFrontProductList(storeFrontProducts);
        } else {
            throw new NoContentException(NO_PRODUCT_MSG + releasedDate);
        }
        return storeOfferingResponses;
    }

    @Override
    public StoreFrontProductResponse getStorePublishedProducts(String storeId, String articleId, String uom) {
        LOG.debug("StoreServiceImpl.getStorePublishedProducts  called for  storeId{}, articleId {} uom{}", storeId, articleId ,uom);
        StoreFrontProductResponse storeOfferingResponses = null;
        List<StoreFrontProduct> storeFrontProducts = new ArrayList<>();

        List<StoreOfferingChannelProductDto> storeOfferingChannelProductDtos = storeDao.getStoreOfferingsChannelBySapArticleIdAndStoreId(storeId, articleId, uom, RecordStatusEnum.PUBLISHED.getStatusId());
        if (!ObjectUtils.isEmpty(storeOfferingChannelProductDtos)) {
            storeOfferingResponses = new StoreFrontProductResponse();
            storeOfferingChannelProductDtos.stream().forEach(storeOfferingChannelProductDto -> {
                storeFrontProducts.add(storeServiceHelper.buildStorefrontProduct(storeOfferingChannelProductDto, null));
            });
            storeOfferingResponses.setStoreFrontProductList(storeFrontProducts);
        } else {
            throw new NoContentException("No Products found for sapArticleId= " + articleId + "storeId= " + storeId);
        }
        return storeOfferingResponses;
    }
}
