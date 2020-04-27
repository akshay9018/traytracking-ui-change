package com.pointclickcare.nutrition.bean;

import com.momentum.dms.domain.ServiceStyleType;
public class UIKitchen
{
  private Long id;
  private String name;
  private String serviceStyleId;
  private String serviceStyleName;

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

  public String getServiceStyleId()
  {
    return serviceStyleId;
  }

  public void setServiceStyleId(String serviceStyleId)
  {
    this.serviceStyleId = serviceStyleId;
  }

  public String getServiceStyleName()
  {
    return serviceStyleName;
  }

  public void setServiceStyleName(String serviceStyleName)
  {
    this.serviceStyleName = serviceStyleName;
  }

}
