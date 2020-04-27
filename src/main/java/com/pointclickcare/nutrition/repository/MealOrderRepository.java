package com.pointclickcare.nutrition.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.momentum.dms.domain.MealOrder;
import com.momentum.dms.domain.ServiceStyleType;
import com.momentum.dms.domain.TrayEventType;
import com.pointclickcare.nutrition.JPABean.CartMealOrderJPABean;

@Repository
public interface MealOrderRepository extends CrudRepository<MealOrder, Long>
{
  String queryStart = "SELECT mo.batchPrint AS batchPrint, mo.rushOrder AS rushOrder, mo.deliveryDateTime as deliveryDateTime, mo.ticketNumber AS ticketNumber, mo.id AS mealOrderId, "
      + "mo.trackingStatusChangedOn as statusDate, mo.trackingStatus as trackingStatus, "
      + "b.id AS bedId, b.name AS bedName, b.room.id AS roomId, b.room.name AS roomName, b.room.unit.id AS unitId, "
      + "b.room.unit.name AS unitName, b.room.unit.zone AS unitZone "
      + "FROM MealOrder mo"
      + " inner join mo.kitchen kitchen inner join kitchen.facility facility "
      + " LEFT JOIN mo.trayTickets tt left join Bed b on tt.bedId=b.id "
      + "where mo.trackingStatus= :status and date(mo.deliveryDateTime)= :date ";
  String showAll = " and facility.id= :facilityId ";
  String showAllKitchens = " and facility.id= :facilityId and kitchen.serviceStyle= :serviceStyle ";

  String deliveryRecoveredUndoQuery = "SELECT mo.batchPrint AS batchPrint, mo.rushOrder AS rushOrder, mo.deliveryDateTime as deliveryDateTime, mo.ticketNumber AS ticketNumber, mo.id AS mealOrderId, "
      + "mo.trackingStatusChangedOn as statusDate, mo.trackingStatus as trackingStatus, "
      + "b.id AS bedId, b.name AS bedName, b.room.id AS roomId, b.room.name AS roomName, b.room.unit.id AS unitId, "
      + "b.room.unit.name AS unitName, b.room.unit.zone AS unitZone, ts.eventTime as deliveredDateTime "
      + " FROM MealOrder mo "
      + " inner join mo.kitchen kitchen inner join kitchen.facility facility "
      + " LEFT JOIN mo.trayTickets tt left join Bed b on tt.bedId=b.id "
      + " LEFT JOIN TrayStatus ts ON ts.mealOrderId = mo.id AND ts.trayEventType = 'DELIVERED' "
      + "where (mo.trackingStatus= :currentStatus or "
      + "(mo.trackingStatus = :nextStatus and mo.trackingStatusChangedOn >= :trackingStatusDate) ) "
      + "and date(mo.deliveryDateTime)= :date ";

  @Query("select MAX(ts.eventTime) from MealOrder mo LEFT JOIN mo.trayStatusSet ts where mo.id = :mealOrderId")
  public java.util.Date findLastInsertedScanEventTime(@Param("mealOrderId") Long mealOrderId);

  @Query("select MIN(ts.eventTime) from MealOrder mo LEFT JOIN mo.trayStatusSet ts where mo.id = :mealOrderId and ts.trayEventType = :trayEventType")
  public java.util.Date findLastTicketPrintTime(@Param("mealOrderId") Long mealOrderId,
      @Param("trayEventType") TrayEventType trayEventType);

  @Query(queryStart + " and mo.unitId = :unitId ")
  public List<CartMealOrderJPABean> findByTrackingStatusAndFacilityDeliveryDateAndUnitId(
      @Param("status") TrayEventType trayEvent, @Param("date") java.sql.Date sqlDate,
      @Param("unitId") Long unitId);

  @Query(queryStart + " and mo.kitchen.id = :kitchenId ")
  public List<CartMealOrderJPABean> findByTrackingStatusAndFacilityDeliveryDateAndKitchenId(
      @Param("status") TrayEventType trayEvent, @Param("date") java.sql.Date sqlDate,
      @Param("kitchenId") Long kitchenId);

  @Query(queryStart + showAllKitchens)
  public List<CartMealOrderJPABean> findByTrackingStatusAndFacilityDeliveryDateAndServiceStyleAndFacilityId(
      @Param("status") TrayEventType trayEvent, @Param("date") java.sql.Date sqlDate,
      @Param("serviceStyle") ServiceStyleType serviceStyle, @Param("facilityId") Long facilityId);

  @Query(deliveryRecoveredUndoQuery + " and mo.unitId = :unitId ")
  public List<CartMealOrderJPABean> findDeliveredTicketsByUnitId(
      @Param("currentStatus") TrayEventType trayTrackingType,
      @Param("nextStatus") TrayEventType recovered, @Param("unitId") Long unitId,
      @Param("date") java.sql.Date sqlDate, @Param("trackingStatusDate") java.util.Date date);

  @Query(deliveryRecoveredUndoQuery + " and facility.id = :facilityId ")
  public List<CartMealOrderJPABean> findDeliveredTicketsByFacilityId(
      @Param("currentStatus") TrayEventType trayTrackingType,
      @Param("nextStatus") TrayEventType recovered, @Param("facilityId") Long facilityId,
      @Param("date") java.sql.Date sqlDate, @Param("trackingStatusDate") java.util.Date date);

  @Query(queryStart + showAll)
  public List<CartMealOrderJPABean> findByTrackingStatusAndFacilityDeliveryDateAndFacilityId(
      @Param("status") TrayEventType trayEvent, @Param("date") java.sql.Date sqlDate,
      @Param("facilityId") Long facilityId);

  @Query(nativeQuery = true, value = "CALL FetchTicketPrintedMealOrders(:date, :unitId, :kitchenId, :serviceStyle, :facilityId, :mealNameId, :facilityDeliveryDate)")
  public List<CartMealOrderJPABean> fetchOutCartMealOrders(@Param("date") java.sql.Date sqlDate,
      @Param("unitId") Long unitId,
      @Param("kitchenId") Long kitchenId,
      @Param("serviceStyle") String serviceStyle,
      @Param("facilityId") Long facilityId,
      @Param("mealNameId") Long mealNameId,
      @Param("facilityDeliveryDate") java.sql.Date facilityDeliveryDate);

}
