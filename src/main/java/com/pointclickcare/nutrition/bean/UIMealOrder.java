package com.pointclickcare.nutrition.bean;

import java.util.Date;
import com.momentum.dms.domain.TrayEventType;

public class UIMealOrder
{
  private Long id;
  private Long patientId;
  private Integer ticketNumber;
  private Long kitchenId;
  private String firstName;
  private String lastName;
  private String mealPeriodName;
  private Long unitId;
  private String unitName;
  private Long bedId;
  private String bedName;
  private Long roomId;
  private String roomName;
  private Integer zone;
  private String deliveryTime;
  private TrayEventType trackingStatus;
  private String timeFromDelivery;
  private String deliveryDateTime;
  private Date statusDate;
  private boolean delivered;
  private boolean nowTray;
  private boolean rushOrder;
  private String statusDateInString;
  private String eventTime;
  private String serviceStyle;
  
  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public Long getPatientId()
  {
    return patientId;
  }

  public void setPatientId(Long patientId)
  {
    this.patientId = patientId;
  }

  public Integer getTicketNumber()
  {
    return ticketNumber;
  }

  public void setTicketNumber(Integer ticketNumber)
  {
    this.ticketNumber = ticketNumber;
  }

  public Long getKitchenId()
  {
    return kitchenId;
  }

  public void setKitchenId(Long kitchenId)
  {
    this.kitchenId = kitchenId;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }

  public String getMealPeriodName()
  {
    return mealPeriodName;
  }

  public void setMealPeriodName(String mealPeriodName)
  {
    this.mealPeriodName = mealPeriodName;
  }

  public Long getUnitId()
  {
    return unitId;
  }

  public void setUnitId(Long unitId)
  {
    this.unitId = unitId;
  }

  public String getUnitName()
  {
    return unitName;
  }

  public void setUnitName(String unitName)
  {
    this.unitName = unitName;
  }

  public Long getBedId()
  {
    return bedId;
  }

  public void setBedId(Long bedId)
  {
    this.bedId = bedId;
  }

  public String getBedName()
  {
    return bedName;
  }

  public void setBedName(String bedName)
  {
    this.bedName = bedName;
  }

  public Long getRoomId()
  {
    return roomId;
  }

  public void setRoomId(Long roomId)
  {
    this.roomId = roomId;
  }

  public String getRoomName()
  {
    return roomName;
  }

  public void setRoomName(String roomName)
  {
    this.roomName = roomName;
  }

  public Integer getZone()
  {
    return zone;
  }

  public void setZone(Integer zone)
  {
    this.zone = zone;
  }

  public String getDeliveryTime()
  {
    return deliveryTime;
  }

  public void setDeliveryTime(String deliveryTime)
  {
    this.deliveryTime = deliveryTime;
  }

  public Date getStatusDate()
  {
    return statusDate;
  }

  public void setStatusDate(Date statusDate)
  {
    this.statusDate = statusDate;
  }

  public TrayEventType getTrackingStatus()
  {
    return trackingStatus;
  }

  public void setTrackingStatus(TrayEventType trackingStatus)
  {
    this.trackingStatus = trackingStatus;
  }

  public String getTimeFromDelivery()
  {
    return timeFromDelivery;
  }

  public void setTimeFromDelivery(String timeFromDelivery)
  {
    this.timeFromDelivery = timeFromDelivery;
  }

  public boolean isDelivered()
  {
    return delivered;
  }

  public void setDelivered(boolean delivered)
  {
    this.delivered = delivered;
  }

  public String getDeliveryDateTime()
  {
    return deliveryDateTime;
  }

  public void setDeliveryDateTime(String deliveryDateTime)
  {
    this.deliveryDateTime = deliveryDateTime;
  }

  public boolean isNowTray() 
  {
	return nowTray;
  }

  public void setNowTray(boolean nowTray) 
  {
	this.nowTray = nowTray;
  }

  public boolean isRushOrder() 
  {
	return rushOrder;
  }

  public void setRushOrder(boolean rushOrder) 
  {
	this.rushOrder = rushOrder;
  }

public String getStatusDateInString() {
	return statusDateInString;
}

public void setStatusDateInString(String statusDateInString) {
	this.statusDateInString = statusDateInString;
}

public String getEventTime() {
	return eventTime;
}

public void setEventTime(String eventTime) {
	this.eventTime = eventTime;
}

public String getServiceStyle()
{
  return serviceStyle;
}

public void setServiceStyle(String serviceStyle)
{
  this.serviceStyle = serviceStyle;
}
  
  
  
}
