package com.test.catalog.sales.domain.helper;

import com.test.catalog.sales.client.request.product.LocationSearchCriteria;
import com.test.catalog.sales.client.request.store.OfferingsChannel;
import com.test.catalog.sales.client.request.store.StoreChannel;
import com.test.catalog.sales.client.request.store.StoreLocationRequest;
import com.test.catalog.sales.client.response.product.ProductStoreDetail;
import com.test.catalog.sales.client.response.product.StoreFrontProduct;
import com.test.catalog.sales.client.response.product.StoreFrontProductResponse;
import com.test.catalog.sales.client.response.product.StoreOfferingResponse;
import com.test.catalog.sales.client.response.store.StoreLocationDetail;
import com.test.catalog.sales.db.dto.SapArticlePriceDto;
import com.test.catalog.sales.db.dto.StoreDto;
import com.test.catalog.sales.db.dto.StoreOfferingChannelDto;
import com.test.catalog.sales.db.dto.StoreOfferingChannelProductDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class StoreServiceHelperTest {

    @InjectMocks
    StoreServiceHelper storeServiceHelper;

    @Before
    public void setUp(){

    }

    @Test
    public void buildResponseTest(){

        List<StoreOfferingResponse> storeOfferingResponses = new ArrayList<>();
        LocationSearchCriteria locationSearchCriteria = new LocationSearchCriteria();
        locationSearchCriteria.setProductId(UUID.randomUUID());
        ProductStoreDetail productStoreDetail = storeServiceHelper.buildResponse(storeOfferingResponses, locationSearchCriteria);
        Assert.assertEquals(productStoreDetail.getProductId(),"12345");
    }

    @Test
    public void buildProductStoreOfferingListTest(){

        StoreOfferingChannelProductDto storeOfferingChannelProductDto = new StoreOfferingChannelProductDto();
        storeOfferingChannelProductDto.setStoreId("12345");
        storeOfferingChannelProductDto.setOfferingId("999999999");
        List< StoreOfferingChannelProductDto > storeOfferingChannelProductDtos = new ArrayList<>();
        storeOfferingChannelProductDtos.add(storeOfferingChannelProductDto);

        List<StoreOfferingResponse>  storeOfferingResponseList = storeServiceHelper.buildProductStoreOfferingList(storeOfferingChannelProductDtos);
        Assert.assertEquals(storeOfferingResponseList.get(0).getStoreId(),"12345");
    }

    @Test
    public void buildStorefrontProductTest(){
        List< SapArticlePriceDto > priceDtos = new ArrayList<>();
        SapArticlePriceDto sapArticlePriceDto = new SapArticlePriceDto();
        sapArticlePriceDto.setPrice(new BigDecimal(10));
        sapArticlePriceDto.setEffectiveStartDatetime( new Timestamp(new Date().getTime()));
        sapArticlePriceDto.setEffectiveEndDatetime(new Timestamp(new Date().getTime()));
        priceDtos.add(sapArticlePriceDto);
        StoreOfferingChannelProductDto storeOfferingChannelProductDto = new StoreOfferingChannelProductDto();
        storeOfferingChannelProductDto.setChannelId("channal_id");
        StoreFrontProduct storeFrontProduct= storeServiceHelper.buildStorefrontProduct( storeOfferingChannelProductDto,  priceDtos);
        Assert.assertEquals(storeFrontProduct.getChannelId(),"channal_id");

    }

    @Test
    public void buildStorefrontProductsResponseTest(){
        StoreOfferingChannelProductDto storeOfferingChannelProductDto =  new StoreOfferingChannelProductDto();
        storeOfferingChannelProductDto.setStoreId("123456");
        List<StoreOfferingChannelProductDto> storeOfferingChannelProductDtos = new ArrayList<>();
        storeOfferingChannelProductDtos.add(storeOfferingChannelProductDto);
        StoreFrontProductResponse storeFrontProductResponse = storeServiceHelper.buildStorefrontProductsResponse(storeOfferingChannelProductDtos);
        Assert.assertEquals(storeFrontProductResponse.getStoreFrontProductList().get(0).getStoreId(),"123456");
    }
    @Test
    public void setOfferingsTest(){

    }

    @Test
    public void mapToStoreLocationDetailTest(){
        StoreDto storeDto = new StoreDto();
        storeDto.setStoreId("12345");
        StoreOfferingChannelDto storeOfferingChannelDto = new StoreOfferingChannelDto();
        storeOfferingChannelDto.setStoreId("12345");
        storeOfferingChannelDto.setOfferingId("zzzzzzzzzzzzzz");
        List< StoreOfferingChannelDto > storeOfferingChannelDtos = new ArrayList<>();
        storeOfferingChannelDtos.add(storeOfferingChannelDto);
        StoreLocationDetail storeLocationDetail = storeServiceHelper.mapToStoreLocationDetail(storeDto, storeOfferingChannelDtos);
        Assert.assertEquals(storeLocationDetail.getStoreId(),"12345");
    }
    @Test
    public void mapRequestToStoreOfferingChannelDtoTest(){
        StoreLocationRequest storeLocationRequest = new StoreLocationRequest();
        storeLocationRequest.setStoreId("12345");
        OfferingsChannel offeringsChannel = new OfferingsChannel();
        offeringsChannel.setOfferingId("offfff");
        ArrayList offeringsChannelList = new ArrayList();
        StoreChannel storeChannel= new StoreChannel();
        storeChannel.setChannelId("Test");
        List<StoreChannel> salesChannelList = new ArrayList<>();
        salesChannelList.add(storeChannel);
        offeringsChannel.setSalesChannelList(salesChannelList);
        offeringsChannelList.add(offeringsChannel);

        storeLocationRequest.setOfferings(offeringsChannelList);

        List<StoreOfferingChannelDto> storeOfferingChannelDtoList =storeServiceHelper.mapRequestToStoreOfferingChannelDto( storeLocationRequest);
        Assert.assertEquals(storeOfferingChannelDtoList.get(0).getStoreId(),"12345");
    }
}