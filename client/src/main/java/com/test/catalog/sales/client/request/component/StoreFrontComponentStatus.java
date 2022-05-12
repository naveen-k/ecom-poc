package com.test.catalog.sales.client.request.component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.test.catalog.sales.client.util.ValidDate;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Data
@NoArgsConstructor
@JsonInclude()
public class StoreFrontComponentStatus {
    /**
     * The releaseDate .
     */
    @ValidDate(format = "yyyy-MM-dd HH:mm:ssZZZZ")
    String releaseDate;
    /**
     * The component id.
     */
    @NotNull
    private UUID componentId;

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


    @JsonSetter
    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus.toUpperCase();
    }
}
