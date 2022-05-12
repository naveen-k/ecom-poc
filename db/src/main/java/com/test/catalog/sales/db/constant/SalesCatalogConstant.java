package com.test.catalog.sales.db.constant;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SalesCatalogConstant {

  public static final String STOREFRONT_API_CHANNEL = "storefrontApiChannel";

  public static final String AVRO_MIME_TYPE = "application/avro";

  public static final String JSON_MIME_TYPE = "application/json";

  public static final String EVENT_SOURCE_STOREFRONT = "www.test.com/storechanneloffering";

  public static final String EVENT_VERSION = "1";

  public static final String EVENT_SPEC_VERSION = "0.1";

  public static final String EVENT_SUBJECT_STOREFRONT = "storechanneloffering";

  public static final String EVENT_TYPE_STOREFRONT = "storechannelofferingChanged";

  public static final String EVENT_TRACE_PARENT = "traceId";

  public static final String EVENT_TRACE_CONTEXT = "spanId";

}
