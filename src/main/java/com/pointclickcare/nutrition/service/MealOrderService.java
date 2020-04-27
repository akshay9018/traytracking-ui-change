package com.pointclickcare.nutrition.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.momentum.dms.domain.MealOrder;
import com.momentum.dms.domain.TrayEventType;
import com.momentum.dms.domain.TrayStatus;
import com.momentum.dms.util.TimeZoneConvertor;
import com.pointclickcare.nutrition.JPABean.CartMealOrderJPABean;
import com.pointclickcare.nutrition.bean.UIMealOrder;
import com.pointclickcare.nutrition.converter.MealOrderConverter;
import com.pointclickcare.nutrition.repository.FacilityRepository;
import com.pointclickcare.nutrition.repository.KitchenRepository;
import com.pointclickcare.nutrition.repository.MealOrderRepository;
import com.pointclickcare.nutrition.repository.UnitRepository;
import com.pointclickcare.nutrition.util.TimeUtil;

@Service
@Transactional
public class MealOrderService
{

  @Autowired
  MealOrderRepository mealOrderRepository;
  @Autowired
  FacilityRepository facilityRepository;
  @Autowired
  KitchenRepository kitchenRepository;
  @Autowired
  UnitRepository unitRepository;
  @Autowired
  MealOrderConverter moConverter;
  @Autowired
  CartService cartService;

  

  public List<UIMealOrder> fetchOutCartMealOrders(Long facilityId, String timezone, String serviceStyle,
      Long kitchenId, Long unitId, TrayEventType trayEvent, Long mealNameId)
  {
    java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
    java.sql.Date facilityTodayDate = new java.sql.Date(TimeZoneConvertor.convertToTimeZoneDate(new Date(),timezone).getTime());
    List<CartMealOrderJPABean> mealOrderList = mealOrderRepository.fetchOutCartMealOrders(sqlDate, unitId, kitchenId, serviceStyle, facilityId, mealNameId, facilityTodayDate);
    return moConverter.getUIMealOrderFromMealOrders(mealOrderList, timezone);
  }
  public List<UIMealOrder> fetchDeliveredMealOrders(Long facilityId, String timezone, Long unitId,
      TrayEventType trayEvent, String sortBy)
  {
    java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
    List<CartMealOrderJPABean> mealOrderList;
    long time = System.currentTimeMillis() - java.time.Duration.ofHours(3).toMillis();
    if (!unitId.equals(-1l))
    {
      mealOrderList = mealOrderRepository
          .findDeliveredTicketsByUnitId(
              trayEvent, TrayEventType.RECOVERED, unitId, sqlDate, new java.sql.Timestamp(time));
    }
    else
    {

      mealOrderList = mealOrderRepository
          .findDeliveredTicketsByFacilityId(
              trayEvent, TrayEventType.RECOVERED, facilityId, sqlDate,
              new java.sql.Timestamp(time));
    }

    return moConverter.getUIMealOrderFromMealOrders(mealOrderList, timezone);
  }

  public MealOrder getMealOrder(Long id)
  {
    MealOrder mo = mealOrderRepository.findById(id).orElse(null);
    Hibernate.initialize(mo.getTrayStatusSet());
    return mo;
  }

  public void markTrayAsDelivered(UIMealOrder uiMealOrder, Long userId, String timezone)
      throws ParseException
  {
    MealOrder mealOrder = mealOrderRepository.findById(uiMealOrder.getId()).orElse(null);
    updateMealOrderTrackingStatus(mealOrder, userId, TrayEventType.DELIVERED,
        uiMealOrder.getEventTime(), timezone);
  }

  public void recoverMealOrder(Long id, Long userId, String eventTime, String timezone)
      throws ParseException
  {
    MealOrder mealOrder = mealOrderRepository.findById(id).orElse(null);
    updateMealOrderTrackingStatus(mealOrder, userId, TrayEventType.RECOVERED, eventTime, timezone);
  }

  public MealOrder updateMealOrderTrackingStatus(MealOrder mo, Long userId,
      TrayEventType trayEventStatus, String eventTime, String timezone) throws ParseException
  {
    if (mo.getTrackingStatus().equals(trayEventStatus))
      return mo;
    Date now = new Date();
    mo.setTrackingStatus(trayEventStatus);
    Date date = null;
    if (eventTime == null)
    {
      mo.setTrackingStatusChangedOn(now);
    }
    else
    {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      date = formatter.parse(eventTime);
      // date = TimeZoneConvertor.convertToTimeZoneDate(date, timezone);
      date = TimeZoneConvertor.convertFromTimeZoneDate(date, timezone);
      mo.setTrackingStatusChangedOn(date);
    }
    TrayStatus ts = updateTrayStatus(mo, now, userId, trayEventStatus, date);
    mo.getTrayStatusSet().add(ts);
    mealOrderRepository.save(mo);
    return mo;
  }

  public MealOrder updateMealOrderTrackingStatus(Long id, Long userId,
      TrayEventType trayEventStatus)
  {
    MealOrder mo = mealOrderRepository.findById(id).orElse(null);
    if (mo.getTrackingStatus().equals(trayEventStatus))
      return mo;
    Date now = new Date();
    mo.setTrackingStatus(trayEventStatus);
    mo.setTrackingStatusChangedOn(now);
    TrayStatus ts = updateTrayStatus(mo, now, userId, trayEventStatus, null);
    mo.getTrayStatusSet().add(ts);
    mealOrderRepository.save(mo);
    return mo;
  }

  public TrayStatus updateTrayStatus(MealOrder mealOrder, Date now, Long userId,
      TrayEventType trayEventStatus, Date eventTimeDate)
  {
    TrayStatus trayStatus = new TrayStatus();
    trayStatus.setMealOrderId(mealOrder.getId());
    trayStatus.setEventTime(eventTimeDate == null ? now : eventTimeDate);
    Short minutesFromPreviousEvent = findMinutesFromPreviousEvent(mealOrder.getId(),
        eventTimeDate == null ? now : eventTimeDate);
    Short minutesFromPrintEvent = findMinutesFromPrintEvent(mealOrder.getId(),
        eventTimeDate == null ? now : eventTimeDate);
    trayStatus.setTimeFromPrevious(minutesFromPreviousEvent);
    trayStatus.setTimeFromPrint(minutesFromPrintEvent);
    trayStatus.setUserId(userId);
    trayStatus.setTrayEventType(trayEventStatus);
    trayStatus.setKitchen(mealOrder.getKitchen());
    return trayStatus;
  }

  private Short findMinutesFromPreviousEvent(long mealOrderId, Date currentTrayEventDate)
  {
    Date previousEventTime = mealOrderRepository.findLastInsertedScanEventTime(mealOrderId);
    return TimeUtil.dateDiffInMin(currentTrayEventDate, previousEventTime);
  }

  private Short findMinutesFromPrintEvent(long mealOrderId, Date currentTrayEventDate)
  {
    Date ticketPrintTime = mealOrderRepository.findLastTicketPrintTime(mealOrderId,
        TrayEventType.TICKET_PRINTED);
    return TimeUtil.dateDiffInMin(currentTrayEventDate, ticketPrintTime);
  }

  public MealOrder updateMealOrderTrackingStatusToPrevious(MealOrder mealOrder,
      Set<TrayStatus> tsSet,
      TrayEventType currentEvent, TrayEventType previousEvent)
  {
    if (mealOrder.getTrackingStatus().equals(previousEvent))
      return mealOrder;
    mealOrder.setTrackingStatus(previousEvent);
    TrayStatus tsToRemove = null;
    for (TrayStatus ts : tsSet)
    {
      if (ts.getTrayEventType().equals(previousEvent))
      {
        mealOrder.setTrackingStatusChangedOn(ts.getEventTime());
      }
      if (ts.getTrayEventType().equals(currentEvent))
      {
        tsToRemove = ts;
      }
    }
    tsSet.remove(tsToRemove);
    mealOrder.setTrayStatusSet(tsSet);
    mealOrderRepository.save(mealOrder);
    return mealOrder;
  }

  public void undoRecoveredMealOrder(Long id)
  {
    MealOrder mo = mealOrderRepository.findById(id).orElse(null);
    updateMealOrderTrackingStatusToPrevious(mo, mo.getTrayStatusSet(), TrayEventType.RECOVERED,
        TrayEventType.DELIVERED);
  }

  public void undoDeliveredMealOrder(Long id)
  {
    MealOrder mo = mealOrderRepository.findById(id).orElse(null);
    updateMealOrderTrackingStatusToPrevious(mo, mo.getTrayStatusSet(), TrayEventType.DELIVERED,
        TrayEventType.DEPARTED);
  }

  public TrayEventType getMealOrderTrayEventType(Long id)
  {
    return mealOrderRepository.findById(id).orElse(null).getTrackingStatus();
  }
}
