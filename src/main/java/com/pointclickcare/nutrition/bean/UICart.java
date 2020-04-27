package com.pointclickcare.nutrition.bean;

import java.time.LocalTime;
import java.util.List;

import com.momentum.dms.domain.CartStatus;

public class UICart
{
  private Long id;
  private String status= CartStatus.IN_PROGRESS.getName();
  private Integer zone;
  private List<UIMealOrder> mealOrders;
  private Long mealOrderId;
  private String firstMealOrderTime;
  private String minimumDeliveryTime;
  
public Long getId()
  {
    return id;
  }
  public void setId(Long id)
  {
    this.id = id;
  }
  public String getStatus()
  {
    return status;
  }
  public void setStatus(String status)
  {
    this.status = status;
  }
  public Integer getZone()
  {
    return zone;
  }
  public void setZone(Integer zone)
  {
    this.zone = zone;
  }
  public List<UIMealOrder> getMealOrders()
  {
    return mealOrders;
  }
  public void setMealOrders(List<UIMealOrder> mealOrders)
  {
    this.mealOrders = mealOrders;
  }
  public Long getMealOrderId() 
  {
	return mealOrderId;
  }
  public void setMealOrderId(Long mealOrderId) 
  {
	this.mealOrderId = mealOrderId;
  }

  public String getFirstMealOrderTime()
  {
    return firstMealOrderTime;
  }

  public void setFirstMealOrderTime(String firstMealOrderTime)
  {
    this.firstMealOrderTime = firstMealOrderTime;
  }
	
  public String getMinimumDeliveryTime() 
  {
	return minimumDeliveryTime;
  }
	
  public void setMinimumDeliveryTime(String minimumDeliveryTime) 
  {
	this.minimumDeliveryTime = minimumDeliveryTime;
  }
}
