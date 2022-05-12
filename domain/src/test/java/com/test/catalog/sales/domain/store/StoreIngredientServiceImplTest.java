package com.test.catalog.sales.domain.store;

import com.test.catalog.sales.client.request.component.StoreFrontComponentStatus;
import com.test.catalog.sales.client.response.component.StoreFrontComponentStatusResponse;
import com.test.catalog.sales.client.response.ingredient.StoreFrontIngredientResponse;
import com.test.catalog.sales.db.constant.RecordStatusEnum;
import com.test.catalog.sales.db.dao.store.StoreDao;
import com.test.catalog.sales.db.dto.StoreComponentDto;
import com.test.catalog.sales.db.dto.StoreOfferingChannelComponentDto;
import com.test.catalog.sales.domain.service.store.impl.StoreIngredientServiceImpl;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class StoreIngredientServiceImplTest {

    @InjectMocks
    StoreIngredientServiceImpl storeIngredientService;

    @Mock
    StoreDao storeDao;

    private String storeId = "5102";
    private UUID ingredientId = UUID.randomUUID();
    private String offeringId = "cateringLite";
    private String channelId = "store";
    private String sDate = "2020-07-11 12:30:00-0000";
    private String dateFormat = "yyyy-MM-dd HH:mm:ssZZZZ";
    Date releaseDate = null;
    String nextState = null;

    StoreOfferingChannelComponentDto storeOfferingChannelComponentDto = new StoreOfferingChannelComponentDto();
    List<StoreOfferingChannelComponentDto> storeOfferingChannelComponentDtos = new ArrayList<>();
    List<StoreFrontComponentStatus> storeFrontComponentStatuses = new ArrayList<>();
    StoreComponentDto storeComponentDto = new StoreComponentDto();

    @Before
    public void setup() throws ParseException {
        releaseDate = new SimpleDateFormat(dateFormat).parse(sDate);
        storeOfferingChannelComponentDto.setComponentId(ingredientId);
        storeOfferingChannelComponentDto.setStoreId(storeId);
        storeOfferingChannelComponentDto.setChannelId(channelId);
        storeOfferingChannelComponentDto.setOfferingId(offeringId);
        storeOfferingChannelComponentDtos.add(storeOfferingChannelComponentDto);
        nextState = "PUBLISHED";

        StoreFrontComponentStatus storeFrontComponentStatus = new StoreFrontComponentStatus();
        storeFrontComponentStatus.setChannelId("Channel1");
        storeFrontComponentStatus.setComponentId(UUID.randomUUID());
        storeFrontComponentStatus.setOfferingId("Offering1");
        storeFrontComponentStatus.setRecordStatus("Published");
        storeFrontComponentStatus.setStoreId("279");
        storeFrontComponentStatus.setReleaseDate("2020-17-9");
        storeFrontComponentStatuses.add(storeFrontComponentStatus);


        storeComponentDto.setChannelId(storeFrontComponentStatus.getChannelId());
        storeComponentDto.setComponentId(storeFrontComponentStatus.getComponentId());
        storeComponentDto.setOfferingId(storeFrontComponentStatus.getOfferingId());
        storeComponentDto.setReleaseDate(storeFrontComponentStatus.getReleaseDate());
        storeComponentDto.setStoreId(storeFrontComponentStatus.getStoreId());
        storeComponentDto.setRecordStatusId(RecordStatusEnum.valueOfLabel(storeFrontComponentStatus.getRecordStatus()).getStatusId());
    }

    @Test
    public void getStorefrontIngredientsSuccess(){
        Mockito.when(storeDao.getStoreComponentItem(releaseDate, RecordStatusEnum.ACTIVE.getStatusId(), RecordStatusEnum.ACTIVE.getStatusId(),RecordStatusEnum.PUBLISHED.getStatusId(), 5)).thenReturn(storeOfferingChannelComponentDtos);
        StoreFrontIngredientResponse response = storeIngredientService.getStorefrontIngredients(nextState, releaseDate, 5);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getStoreFrontIngredientList());
    }

    @Test
    public void getPublishedStorefrontIngredientsSuccess(){
        nextState = "UNPUBLISHED";
        Mockito.when(storeDao.getStoreComponentItem(releaseDate, RecordStatusEnum.PUBLISHED.getStatusId(), RecordStatusEnum.ACTIVE.getStatusId(),RecordStatusEnum.PUBLISHED.getStatusId(), 5)).thenReturn(storeOfferingChannelComponentDtos);
        StoreFrontIngredientResponse response = storeIngredientService.getStorefrontIngredients(nextState, releaseDate, 5);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getStoreFrontIngredientList());
    }

    @Test
    public void testUpdateStoreFrontComponent(){
        Mockito.when(storeDao.updateStoreFrontComponent(storeComponentDto)).thenReturn(storeComponentDto);
        StoreFrontComponentStatusResponse response = storeIngredientService.updateStoreFrontComponent(storeFrontComponentStatuses);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getStateList());
        Assert.assertNotNull(response.getStateList().get(0));
    }
}
