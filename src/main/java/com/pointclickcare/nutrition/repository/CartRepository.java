package com.pointclickcare.nutrition.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.momentum.dms.domain.Cart;
import com.pointclickcare.nutrition.JPABean.CartMealOrderJPABean;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long>, CustomCartRepository
{
  public List<Cart> findByStatus(String status);

  String query = "SELECT c.id AS id, c.zone AS zone, c.status as cartStatus,mo.ticket_number AS ticketNumber, mo.id AS mealOrderId,"
      + "b.id AS bedId, b.name AS bedName, r.id AS roomId, r.name AS roomName, u.id AS unitId, u.name AS unitName, u.zone AS unitZone, "
      + "mo.batch_print AS batchPrint, mo.rush_order AS rushOrder, mo.tracking_status_changed_on AS statusDate, mo.delivery_date_time AS DeliveryDateTime, mo.tracking_Status as trackingStatus, "
      + " ts.event_time as inCartDateTime, k.id as  kitchenId, k.service_style as serviceStyle "
      + "FROM cart c LEFT JOIN cart_meal_order cmo ON cmo.cart_id = c.id "
      + "LEFT JOIN meal_order mo ON mo.id=cmo.meal_order_id and mo.delivery_date_time > :deliveryDate "
      + "LEFT JOIN tray_status ts1 ON ts1.meal_order_id = mo.id "
      + "LEFT JOIN tray_status ts ON ts.meal_order_id = mo.id AND ts.scan_event_type = :scanEventType "
      + "LEFT JOIN kitchen k ON k.id = mo.kitchen_id AND k.facility_id = :id "
      + "LEFT JOIN tray_ticket tt ON tt.meal_order_id =mo.id LEFT JOIN bed b ON b.id=tt.bed_id "
      + "LEFT JOIN room r ON r.id=b.room_id LEFT JOIN unit u ON u.id=r.unit_id "
      + "WHERE k.facility_id = :id and c.status= :status "
      + "and ts1.scan_event_type = :mealOrderStatus AND mo.tracking_Status <> 'RECOVERED' ";
  String orderBy = " order by mo.tracking_Status = :mealOrderStatus DESC, c.id DESC, index_id ";
  @Query(nativeQuery = true, value = query+orderBy)
  public List<CartMealOrderJPABean> fetchCartsByStatusAndFacilityId(
      @Param("deliveryDate") Date deliveryDate,
      @Param("scanEventType") String scanEventType,
      @Param("status") String status, @Param("id") Long id, @Param("mealOrderStatus") String mealOrderStatus);
  
  String findByZoneQuery = " Select 1 from Cart cart left join cart.mealOrders mo left join mo.kitchen kitchen"
      + " where mo.id = :mealOrderId or (cart.status = :status and  cart.zone = :zone and kitchen.facility.id = :facilityId) ";
  @Query(value = findByZoneQuery)
  public List<Long> checkMealOrderCartExistsForFacility(@Param("mealOrderId") Long mealOrderId,
      @Param("zone") int zone, @Param("status") String status, @Param("facilityId") Long facilityId);  
  
  
  @Modifying(clearAutomatically = true)
  @Query("update Cart cart set status =:status where id =:cartId")
  public void updateCartStatus(@Param("status") String status, @Param("cartId") Long cartId);
  
  @Modifying(clearAutomatically = true)
  @Query(nativeQuery = true, value = " UPDATE cart c SET STATUS = :cartStatus WHERE c.id IN"
      + " (SELECT cmo.cart_id from cart_meal_order cmo "
      + " JOIN meal_order mo ON mo.id=cmo.meal_order_id and mo.delivery_date_time < :pastDeliveryDate  "
      + " JOIN kitchen k ON k.id = mo.kitchen_id "
      + " where cmo.cart_id = c.id AND k.facility_id = :facilityId and status =:currentStatus and mo.delivery_date_time > :deliveryDate)")
  public void updateOldInProgressCartsToUnused(@Param("deliveryDate") Date deliveryDate,
      @Param("pastDeliveryDate") Date pastDeliveryDate,
      @Param("cartStatus") String cartStatus,
      @Param("facilityId") Long facilityId,
      @Param("currentStatus") String currentStatus);
  
}
