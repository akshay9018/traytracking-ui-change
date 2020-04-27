package com.pointclickcare.nutrition.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.momentum.dms.domain.UnitMealEventType;
import com.momentum.dms.domain.UnitMealStatus;
import com.pointclickcare.nutrition.JPABean.UMSJPABean;

@Repository
public interface UnitMealStatusRepository extends CrudRepository<UnitMealStatus, Long>
{
  String QUERY_SORT = "order by u.breakfastMealTime, u.lunchMealTime, u.dinnerMealTime, u.name";

  public UnitMealStatus findByUnitIdAndMealNameIdAndEventTypeAndEventTimeGreaterThanEqual(
      Long unitId, Long mealNameId, UnitMealEventType event, Date today);
  
  String FETCH_DELIVERY_UNITS = "SELECT distinct u.id as unitId, u.name as unitName, ums.id as umsId, ums.eventType as umsEvent,"
      + "ums.eventTime as umsEventTime FROM MealOrder mo "
      + "join Kitchen k on mo.kitchen.id = k.id "
      + "join Unit u on u.id = mo.unitId "
      + "LEFT JOIN UnitMealStatus ums ON mo.unitId = ums.unitId AND mo.mealName.id = ums.mealNameId AND ums.eventTime >= :date "
      + "WHERE (k.id=:kitchenId OR (:kitchenId=-1 and k.facility.id=:facilityId)) "
      + "AND mo.mealName.id=:mealNameId "
      + "and mo.deliveryDateTime>=:date "
      + "and mo.deliveryDateTime < :nextDate "
      + "and mo.facilityDeliveryDate = :facilityTodayDate "
      + "and u.unitTracking = true "
      + "and (ums.eventType='Delivered' OR ums.id is null AND mo.orderStatus='PLACED') ";

  @Query(FETCH_DELIVERY_UNITS + QUERY_SORT)
  public List<UMSJPABean> fetchDeliveryUnits(
      @Param("kitchenId") Long kitchenId,
      @Param("mealNameId") Long mealNameId,
      @Param("date") Date today,
      @Param("nextDate") Date nextDate,
      @Param("facilityId") Long facilityId,
      @Param("facilityTodayDate") Date facilityTodayDate);

  String FETCH_RECOVERY_UNITS = "SELECT distinct u.id as unitId, u.name as unitName, ums.id as umsId, ums.eventType as umsEvent,"
      + "ums.eventTime as umsEventTime FROM UnitMealStatus ums JOIN Unit u on u.id = ums.unitId "
      + "JOIN Kitchen k on u.kitchen.id = k.id "
      + "WHERE ums.eventTime >= :date "
      + "AND ums.mealNameId = :mealNameId "
      + "AND (k.id=:kitchenId OR (:kitchenId=-1 AND k.facility.id=:facilityId)) ";

  @Query(FETCH_RECOVERY_UNITS + QUERY_SORT)
  public List<UMSJPABean> fetchRecoveryUnits(
      @Param("kitchenId") Long kitchenId,
      @Param("mealNameId") Long mealNameId,
      @Param("date") Date today,
      @Param("facilityId") Long facilityId);
  
}
