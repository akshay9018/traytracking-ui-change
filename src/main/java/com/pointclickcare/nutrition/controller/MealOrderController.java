package com.pointclickcare.nutrition.controller;

import java.text.ParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.momentum.dms.domain.TrayEventType;
import com.pointclickcare.nutrition.bean.UIMealOrder;
import com.pointclickcare.nutrition.service.MealOrderService;

@RestController
public class MealOrderController
{
  @Autowired
  MealOrderService mealOrderService;

  @RequestMapping(value = "fetchOutCartMealOrders", method = RequestMethod.GET)
  public List<UIMealOrder> fetchOutCartMealOrders (@RequestParam String serviceStyle, @RequestParam Long kitchenId, @RequestParam Long unitId, 
      @RequestAttribute Long facilityId, @RequestAttribute String timezone, @RequestParam Long mealNameId)
  {
    return mealOrderService.fetchOutCartMealOrders(facilityId, timezone, serviceStyle, kitchenId, unitId,
        TrayEventType.TICKET_PRINTED, mealNameId);
  }
  
  @RequestMapping(value = "fetchDeliveredMealOrders", method = RequestMethod.GET)
  public List<UIMealOrder> fetchDeliveredMealOrders (@RequestParam Long unitId, 
      @RequestAttribute Long facilityId, @RequestAttribute String timezone)
  {
    return mealOrderService.fetchDeliveredMealOrders(facilityId, timezone, unitId, TrayEventType.DELIVERED, null);
  }
  
  @RequestMapping(value = "recoverMealOrder", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<?> recoverDeliveredMealOrder (@RequestBody UIMealOrder uiMealOrder,      
      @RequestAttribute Long userId, @RequestAttribute String timezone) throws ParseException
  {
   mealOrderService.recoverMealOrder(uiMealOrder.getId(), userId, uiMealOrder.getEventTime(), timezone);
   return ResponseEntity.ok(HttpStatus.OK);
  }
  
  @RequestMapping(value = "undoRecoveredMealOrder", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<?> undoRecoveredMealOrder (@RequestBody UIMealOrder uiMealOrder)
  {
   mealOrderService.undoRecoveredMealOrder(uiMealOrder.getId());
   return ResponseEntity.ok(HttpStatus.OK);
  }
  
  @RequestMapping(value="/markTrayAsDelivered", method=RequestMethod.POST)
  public @ResponseBody ResponseEntity<?> markTrayAsDelivered(@RequestAttribute Long userId, @RequestBody UIMealOrder uiMealOrder, @RequestAttribute String timezone) throws ParseException
  {
    mealOrderService.markTrayAsDelivered(uiMealOrder, userId, timezone);
    return ResponseEntity.ok(HttpStatus.OK);
  }
  
  @RequestMapping(value = "undoDeliveredMealOrder", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<?> undoDeliveredMealOrder (@RequestBody UIMealOrder uiMealOrder)
  {
   mealOrderService.undoDeliveredMealOrder(uiMealOrder.getId());
   return ResponseEntity.ok(HttpStatus.OK);
  }
}
