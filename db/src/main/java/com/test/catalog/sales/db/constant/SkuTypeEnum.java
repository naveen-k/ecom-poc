package com.test.catalog.sales.db.constant;

public enum SkuTypeEnum {
  UPSELL("UPSELL"),
  NON_UPSELL( "NOUPSELL");

  private final String skuType;

  SkuTypeEnum( String skuType) {
    this.skuType = skuType;
  }

  public String getSkuType() {
    return skuType;
  }

}
