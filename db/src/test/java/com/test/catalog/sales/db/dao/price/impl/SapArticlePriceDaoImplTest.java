package com.test.catalog.sales.db.dao.price.impl;

import com.test.catalog.sales.db.constant.RecordStatusEnum;
import com.test.catalog.sales.db.dao.store.StoreDao;
import com.test.catalog.sales.db.dto.PriceStatusHistoryDto;
import com.test.catalog.sales.db.dto.SapArticlePriceDto;
import com.test.catalog.sales.db.dto.wcm.StoreChannelOfferingEventWcm;
import com.test.catalog.sales.db.entity.price.PriceStatusHistoryEntity;
import com.test.catalog.sales.db.entity.price.SapArticlePriceEntity;
import com.test.catalog.sales.db.entity.store.StoreOfferingChannelProductEntity;
import com.test.catalog.sales.db.mapper.SapArticlePriceMapper;
import com.test.catalog.sales.db.repository.store.PriceStatusHistoryRepository;
import com.test.catalog.sales.db.repository.store.SapArticlePriceRepository;
import com.test.catalog.sales.db.repository.store.StoreOfferingChannelProductRepository;
import com.test.catalog.sales.db.util.CatalogUtilities;
import com.test.catalog.sales.exception.handler.SalesCatalogDBException;
import com.test.catalog.sales.exception.handler.SalesCatalogGenericException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class SapArticlePriceDaoImplTest {

    @Mock
    SapArticlePriceMapper sapArticlePriceMapper;
    @Mock
    SapArticlePriceRepository sapArticlePriceRepository;
    @Mock
    PriceStatusHistoryRepository priceStatusHistoryRepository;
    @Mock
    StoreOfferingChannelProductRepository storeOfferingChannelProductRepository;
    @Mock
    StoreDao storeDao;

    UUID productId = UUID.randomUUID();

    @InjectMocks
    SapArticlePriceDaoImpl sapArticlePriceDao;

    SapArticlePriceDto sapArticlePriceDto;
    SapArticlePriceEntity sapArticlePriceEntity;
    List<Integer> recordStatusIds = new ArrayList<>();
    List<StoreOfferingChannelProductEntity> storeOfferingChannelProductEntities;
    List<PriceStatusHistoryEntity> priceStatusHistoryEntityList;

    String itemSku="sku-1";


    @Before
    public void setUp() throws Exception {
        sapArticlePriceDto = new SapArticlePriceDto();
        sapArticlePriceDto.setStoreId("279");
        sapArticlePriceDto.setUnitOfMeasureId("UOM2");
        sapArticlePriceDto.setSapArticleId("mdm-p-56_sap_id");
        sapArticlePriceDto.setChannelId("kiosk");
        sapArticlePriceDto.setItemSku(itemSku);
        sapArticlePriceDto.setPrice(new BigDecimal(10.00));
        sapArticlePriceDto.setEffectiveStartDatetime(new Timestamp(1));
        sapArticlePriceDto.setEffectiveEndDatetime(new Timestamp(1));

        sapArticlePriceEntity = new SapArticlePriceEntity();
        sapArticlePriceEntity.setStoreId("279");
        sapArticlePriceEntity.setUnitOfMeasureId("UOM2");
        sapArticlePriceEntity.setSapArticleId("mdm-p-56_sap_id");
        sapArticlePriceEntity.setChannelId("kiosk");
        sapArticlePriceEntity.setPrice(10.00);
        sapArticlePriceEntity.setEffectiveStartDatetime(new Timestamp(1));
        sapArticlePriceEntity.setEffectiveEndDatetime(new Timestamp(1));
        recordStatusIds.add(RecordStatusEnum.ACTIVE.getStatusId());
        recordStatusIds.add(RecordStatusEnum.PUBLISHED.getStatusId());

        storeOfferingChannelProductEntities = new ArrayList<>();
        StoreOfferingChannelProductEntity storeOfferingChannelProductEntity = new StoreOfferingChannelProductEntity();
        storeOfferingChannelProductEntity.setStoreId("279");
        storeOfferingChannelProductEntity.setOfferingId("cateringlite");
        storeOfferingChannelProductEntity.setProductId(productId);
        storeOfferingChannelProductEntity.setChannelId("kiosk");
        //storeOfferingChannelProductEntity.setPrice(10.00);
        storeOfferingChannelProductEntities.add(storeOfferingChannelProductEntity);

        priceStatusHistoryEntityList = new ArrayList<>();
        PriceStatusHistoryEntity priceStatusHistoryEntity = new PriceStatusHistoryEntity();
        priceStatusHistoryEntity.setPrice(sapArticlePriceDto.getPrice());
        priceStatusHistoryEntity.setChannelId(storeOfferingChannelProductEntity.getChannelId());
        priceStatusHistoryEntity.setEffectiveEndDatetime(sapArticlePriceDto.getEffectiveEndDatetime());
        priceStatusHistoryEntity.setEffectiveStartDatetime(sapArticlePriceDto.getEffectiveStartDatetime());
        priceStatusHistoryEntity.setStoreId(storeOfferingChannelProductEntity.getStoreId());
        priceStatusHistoryEntity.setOfferingId(storeOfferingChannelProductEntity.getOfferingId());
        priceStatusHistoryEntity.setRecordStatusId(RecordStatusEnum.ACTIVE.getStatusId());
        priceStatusHistoryEntity.setProductId(storeOfferingChannelProductEntity.getProductId());
        priceStatusHistoryEntityList.add(priceStatusHistoryEntity);
    }

    @Test
    public void testCreateSapArticlePrice(){
        Mockito.when(sapArticlePriceMapper
                .sapArticlePriceDtoListToSapArticlePriceEntity(sapArticlePriceDto, new SapArticlePriceEntity())).thenReturn(sapArticlePriceEntity);
        Mockito.when(sapArticlePriceRepository
                .save(sapArticlePriceEntity)).thenReturn(sapArticlePriceEntity);
        Mockito.when(sapArticlePriceMapper.sapArticlePriceEntityToSapArticlePriceDto(sapArticlePriceEntity, new SapArticlePriceDto())).thenReturn(sapArticlePriceDto);
        Mockito.when(storeOfferingChannelProductRepository.findAllByStoreIdAndChannelIdAndSapArticleIdAndPublishAndActiveState(
                sapArticlePriceDto.getStoreId(), sapArticlePriceDto.getChannelId(),
                sapArticlePriceDto.getSapArticleId(),sapArticlePriceDto.getUnitOfMeasureId(),itemSku, recordStatusIds)).thenReturn(storeOfferingChannelProductEntities);
        SapArticlePriceDto result = sapArticlePriceDao.createSapArticlePrice(sapArticlePriceDto);
        Assert.assertNotNull(result);
        Assert.assertEquals("mdm-p-56_sap_id", result.getSapArticleId());
    }

    @Test(expected = SalesCatalogDBException.class)
    public void testStoreFrontSalesCatalogDBException(){
        Mockito.when(sapArticlePriceMapper
                .sapArticlePriceDtoListToSapArticlePriceEntity(sapArticlePriceDto, new SapArticlePriceEntity())).thenReturn(sapArticlePriceEntity);
        Mockito.when(sapArticlePriceRepository
                .save(sapArticlePriceEntity)).thenReturn(sapArticlePriceEntity);
        Mockito.when(sapArticlePriceMapper.sapArticlePriceEntityToSapArticlePriceDto(sapArticlePriceEntity, new SapArticlePriceDto())).thenReturn(sapArticlePriceDto);
        Mockito.when(storeOfferingChannelProductRepository.findAllByStoreIdAndChannelIdAndSapArticleIdAndPublishAndActiveState(
                sapArticlePriceDto.getStoreId(), sapArticlePriceDto.getChannelId(),
                sapArticlePriceDto.getSapArticleId(), sapArticlePriceDto.getUnitOfMeasureId(),itemSku, recordStatusIds)).thenThrow(new SalesCatalogDBException(""));
        sapArticlePriceDao.createSapArticlePrice(sapArticlePriceDto);
    }

    @Test(expected = SalesCatalogGenericException.class)
    public void testStoreFrontSalesCatalogGenericException(){
        Mockito.when(sapArticlePriceMapper
                .sapArticlePriceDtoListToSapArticlePriceEntity(sapArticlePriceDto, new SapArticlePriceEntity())).thenReturn(sapArticlePriceEntity);
        Mockito.when(sapArticlePriceRepository
                .save(sapArticlePriceEntity)).thenReturn(sapArticlePriceEntity);
        Mockito.when(sapArticlePriceMapper.sapArticlePriceEntityToSapArticlePriceDto(sapArticlePriceEntity, new SapArticlePriceDto())).thenReturn(sapArticlePriceDto);
        Mockito.when(storeOfferingChannelProductRepository.findAllByStoreIdAndChannelIdAndSapArticleIdAndPublishAndActiveState(
                sapArticlePriceDto.getStoreId(), sapArticlePriceDto.getChannelId(),
                sapArticlePriceDto.getSapArticleId(),sapArticlePriceDto.getUnitOfMeasureId(),itemSku, recordStatusIds)).thenThrow(new SalesCatalogGenericException(""));
        sapArticlePriceDao.createSapArticlePrice(sapArticlePriceDto);
    }

    @Test(expected = SalesCatalogGenericException.class)
    public void testSapArticlePriceSalesCatalogGenericException(){
        Mockito.when(sapArticlePriceMapper
                .sapArticlePriceDtoListToSapArticlePriceEntity(sapArticlePriceDto, new SapArticlePriceEntity())).thenReturn(sapArticlePriceEntity);
        Mockito.when(sapArticlePriceRepository
                .save(sapArticlePriceEntity)).thenThrow(new SalesCatalogGenericException(""));
        sapArticlePriceDao.createSapArticlePrice(sapArticlePriceDto);
    }

    @Test(expected = SalesCatalogDBException.class)
    public void testSapArticlePriceSalesCatalogDBException(){
        Mockito.when(sapArticlePriceMapper
                .sapArticlePriceDtoListToSapArticlePriceEntity(sapArticlePriceDto, new SapArticlePriceEntity())).thenReturn(sapArticlePriceEntity);
        Mockito.when(sapArticlePriceRepository
                .save(sapArticlePriceEntity)).thenThrow(new SalesCatalogDBException(""));
        sapArticlePriceDao.createSapArticlePrice(sapArticlePriceDto);
    }

    @Test
    public void testUpdatePriceStatusHistory(){
        Mockito.when(storeDao.createStoreChannelOfferingEventObject(any(String.class),any(String.class),any(String.class))).thenReturn(new StoreChannelOfferingEventWcm());

        PriceStatusHistoryDto dto = new PriceStatusHistoryDto();
        dto.setPrice(new BigDecimal(1.0));
        dto.setChannelId("channelId1");
        dto.setOfferingId("offeringId1");
        dto.setStoreId("storeId1");
        dto.setRecordStatusId(4);
        dto.setProductId(productId);
        try {
            dto.setEffectiveEndDatetime(CatalogUtilities.getTimestampDate("2020-10-11",
                    CatalogUtilities.DATE_FORMAT));
            dto.setEffectiveStartDatetime(CatalogUtilities.getTimestampDate("2020-10-9",
                    CatalogUtilities.DATE_FORMAT));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dto = sapArticlePriceDao.updatePricingHistory(dto);
        Assert.assertNotNull(dto);
        Assert.assertEquals(true, dto.isSuccess());
    }

   }
