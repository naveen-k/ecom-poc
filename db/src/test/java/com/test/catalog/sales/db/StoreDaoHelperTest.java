package com.test.catalog.sales.db;

import com.test.catalog.sales.db.dto.PriceStatusHistoryDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class StoreDaoHelperTest {
    @InjectMocks
    StoreDaoHelper storeDaoHelper;
    PriceStatusHistoryDto priceStatusHistoryDto;
    Date releasedDate;
    @Mock
    private EntityManager entityManager;
    private List<Integer> recordStatusIds;

    @Before
    public void setUp() throws Exception {
        recordStatusIds = new ArrayList<Integer>(Arrays.asList(2, 4));
        releasedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZZZZ").parse("2020-06-01 13:33:45-0000");
        priceStatusHistoryDto = new PriceStatusHistoryDto();
    }

    @Test(expected = NullPointerException.class)
    public void populateStorefrontProductPrice() {
        Mockito.when(storeDaoHelper.populateStorefrontProductPrice(entityManager, releasedDate, recordStatusIds))
                .thenReturn(true);
        boolean rtn = storeDaoHelper.populateStorefrontProductPrice(entityManager, releasedDate, recordStatusIds);
        Assert.assertEquals(rtn, true);
    }
}