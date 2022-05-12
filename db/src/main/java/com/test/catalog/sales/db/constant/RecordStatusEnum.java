package com.test.catalog.sales.db.constant;

public enum RecordStatusEnum {
  ACTIVE(2, "Active"),
  PUBLISHED(4, "Published"),
  PUBLISHED_FAILED(3, "Published_Failed"),
  ARCHIVED(5, "Archived"),
  UNPUBLISHED(6, "UnPublished"),
  UNPUBLISHED_FAILED(7, "UnPublished_Failed");

  private final int statusId;

  private final String statusName;

  RecordStatusEnum(int statusId, String statusName) {
    this.statusId = statusId;
    this.statusName = statusName;
  }

  public int getStatusId() {
    return statusId;
  }
  public String getStatusName() {
    return statusName;
  }
  public static RecordStatusEnum valueOfLabel(String label) {
    for (RecordStatusEnum e : values()) {
      if (e.statusName.equalsIgnoreCase(label)) {
        return e;
      }
    }
    return null;
  }
}
