package com.pointclickcare.nutrition.bean;

public class UIFacility
{
  private Long id;
  private String name;
  private boolean selected;
  
  public Long getId()
  {
    return id;
  }
  public String getName()
  {
    return name;
  }
  public void setId(Long id)
  {
    this.id = id;
  }
  public void setName(String name)
  {
    this.name = name;
  }
  public boolean isSelected()
  {
    return selected;
  }
  public void setSelected(boolean selected)
  {
    this.selected = selected;
  }
}
