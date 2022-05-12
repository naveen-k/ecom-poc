package com.test.catalog.sales.db;

import com.test.catalog.sales.db.constant.RecordStatusEnum;
import com.test.catalog.sales.db.dao.store.impl.StoreDaoImpl;
import com.test.catalog.sales.db.dto.*;
import com.test.catalog.sales.db.repository.store.*;
import com.test.catalog.sales.db.dto.wcm.StoreChannelOfferingEventWcm;
import com.test.catalog.sales.db.entity.price.PriceStatusHistoryEntity;
import com.test.catalog.sales.db.entity.product.StoreProductEntity;
import com.test.catalog.sales.db.entity.store.StoreComponentEntity;
import com.test.catalog.sales.db.entity.store.StoreEntity;
import com.test.catalog.sales.db.entity.store.StoreOfferingChannelEntity;
import com.test.catalog.sales.db.entity.store.StoreOfferingChannelProductEntity;
import com.test.catalog.sales.db.mapper.StoreEntityMapper;
import com.test.catalog.sales.db.repository.product.ProductCategoryRepository;
import com.test.catalog.sales.db.util.CatalogUtilities;
import com.test.catalog.sales.exception.handler.NoContentException;
import com.test.catalog.sales.exception.handler.SalesCatalogDBException;
import com.test.catalog.sales.exception.handler.SalesCatalogGenericException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class StoreDaoImplTest {

    @InjectMocks
    private StoreDaoImpl storeDao;

    @Mock
    private StoreOfferingChannelProductRepository storeOfferingChannelProductRepository;

    @Mock
    StoreRepository storeRepository;

    @Mock
    StoreComponentRepository storeComponentRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    StoreOfferingChannelRepository storeOfferingChannelRepository;

    @Mock
    private StoreEntityMapper storeEntityMapper;

    @Mock
    private StoreDaoHelper storeDaoHelper;

    @Mock
    PriceStatusHistoryRepository priceStatusHistoryRepository;

    @Mock
    StoreProductRepository storeProductRepository;

    @Mock
    ProductCategoryRepository productCategoryRepository;

    private List<Integer> recordStatusIds = new ArrayList<Integer>(Arrays.asList(2, 6));
    private List<StoreProductEntity> storeProductEntities = new ArrayList<>();
    private List<StoreOfferingChannelEntity> storeOfferingChannelEntities = new ArrayList<>();
    private List<StoreProductDto> storeProductDtos = new ArrayList<>();
    private List<StoreOfferingChannelDto> storeOfferingChannelDtos = new ArrayList<>();
    private List<StoreOfferingChannelProductDto> storeOfferingChannelProductDtos = new ArrayList<>();
    private List<StoreOfferingChannelProductEntity> storeOfferingChannelProductEntities = new ArrayList<>();
    private String offeringId = "CATLITE";
    private UUID productId = UUID.randomUUID();
    private String storeId = "store_5";
    private String storeId2 = "store_7";
    private String storeName = "store_5";
    private String sapArticleId = "sap-1234";
    private String channelId = "store";
    private UUID componentId = UUID.randomUUID();
    private String itemId = "mdm-item-4";
    private String itemSku = "sku-item-4";
    private String skuType = "upsell";
    private final Integer recordsSize=4;
    StoreEntity storeEntity = new StoreEntity();
    StoreDto storeDto = new StoreDto();
    StoreOfferingChannelEntity storeOfferingChannelEntity = new StoreOfferingChannelEntity();
    StoreOfferingChannelDto storeOfferingChannelDto = new StoreOfferingChannelDto();
    List<StoreComponentItem> storeComponentItems = new ArrayList<>();
    List<StoreOfferingChannelProductItem> storeOfferingChannelProductItems = new ArrayList<>();
    List<Integer> storeComponentKeys = new ArrayList<>();

    StoreOfferingChannelProductItem storeOfferingChannelProductItem = new StoreOfferingChannelProductItem(){

        @Override
        public String getStoreId() {
            return storeId;
        }

        @Override
        public String getOfferingId() {
            return offeringId;
        }

        @Override
        public String getChannelId() {
            return channelId;
        }

        @Override
        public String getProductId() {
            return productId.toString();
        }

        @Override
        public String getItemSku() {
            return itemSku;
        }

        @Override
        public String getSkuType() {
            return skuType;
        }

        @Override
        public Timestamp getReleaseDate() {
            return null;
        }
    };

    StoreComponentItem storeComponentItem = new StoreComponentItem() {
        @Override
        public String getStoreId() {
            return storeId;
        }

        @Override
        public String getComponentId() {
            return componentId.toString();
        }

        @Override
        public String getChannelId() {
            return channelId;
        }

        @Override
        public String getOfferingId() {
            return offeringId;
        }

        @Override
        public Timestamp getReleaseDate() {
            return Timestamp.valueOf("2020-01-11 12:30:00");
        }
    };

    StoreComponentDto storeComponentDto = new StoreComponentDto();
    StoreComponentEntity storeComponentEntity = new StoreComponentEntity();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setup() throws ParseException {
        storeOfferingChannelProductItems.add(storeOfferingChannelProductItem);
        storeComponentItems.add(storeComponentItem);
        StoreProductEntity storeProductEntity = new StoreProductEntity();
        StoreProductEntity storeProductEntity1 = new StoreProductEntity();
        storeProductEntity.setProductId(productId);
        storeProductEntity.setStoreId(storeId2);
        storeProductEntity1.setProductId(productId);
        storeProductEntity1.setStoreId(storeId);
        storeProductEntities.add(storeProductEntity);
        storeProductEntities.add(storeProductEntity1);

        StoreProductDto storeProductDto = new StoreProductDto();
        storeProductDto.setProductId(productId);
        storeProductDto.setStoreId(storeId2);
        StoreProductDto storeProductDto1 = new StoreProductDto();
        storeProductDto1.setProductId(productId);
        storeProductDto1.setStoreId(storeId);
        storeProductDtos.add(storeProductDto);
        storeProductDtos.add(storeProductDto1);

        StoreOfferingChannelProductDto storeOfferingChannelProductDto = new StoreOfferingChannelProductDto();
        storeOfferingChannelProductDto.setChannelId("WEB");
        storeOfferingChannelProductDto.setOfferingId(offeringId);
        storeOfferingChannelProductDto.setStoreId(storeId);
        storeOfferingChannelProductDto.setProductId(productId);
        storeOfferingChannelProductDtos.add(storeOfferingChannelProductDto);
        StoreOfferingChannelProductEntity storeOfferingChannelProductEntity = new StoreOfferingChannelProductEntity();
        storeOfferingChannelProductEntity.setChannelId("WEB");
        storeOfferingChannelProductEntity.setOfferingId(offeringId);
        storeOfferingChannelProductEntity.setStoreId(storeId);
        storeOfferingChannelProductEntity.setProductId(productId);
        storeOfferingChannelProductEntities.add(storeOfferingChannelProductEntity);


        storeOfferingChannelDto.setChannelId("WEB");
        storeOfferingChannelDto.setOfferingId(offeringId);
        storeOfferingChannelDto.setStoreId(storeId2);
        StoreOfferingChannelDto storeOfferingChannelDto1 = new StoreOfferingChannelDto();
        storeOfferingChannelDto1.setChannelId("MSITE");
        storeOfferingChannelDto1.setOfferingId("OFFFERING");
        storeOfferingChannelDto1.setStoreId(storeId);
        storeOfferingChannelDtos.add(storeOfferingChannelDto);
        storeOfferingChannelDtos.add(storeOfferingChannelDto1);


        storeOfferingChannelEntity.setChannelId("WEB");
        storeOfferingChannelEntity.setOfferingId(offeringId);
        storeOfferingChannelEntity.setStoreId(storeId2);
        StoreOfferingChannelEntity storeOfferingChannelEntity1 = new StoreOfferingChannelEntity();
        storeOfferingChannelEntity1.setChannelId("MSITE");
        storeOfferingChannelEntity1.setOfferingId("OFFFERING");
        storeOfferingChannelEntity1.setStoreId(storeId);
        storeOfferingChannelEntities.add(storeOfferingChannelEntity);
        storeOfferingChannelEntities.add(storeOfferingChannelEntity1);

        storeEntity.setStoreId(storeId);
        storeEntity.setStoreName(storeName);
        storeDto.setStoreId(storeId);
        storeDto.setStoreName(storeName);

        storeComponentDto.setChannelId("Channel1");
        storeComponentDto.setComponentId(componentId);
        storeComponentDto.setOfferingId("Offering1");
        storeComponentDto.setRecordStatusId(4);
        storeComponentDto.setStoreId("279");
        storeComponentDto.setReleaseDate("2020-07-11 12:30:00-0000");

        storeComponentEntity.setRecordStatusId(2);
        storeComponentEntity.setComponentId(storeComponentDto.getComponentId());
        storeComponentEntity.setReleaseDate(CatalogUtilities.getTimestampDate(storeComponentDto.getReleaseDate()));
        storeComponentEntity.setStoreId(storeComponentDto.getStoreId());
        storeComponentKeys.add(1);
        storeComponentKeys.add(4);
        storeComponentKeys.add(15);
        storeComponentKeys.add(18);
    }

    /**
     * test to validate get Store offerings with channel
     */
    @Test
    public void getStoreOfferingsChannelByProductIdSuccess() {
        Timestamp releaseDate = Timestamp.valueOf("2020-07-11 12:30:00");
        Mockito.when(
                storeOfferingChannelProductRepository.findAllByProductIdAndReleaseDateAndRecordStatusIdIn(productId,
                        releaseDate, recordStatusIds))
                .thenReturn(storeOfferingChannelProductEntities);
        Mockito.when(
                storeEntityMapper.storeOfferingChannelProductEntityListToDtoList(
                        storeOfferingChannelProductEntities, new ArrayList<>()))
                .thenReturn(storeOfferingChannelProductDtos);
        List<StoreOfferingChannelProductDto> response =
                storeDao.getStoreOfferingsChannelByProductId(productId, releaseDate, recordStatusIds);
        Assert.assertNotNull(response);
        Assert.assertEquals(offeringId, response.get(0).getOfferingId());
    }

    /**
     * test to validate get getStoreOfferingsChannelProduct details list
     */
    @Test(expected = NoContentException.class)
    public void getStoreOfferingsChannelByProductIdNoContent() {
        Timestamp releaseDate = Timestamp.valueOf("2020-01-11 12:30:00");
        Mockito.when(
                storeOfferingChannelProductRepository.findAllByProductIdAndReleaseDateAndRecordStatusIdIn(productId,
                        releaseDate, recordStatusIds))
                .thenReturn(null);

        storeDao.getStoreOfferingsChannelByProductId(productId, releaseDate, recordStatusIds);
    }

    /**
     * test to validate get getStoreOfferingsChannelProduct details list
     */
    @Test(expected = Exception.class)
    public void getStoreOfferingsChannelByProductIdException() {
        Timestamp releaseDate = Timestamp.valueOf("2020-01-11 12:30:00");
        Mockito.when(
                storeOfferingChannelProductRepository.findAllByProductIdAndReleaseDateAndRecordStatusIdIn(productId,
                        releaseDate, recordStatusIds)).thenThrow(new RuntimeException());

        storeDao.getStoreOfferingsChannelByProductId(productId, releaseDate, recordStatusIds);
    }

    /*
     *  Test to validate create store
     */
    @Test
    public void createStoreSuccess() {
        Mockito.when(storeRepository.save(storeEntity)).thenReturn(storeEntity);
        Mockito.when(storeEntityMapper.storeDtoToStoreEntity(storeDto, new StoreEntity())).thenReturn(storeEntity);
        Mockito.when(storeEntityMapper.storeEntityToStoreDto(storeEntity, new StoreDto())).thenReturn(storeDto);

        StoreDto response = storeDao.createStore(storeDto);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getStoreId());
    }

    /*
     *  Test to validate create storeOfferingChannel
     */
    @Test
    public void createStoreOfferingChannelSuccess() {
        Mockito.when(storeOfferingChannelRepository.saveAll(storeOfferingChannelEntities)).thenReturn(storeOfferingChannelEntities);
        Mockito.when(storeEntityMapper.storeOfferingChannelDtoListToStoreOfferingChannelEntityList(storeOfferingChannelDtos, new ArrayList<>())).thenReturn(storeOfferingChannelEntities);
        Mockito.when(storeEntityMapper.storeOfferingChannelEntityListToStoreOfferingChannelDtoList(storeOfferingChannelEntities, new ArrayList<>())).thenReturn(storeOfferingChannelDtos);

        List<StoreOfferingChannelDto> response = storeDao.createStoreOfferingChannel(storeOfferingChannelDtos);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.get(0).getStoreId());
    }

    /*
     *  Test to validate create storeOfferingChannel
     */
    @Test(expected = Exception.class)
    public void createStoreOfferingChannelException() {
        Mockito.when(storeOfferingChannelRepository.saveAll(storeOfferingChannelEntities)).thenThrow(new RuntimeException());
        Mockito.when(storeEntityMapper.storeOfferingChannelDtoListToStoreOfferingChannelEntityList(storeOfferingChannelDtos, new ArrayList<>())).thenReturn(storeOfferingChannelEntities);
        //Mockito.when(storeEntityMapper.storeOfferingChannelEntityListToStoreOfferingChannelDtoList(storeOfferingChannelEntities, new ArrayList<>())).thenReturn(storeOfferingChannelDtos);

        List<StoreOfferingChannelDto> response = storeDao.createStoreOfferingChannel(storeOfferingChannelDtos);

    }

    /**
     * test to validate get Store offerings with channel
     */
    @Test
    public void getStoreByStoreIDSuccess(){
        Mockito.when(storeRepository.findFirstByStoreIdOrderByCreateDatetimeDesc(storeId)).thenReturn(storeEntity);
        Mockito.when(storeEntityMapper.storeEntityToStoreDto(storeEntity, new StoreDto())).thenReturn(storeDto);

        StoreDto response = storeDao.getLatestStore(storeId);
        Assert.assertNotNull(response);
    }

    @Test
    public void getStoreEntityIsNull(){
        StoreEntity storeEntity = null;
        Mockito.when(storeRepository.findFirstByStoreIdOrderByCreateDatetimeDesc(storeId)).thenReturn(storeEntity);

        StoreDto response = storeDao.getLatestStore(storeId);
        Assert.assertNull(response);
    }

    /**
     * test to validate get Store offerings with channel
     */
    @Test
    public void getStoreOfferingsChannelSuccess(){
        Mockito.when(storeOfferingChannelRepository.findFirstByStoreIdAndOfferingIdAndChannelIdOrderByCreateDatetimeDesc(storeId, offeringId, "WEB")).thenReturn(storeOfferingChannelEntity);
        Mockito.when(storeEntityMapper.storeOfferingChannelEntityToStoreOfferingChannelDto(storeOfferingChannelEntity, new StoreOfferingChannelDto())).thenReturn(storeOfferingChannelDto);

        StoreOfferingChannelDto response = storeDao.getLatestStoreOfferingChannel(storeId, offeringId, "WEB");
        Assert.assertNotNull(response);
    }

    /**
     * test to validate exception thrown while create Store
     */
    @Test
    public void whenSalesCatalogDExceptionThrownSavingStore() {

        exceptionRule.expect(SalesCatalogDBException.class);
        Mockito.when(storeRepository.save(storeEntity)).thenThrow(new SalesCatalogDBException("DB error"));
        Mockito.when(storeEntityMapper.storeDtoToStoreEntity(storeDto, new StoreEntity())).thenReturn(storeEntity);
//        Mockito.when(storeEntityMapper.storeEntityToStoreDto(storeEntity, new StoreDto())).thenReturn(storeDto);
        StoreDto response = storeDao.createStore(storeDto);
    }

    /**
     * test to validate exception thrown while create Store
     */
    @Test
    public void whenSalesCatalogGenericExceptionThrownWhilesavingStore() {

        exceptionRule.expect(SalesCatalogGenericException.class);
        Mockito.when(storeEntityMapper.storeDtoToStoreEntity(storeDto, new StoreEntity())).thenThrow(new SalesCatalogGenericException("Mapping Exception"));
        StoreDto response = storeDao.createStore(storeDto);
    }

    /**
     * test to validate exception thrown while create Store
     */
    @Test
    public void whenExceptionThrownWhilesavingStore() {

        exceptionRule.expect(Exception.class);
        Mockito.when(storeEntityMapper.storeDtoToStoreEntity(storeDto, new StoreEntity())).thenThrow(new Exception("Exception occurred"));
        StoreDto response = storeDao.createStore(storeDto);
    }

    /**
     * test to validate exception thrown while fetching Store
     */
    @Test
    public void whenExceptionThrownfetchStoreByStoreID(){
        exceptionRule.expect(SalesCatalogGenericException.class);
        Mockito.when(storeRepository.findFirstByStoreIdOrderByCreateDatetimeDesc(storeId)).thenThrow(new SalesCatalogGenericException("DB error"));

        StoreDto response = storeDao.getLatestStore(storeId);
    }

    /**
     * test to validate exception thrown while fetching Store
     */
    @Test
    public void whenStoreIsfetched(){
        exceptionRule.expect(SalesCatalogGenericException.class);
        Mockito.doThrow(new SalesCatalogGenericException("")).when(storeDao.getLatestStore(storeId));
    }

    /**
     * test to validate SalesCatalogDBException exception thrown while saving StoreOfferingChannel
     */
    @Test
    public void whenSalesCatalogDBExceptionThrownWhilesavingStoreOfferingChannel() {
        exceptionRule.expect(SalesCatalogDBException.class);
        Mockito.when(storeOfferingChannelRepository.saveAll(storeOfferingChannelEntities)).thenThrow(new SalesCatalogDBException("DB error"));
        Mockito.when(storeEntityMapper.storeOfferingChannelDtoListToStoreOfferingChannelEntityList(storeOfferingChannelDtos, new ArrayList<>())).thenReturn(storeOfferingChannelEntities);
//        Mockito.when(storeEntityMapper.storeOfferingChannelEntityListToStoreOfferingChannelDtoList(storeOfferingChannelEntities, new ArrayList<>())).thenReturn(storeOfferingChannelDtos);
        List<StoreOfferingChannelDto> response = storeDao.createStoreOfferingChannel(storeOfferingChannelDtos);

    }

    /**
     * test to validate SalesCatalogGenericException exception thrown while saving StoreOfferingChannel
     */
    @Test
    public void whenSalesCatalogGenericExceptionThrownWhilesavingStoreOfferingChannel() {
        exceptionRule.expect(SalesCatalogGenericException.class);
        Mockito.when(storeEntityMapper.storeOfferingChannelDtoListToStoreOfferingChannelEntityList(storeOfferingChannelDtos, new ArrayList<>())).thenThrow(new SalesCatalogGenericException("Mapping exception"));
        Mockito.when(storeEntityMapper.storeOfferingChannelDtoListToStoreOfferingChannelEntityList(storeOfferingChannelDtos, new ArrayList<>())).thenReturn(storeOfferingChannelEntities);
        Mockito.when(storeEntityMapper.storeOfferingChannelEntityListToStoreOfferingChannelDtoList(storeOfferingChannelEntities, new ArrayList<>())).thenReturn(storeOfferingChannelDtos);
        List<StoreOfferingChannelDto> response = storeDao.createStoreOfferingChannel(storeOfferingChannelDtos);

    }

    /**
     * test to validate Exception exception thrown while saving StoreOfferingChannel
     */
    @Test
    public void whenExceptionThrownWhilesavingStoreOfferingChannel() {
        exceptionRule.expect(Exception.class);
        Mockito.when(storeEntityMapper.storeOfferingChannelDtoListToStoreOfferingChannelEntityList(storeOfferingChannelDtos, new ArrayList<>())).thenThrow(new Exception("Mapping exception"));
        Mockito.when(storeEntityMapper.storeOfferingChannelDtoListToStoreOfferingChannelEntityList(storeOfferingChannelDtos, new ArrayList<>())).thenReturn(storeOfferingChannelEntities);
        Mockito.when(storeEntityMapper.storeOfferingChannelEntityListToStoreOfferingChannelDtoList(storeOfferingChannelEntities, new ArrayList<>())).thenReturn(storeOfferingChannelDtos);
        List<StoreOfferingChannelDto> response = storeDao.createStoreOfferingChannel(storeOfferingChannelDtos);

    }

    /**
     * test to validate SalesCatalogGenericException  thrown while fetching StoreOfferingChannel
     */
    @Test
    public void exceptionThrownWhenStoreOfferingsChannelIsFetched(){
        exceptionRule.expect(SalesCatalogGenericException.class);
        Mockito.doThrow(new SalesCatalogGenericException("")).when(storeDao.getLatestStoreOfferingChannel(storeId, offeringId, "WEB"));
    }

    /**
     * test to validate when StoreOfferingChannelEntity  is null
     */
    @Test
    public void whenStoreOfferingChannelEntityIsNull(){
        StoreOfferingChannelEntity storeOfferingChannelEntityNull= null;

        Mockito.when(storeOfferingChannelRepository.findFirstByStoreIdAndOfferingIdAndChannelIdOrderByCreateDatetimeDesc(storeId, offeringId, "WEB")).thenReturn(storeOfferingChannelEntityNull);
        StoreOfferingChannelDto response = storeDao.getLatestStoreOfferingChannel(storeId, offeringId, "WEB");
        Assert.assertNull(response);


    }

    /**
     * test to validate get StoreOfferingsChannelByReleaseDate
     */
    @Test
    public void getStoreOfferingsChannelByReleaseDateSuccess() {
        Timestamp releaseDate = Timestamp.valueOf("2020-07-11 12:30:00");
        Mockito.when(storeOfferingChannelProductRepository.findAllEligibleStorefrontProductKeys(releaseDate, recordStatusIds,recordsSize)).thenReturn(storeComponentKeys);
        Mockito.when(storeOfferingChannelProductRepository.findAllEligibleStorefrontProducts(storeComponentKeys,RecordStatusEnum.PUBLISHED.getStatusId())).thenReturn(storeOfferingChannelProductItems);
        List<StoreOfferingChannelProductDto> response =
                storeDao.getStoreOfferingsChannelByReleaseDate(releaseDate, recordStatusIds, recordsSize);
        Assert.assertNotNull(response);
        Assert.assertEquals(offeringId, response.get(0).getOfferingId());
    }

    /**
     * test to validate get getStoreOfferingsChannelByReleaseDate  list
     */
    @Test(expected = NoContentException.class)
    public void getStoreOfferingsChannelByReleaseDateNoContent() {
        Timestamp releaseDate = Timestamp.valueOf("2020-01-11 12:30:00");
        storeDao.getStoreOfferingsChannelByReleaseDate(releaseDate, recordStatusIds, recordsSize);
    }
    /**
     * test to validate get getStoreOfferingsChannelByReleaseDate  list
     */
    @Test(expected = Exception.class)
    public void getStoreOfferingsChannelByReleaseDateUnknownError() {
        Timestamp releaseDate = Timestamp.valueOf("2020-01-11 12:30:00");
        Mockito.when(storeOfferingChannelProductRepository.findAllEligibleStorefrontProductKeys(releaseDate, recordStatusIds,recordsSize)).thenReturn(storeComponentKeys);
        Mockito.when(
                storeOfferingChannelProductRepository.findAllEligibleStorefrontProducts(storeComponentKeys, RecordStatusEnum.PUBLISHED.getStatusId()))
                .thenThrow(new RuntimeException());
        storeDao.getStoreOfferingsChannelByReleaseDate(releaseDate, recordStatusIds, recordsSize);
    }

    /**
     * test to validate get populateStorefrontProductPriceSuccess
     */
    public void populateStorefrontProductPriceSuccess() {
        Timestamp releaseDate = Timestamp.valueOf("2020-01-11 12:30:00");
        Mockito.when(storeDaoHelper.populateStorefrontProductPrice(entityManager,releaseDate, recordStatusIds))
                .thenReturn(true);
        boolean rtn = storeDao.populateStorefrontProductPrice(releaseDate, recordStatusIds);
        Assert.assertEquals(rtn, true);
    }
    /**
     * test to validate get populateStorefrontProductPriceError
     */
    @Test(expected = SalesCatalogDBException.class)
    public void populateStorefrontProductPriceError() {
        Timestamp releaseDate = Timestamp.valueOf("2020-01-11 12:30:00");
        Mockito.when(storeDaoHelper.populateStorefrontProductPrice(entityManager,releaseDate, recordStatusIds))
                .thenThrow(new SalesCatalogDBException("error"));
        boolean rtn = storeDao.populateStorefrontProductPrice(releaseDate, recordStatusIds);
        Assert.assertEquals(rtn, true);
    }

    /**
     * test to validate get StoreOfferingsChannelByReleaseDate
     */
    @Test
    public void getStoreOfferingsChannelBySapArticleIdAndStoreIdSuccess() {
        Timestamp releaseDate = Timestamp.valueOf("2020-07-11 12:30:00");
        Mockito.when(
                storeOfferingChannelProductRepository.findAllByStoreIdAndSapArticleIdAndUnitOfMeasureIdPublishState(storeId, sapArticleId, "EA", RecordStatusEnum.PUBLISHED.getStatusId()))
                .thenReturn(storeOfferingChannelProductEntities);
        Mockito.when(
                storeEntityMapper.storeOfferingChannelProductEntityListToDtoList(
                        storeOfferingChannelProductEntities, new ArrayList<>()))
                .thenReturn(storeOfferingChannelProductDtos);
        List<StoreOfferingChannelProductDto> response =
                storeDao.getStoreOfferingsChannelBySapArticleIdAndStoreId(storeId, sapArticleId, "EA", RecordStatusEnum.PUBLISHED.getStatusId());
        Assert.assertNotNull(response);
        Assert.assertEquals(offeringId, response.get(0).getOfferingId());
    }

    /**
     * test to validate  getStoreOfferingsChannelBySapArticleIdAndStoreId  No content
     */
    @Test(expected = NoContentException.class)
    public void getStoreOfferingsChannelBySapArticleIdAndStoreIdNoContent() {
        Timestamp releaseDate = Timestamp.valueOf("2020-01-11 12:30:00");
        Mockito.when(
                storeOfferingChannelProductRepository.findAllByStoreIdAndSapArticleIdAndUnitOfMeasureIdPublishState(storeId, sapArticleId, "EA", RecordStatusEnum.PUBLISHED.getStatusId()))
                .thenReturn(null);

        storeDao.getStoreOfferingsChannelBySapArticleIdAndStoreId(storeId, sapArticleId, "EA", RecordStatusEnum.PUBLISHED.getStatusId());
    }

    /**
     * test to validate  getStoreComponentItem  Succes
     */
    @Test
    public void getStoreComponentItemSuccess(){
        Timestamp releaseDate = Timestamp.valueOf("2020-07-11 12:30:00");
        Mockito.when(storeComponentRepository.getStoreComponentItemKeyList(releaseDate,RecordStatusEnum.ACTIVE.getStatusId(),RecordStatusEnum.PUBLISHED.getStatusId(),5)).thenReturn(storeComponentKeys);
        Mockito.when(storeComponentRepository.getStoreComponentItemList(storeComponentKeys,RecordStatusEnum.ACTIVE.getStatusId(),RecordStatusEnum.PUBLISHED.getStatusId())).thenReturn(storeComponentItems);
        List<StoreOfferingChannelComponentDto> response = storeDao.getStoreComponentItem(releaseDate,RecordStatusEnum.ACTIVE.getStatusId(),RecordStatusEnum.ACTIVE.getStatusId(),RecordStatusEnum.PUBLISHED.getStatusId(),5);
        Assert.assertNotNull(response);
        Assert.assertEquals("store_5",response.get(0).getStoreId());
    }

    /**
     * test to validate  getStoreOfferingsChannelBySapArticleIdAndStoreId  No content
     */
    @Test(expected = NoContentException.class)
    public void getStoreComponentItem_NoContent() {
        Timestamp releaseDate = Timestamp.valueOf("2020-01-11 12:30:00");
        Mockito.when(storeComponentRepository.getStoreComponentItemKeyList(releaseDate,RecordStatusEnum.ACTIVE.getStatusId(), RecordStatusEnum.PUBLISHED.getStatusId(),5)).thenReturn(storeComponentKeys);
        Mockito.when(storeComponentRepository.getStoreComponentItemList(storeComponentKeys,RecordStatusEnum.ACTIVE.getStatusId(),RecordStatusEnum.PUBLISHED.getStatusId())).thenReturn(storeComponentItems);

        List<StoreOfferingChannelComponentDto> response = storeDao.getStoreComponentItem(releaseDate,RecordStatusEnum.ACTIVE.getStatusId(), RecordStatusEnum.ACTIVE.getStatusId(),RecordStatusEnum.PUBLISHED.getStatusId(), 5);
    }

    /**
     * test to validate exception thrown while fetching Store
     */
    @Test
    public void whenExceptionThrownfetchStoreCompenent(){
        Timestamp releaseDate = Timestamp.valueOf("2020-01-11 12:30:00");
        exceptionRule.expect(SalesCatalogGenericException.class);
        Mockito.when(storeComponentRepository.getStoreComponentItemKeyList(releaseDate,RecordStatusEnum.ACTIVE.getStatusId(),RecordStatusEnum.PUBLISHED.getStatusId(),5)).thenReturn(storeComponentKeys);
        Mockito.when(storeComponentRepository.getStoreComponentItemList(storeComponentKeys,RecordStatusEnum.ACTIVE.getStatusId(),RecordStatusEnum.PUBLISHED.getStatusId())).thenReturn(storeComponentItems);
        List<StoreOfferingChannelComponentDto> response = storeDao.getStoreComponentItem(releaseDate,RecordStatusEnum.ACTIVE.getStatusId(),RecordStatusEnum.ACTIVE.getStatusId(), RecordStatusEnum.PUBLISHED.getStatusId(), 5);
    }

    @Test
    public void testUpdateStoreFrontComponent() throws ParseException {
        Mockito.when(storeComponentRepository.findLatest(CatalogUtilities.getTimestampDate(storeComponentDto.getReleaseDate()),
                storeComponentDto.getStoreId(), storeComponentDto.getComponentId(),
                2)).thenReturn(storeComponentEntity);
        StoreComponentDto storeComponent = storeDao.updateStoreFrontComponent(storeComponentDto);
        Assert.assertNotNull(storeComponent);
        Assert.assertEquals(storeComponentDto.getChannelId(), storeComponent.getChannelId());
        Assert.assertEquals(false, storeComponent.isSuccess());
    }

    @Test
    public void testUpdateStoreFrontComponentUnPublish() throws ParseException {
        storeComponentDto.setRecordStatusId(RecordStatusEnum.PUBLISHED.getStatusId());
        Mockito.when(storeComponentRepository.findLatest(CatalogUtilities.getTimestampDate(storeComponentDto.getReleaseDate()),
                storeComponentDto.getStoreId(), storeComponentDto.getComponentId(),
                2)).thenReturn(storeComponentEntity);
        StoreComponentDto storeComponent = storeDao.updateStoreFrontComponent(storeComponentDto);
        Assert.assertNotNull(storeComponent);
        Assert.assertEquals(storeComponentDto.getChannelId(), storeComponent.getChannelId());
        Assert.assertEquals(false, storeComponent.isSuccess());
    }

    @Test
    public void testUpdateStoreFrontComponentFail() throws ParseException {
      /*  Mockito.when(storeComponentRepository.findLatest(CatalogUtilities.getTimestampDate(storeComponentDto.getReleaseDate()),
                storeComponentDto.getStoreId(), storeComponentDto.getComponentId(),
                storeComponentDto.getRecordStatusId())).thenReturn(null);*/
        StoreComponentDto storeComponent = storeDao.updateStoreFrontComponent(storeComponentDto);
        Assert.assertNotNull(storeComponent);
        Assert.assertEquals(storeComponentDto.getChannelId(), storeComponent.getChannelId());
        Assert.assertEquals(false, storeComponent.isSuccess());
    }

    @Test (expected = ParseException.class )
    public void testUpdateStoreFrontComponentException() throws ParseException {
        Mockito.when(storeComponentRepository.findLatest(CatalogUtilities.getTimestampDate("2020-07-19"),
                storeComponentDto.getStoreId(), storeComponentDto.getComponentId(),
                storeComponentDto.getRecordStatusId())).thenReturn(null);
        storeDao.updateStoreFrontComponent(storeComponentDto);

    }

    @Test
    public void testCreateStoreChannelOfferingEventObject() throws ParseException {

        List<UUID> productIds = new ArrayList<>();
        productIds.add(UUID.randomUUID());
        List<PriceStatusHistoryEntity> priceStatusHistoryEntityList = new ArrayList<>();

        StoreProductEntity storeProductEntity = new StoreProductEntity();
        storeProductEntity.setProductId(UUID.randomUUID());
        List<StoreProductEntity> storeProductEntityList =  new ArrayList<>();
        storeProductEntityList.add(storeProductEntity);

        List<ProductCategory>  productCategoryList = new ArrayList<>();

         Mockito.when(priceStatusHistoryRepository.findLatest(storeId, channelId, offeringId, RecordStatusEnum.PUBLISHED.getStatusId()
                            )).thenReturn(priceStatusHistoryEntityList);

        Mockito.when(storeProductRepository.findProductsOfStoreFront(
                storeId, channelId, offeringId, RecordStatusEnum.PUBLISHED.getStatusId()))
                .thenReturn(storeProductEntityList);


        //Mockito.when(productCategoryRepository.findLatest(productIds, RecordStatusEnum.PUBLISHED.getStatusId())).thenReturn(productCategoryList);

        StoreChannelOfferingEventWcm storeChannelOfferingEventWcm = storeDao.createStoreChannelOfferingEventObject (storeId, channelId, offeringId);
            Assert.assertNotNull(storeChannelOfferingEventWcm);
    }
}
