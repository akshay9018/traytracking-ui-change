package com.pointclickcare.nutrition.bean;

public class UIFacilityMealName
{
  private Long mealNameId;
  private String name;
  public UIFacilityMealName(Long mealNameId, String name)
  {
    this.mealNameId = mealNameId;
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
  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }
}
