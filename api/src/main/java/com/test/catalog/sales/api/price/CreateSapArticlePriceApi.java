package com.test.catalog.sales.api.price;

import com.test.catalog.sales.api.constant.HttpMessageEnum;
import com.test.catalog.sales.client.request.price.SapArticlePriceRequest;
import com.test.catalog.sales.client.response.HttpResponseEntity;
import com.test.catalog.sales.client.response.Status;
import com.test.catalog.sales.domain.service.price.SapArticlePriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.text.ParseException;

/**
 * this is the controller class to handle creation of Sap Article Price
 */
@RestController
@RequestMapping(value = "${salescatalog.relative.path}", produces = "application/json")
public class CreateSapArticlePriceApi {

    private static final Logger LOG = LoggerFactory.getLogger(CreateSapArticlePriceApi.class);

    private final SapArticlePriceService sapArticlePriceService;

    @Autowired
    public CreateSapArticlePriceApi(SapArticlePriceService sapArticlePriceService) {
        this.sapArticlePriceService = sapArticlePriceService;
    }

    /**
     * This method call the SapArticlePriceService service impl to create SapArticlePrice and update price history table
     *
     * @param sapArticlePriceRequest
     * @return HttpResponseEntity
     */
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping(path = "/prices")
    public HttpResponseEntity<SapArticlePriceRequest> createSapArticlePrice(@Valid @RequestBody final SapArticlePriceRequest sapArticlePriceRequest) throws ParseException {
        LOG.debug("Calling CreateSapArticlePriceApi.createSapArticlePrice ");
        sapArticlePriceService.createSapArticlePrice(sapArticlePriceRequest);
        final Status status =
                new Status(
                        HttpStatus.CREATED, HttpStatus.CREATED.value(), HttpMessageEnum.CREATED_SUCCESS.getMessage());
        return new HttpResponseEntity<>(status, sapArticlePriceRequest);
    }
}
