package com.pointclickcare.nutrition.util;

import com.momentum.dms.HasName;

public enum SortType implements HasName
{

  // these events need to be in proper workflow sequence
  UNIT_ROOM_BED("UNIT_ROOM_BED"), DELIVERY_DATE_TIME("DELIVERY_DATE_TIME");

  private String name;

  private SortType(final String name)
  {
    this.name = name;
  }

  public String getName()
  {
    return name;
  }

}
