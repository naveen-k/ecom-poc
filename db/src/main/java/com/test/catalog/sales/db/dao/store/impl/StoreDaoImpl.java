package com.test.catalog.sales.db.dao.store.impl;

import com.test.catalog.sales.db.constant.ExceptionCodeEnum;
import com.test.catalog.sales.db.constant.RecordStatusEnum;
import com.test.catalog.sales.db.constant.SalesCatalogConstant;
import com.test.catalog.sales.db.dao.store.StoreDao;
import com.test.catalog.sales.db.dto.*;
import com.test.catalog.sales.db.dto.wcm.*;
import com.test.catalog.sales.db.entity.price.PriceStatusHistoryEntity;
import com.test.catalog.sales.db.entity.product.StoreProductEntity;
import com.test.catalog.sales.db.entity.store.*;
import com.test.catalog.sales.db.mapper.StoreEntityMapper;
import com.test.catalog.sales.db.repository.product.ProductCategoryRepository;
import com.test.catalog.sales.db.repository.store.*;
import com.test.catalog.sales.db.util.CatalogUtilities;
import com.test.catalog.sales.db.StoreDaoHelper;
import com.test.catalog.sales.exception.handler.NoContentException;
import com.test.catalog.sales.exception.handler.SalesCatalogDBException;
import com.test.catalog.sales.exception.handler.SalesCatalogGenericException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StoreDaoImpl implements StoreDao {

    private static final Logger LOG = LoggerFactory.getLogger(StoreDaoImpl.class);
    private static final String ERROR_MSG = "Exception occurred while retrieving the Stores by ProductId";
    private static final String ERROR_MSG_RELEASE_DATE = "Exception occurred while retrieving the Stores by releaseDate";
    @PersistenceContext
    private final EntityManager entityManager;
    private StoreEntityMapper storeEntityMapper;
    private StoreOfferingChannelProductRepository storeOfferingChannelProductRepository;
    private StoreOfferingChannelRepository storeOfferingChannelRepository;
    private StoreRepository storeRepository;
    private StoreDaoHelper storeDaoHelper;
    private StoreComponentRepository storeComponentRepository;
    private StoreProductRepository storeProductRepository;
    private StoreOfferingChannelComponentRepository storeOfferingChannelComponentRepository;
    private PriceStatusHistoryRepository priceStatusHistoryRepository;
    private ProductCategoryRepository productCategoryRepository;

    public StoreDaoImpl(
            StoreEntityMapper storeEntityMapper, StoreRepository storeRepository,
            StoreOfferingChannelProductRepository storeOfferingChannelProductRepository,
            StoreOfferingChannelRepository storeOfferingChannelRepository,
            PriceStatusHistoryRepository priceStatusHistoryRepository,
            ProductCategoryRepository productCategoryRepository,
            StoreDaoHelper storeDaoHelper,
            StoreComponentRepository storeComponentRepository,
            StoreProductRepository storeProductRepository,
            EntityManager entityManager,
            StoreOfferingChannelComponentRepository storeOfferingChannelComponentRepository) {
        this.storeEntityMapper = storeEntityMapper;
        this.storeRepository = storeRepository;
        this.storeOfferingChannelProductRepository = storeOfferingChannelProductRepository;
        this.storeOfferingChannelRepository = storeOfferingChannelRepository;
        this.entityManager = entityManager;
        this.storeDaoHelper = storeDaoHelper;
        this.storeOfferingChannelComponentRepository = storeOfferingChannelComponentRepository;
        this.storeComponentRepository = storeComponentRepository;
        this.storeProductRepository = storeProductRepository;
        this.priceStatusHistoryRepository=priceStatusHistoryRepository;
        this.productCategoryRepository=productCategoryRepository;

    }


    /**
     * This method call calls StoreOfferingChannelRepository to get Stores,offerings and channel by productId and
     * the status of the record(subRecordStatusId)
     *
     * @param productId
     * @param subRecordStatusId
     * @return List<StoreOfferingChannelProductDto>
     */
    @Override
    public List<StoreOfferingChannelProductDto> getStoreOfferingsChannelByProductId(UUID productId, Date releaseDate, List<Integer> subRecordStatusId) {
        LOG.debug(" StoreDaoImpl.getStoreOfferingsChannelByProductId  called");
        List<StoreOfferingChannelProductDto> storeOfferingChannelProductDtos = null;
        try {
            List<StoreOfferingChannelProductEntity> storeOfferingChannelProductEntities =
                    storeOfferingChannelProductRepository.findAllByProductIdAndReleaseDateAndRecordStatusIdIn(productId, CatalogUtilities.getTimestampDate(releaseDate), subRecordStatusId);
            if (!ObjectUtils.isEmpty(storeOfferingChannelProductEntities)) {
                storeOfferingChannelProductDtos =
                        storeEntityMapper.storeOfferingChannelProductEntityListToDtoList(
                                storeOfferingChannelProductEntities, new ArrayList<>());
                return storeOfferingChannelProductDtos;
            } else {
                throw new NoContentException(new Throwable(ERROR_MSG), ExceptionCodeEnum.NO_CONTENT.getExceptionMessage(), ExceptionCodeEnum.NO_CONTENT.getExceptionCode());
            }
        } catch (NoContentException e) {
            throw new NoContentException(new Throwable(ERROR_MSG), ExceptionCodeEnum.NO_CONTENT.getExceptionMessage(), ExceptionCodeEnum.NO_CONTENT.getExceptionCode());
        } catch (Exception e) {
            throw new SalesCatalogGenericException(
                    new Throwable(ERROR_MSG), ExceptionCodeEnum.GENERIC_DB_ERROR.getExceptionMessage(), ExceptionCodeEnum.GENERIC_DB_ERROR.getExceptionCode());
        }
    }

    /**
     * This method  calls storeRepository and save the storeEntity in the store table
     *
     * @param storeDto
     * @return StoreDto
     */
    @Override
    public StoreDto createStore(StoreDto storeDto) {
        LOG.debug(" StoreDaoImpl.createStoreOfferingChannel  called");
        try {
            StoreEntity storeEntity = storeRepository.save(storeEntityMapper.storeDtoToStoreEntity(storeDto, new StoreEntity()));

            return storeEntityMapper.storeEntityToStoreDto(storeEntity, new StoreDto());
        } catch (SalesCatalogDBException e) {
            throw new SalesCatalogDBException(new Throwable("Exception occurred while creating the Store"), ExceptionCodeEnum.NO_CONTENT.getExceptionMessage(), ExceptionCodeEnum.NO_CONTENT.getExceptionCode());
        } catch (Exception e) {
            throw new SalesCatalogGenericException(
                    new Throwable("Some exception occurred in create Store"), ExceptionCodeEnum.GENERIC_DB_ERROR.getExceptionMessage(), ExceptionCodeEnum.GENERIC_DB_ERROR.getExceptionCode());
        }
    }


    /**
     * This method  calls storeRepository and fetch the latest record based on storeId
     *
     * @param storeId
     * @return StoreDto
     */
    public StoreDto getLatestStore(String storeId) {
        StoreEntity storeEntity = null;
        try {
            storeEntity = storeRepository.findFirstByStoreIdOrderByCreateDatetimeDesc(storeId);

            if (!ObjectUtils.isEmpty(storeEntity)) {
                return storeEntityMapper.storeEntityToStoreDto(storeEntity, new StoreDto());
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new SalesCatalogGenericException(
                    new Throwable("Exception occurred while retrieving the Store"), ExceptionCodeEnum.GENERIC_DB_ERROR.getExceptionMessage(), ExceptionCodeEnum.GENERIC_DB_ERROR.getExceptionCode());
        }
    }

    /**
     * This method  calls storeOfferingChannelRepository and save the StoreOfferingChannelEntity in the store table
     *
     * @param storeOfferingChannelDtos
     * @return List<StoreOfferingChannelDto>
     */
    @Override
    public List<StoreOfferingChannelDto> createStoreOfferingChannel(List<StoreOfferingChannelDto> storeOfferingChannelDtos) {
        LOG.debug(" StoreDaoImpl.createStoreOfferingChannel  called");
        try {
            List<StoreOfferingChannelEntity> storeOfferingChannelEntities = storeOfferingChannelRepository.saveAll(storeEntityMapper.storeOfferingChannelDtoListToStoreOfferingChannelEntityList(storeOfferingChannelDtos, new ArrayList<>()));

            return storeEntityMapper.storeOfferingChannelEntityListToStoreOfferingChannelDtoList(storeOfferingChannelEntities, new ArrayList<>());
        } catch (SalesCatalogDBException e) {
            throw new SalesCatalogDBException(new Throwable("Exception occurred while creating record in storeofferingchannel"), ExceptionCodeEnum.NO_CONTENT.getExceptionMessage(), ExceptionCodeEnum.NO_CONTENT.getExceptionCode());
        } catch (Exception e) {
            throw new SalesCatalogGenericException(
                    new Throwable("Some exception occurred in create storeofferingchannel"), ExceptionCodeEnum.GENERIC_DB_ERROR.getExceptionMessage(), ExceptionCodeEnum.GENERIC_DB_ERROR.getExceptionCode());
        }
    }

    /**
     * This method  calls storeOfferingChannelRepository and fetch the latest record based on storeId, offeringId, channelId
     *
     * @param storeId
     * @param offeringId
     * @param channelId
     * @return StoreOfferingChannelDto
     */
    @Override
    public StoreOfferingChannelDto getLatestStoreOfferingChannel(String storeId, String offeringId, String channelId) {
        StoreOfferingChannelEntity storeOfferingChannelEntity = null;
        try {
            storeOfferingChannelEntity = storeOfferingChannelRepository.findFirstByStoreIdAndOfferingIdAndChannelIdOrderByCreateDatetimeDesc(storeId, offeringId, channelId);

            if (!ObjectUtils.isEmpty(storeOfferingChannelEntity)) {
                return storeEntityMapper.storeOfferingChannelEntityToStoreOfferingChannelDto(storeOfferingChannelEntity, new StoreOfferingChannelDto());
            } else {
                return null;
            }

        } catch (Exception e) {
            throw new SalesCatalogGenericException(
                    new Throwable("Exception occurred while retrieving the storeofferingchannel"), ExceptionCodeEnum.GENERIC_DB_ERROR.getExceptionMessage(), ExceptionCodeEnum.GENERIC_DB_ERROR.getExceptionCode());
        }
    }

    /**
     * This method call calls StoreOfferingChannelRepository to get Stores,offerings and channel  and
     * the status of the record(subRecordStatusId)
     *
     * @param subRecordStatusId
     * @return List<StoreOfferingChannelProductDto>
     */
    @Override
    public List<StoreOfferingChannelProductDto> getStoreOfferingsChannelByReleaseDate(Date releaseDate, List<Integer> subRecordStatusId, Integer recordsSize) {
        List<StoreOfferingChannelProductDto> storeOfferingChannelProductDtos = null;
        try {
            List<Integer>  storeOfferingChannelProductKeys = storeOfferingChannelProductRepository.findAllEligibleStorefrontProductKeys(CatalogUtilities.getTimestampDate(releaseDate), subRecordStatusId, recordsSize);
            List<StoreOfferingChannelProductItem>  storeOfferingChannelProductItems = storeOfferingChannelProductRepository.findAllEligibleStorefrontProducts(storeOfferingChannelProductKeys, RecordStatusEnum.PUBLISHED.getStatusId());
            if (!ObjectUtils.isEmpty(storeOfferingChannelProductItems)) {
                storeOfferingChannelProductDtos = storeOfferingChannelProductItems.stream().map(storeOfferingChannelProductItem -> {
                    StoreOfferingChannelProductDto dto = new StoreOfferingChannelProductDto();
                    dto.setStoreId(storeOfferingChannelProductItem.getStoreId());
                    dto.setChannelId(storeOfferingChannelProductItem.getChannelId());
                    dto.setOfferingId(storeOfferingChannelProductItem.getOfferingId());
                    dto.setSkuType(storeOfferingChannelProductItem.getSkuType());
                    dto.setProductId(UUID.fromString(storeOfferingChannelProductItem.getProductId()));
                    dto.setItemSku(storeOfferingChannelProductItem.getItemSku());
                    dto.setReleaseDate(storeOfferingChannelProductItem.getReleaseDate());
                    return  dto;
                }).collect(Collectors.toList());
                return storeOfferingChannelProductDtos;
            }  else {
                throw new NoContentException(new Throwable(ERROR_MSG_RELEASE_DATE), ExceptionCodeEnum.NO_CONTENT.getExceptionMessage(), ExceptionCodeEnum.NO_CONTENT.getExceptionCode());
            }
        } catch (NoContentException e) {
            throw new NoContentException(new Throwable(ERROR_MSG_RELEASE_DATE), ExceptionCodeEnum.NO_CONTENT.getExceptionMessage(), ExceptionCodeEnum.NO_CONTENT.getExceptionCode());
        } catch (Exception e) {
            e.printStackTrace();
            throw new SalesCatalogGenericException(
                    new Throwable(ERROR_MSG_RELEASE_DATE), ExceptionCodeEnum.GENERIC_DB_ERROR.getExceptionMessage(), ExceptionCodeEnum.GENERIC_DB_ERROR.getExceptionCode());
        }
    }

    @Override
    public boolean populateStorefrontProductPrice(Date releaseDate, List<Integer> subRecordStatusId) {
        try {
            LOG.debug("populateStorefrontProductPrice- releaseDate {} subRecordStatusId {}", releaseDate, subRecordStatusId);
            return storeDaoHelper.populateStorefrontProductPrice(entityManager, releaseDate, subRecordStatusId);
        } catch (Exception e) {
            LOG.error(" Error   in StoreServiceHelper.populateStorefrontProductPrice  {}", e.getLocalizedMessage());
            throw new SalesCatalogDBException(new Throwable("Exception occurred while populating  the Stores by releaseDate"), ExceptionCodeEnum.DB_ERROR.getExceptionMessage(), ExceptionCodeEnum.DB_ERROR.getExceptionCode());
        }
    }

    /**
     * This method call calls StoreOfferingChannelRepository to get Stores,offerings and channel
     *
     * @param storeId
     * @param sapArticleId
     * @param publishedStatusId
     * @return List<StoreOfferingChannelProductDto>
     */

    @Override
    public List<StoreOfferingChannelProductDto> getStoreOfferingsChannelBySapArticleIdAndStoreId(String storeId, String sapArticleId, String uom, Integer publishedStatusId) {
        LOG.debug("getStoreOfferingsChannelBySapArticleIdAndStoreId- storeId {} sapArticleId {} uom {}", storeId, sapArticleId, uom);
        List<StoreOfferingChannelProductDto> storeOfferingChannelProductDtos = null;
        try {
            List<StoreOfferingChannelProductEntity> storeOfferingChannelProductEntities =
                    storeOfferingChannelProductRepository.findAllByStoreIdAndSapArticleIdAndUnitOfMeasureIdPublishState(storeId, sapArticleId, uom, publishedStatusId);
            if (!ObjectUtils.isEmpty(storeOfferingChannelProductEntities)) {
                storeOfferingChannelProductDtos =
                        storeEntityMapper.storeOfferingChannelProductEntityListToDtoList(
                                storeOfferingChannelProductEntities, new ArrayList<>());
                return storeOfferingChannelProductDtos;
            } else {
                throw new NoContentException(new Throwable(ERROR_MSG_RELEASE_DATE), ExceptionCodeEnum.NO_CONTENT.getExceptionMessage(), ExceptionCodeEnum.NO_CONTENT.getExceptionCode());
            }
        } catch (NoContentException e) {
            throw new NoContentException(new Throwable(ERROR_MSG_RELEASE_DATE), ExceptionCodeEnum.NO_CONTENT.getExceptionMessage(), ExceptionCodeEnum.NO_CONTENT.getExceptionCode());
        } catch (Exception e) {
            LOG.error(" Error   in getStoreOfferingsChannelBySapArticleIdAndStoreId  {}", e.getLocalizedMessage());
            throw new SalesCatalogGenericException(
                    new Throwable(ERROR_MSG_RELEASE_DATE), ExceptionCodeEnum.GENERIC_DB_ERROR.getExceptionMessage(), ExceptionCodeEnum.GENERIC_DB_ERROR.getExceptionCode());
        }
    }

    /**
     * This method call calls storeComponentRepository to get Stores,offerings,channel and components
     *
     * @param releaseDate
     * @param subRecordStatusId
     * @param recordActStatusId
     * @param publishedStatusId
     * @return List<StoreOfferingChannelProductDto>
     */
    @Override
    public List<StoreOfferingChannelComponentDto> getStoreComponentItem(Date releaseDate, Integer subRecordStatusId, Integer recordActStatusId, Integer publishedStatusId, Integer recordsSize) {
        LOG.debug("getStoreComponentItem- releaseDate {}", releaseDate);
        List<StoreOfferingChannelComponentDto> storeOfferingChannelComponentDtos = null;
        try {

            List<Integer> storeComponentKeys = storeComponentRepository.getStoreComponentItemKeyList(releaseDate, recordActStatusId,publishedStatusId, recordsSize);
            List<StoreComponentItem> storeComponentItems = storeComponentRepository.getStoreComponentItemList(storeComponentKeys,  recordActStatusId,publishedStatusId);
            if (!ObjectUtils.isEmpty(storeComponentItems)) {
                storeOfferingChannelComponentDtos = new ArrayList<>();
                for (StoreComponentItem storeComponentItem : storeComponentItems) {
                    StoreOfferingChannelComponentDto storeOfferingChannelComponentDto = new StoreOfferingChannelComponentDto();
                    storeOfferingChannelComponentDto.setStoreId(storeComponentItem.getStoreId());
                    storeOfferingChannelComponentDto.setChannelId(storeComponentItem.getChannelId());
                    storeOfferingChannelComponentDto.setOfferingId(storeComponentItem.getOfferingId());
                    storeOfferingChannelComponentDto.setComponentId(UUID.fromString(storeComponentItem.getComponentId()));

                    storeOfferingChannelComponentDto.setReleaseDate(storeComponentItem.getReleaseDate());
                    storeOfferingChannelComponentDtos.add(storeOfferingChannelComponentDto);
                }
                return storeOfferingChannelComponentDtos;
            } else {
                throw new NoContentException(new Throwable(ERROR_MSG_RELEASE_DATE), ExceptionCodeEnum.NO_CONTENT.getExceptionMessage(), ExceptionCodeEnum.NO_CONTENT.getExceptionCode());
            }
        } catch (NoContentException e) {
            throw new NoContentException(new Throwable(ERROR_MSG_RELEASE_DATE), ExceptionCodeEnum.NO_CONTENT.getExceptionMessage(), ExceptionCodeEnum.NO_CONTENT.getExceptionCode());
        } catch (Exception e) {
            LOG.error(" Error   in getStoreComponentItem  {}", e.getLocalizedMessage());
            throw new SalesCatalogGenericException(
                    new Throwable(ERROR_MSG_RELEASE_DATE), ExceptionCodeEnum.GENERIC_DB_ERROR.getExceptionMessage(), ExceptionCodeEnum.GENERIC_DB_ERROR.getExceptionCode());
        }
    }


    /**
     * This method update the status for store front component into Store_component and StoreFront_Component tables
     * @param storeComponentDto
     * @return StoreComponentDto
     */
    public StoreComponentDto updateStoreFrontComponent(StoreComponentDto storeComponentDto) {
        try {
            Integer statusId = null;
            if (storeComponentDto.getRecordStatusId()==RecordStatusEnum.UNPUBLISHED.getStatusId() || storeComponentDto.getRecordStatusId()==RecordStatusEnum.UNPUBLISHED_FAILED.getStatusId()){
                 statusId= RecordStatusEnum.PUBLISHED.getStatusId();
            } else {
                statusId= RecordStatusEnum.ACTIVE.getStatusId();
            }
            StoreComponentEntity storeComponentEntity = storeComponentRepository.findLatest(CatalogUtilities.getTimestampDate(storeComponentDto.getReleaseDate()),
                    storeComponentDto.getStoreId(), storeComponentDto.getComponentId(), statusId);
            if (!ObjectUtils.isEmpty(storeComponentEntity)) {
                StoreComponentEntity entity = new StoreComponentEntity();
                entity.setStoreId(storeComponentEntity.getStoreId());
                entity.setComponentId(storeComponentEntity.getComponentId());
                if(RecordStatusEnum.ACTIVE.getStatusId()==storeComponentDto.getRecordStatusId()) {
                    entity.setReleaseDate(storeComponentEntity.getReleaseDate());
                }
                entity.setRecordStatusId(storeComponentDto.getRecordStatusId());
                entity.setCreateDatetime(new Timestamp(System.currentTimeMillis()));
                storeComponentRepository.save(entity);

                StoreOfferingChannelComponentEntity storeOfferingChannelComponentEntity = new StoreOfferingChannelComponentEntity();
                storeOfferingChannelComponentEntity.setChannelId(storeComponentDto.getChannelId());
                storeOfferingChannelComponentEntity.setOfferingId(storeComponentDto.getOfferingId());
                storeOfferingChannelComponentEntity.setComponentId(storeComponentDto.getComponentId());
                storeOfferingChannelComponentEntity.setStoreId(storeComponentDto.getStoreId());
                storeOfferingChannelComponentEntity.setRecordStatusId(storeComponentDto.getRecordStatusId());
                storeOfferingChannelComponentEntity.setCreateDatetime(new Timestamp(System.currentTimeMillis()));
                storeOfferingChannelComponentRepository.save(storeOfferingChannelComponentEntity);
                storeComponentDto.setSuccess(true);
            } else {
                storeComponentDto.setSuccess(false);
                LOG.warn("Unable to fetch  StoreOfferingChannelComponent detail {}", storeComponentDto);
            }
        } catch (Exception e) {
            storeComponentDto.setSuccess(false);
            LOG.error("Error on occurred while fetching detail {}", e.getLocalizedMessage());
        }
        return storeComponentDto;
    }

    /**
     *
     * @param storeId
     * @param channelId
     * @param offeringId
     * @return A StoreFrontEventWCM is created here.
     */
    public StoreChannelOfferingEventWcm createStoreChannelOfferingEventObject (String storeId, String channelId, String offeringId){
        StoreChannelOfferingEventWcm storeChannelOfferingEventWcm = new StoreChannelOfferingEventWcm();

        try {
            //Instant.now().toString() gives 2020-11-28T22:49:40.186980600Z where as LocalDateTime.now().toString() gives 2020-11-28T22:51:54.613984800
            storeChannelOfferingEventWcm.setTime(LocalDateTime.now().toString());
            storeChannelOfferingEventWcm.setSource(SalesCatalogConstant.EVENT_SOURCE_STOREFRONT);
            storeChannelOfferingEventWcm.setId(UUID.randomUUID().toString());
            storeChannelOfferingEventWcm.setSpecversion(SalesCatalogConstant.EVENT_SPEC_VERSION);
            storeChannelOfferingEventWcm.setEventversion(SalesCatalogConstant.EVENT_VERSION);
            storeChannelOfferingEventWcm.setType(SalesCatalogConstant.EVENT_TYPE_STOREFRONT);
            storeChannelOfferingEventWcm.setDatacontenttype(SalesCatalogConstant.JSON_MIME_TYPE);
            storeChannelOfferingEventWcm.setSubject(SalesCatalogConstant.EVENT_SUBJECT_STOREFRONT);
            storeChannelOfferingEventWcm.setTracecontext(mdcGet(SalesCatalogConstant.EVENT_TRACE_CONTEXT));
            storeChannelOfferingEventWcm.setTraceparent(mdcGet(SalesCatalogConstant.EVENT_TRACE_PARENT));

            List<PriceStatusHistoryEntity> priceStatusHistoryEntityList = priceStatusHistoryRepository.findLatest(storeId, channelId, offeringId, RecordStatusEnum.PUBLISHED.getStatusId());

            List<ProductsRecordWcm> productsRecordWcmList = new ArrayList<ProductsRecordWcm>();

            List<StoreProductEntity> storeProductEntityList = storeProductRepository.findProductsOfStoreFront(
                    storeId, channelId, offeringId, RecordStatusEnum.PUBLISHED.getStatusId());

            //Ideally a distinct clause must be put in the db query itself, will visit this later.
            List<UUID> productIds = storeProductEntityList.stream().map(StoreProductEntity::getProductId)
                    .distinct().collect(Collectors.toList());

            if (!CollectionUtils.isEmpty(productIds)){
                List<ProductCategory>  productCategoryList  = productCategoryRepository.findLatest(productIds, RecordStatusEnum.PUBLISHED.getStatusId());
                for (UUID productId : productIds) {
                    List<PriceStatusHistoryEntity> priceStatusHistoryEntityListForASingleProduct = priceStatusHistoryEntityList.stream().filter(p -> p.getProductId().equals(productId)).collect(Collectors.toList());
                    //Add prices to product
                    List<ProductListPricesRecordWcm> productListPricesRecordWcmList = new ArrayList<ProductListPricesRecordWcm>();
                    for (PriceStatusHistoryEntity priceStatusHistoryEntity : priceStatusHistoryEntityListForASingleProduct) {
                        productListPricesRecordWcmList.add(new ProductListPricesRecordWcm(priceStatusHistoryEntity.getPrice().toString(),
                                priceStatusHistoryEntity.getEffectiveStartDatetime().toString(), priceStatusHistoryEntity.getEffectiveStartDatetime().toString()));
                    }

                    List< ProductCategory>  ProductCategoryListForASingleProduct =  productCategoryList.stream().filter(p -> p.getProductId().equals(productId.toString())).collect(Collectors.toList());
                    //Add categories to product
                    List<ProductCategoriesRecordWcm> categoriesRecordWcmList = new ArrayList<ProductCategoriesRecordWcm>();
                    for ( ProductCategory  productCategory :  ProductCategoryListForASingleProduct) {
                        categoriesRecordWcmList.add(new ProductCategoriesRecordWcm( productCategory.getCategoryId()));
                    }

                    productsRecordWcmList.add(new ProductsRecordWcm(productId.toString(), productListPricesRecordWcmList, categoriesRecordWcmList));
                }
            }

            List<IngredientsRecordWcm> ingredientsRecordWcmList = new ArrayList<IngredientsRecordWcm>();
            List<StoreComponentItem> storeComponentItemList = storeComponentRepository.findComponentsOfStoreFront(
                    storeId, channelId, offeringId, RecordStatusEnum.PUBLISHED.getStatusId(), RecordStatusEnum.ACTIVE.getStatusId());
            if (!CollectionUtils.isEmpty(storeComponentItemList)){
                for (StoreComponentItem storeOfferingChannelComponentEntity : storeComponentItemList) {
                    //We pass an empty list for now, because ingredient does not have prices for now,  they may have it later though.
                    ingredientsRecordWcmList.add(new IngredientsRecordWcm(storeOfferingChannelComponentEntity.getComponentId().toString(), new ArrayList<IngredientListPricesRecordWcm>()));
                }
            }

            StoreChannelOfferingWcm storeChannelOfferingWcm = new StoreChannelOfferingWcm(storeId, channelId, offeringId, productsRecordWcmList, ingredientsRecordWcmList);
            DataWcm dataWcm = new DataWcm();
            dataWcm.setStorechanneloffering(storeChannelOfferingWcm);

            storeChannelOfferingEventWcm.setData(dataWcm);

        } catch (Exception e) {// we don't throw this exception because we do not want to to block other operations
            LOG.error("Error on sending storefront event {} {}", storeChannelOfferingEventWcm, e.getLocalizedMessage());

        }
        return storeChannelOfferingEventWcm;

    }

    /**
     * This is a wrapper class to facilitate unit testing. (Naveen: I could have used Powermock but it leads to other issues related to the StoreFrontBuilder.
     * @param key
     */
    public String mdcGet(String key){
        return MDC.get(key);
    }

}
