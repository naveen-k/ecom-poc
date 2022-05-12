package com.test.catalog.sales.client.request.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.test.catalog.sales.client.response.product.StoreFrontProductPrice;
import com.test.catalog.sales.client.util.ValidDate;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@JsonInclude()
public class StoreFrontProductStatus {
    /**
     * The releaseDate .
     */
    @ValidDate(format = "yyyy-MM-dd HH:mm:ssZZZZ")
    String releaseDate;
    /**
     * The product id.
     */
    @NotNull
    private UUID productId;

    /**
     * The item sku.
     */
    @NotNull
    private String itemSku;

    /**
     * The storeId  .
     */
    @NotBlank
    private String storeId;

    /**
     * The offeringId  .
     */
    @NotBlank
    private String offeringId;

    /**
     * The channelId  .
     */
    @NotBlank
    private String channelId;

    /**
     * The recordStatus.
     */
    @NotNull
    @Pattern(regexp = "^(ACTIVE|PUBLISHED|ARCHIVED|PUBLISHED_FAILED|UNPUBLISHED|UNPUBLISHED_FAILED)$")
    private String recordStatus;

    /**
     * The product price .
     */
    @NotNull
    @Valid
    @Size(min = 1)
    private List<StoreFrontProductPrice> priceList;

    /**
     * The status.
     */
    private boolean isSuccess;

    @JsonSetter
    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus.toUpperCase();
    }
}
