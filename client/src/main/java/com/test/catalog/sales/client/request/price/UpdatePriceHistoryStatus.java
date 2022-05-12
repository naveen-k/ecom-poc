package com.test.catalog.sales.client.request.price;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.test.catalog.sales.client.util.ValidDate;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@JsonInclude()
public class UpdatePriceHistoryStatus {

    /**
     * storeId
     */
    @NotBlank
    String storeId;

    /**
     * channelId
     */
    @NotBlank
    String channelId;

    /**
     * offeringId
     */
    @NotBlank
    String offeringId;

    /**
     * productId
     */
    @NotNull
    UUID productId;

    /**
     * itemSku
     */
    @NotBlank
    String itemSku;

    /**
     * The valid from.
     */
    @ValidDate(format = "yyyy-MM-dd")
    private String beginEffectiveDate;

    /**
     * The valid till.
     */
    @ValidDate(format = "yyyy-MM-dd")
    private String endEffectiveDate;

    /**
     * price
     */
    @NotNull
    BigDecimal price;

    /**
     * The recordStatus.
     */
    @NotNull
    @Pattern(regexp = "^(ACTIVE|PUBLISHED|ARCHIVED|PUBLISHED_FAILED|UNPUBLISHED|UNPUBLISHED_FAILED)$")
    private String recordStatus;


    @JsonSetter
    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus.toUpperCase();
    }


}
