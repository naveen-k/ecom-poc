package com.test.catalog.sales.db.dao.store.impl;

import com.test.catalog.sales.db.dao.price.impl.SapArticlePriceDaoImpl;
import com.test.catalog.sales.db.dto.SapArticlePriceDto;
import com.test.catalog.sales.db.entity.price.SapArticlePriceEntity;
import com.test.catalog.sales.db.mapper.SapArticlePriceMapper;
import com.test.catalog.sales.db.repository.store.SapArticlePriceRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class SapArticlePriceEntityDaoImplTest {
    @Mock
    List<SapArticlePriceDto> sapArticlePriceDtos;
    @InjectMocks
    private SapArticlePriceDaoImpl sapArticlePriceDao;
    @Mock
    private SapArticlePriceRepository sapArticlePriceRepository;
    @Mock
    private SapArticlePriceMapper sapArticlePriceMapper;
    private UUID productId =UUID.randomUUID();
    private String storeId = "069";
    private String itemSku = "itemSku";
    private String offeringId = "web";
    private String channelId = "catering";
    private List<Integer> recordStatusId = new ArrayList<Integer>();
    @Mock
    private List<SapArticlePriceEntity> sapArticlePrices;

    @Before
    public void setUp() throws Exception {
        recordStatusId.add(2);
    }

    @Test
    public void getStorefrontProductPrice() {
        Mockito.when(
                sapArticlePriceRepository.findByProductIdnAndStoreIdAndChannelId(productId, storeId, channelId,offeringId,itemSku,recordStatusId))
                .thenReturn(sapArticlePrices);
        Mockito.when(
                sapArticlePriceMapper.sapArticlePriceEntityListToSapArticlePriceDtoList(sapArticlePrices, new ArrayList<>()))
                .thenReturn(sapArticlePriceDtos);
        List<SapArticlePriceDto>  response =sapArticlePriceDao.getStorefrontProductPrice(productId,storeId,channelId,offeringId,itemSku,recordStatusId);
        Assert.assertNotNull(response);

    }
}