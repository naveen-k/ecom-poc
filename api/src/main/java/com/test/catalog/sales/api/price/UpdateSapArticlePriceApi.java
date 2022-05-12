package com.test.catalog.sales.api.price;

import com.test.catalog.sales.api.constant.HttpMessageEnum;
import com.test.catalog.sales.client.request.price.UpdatePriceHistoryStatusRequest;
import com.test.catalog.sales.client.response.HttpResponseEntity;
import com.test.catalog.sales.client.response.Status;
import com.test.catalog.sales.client.response.price.UpdatePriceHistoryStatusResponse;
import com.test.catalog.sales.domain.service.price.SapArticlePriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * this is the controller class to handle update Price status history
 */
@RestController
@RequestMapping(value = "${salescatalog.relative.path}", produces = "application/json")
public class UpdateSapArticlePriceApi {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateSapArticlePriceApi.class);

    private final SapArticlePriceService sapArticlePriceService;

    /**
     * constructor to initialize service
     * @param sapArticlePriceService
     */
    @Autowired
    public UpdateSapArticlePriceApi(SapArticlePriceService sapArticlePriceService) {
        this.sapArticlePriceService = sapArticlePriceService;
    }

    /**
     * This method call the SapArticlePriceService service impl to update price status history
     *
     * @param request
     * @return HttpResponseEntity
     */
    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping(path = "/prices/state")
    public HttpResponseEntity<UpdatePriceHistoryStatusResponse> updatePricingHistory(
            @Valid @RequestBody final UpdatePriceHistoryStatusRequest request) {
        LOG.debug("Calling UpdateSapArticlePriceApi.updatePricingHistory ");
        UpdatePriceHistoryStatusResponse response = sapArticlePriceService.updatePricingHistory(
                request.getStatusList());
        final Status status = new Status(HttpStatus.OK, HttpStatus.OK.value(),
                HttpMessageEnum.UPDATED_SUCCESS.getMessage());
        return new HttpResponseEntity<>(status, response);
    }
}
