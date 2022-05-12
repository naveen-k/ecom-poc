package com.test.catalog.sales.domain.price;

import com.test.catalog.sales.client.request.price.SapArticlePriceRequest;
import com.test.catalog.sales.client.request.price.UpdatePriceHistoryStatus;
import com.test.catalog.sales.client.response.price.UpdatePriceHistoryStatusResponse;
import com.test.catalog.sales.db.constant.RecordStatusEnum;
import com.test.catalog.sales.db.dao.price.SapArticlePriceDao;
import com.test.catalog.sales.db.dto.PriceStatusHistoryDto;
import com.test.catalog.sales.db.dto.SapArticlePriceDto;
import com.test.catalog.sales.db.util.CatalogUtilities;
import com.test.catalog.sales.domain.service.price.impl.SapArticlePriceServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class SapArticlePriceServiceImplTest {

    @InjectMocks
    SapArticlePriceServiceImpl sapArticlePriceService;

    @Mock
    SapArticlePriceDao sapArticlePriceDao;

    @Mock
    CatalogUtilities catalogUtilities;

    SapArticlePriceRequest sapArticlePriceRequest;
    SapArticlePriceDto sapArticlePriceDto;

    List<UpdatePriceHistoryStatus> updatePriceHistoryStatusList = new ArrayList<>();
    PriceStatusHistoryDto dto = new PriceStatusHistoryDto();



    @Before
    public void setUp() throws Exception {
        sapArticlePriceDto = new SapArticlePriceDto();
        sapArticlePriceDto.setStoreId("279");
        sapArticlePriceDto.setUnitOfMeasureId("UOM2");
        sapArticlePriceDto.setSapArticleId("mdm-p-56_sap_id");
        sapArticlePriceDto.setChannelId("kiosk");
        sapArticlePriceDto.setPrice(new BigDecimal(10.00));
        sapArticlePriceDto.setEffectiveStartDatetime(new Timestamp(1));
        sapArticlePriceDto.setEffectiveEndDatetime(new Timestamp(1));
        sapArticlePriceRequest = new SapArticlePriceRequest();
        sapArticlePriceRequest.setStoreId("279");
        sapArticlePriceRequest.setUom("UOM2");
        sapArticlePriceRequest.setArticleId("mdm-p-56_sap_id");
        sapArticlePriceRequest.setChannelId("kiosk");
        sapArticlePriceRequest.setPrice(new BigDecimal(10.00));
        sapArticlePriceRequest.setBeginEffectiveDate("2020-05-05");
        sapArticlePriceRequest.setEndEffectiveDate("2020-05-07");

        UpdatePriceHistoryStatus status = new UpdatePriceHistoryStatus();
        status.setBeginEffectiveDate("2020-10-11");
        status.setEndEffectiveDate("2020-11-11");
        status.setChannelId("channelId1");
        status.setStoreId("storeId1");
        status.setOfferingId("offeringId1");
        status.setPrice(new BigDecimal(1.0));
        status.setProductId(UUID.randomUUID());
        status.setRecordStatus(RecordStatusEnum.PUBLISHED.name());
        updatePriceHistoryStatusList.add(status);


        dto.setPrice(status.getPrice());
        dto.setChannelId(status.getChannelId());
        try {
            dto.setEffectiveEndDatetime(CatalogUtilities.getTimestampDate(status.getEndEffectiveDate(),
                    CatalogUtilities.DATE_FORMAT));
            dto.setEffectiveStartDatetime(CatalogUtilities.getTimestampDate(status.getBeginEffectiveDate(),
                    CatalogUtilities.DATE_FORMAT));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dto.setStoreId(status.getStoreId());
        dto.setOfferingId(status.getOfferingId());
        dto.setRecordStatusId(RecordStatusEnum.valueOfLabel(status.getRecordStatus()).getStatusId());
        dto.setProductId(status.getProductId());

    }

    @Test
    public void testCreateSapArticlePrice() throws ParseException {
        SapArticlePriceDto sapArticlePriceDto = sapArticlePriceService.createSapArticlePrice(sapArticlePriceRequest);
        Assert.assertNull(sapArticlePriceDto);
    }

    @Test
    public void testUpdatePriceStatusHistory(){
        Mockito.when(sapArticlePriceDao.updatePricingHistory(dto)).thenReturn(dto);
        UpdatePriceHistoryStatusResponse response = sapArticlePriceService.updatePricingHistory(updatePriceHistoryStatusList);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getStatusList());
    }
}
