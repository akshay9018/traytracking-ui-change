package com.pointclickcare.nutrition.JPABean;

import java.util.Date;

import com.momentum.dms.domain.TrayEventType;

public interface CartMealOrderJPABean
{
  Long getId();

  Integer getZone();

  String getCartStatus();

  Integer getTicketNumber();

  Long getMealOrderId();

  Long getBedId();

  String getBedName();

  Long getRoomId();

  String getRoomName();

  Long getUnitId();

  String getUnitName();

  Integer getUnitZone();
  
  Date getDeliveryDateTime();
  
  Date getStatusDate();
  
  TrayEventType getTrackingStatus();
  
  Date getInCartDateTime();
  
  Date getDeliveredDateTime();
  
  Boolean getBatchPrint();
  
  Boolean getRushOrder();
  
  Long getKitchenId();
  
  String getServiceStyle();
}
