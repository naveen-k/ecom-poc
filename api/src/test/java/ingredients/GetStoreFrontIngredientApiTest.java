package ingredients;

import com.test.catalog.sales.api.ingredient.GetStoreFrontIngredientApi;
import com.test.catalog.sales.client.response.HttpResponseEntity;
import com.test.catalog.sales.client.response.ingredient.StoreFrontIngredient;
import com.test.catalog.sales.client.response.ingredient.StoreFrontIngredientResponse;
import com.test.catalog.sales.client.response.product.StoreFrontProductPrice;
import com.test.catalog.sales.db.constant.RecordStatusEnum;
import com.test.catalog.sales.domain.service.store.StoreIngredientService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class GetStoreFrontIngredientApiTest {

    @InjectMocks
    GetStoreFrontIngredientApi getStoreFrontIngredientApi;

    @Mock
    StoreIngredientService storeIngredientService;

    StoreFrontIngredientResponse storeFrontIngredientResponse = new StoreFrontIngredientResponse();
    Set<StoreFrontIngredient> storeFrontIngredientList = new HashSet<>();
    StoreFrontIngredient storeFrontIngredient = new StoreFrontIngredient();
    private String sDate = "2020-07-11 12:30:00-0000";
    private String dateFormat = "yyyy-MM-dd HH:mm:ssZZZZ";
    List<StoreFrontProductPrice> storeFrontProductPriceList = new ArrayList<>();
    StoreFrontProductPrice storeFrontProductPrice = new StoreFrontProductPrice();

    @Before
    public void  setup(){
        UUID productId = UUID.randomUUID();
        storeFrontProductPrice.setPrice(new BigDecimal(0));
        storeFrontProductPriceList.add(storeFrontProductPrice);
        storeFrontIngredient.setIngredientId(productId);
        storeFrontIngredient.setStoreId("5102");
        storeFrontIngredient.setOfferingId("cateringLite");
        storeFrontIngredient.setChannelId("store");
        storeFrontIngredient.setPriceList(storeFrontProductPriceList);

    }

    /**
     * Test to validate get storeFrontIngredients
     */
    @Test
    public void getStoreFrontIngredients() throws ParseException {
        Date releaseDate = new SimpleDateFormat(dateFormat).parse(sDate);
        //Mockito.when(productStoreService.getStorefrontProducts(releaseDate, recordStatusId)).thenReturn(productStoreResponse);
        HttpResponseEntity<StoreFrontIngredientResponse> responseEntity =
                getStoreFrontIngredientApi.getStoreFrontIngredients(RecordStatusEnum.PUBLISHED.getStatusName(),releaseDate, 5);
        Assert.assertNotNull(responseEntity);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatus().getHttpStatus());
    }

    /**
     * Test to validate get of storeFrontIngredients incorrect date format
     */
    @Test(expected = ParseException.class)
    public void validateDateExceptionResponse() throws ParseException {
        String sDate1 = "2020-06-11 12";
        Date releaseDate = new SimpleDateFormat(dateFormat).parse(sDate1);
    }
}
