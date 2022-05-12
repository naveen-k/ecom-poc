package com.test.catalog.sales.domain.product;

import com.test.catalog.sales.client.request.product.LocationSearchCriteria;
import com.test.catalog.sales.client.response.product.*;
import com.test.catalog.sales.db.constant.RecordStatusEnum;
import com.test.catalog.sales.db.dao.price.SapArticlePriceDao;
import com.test.catalog.sales.db.dao.store.StoreDao;
import com.test.catalog.sales.db.dto.SapArticlePriceDto;
import com.test.catalog.sales.db.dto.StoreOfferingChannelProductDto;
import com.test.catalog.sales.db.dto.StoreProductDto;
import com.test.catalog.sales.domain.helper.StoreServiceHelper;
import com.test.catalog.sales.domain.service.store.impl.ProductStoreServiceImpl;
import com.test.catalog.sales.exception.handler.NoContentException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductStoreServiceImplTest {

    @InjectMocks
    private ProductStoreServiceImpl storeService;
    @Mock
    private StoreDao storeDao;
    @Mock
    private SapArticlePriceDao sapArticlePriceDao;
    @Mock
    private StoreServiceHelper storeServiceHelper;
    @Mock
    List<SapArticlePriceDto> sapArticlePriceDtos;

    LocationSearchCriteria locationSearchCriteria = new LocationSearchCriteria();
    private List<Integer> recordStatusIds = new ArrayList<Integer>(Arrays.asList(2, 6));
    private List<StoreProductDto> storeProductDtos = new ArrayList<>();
    private List<StoreOfferingChannelProductDto> storeOfferingChannelProductDtos = new ArrayList<>();
    private List<StoreOfferingResponse> storeOfferingResponses = new ArrayList<>();
    private List<StoreOfferingsChannelResponse> offeringList = new ArrayList<>();
    List<OfferingChannelResponse> salesChannelList = new ArrayList<>();
    StoreOfferingResponse storeOfferingResponse = new StoreOfferingResponse();
    private ProductStoreDetail productStoreDetail = new ProductStoreDetail();

    private String dateFormat = "yyyy-MM-dd HH:mm:ssZZZZ";
    private String sDate1 = "2020-06-01 13:33:45-0000";
    private UUID productId = UUID.randomUUID();
    private String storeId = "store_7";
    private String channelId = "catering";
    private String sapArticleId = "sap-1234";
    private final Integer recordsSize=4;

    private StoreFrontProductResponse productStoreResponse;
    private List<StoreFrontProduct> storeFrontProductList;
    private StoreFrontProduct storeFrontProduct;

    @Before
    public void setup() {

        StoreProductDto storeProductDto = new StoreProductDto();
        storeProductDto.setProductId(productId);
        storeProductDto.setStoreId(storeId);
        storeProductDto.setPrice(80.0);
        storeProductDtos.add(storeProductDto);
        StoreOfferingChannelProductDto storeOfferingChannelProductDto = new StoreOfferingChannelProductDto();
        storeOfferingChannelProductDto.setChannelId("WEB");
        storeOfferingChannelProductDto.setOfferingId("CATLITE");
        storeOfferingChannelProductDto.setStoreId(storeId);
        storeOfferingChannelProductDto.setProductId(productId);
        StoreOfferingChannelProductDto storeOfferingChannelProductDto1 = new StoreOfferingChannelProductDto();
        storeOfferingChannelProductDto1.setChannelId("MSITE");
        storeOfferingChannelProductDto1.setOfferingId("OFFFERING");
        storeOfferingChannelProductDto1.setStoreId(storeId);
        storeOfferingChannelProductDto1.setProductId(productId);

        storeOfferingChannelProductDtos.add(storeOfferingChannelProductDto);
        storeOfferingChannelProductDtos.add(storeOfferingChannelProductDto1);

        OfferingChannelResponse offeringChannelResponse = new OfferingChannelResponse();
        offeringChannelResponse.setChannelId("WEB");
        OfferingChannelResponse offeringChannelResponse1 = new OfferingChannelResponse();
        offeringChannelResponse1.setChannelId("MOBILE");
        salesChannelList.add(offeringChannelResponse);
        salesChannelList.add(offeringChannelResponse1);

        StoreOfferingsChannelResponse storeOfferingsChannelResponse = new StoreOfferingsChannelResponse();
        storeOfferingsChannelResponse.setSalesChannelList(salesChannelList);
        storeOfferingsChannelResponse.setOfferingId("OFFERING");
        offeringList.add(storeOfferingsChannelResponse);

        storeOfferingResponse.setOfferingList(offeringList);
        storeOfferingResponse.setStoreId(storeId);
        // storeOfferingResponse.setPrice(80.0);
        storeOfferingResponses.add(storeOfferingResponse);
        productStoreDetail.setProductId(productId);
        productStoreDetail.setLocations(storeOfferingResponses);

        storeFrontProductList = new ArrayList<>();
        storeFrontProduct = new StoreFrontProduct();
        storeFrontProduct.setStoreId(storeId);
        storeFrontProduct.setChannelId("catering");
        storeFrontProduct.setOfferingId("web");
        storeFrontProduct.setProductId(productId);
        storeFrontProductList.add(storeFrontProduct);
        productStoreResponse = new StoreFrontProductResponse();
        productStoreResponse.setStoreFrontProductList(storeFrontProductList);
    }

    /**
     * test to validate get stores
     */
    @Test
    public void getStoresSuccess() throws ParseException {
        Date releaseDate = new SimpleDateFormat(dateFormat).parse(sDate1);
        locationSearchCriteria.setProductId(productId);
        locationSearchCriteria.setReleasteDate(releaseDate);
        locationSearchCriteria.setSubRecordStatusId(recordStatusIds);

        Mockito.when(storeDao.getStoreOfferingsChannelByProductId(productId, locationSearchCriteria.getReleasteDate(), recordStatusIds)).thenReturn(storeOfferingChannelProductDtos);
        Mockito.when(storeServiceHelper.buildProductStoreOfferingList(storeOfferingChannelProductDtos)).thenReturn(storeOfferingResponses);
        Mockito.when(storeServiceHelper.buildResponse(storeOfferingResponses, locationSearchCriteria)).thenReturn(productStoreDetail);

        ProductStoreResponse response = storeService.getStores(locationSearchCriteria);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getProductStoreDetail().getLocations());
        Assert.assertNotNull(response.getProductStoreDetail().getLocations().get(0).getOfferingList());
        Assert.assertNotNull(response.getProductStoreDetail().getLocations().get(0).getOfferingList().get(0).getSalesChannelList());

    }

    /**
     * test to validate get product details list
     */
    @Test(expected = NoContentException.class)
    public void testProductDetailListNoContent() {
        LocationSearchCriteria locationSearchCriteria = new LocationSearchCriteria();
        locationSearchCriteria.setReleasteDate(new Date());

        storeService.getStores(locationSearchCriteria);
    }


    /**
     * test to validate getStorefrontProducts
     */
    @Test
    public void getStorefrontProductsSuccess() throws ParseException {
        Date releaseDate = new SimpleDateFormat(dateFormat).parse(sDate1);
        Mockito.when(storeDao.getStoreOfferingsChannelByReleaseDate(releaseDate, recordStatusIds,recordsSize)).thenReturn(storeOfferingChannelProductDtos);
        //Mockito.when(storeDao.populateStorefrontProductPrice(releaseDate, recordStatusIds)).thenReturn(true);
        StoreFrontProductResponse response = storeService.getStorefrontProducts(releaseDate,recordStatusIds,recordsSize);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getStoreFrontProductList());
    }
    /**
     * test to validate get product details list
     */
    @Test(expected = NoContentException.class)
    public void getStorefrontProductsNoContent() throws ParseException {
        Date releaseDate = new SimpleDateFormat(dateFormat).parse(sDate1);
        storeService.getStorefrontProducts(releaseDate,recordStatusIds,recordsSize);
    }

    /**
     * test to validate getStorefrontProducts
     */
    @Test
    public void getStorePublishedProductsSuccess() throws ParseException {
        Mockito.when(storeDao.getStoreOfferingsChannelBySapArticleIdAndStoreId(storeId, sapArticleId, "EA", RecordStatusEnum.PUBLISHED.getStatusId())).thenReturn(storeOfferingChannelProductDtos);
        StoreFrontProductResponse response = storeService.getStorePublishedProducts(storeId, sapArticleId, "EA");
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getStoreFrontProductList());
    }

    @Test(expected = NoContentException.class)
    public void getStorePublishedProductsNoContent() throws ParseException {
        storeService.getStorePublishedProducts(storeId, sapArticleId, "EA");
    }
}

