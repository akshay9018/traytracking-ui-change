package com.pointclickcare.nutrition.bean;

import java.util.Date;

public class UIUMS
{
  private Long umsId;
  private Long id;
  private String name;
  private Long mealNameId;
  private String eventType;
  private Date eventTime;
  private Long kitchenId;

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public Long getMealNameId()
  {
    return mealNameId;
  }

  public void setMealNameId(Long mealNameId)
  {
    this.mealNameId = mealNameId;
  }

  public String getEventType()
  {
    return eventType;
  }

  public void setEventType(String eventType)
  {
    this.eventType = eventType;
  }

  public Long getUmsId()
  {
    return umsId;
  }

  public void setUmsId(Long umsId)
  {
    this.umsId = umsId;
  }

  public Date getEventTime()
  {
    return eventTime;
  }

  public void setEventTime(Date eventTime)
  {
    this.eventTime = eventTime;
  }

  public Long getKitchenId()
  {
    return kitchenId;
  }

  public void setKitchenId(Long kitchenId)
  {
    this.kitchenId = kitchenId;
  }
  
  
}
