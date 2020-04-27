package com.pointclickcare.nutrition.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.momentum.dms.domain.MealOrder;
import com.momentum.dms.domain.TrayEventType;
import com.pointclickcare.nutrition.JPABean.CartMealOrderJPABean;
import com.pointclickcare.nutrition.bean.UIMealOrder;
import com.pointclickcare.nutrition.util.TimeUtil;

@Component
public class MealOrderConverter
{
  public List<UIMealOrder> getUIMealOrderFromMealOrders(List<CartMealOrderJPABean> mealOrderList,
      String timezone)
  {
    List<UIMealOrder> uiMealOrderList = new ArrayList<UIMealOrder>();
    if (mealOrderList != null && !mealOrderList.isEmpty())
    {
      for (CartMealOrderJPABean cartMealOrder : mealOrderList)
      {
        uiMealOrderList.add(getUIMealOrder(cartMealOrder, timezone));
      }
    }
    return uiMealOrderList;
  }
  
  public UIMealOrder getUIMealOrder(CartMealOrderJPABean cartMealOrder, String timezone)
  {
    UIMealOrder uiMealOrder = new UIMealOrder();

    uiMealOrder.setId(cartMealOrder.getMealOrderId());
    uiMealOrder.setTicketNumber(cartMealOrder.getTicketNumber());
    uiMealOrder.setUnitId(cartMealOrder.getUnitId());
    uiMealOrder.setUnitName(cartMealOrder.getUnitName());
    uiMealOrder.setRoomId(cartMealOrder.getRoomId());
    uiMealOrder.setRoomName(cartMealOrder.getRoomName());
    uiMealOrder.setBedId(cartMealOrder.getBedId());
    uiMealOrder.setBedName(cartMealOrder.getBedName());
    uiMealOrder.setZone(cartMealOrder.getUnitZone());
    uiMealOrder.setDeliveryTime(TimeUtil.getTimeIn12HourFormat(timezone, cartMealOrder.getDeliveryDateTime()));
    uiMealOrder.setDeliveryDateTime(TimeUtil.getTimeIn24HourFormat(timezone, cartMealOrder.getDeliveryDateTime()));
    uiMealOrder.setStatusDate(cartMealOrder.getStatusDate());
    uiMealOrder.setStatusDateInString(TimeUtil.getTimeIn24HourFormat(timezone, cartMealOrder.getStatusDate()));
    uiMealOrder.setTrackingStatus(cartMealOrder.getTrackingStatus());
    uiMealOrder.setKitchenId(cartMealOrder.getKitchenId());
    uiMealOrder.setServiceStyle(cartMealOrder.getServiceStyle());
    if(cartMealOrder.getTrackingStatus().equals(TrayEventType.DELIVERED))
    {
    	uiMealOrder.setTimeFromDelivery(TimeUtil.getTimeInMinutes(cartMealOrder.getStatusDate()));
    }
    if(cartMealOrder.getTrackingStatus().equals(TrayEventType.RECOVERED))
    {
    	uiMealOrder.setTimeFromDelivery(TimeUtil.getTimeInMinutes(cartMealOrder.getDeliveredDateTime()));
    }
    uiMealOrder.setDelivered(cartMealOrder.getTrackingStatus().equals(TrayEventType.DELIVERED));
    uiMealOrder.setRushOrder(cartMealOrder.getRushOrder() && !cartMealOrder.getBatchPrint());
    uiMealOrder.setNowTray(cartMealOrder.getBatchPrint() && cartMealOrder.getRushOrder());
    return uiMealOrder;
  }
  
  public List<UIMealOrder> getUIMealOrders(List<MealOrder> mealOrders)
  {
    List<UIMealOrder> uiMealOrders = new ArrayList<UIMealOrder>();
    mealOrders.forEach(mealOrder -> {
      UIMealOrder uiMealOrder = new UIMealOrder();
      uiMealOrder.setId(mealOrder.getId());
      uiMealOrder.setTicketNumber(mealOrder.getTicketNumber());
      uiMealOrders.add(uiMealOrder);
    });
    return uiMealOrders;
  }
}
