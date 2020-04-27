package com.pointclickcare.nutrition.JPABean;

import java.util.Date;

import com.momentum.dms.domain.UnitMealEventType;

public interface UMSJPABean
{
  Long getUnitId();

  String getUnitName();

  Long getUmsId();

  UnitMealEventType getUmsEvent();

  Date getUmsEventTime();
}
