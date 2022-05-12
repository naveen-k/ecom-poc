package com.test.catalog.sales.domain.helper;

import com.test.catalog.sales.client.request.product.LocationSearchCriteria;
import com.test.catalog.sales.client.request.store.OfferingsChannel;
import com.test.catalog.sales.client.request.store.StoreChannel;
import com.test.catalog.sales.client.request.store.StoreLocationRequest;
import com.test.catalog.sales.client.response.product.*;
import com.test.catalog.sales.client.response.store.StoreLocationDetail;
import com.test.catalog.sales.db.dto.SapArticlePriceDto;
import com.test.catalog.sales.db.dto.StoreDto;
import com.test.catalog.sales.db.dto.StoreOfferingChannelDto;
import com.test.catalog.sales.db.dto.StoreOfferingChannelProductDto;
import com.test.catalog.sales.db.util.CatalogUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StoreServiceHelper {
    private static final Logger LOG = LoggerFactory.getLogger(StoreServiceHelper.class);
    private static final String DEFAULT_CURRENCY_CODE="USD";

    /**
     * This method builds response format for product store-offering and respective channels
     *
     * @param storeOfferingResponses
     * @param locationSearchCriteria
     * @return List<StoreOfferingChannelDto>
     */
    public ProductStoreDetail buildResponse(
            List<StoreOfferingResponse> storeOfferingResponses,
            LocationSearchCriteria locationSearchCriteria) {
        LOG.debug(
                " StoreServiceHelper.buildResponse  called for productId {}",
                locationSearchCriteria.getProductId());
        ProductStoreDetail productStoreDetail = new ProductStoreDetail();
        productStoreDetail.setLocations(storeOfferingResponses);
        productStoreDetail.setProductId(locationSearchCriteria.getProductId());
        productStoreDetail.setReleaseDate(locationSearchCriteria.getReleasteDate());
        return productStoreDetail;
    }

    /**
     * This method builds response format for stores, offerings and channels
     *
     * @param storeOfferingChannelProductDtos
     * @return List<StoreOfferingResponse>
     */
    public List<StoreOfferingResponse> buildProductStoreOfferingList(
            List<StoreOfferingChannelProductDto> storeOfferingChannelProductDtos) {
        LOG.debug(" StoreServiceHelper.buildStoreOfferingList  called");
        StoreOfferingResponse storeOfferingResponse = null;
        List<StoreOfferingResponse> storeOfferingResponseList = null;

        List<StoreOfferingsChannelResponse> storeOfferingsChannelResponsesList = null;


        Set<String> storeIds =
                storeOfferingChannelProductDtos.stream().map(p -> p.getStoreId()).collect(Collectors.toSet());
        if (!ObjectUtils.isEmpty(storeIds)) {
            storeOfferingResponseList = new ArrayList<>();
            for (String storeId : storeIds) {
                storeOfferingResponse = new StoreOfferingResponse();
                storeOfferingResponse.setStoreId(storeId);
                storeOfferingsChannelResponsesList = setOfferings(storeId,storeOfferingChannelProductDtos,storeOfferingsChannelResponsesList);
                storeOfferingResponse.setOfferingList(storeOfferingsChannelResponsesList);
                storeOfferingResponseList.add(storeOfferingResponse);
            }
        }
        return storeOfferingResponseList;
    }


    /**
     * This method builds response format for stores, offerings and channels
     *
     * @param storeOfferingChannelProductDto
     * @param priceDtos
     * @return StoreFrontProduct
     */
    public StoreFrontProduct buildStorefrontProduct(StoreOfferingChannelProductDto storeOfferingChannelProductDto, List<SapArticlePriceDto> priceDtos) {
        LOG.debug(" StoreServiceHelper.buildStorefrontProductsResponse  called {}", storeOfferingChannelProductDto);
        StoreFrontProduct storeFrontProduct = new StoreFrontProduct();
        storeFrontProduct.setProductId(storeOfferingChannelProductDto.getProductId());
        storeFrontProduct.setStoreId(storeOfferingChannelProductDto.getStoreId());
        storeFrontProduct.setReleaseDate(storeOfferingChannelProductDto.getReleaseDate());
        storeFrontProduct.setOfferingId(storeOfferingChannelProductDto.getOfferingId());
        storeFrontProduct.setSkuType(storeOfferingChannelProductDto.getSkuType());
        storeFrontProduct.setItemSku(storeOfferingChannelProductDto.getItemSku());
        storeFrontProduct.setChannelId(storeOfferingChannelProductDto.getChannelId());
        if (!ObjectUtils.isEmpty(priceDtos)) {
           List<StoreFrontProductPrice> priceList = new ArrayList<>();
            priceDtos.stream().forEach(sapArticlePriceDto -> {
                priceList.add(new StoreFrontProductPrice(sapArticlePriceDto.getPrice(),
                        CatalogUtilities.getDateStr(sapArticlePriceDto.getEffectiveStartDatetime()),
                        CatalogUtilities.getDateStr(sapArticlePriceDto.getEffectiveEndDatetime()),
                        DEFAULT_CURRENCY_CODE));
            });
            storeFrontProduct.setPriceList(priceList);
        }

        return storeFrontProduct;
    }

    /**
     * This method builds response format for stores, offerings and channels
     *
     * @param storeOfferingChannelProductDtos
     * @return StoreFrontProductResponse
     */
    public StoreFrontProductResponse buildStorefrontProductsResponse(
            List<StoreOfferingChannelProductDto> storeOfferingChannelProductDtos) {
        LOG.debug(" StoreServiceHelper.buildStorefrontProductsResponse  called {}",storeOfferingChannelProductDtos);
        StoreFrontProductResponse response = new StoreFrontProductResponse();
        List<StoreFrontProduct> storeFrontProducts = new ArrayList<>();
        storeOfferingChannelProductDtos.stream().forEach(storeOfferingChannelProductDto -> {
            StoreFrontProduct storeFrontProduct = new StoreFrontProduct();
            storeFrontProduct.setProductId(storeOfferingChannelProductDto.getProductId());
            storeFrontProduct.setStoreId(storeOfferingChannelProductDto.getStoreId());
            storeFrontProduct.setOfferingId(storeOfferingChannelProductDto.getOfferingId());
            storeFrontProduct.setChannelId(storeOfferingChannelProductDto.getChannelId());
            List<StoreFrontProductPrice> priceList = new ArrayList<>();
            //priceList.add(new StoreFrontProductPrice(storeOfferingChannelProductDto.getPrice(),"","",DEFAULT_CURRENCY_CODE));
            storeFrontProduct.setPriceList(priceList);
            storeFrontProducts.add(storeFrontProduct);
        });
        response.setStoreFrontProductList(storeFrontProducts);
        return response;
    }



    private  List<StoreOfferingsChannelResponse>  setOfferings( String storeId,List<StoreOfferingChannelProductDto> storeOfferingChannelProductDtos,List<StoreOfferingsChannelResponse> storeOfferingsChannelResponsesList){
        StoreOfferingsChannelResponse storeOfferingsChannelResponse = null;
        List<OfferingChannelResponse> offeringChannelResponseList = null;
        OfferingChannelResponse offeringChannelResponse = null;
        Set<String> offeringIds =
                storeOfferingChannelProductDtos.stream().filter(obj -> storeId.equals(obj.getStoreId())).map(p -> p.getOfferingId()).collect(Collectors.toSet());
        if (!ObjectUtils.isEmpty(offeringIds)) {
            storeOfferingsChannelResponsesList = new ArrayList<>();
            for (String offeringId : offeringIds) {
                storeOfferingsChannelResponse = new StoreOfferingsChannelResponse();
                storeOfferingsChannelResponse.setOfferingId(offeringId);
                List<StoreOfferingChannelProductDto> offeringChannelProductDtos =
                        storeOfferingChannelProductDtos.stream().filter(obj -> offeringId.equals(obj.getOfferingId()) && storeId.equals(obj.getStoreId())).collect(Collectors.toList());
                if (!ObjectUtils.isEmpty(offeringChannelProductDtos)) {
                    offeringChannelResponseList = new ArrayList<>();
                    for (StoreOfferingChannelProductDto storeOfferingChannelProductDto : offeringChannelProductDtos) {
                        offeringChannelResponse = new OfferingChannelResponse();
                        offeringChannelResponse.setChannelId(storeOfferingChannelProductDto.getChannelId());
                        offeringChannelResponseList.add(offeringChannelResponse);
                    }
                }
                storeOfferingsChannelResponse.setSalesChannelList(offeringChannelResponseList);
                storeOfferingsChannelResponsesList.add(storeOfferingsChannelResponse);
            }
        }
        return storeOfferingsChannelResponsesList;
    }

    /**
     * This maps fields from storeDto and StoreOfferingChannelDto to StoreLocationDetail
     *
     * @param storeOfferingChannelDtos
     * @param storeDto
     * @return StoreLocationDetail
     */
    public StoreLocationDetail mapToStoreLocationDetail(StoreDto storeDto, List<StoreOfferingChannelDto> storeOfferingChannelDtos) {
        LOG.debug(" StoreLocationServiceImpl.mapToStoreLocationDetail  called");
        //TODO: Exception Handling using new Exception FrameWork
        StoreLocationDetail storeLocationDetail = new StoreLocationDetail();
        storeLocationDetail.setStoreId(storeDto.getStoreId());
        storeLocationDetail.setStorename(storeDto.getStoreName());
        storeLocationDetail.setRecordStatusId(storeDto.getRecordStatusId());
        List<StoreChannel> storeChannels = null;
        Set<String> offeringIds =
                storeOfferingChannelDtos.stream().map(p -> p.getOfferingId()).collect(Collectors.toSet());
        List<OfferingsChannel> offeringsChannels = new ArrayList<>();
        for (String offeringId : offeringIds) {
            OfferingsChannel offeringsChannel = new OfferingsChannel();
            offeringsChannel.setOfferingId(offeringId);
            storeChannels = new ArrayList<>();
            for (StoreOfferingChannelDto storeOfferingChannelDto : storeOfferingChannelDtos) {
                if (offeringId.equals(storeOfferingChannelDto.getOfferingId())) {
                    StoreChannel storeChannel = new StoreChannel();
                    storeChannel.setChannelId(storeOfferingChannelDto.getChannelId());
                    storeChannels.add(storeChannel);
                }
                offeringsChannel.setSalesChannelList(storeChannels);
            }
            offeringsChannels.add(offeringsChannel);
        }
        storeLocationDetail.setOfferings(offeringsChannels);
        return storeLocationDetail;
    }

    /**
     * This maps fields from storeLocationRequest to StoreOfferingChannelDto List
     *
     * @param storeLocationRequest
     * @return List<StoreOfferingChannelDto>
     */
    public List<StoreOfferingChannelDto> mapRequestToStoreOfferingChannelDto(StoreLocationRequest storeLocationRequest) {
        LOG.debug(" StoreLocationServiceImpl.mapRequestToStoreOfferingChannelDto  called");
        List<StoreOfferingChannelDto> storeOfferingChannelDtos = new ArrayList<>();
        //TODO: Exception Handling using new Exception FrameWork
        if (!ObjectUtils.isEmpty(storeLocationRequest)) {
            for (OfferingsChannel offeringsChannel : storeLocationRequest.getOfferings()) {
                for (StoreChannel storeChannel : offeringsChannel.getSalesChannelList()) {
                    StoreOfferingChannelDto storeOfferingChannelDto = new StoreOfferingChannelDto();
                    storeOfferingChannelDto.setStoreId(storeLocationRequest.getStoreId());
                    storeOfferingChannelDto.setOfferingId(offeringsChannel.getOfferingId());
                    storeOfferingChannelDto.setChannelId(storeChannel.getChannelId());
                    storeOfferingChannelDto.setRecordStatusId(storeLocationRequest.getRecordStatusId());
                    storeOfferingChannelDto.setCreateDatetime(Timestamp.from(Instant.now()));
                    storeOfferingChannelDtos.add(storeOfferingChannelDto);
                }
            }
        }
        return storeOfferingChannelDtos;
    }
}
