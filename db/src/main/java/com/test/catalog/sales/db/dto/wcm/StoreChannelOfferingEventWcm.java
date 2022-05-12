package com.test.catalog.sales.db.dto.wcm;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreChannelOfferingEventWcm {
     String id;
     String source;
     String specversion;
     String eventversion;
     String type;
     String datacontenttype;
     String subject;
     String time;
     String tracecontext;
     String traceparent;
     DataWcm  data;


}
