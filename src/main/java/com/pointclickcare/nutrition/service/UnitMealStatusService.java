package com.pointclickcare.nutrition.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.momentum.dms.domain.UnitMealEventType;
import com.momentum.dms.domain.UnitMealStatus;
import com.momentum.dms.util.TimeZoneConvertor;
import com.pointclickcare.nutrition.JPABean.UMSJPABean;
import com.pointclickcare.nutrition.bean.UIUMS;
import com.pointclickcare.nutrition.converter.UMSConverter;
import com.pointclickcare.nutrition.converter.UnitConverter;
import com.pointclickcare.nutrition.repository.UnitMealStatusRepository;
import com.pointclickcare.nutrition.util.TimeUtil;

@Service
@Transactional
public class UnitMealStatusService
{

  @Autowired
  UnitMealStatusRepository unitMealStatusRepository;
  @Autowired
  UnitConverter unitConverter;
  @Autowired
  UMSConverter umsConverter;

  public List<UIUMS> fetchDeliveryUnits(Long facilityId, Long kitchenId, Long mealNameId, String timezone)
  {
    Date today = TimeUtil.getTodayDateToStartOfDay();
    Date facilityTodayDate = TimeZoneConvertor.convertToTimeZoneDate(new Date(),timezone);
    Date nextDate = TimeUtil.addNToDate(today, 2);
    List<UMSJPABean> units = unitMealStatusRepository.fetchDeliveryUnits(kitchenId, mealNameId, today,
        nextDate, facilityId, facilityTodayDate);
    return umsConverter.getUIUnitFromUMS(units);
  }
  
  public List<UIUMS> fetchRecoveryUnits(Long facilityId, Long kitchenId, Long mealNameId)
  {
    Date today = TimeUtil.getTodayDateToStartOfDay();
    List<UMSJPABean> units = unitMealStatusRepository.fetchRecoveryUnits(kitchenId, mealNameId, today, facilityId);
    return umsConverter.getUIUnitFromUMS(units);
  }
  
  public List<UIUMS> markUnitAsGivenEvent(UIUMS uiUMS, Long userId, Long facilityId, String timezone)
  {
    if(UnitMealEventType.DELIVERED.getName().equals(uiUMS.getEventType())){
      return markUnitDelivered(userId, facilityId, uiUMS, timezone);
    } else {
      return markUnitRecovered(userId, facilityId, uiUMS);
    }
  }

  
  private List<UIUMS> markUnitRecovered(Long userId, Long facilityId, UIUMS uiUMS)
  {
    Date today = TimeUtil.getTodayDateToStartOfDay();
    UnitMealStatus ums = unitMealStatusRepository
        .findByUnitIdAndMealNameIdAndEventTypeAndEventTimeGreaterThanEqual(
            uiUMS.getId(), uiUMS.getMealNameId(), UnitMealEventType.DELIVERED, today);
    if (ums != null)
    {
      Date now = new Date();
      ums.setEventTime(now);
      ums.setEventType(UnitMealEventType.RECOVERED);
      ums.setUserId(userId);
      unitMealStatusRepository.save(ums);
    }
    return fetchRecoveryUnits(facilityId, uiUMS.getKitchenId(), uiUMS.getMealNameId());
  }

  private List<UIUMS> markUnitDelivered(Long userId, Long facilityId,
      UIUMS uiUMS, String timezone)
  {
    Date today = TimeUtil.getTodayDateToStartOfDay();
    UnitMealStatus ums = unitMealStatusRepository
        .findByUnitIdAndMealNameIdAndEventTypeAndEventTimeGreaterThanEqual(
            uiUMS.getId(), uiUMS.getMealNameId(), getEventType(uiUMS.getEventType()), today);
    if (ums == null)
    {
      Date now = new Date();
      ums = new UnitMealStatus();
      ums.setEventTime(now);
      ums.setEventType(UnitMealEventType.DELIVERED);
      ums.setMealNameId(uiUMS.getMealNameId());
      ums.setUnitId(uiUMS.getId());
      ums.setUserId(userId);
      unitMealStatusRepository.save(ums);
    }
    return fetchDeliveryUnits(facilityId, uiUMS.getKitchenId(), uiUMS.getMealNameId(), timezone);
  }

  public List<UIUMS> undoMarkedUnit(UIUMS uiUMS, Long facilityId, Long userId, String timezone)
  {
    UnitMealEventType event = getEventType(uiUMS.getEventType());
    if (event.equals(UnitMealEventType.DELIVERED))
    {
      undoDeliveredUnit(uiUMS.getUmsId());
      return fetchDeliveryUnits(facilityId, uiUMS.getKitchenId(), uiUMS.getMealNameId(), timezone);
    }
    else
    {
      undoRecoveredUnit(uiUMS.getUmsId(), userId);
      return fetchRecoveryUnits(facilityId, uiUMS.getKitchenId(), uiUMS.getMealNameId());
    }
  }

  private void undoDeliveredUnit(Long umsId)
  {
    unitMealStatusRepository.deleteById(umsId);
  }
  private void undoRecoveredUnit(Long umsId, Long userId)
  {
    UnitMealStatus ums = unitMealStatusRepository
        .findById(umsId).orElse(null);
    if (ums != null && ums.getEventType().equals(UnitMealEventType.RECOVERED))
    {
      Date now = new Date();
      ums.setEventTime(now);
      ums.setEventType(UnitMealEventType.DELIVERED);
      ums.setUserId(userId);
      unitMealStatusRepository.save(ums);
    }
  }
  
  private UnitMealEventType getEventType(String eventType)
  {
    return Objects.nonNull(eventType) && !eventType.equals("")
        ? UnitMealEventType.getUnitMealEventTypeByName(eventType) : null;
  }
}
