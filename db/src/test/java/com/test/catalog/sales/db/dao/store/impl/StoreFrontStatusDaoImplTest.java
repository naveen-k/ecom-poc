package com.test.catalog.sales.db.dao.store.impl;

import com.test.catalog.sales.db.constant.RecordStatusEnum;
import com.test.catalog.sales.db.dto.*;
import com.test.catalog.sales.db.entity.price.SapArticlePriceEntity;
import com.test.catalog.sales.db.entity.product.StoreProductEntity;
import com.test.catalog.sales.db.mapper.StoreEntityMapper;
import com.test.catalog.sales.db.repository.store.PriceStatusHistoryRepository;
import com.test.catalog.sales.db.repository.store.SapArticlePriceRepository;
import com.test.catalog.sales.db.repository.store.StoreOfferingChannelProductRepository;
import com.test.catalog.sales.db.repository.store.StoreProductRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class StoreFrontStatusDaoImplTest {
    @InjectMocks
    StoreFrontStatusDaoImpl storeFrontStatusDao;
    StoreFrontProductStatusDto storeFrontProductStatusDto;
    StoreOfferingChannelProductDto storeOfferingChannelProductDto;
    StoreProductDto storeProductDto;
    PriceStatusHistoryDto priceStatusHistoryDto;
    @Mock
    private StoreOfferingChannelProductRepository storeOfferingChannelProductRepository;
    @Mock
    private PriceStatusHistoryRepository priceStatusHistoryRepository;
    @Mock
    private SapArticlePriceRepository sapArticlePriceRepository;
    @Mock
    private StoreProductRepository storeProductRepository;
    @Mock
    private StoreEntityMapper storeEntityMapper;
    List<StoreFrontProductDto> prices;

    @Before
    public void setUp() throws Exception {
       UUID productId  = UUID.randomUUID();
        prices = new ArrayList<>();
        storeFrontProductStatusDto = new StoreFrontProductStatusDto();
        storeFrontProductStatusDto.setProductId(productId);
        storeFrontProductStatusDto.setStoreId("279");
        storeFrontProductStatusDto.setChannelId("web");
        storeFrontProductStatusDto.setRecordStatus(RecordStatusEnum.PUBLISHED.getStatusId());
        storeFrontProductStatusDto.setOfferingId("cateringlite");
        storeFrontProductStatusDto.setReleaseDate("2020-07-11 12:30:00-0000");
        prices.add(new StoreFrontProductDto(3, "2020-07-11 12:30:00-0000","2020-07-11 12:30:00-0000", "USD"));
        storeFrontProductStatusDto.setPrices(prices);

        storeOfferingChannelProductDto = new StoreOfferingChannelProductDto();
        storeOfferingChannelProductDto.setProductId(productId);
        storeOfferingChannelProductDto.setStoreId("279");
        storeOfferingChannelProductDto.setChannelId("web");
        storeOfferingChannelProductDto.setOfferingId("cateringlite");


        storeProductDto = new StoreProductDto();
        storeProductDto.setProductId(productId);
        storeProductDto.setStoreId("279");

        priceStatusHistoryDto = new PriceStatusHistoryDto();
        priceStatusHistoryDto.setProductId(productId);
        priceStatusHistoryDto.setStoreId("279");
        priceStatusHistoryDto.setChannelId("web");
        priceStatusHistoryDto.setOfferingId("cateringlite");
    }

    @Test
    public void updateStoreFrontStatus() throws ParseException {
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse("2020-07-11 12:30:00-0000");
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2020-07-11 12:30:00-0000");

        SapArticlePriceEntity entity = new SapArticlePriceEntity();
        entity.setEffectiveEndDatetime(new Timestamp(startDate.getTime()));
        entity.setEffectiveEndDatetime(new Timestamp(endDate.getTime()));
        entity.setPrice(3.0);
        
         /*Dennis Mathew: These 2 mocks were unnecessary hence was commented, One gets a unnecessary stubbing  error if one runs them.
        Mockito.when(storeFrontStatusDao.findLatestStoreProduct(storeFrontProductStatusDto))
                .thenReturn(storeProductDto);

        Mockito.when(sapArticlePriceRepository.findByProductIdnAndStoreIdAndChannelIdAndDates(storeFrontProductStatusDto.getProductId(),
                storeFrontProductStatusDto.getStoreId(),
                storeFrontProductStatusDto.getChannelId(),
                startDate,
                endDate)).thenReturn(entity);
        */
        StoreFrontProductStatusDto rtn = storeFrontStatusDao.updateStoreFrontStatus(storeFrontProductStatusDto);
        Assert.assertNotNull(rtn);
       // Assert.assertEquals(rtn.getProductId(), "mdm-p-52");
    }
    @Test
    public void updateStoreFrontStatusFail() throws ParseException {
        StoreProductEntity entity = new StoreProductEntity();
        entity.setStoreId("279");
        entity.setProductId(UUID.randomUUID());
        Mockito.lenient().when(storeProductRepository.findLatest("279","web","cateringlite",UUID.randomUUID(),2))
                .thenReturn(entity);
        Mockito.lenient().when(storeFrontStatusDao.findLatestStoreOfferingChannelProduct(storeFrontProductStatusDto,RecordStatusEnum.ACTIVE.getStatusId()))
                .thenReturn(null);
        StoreFrontProductStatusDto rtn = storeFrontStatusDao.updateStoreFrontStatus(new StoreFrontProductStatusDto());
        Assert.assertNotNull(rtn);
        Assert.assertEquals(rtn.isSuccess(),Boolean.FALSE);
    }
    @Test
    public void updateStoreFrontStatusException() throws ParseException {
        StoreProductEntity entity = new StoreProductEntity();
        entity.setStoreId("279");
        entity.setProductId(UUID.randomUUID());
        Mockito.lenient().when(storeProductRepository.findLatest("279","web","cateringlite",UUID.randomUUID(),2))
                .thenReturn(entity);
        Mockito.lenient().when(storeFrontStatusDao.findLatestStoreOfferingChannelProduct(storeFrontProductStatusDto,RecordStatusEnum.ACTIVE.getStatusId()))
                .thenThrow(new RuntimeException());
        StoreFrontProductStatusDto rtn = storeFrontStatusDao.updateStoreFrontStatus(new StoreFrontProductStatusDto());
        Assert.assertNotNull(rtn);
        Assert.assertEquals(rtn.isSuccess(),Boolean.FALSE);
    }
}