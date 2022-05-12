package com.test.catalog.sales.domain.store;

import com.test.catalog.sales.client.request.product.StoreFrontProductStatus;
import com.test.catalog.sales.client.request.product.StoreFrontProductStatusRequest;
import com.test.catalog.sales.client.request.store.OfferingsChannel;
import com.test.catalog.sales.client.request.store.StoreChannel;
import com.test.catalog.sales.client.request.store.StoreLocationRequest;
import com.test.catalog.sales.client.response.store.StoreLocationDetail;
import com.test.catalog.sales.client.response.store.StoreLocationResponse;
import com.test.catalog.sales.db.constant.RecordStatusEnum;
import com.test.catalog.sales.db.dao.store.StoreDao;
import com.test.catalog.sales.db.dao.store.StoreFrontStatusDao;
import com.test.catalog.sales.db.dto.StoreDto;
import com.test.catalog.sales.db.dto.StoreFrontProductStatusDto;
import com.test.catalog.sales.db.dto.StoreOfferingChannelDto;
import com.test.catalog.sales.domain.helper.StoreServiceHelper;
import com.test.catalog.sales.domain.service.store.impl.StoreLocationServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class StoreLocationServiceImplTest {

    @InjectMocks
    private StoreLocationServiceImpl storeLocationService;
    @Mock
    private StoreDao storeDao;
    @Mock
    private StoreServiceHelper storeServiceHelper;

    @Mock
    StoreFrontStatusDao storeFrontStatusDao;

    private String storeId = "00322";
    private String storeName = "test, STORE # 0322";
    private String offeringId = "cateringLite";
    private StoreDto storeDto = null;
    private StoreDto newStoreDto = null;
    private List<OfferingsChannel> offeringsChannels = new ArrayList<>();
    private List<StoreChannel> storeChannels = new ArrayList<>();
    private List<StoreOfferingChannelDto> storeOfferingChannelDtos = new ArrayList<>();
    StoreLocationDetail storeLocationDetail = new StoreLocationDetail();
    StoreLocationRequest storeLocationRequest = new StoreLocationRequest();
    StoreOfferingChannelDto storeOfferingChannelDto = new StoreOfferingChannelDto();

    StoreOfferingChannelDto finalStoreOfferingChannelDto = new StoreOfferingChannelDto();

    List<StoreOfferingChannelDto> finalStoreOfferingChannelDtos = new ArrayList<>();
    private StoreFrontProductStatusRequest request;
    @Before
    public void setup() {
        storeDto = new StoreDto();
        storeDto.setStoreId(storeId);
        storeDto.setStoreName(storeName);
        storeDto.setRecordStatusId(5);

        storeOfferingChannelDto.setOfferingId(offeringId);
        storeOfferingChannelDto.setStoreId(storeId);
        storeOfferingChannelDto.setChannelId("WEB");
        storeOfferingChannelDto.setRecordStatusId(5);

        storeOfferingChannelDtos.add(storeOfferingChannelDto);

        finalStoreOfferingChannelDtos.add(finalStoreOfferingChannelDto);

        OfferingsChannel offeringsChannel = new OfferingsChannel();
        offeringsChannel.setOfferingId(offeringId);
        StoreChannel storeChannel = new StoreChannel();
        storeChannel.setChannelId("WEB");
        storeChannels.add(storeChannel);
        offeringsChannel.setSalesChannelList(storeChannels);
        offeringsChannels.add(offeringsChannel);

        storeLocationRequest.setStoreId(storeId);
        storeLocationRequest.setOfferings(offeringsChannels);
        storeLocationRequest.setStoreName(storeName);
        storeLocationRequest.setRecordStatusId(5);

        storeLocationDetail.setStoreId(storeId);
        storeLocationDetail.setStorename(storeName);
        storeLocationDetail.setOfferings(offeringsChannels);
        storeLocationDetail.setRecordStatusId(5);

        request= new StoreFrontProductStatusRequest();
        StoreFrontProductStatus item= new StoreFrontProductStatus();
        item.setProductId(UUID.randomUUID());
        item.setStoreId("9999");
        item.setOfferingId("inStore");
        item.setChannelId("web");
        item.setRecordStatus(RecordStatusEnum.PUBLISHED.getStatusName());
        item.setPriceList(Collections.emptyList());
        List<StoreFrontProductStatus> stateList = new ArrayList<>();
        stateList.add(item);
        request.setStateList(stateList);
    }

    @Test
    public void createStoreOfferingChannelAndStoreSuccess() {
        Mockito.when(storeDao.getLatestStore(storeId)).thenReturn(storeDto);
        Mockito.when(storeDao.getLatestStoreOfferingChannel(storeId, offeringId, "WEB")).thenReturn(storeOfferingChannelDto);
        Mockito.when(storeDao.createStore(storeDto)).thenReturn(storeDto);
        Mockito.when(storeDao.createStoreOfferingChannel(storeOfferingChannelDtos)).thenReturn(storeOfferingChannelDtos);
        Mockito.when(storeServiceHelper.mapRequestToStoreOfferingChannelDto(storeLocationRequest)).thenReturn(storeOfferingChannelDtos);
        Mockito.when(storeServiceHelper.mapToStoreLocationDetail(storeDto, storeOfferingChannelDtos)).thenReturn(storeLocationDetail);

        StoreLocationResponse response = storeLocationService.createStoreLocation(storeLocationRequest);

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getStoreLocationDetails().getStoreId());
        Assert.assertNotNull(response.getStoreLocationDetails().getOfferings().get(0).getOfferingId());
        Assert.assertNotNull(response.getStoreLocationDetails().getOfferings().get(0).getSalesChannelList());
    }

    @Test
    public void createStoreOfferingChannelStoreLocationRequestIsNull() {
        StoreLocationRequest storeLocationRequest = null;
        StoreLocationResponse response = storeLocationService.createStoreLocation(storeLocationRequest);
        Assert.assertNull(response.getStoreLocationDetails());
    }

    @Test
    public void testPrepareStoreFrontProductStatusDto() {
        StoreFrontProductStatus item= new StoreFrontProductStatus();
        item.setProductId(UUID.randomUUID());
        item.setStoreId("9999");
        item.setOfferingId("inStore");
        item.setChannelId("web");
        item.setRecordStatus(RecordStatusEnum.PUBLISHED.getStatusName());
        item.setPriceList(Collections.emptyList());
        StoreFrontProductStatusDto response = storeLocationService.prepareStoreFrontProductStatusDto(item);
        Assert.assertNotNull(response);
    }

    @Test
    public void testUpdateStoreFrontStatus() {
        StoreFrontProductStatusDto rtn =  new StoreFrontProductStatusDto();
        Mockito.when(storeFrontStatusDao.updateStoreFrontStatus(Mockito.any())
        ).thenReturn(rtn);

        StoreFrontProductStatusRequest response = storeLocationService.updateStoreFrontStatus(request);
        Assert.assertNotNull(response);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateStoreFrontStatusFail() {
        StoreFrontProductStatusRequest response = storeLocationService.updateStoreFrontStatus(request);
        Assert.assertNotNull(response);
    }

}
