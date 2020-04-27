package com.pointclickcare.nutrition.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.momentum.dms.domain.Cart;
import com.momentum.dms.domain.MealOrder;
import com.momentum.dms.domain.TrayEventType;
import com.pointclickcare.nutrition.bean.UICart;
import com.pointclickcare.nutrition.service.CartService;
import com.pointclickcare.nutrition.service.MealOrderService;

@RestController
public class CartController
{
  @Autowired
  CartService cartService;
  @Autowired
  MealOrderService mealOrderService;
  
  @RequestMapping(value = "createCartAndAddMealOrder", method = RequestMethod.POST)
  public ResponseEntity<?> createCartAndAddMealOrder(@RequestAttribute Long userId, @RequestAttribute Long facilityId, @RequestBody UICart uiCart)
  {
    try{
    if(mealOrderService.getMealOrderTrayEventType(uiCart.getMealOrderId()).equals(TrayEventType.TICKET_PRINTED)){
    boolean cartMealExists = cartService.checkMealOrderCartExistsForFacility(uiCart, facilityId);
    if(!cartMealExists)
    {
      Long cartId = cartService.createCartAndAddMealOrder(uiCart.getZone(), uiCart.getMealOrderId(), userId);
      return new ResponseEntity<>(
          cartId, 
          HttpStatus.OK);
    }
    else{
      return new ResponseEntity<>(
          "Cart already added for this zone.", 
          HttpStatus.CONFLICT);
    }
    } else
      return new ResponseEntity<>(
          "Meal Order already added to some other cart.", 
          HttpStatus.CONFLICT);
  }
    catch (Exception e) {
      return  new ResponseEntity<>(
          "Internal Server Error", 
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(value = "addMealOrderToCart", method = RequestMethod.POST)
  public ResponseEntity<?> addMealOrderToCart(@RequestAttribute Long userId, @RequestBody UICart uiCart)
  {
    try
    {
      if(mealOrderService.getMealOrderTrayEventType(uiCart.getMealOrderId()).equals(TrayEventType.TICKET_PRINTED))
      {
        cartService.addMealOrderToCart(uiCart.getId(), uiCart.getMealOrderId(), userId);
    	return ResponseEntity.ok(HttpStatus.OK);
      }
      else
      {        
        return new ResponseEntity<>(
            "Meal Order already added to some other cart.", 
            HttpStatus.CONFLICT);
      }
     } catch (Exception e) {
        return  new ResponseEntity<>(
            "Internal Server Error", 
            HttpStatus.INTERNAL_SERVER_ERROR);
     }
  }

  @RequestMapping(value = "fetchInProgressCarts", method = RequestMethod.GET)
  public List<UICart> fetchInProgressCarts(@RequestAttribute Long facilityId, @RequestAttribute String timezone)
  {
    return cartService.fetchInProgressCarts(facilityId, timezone);
  }

  @RequestMapping(value = "removeTrayFromCart", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<HttpStatus> removeTrayFromCart(@RequestBody UICart uiCart)
  {
    MealOrder mo = mealOrderService.getMealOrder(uiCart.getMealOrderId());  
    Cart c = cartService.removeTrayFromCart(uiCart.getId(), mo, mo.getTrayStatusSet());
    if (c.getMealOrders().isEmpty())
    {
      cartService.deleteCart(c);
    }
    return ResponseEntity.ok(HttpStatus.OK);
  }
  
  @RequestMapping(value = "markCartAsComplete", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<?> markCartAsComplete(@RequestBody UICart uiCart)
  {
    cartService.markCartAsComplete(uiCart.getId());
    return ResponseEntity.ok(HttpStatus.OK);
  }

  @RequestMapping(value = "/fetchReadyToDepartCarts", method = RequestMethod.GET)
  public List<UICart> fetchReadyToDepartCarts(@RequestAttribute Long facilityId, @RequestAttribute String timezone)
  {
    return cartService.fetchReadyToDepartCarts(facilityId, timezone);
  }
  
  @RequestMapping(value="/markCartDeparted", method=RequestMethod.POST)
  public @ResponseBody ResponseEntity<?> markCartAsDeparted(@RequestAttribute Long userId, @RequestBody UICart uiCart)
  {
    cartService.markCartAsDeparted(uiCart, userId);
    return ResponseEntity.ok(HttpStatus.OK);
  }
  
  @RequestMapping(value = "/fetchReadyToDeliverCarts", method = RequestMethod.GET)
  public List<UICart> fetchReadyToDeliverCarts(@RequestAttribute Long facilityId, @RequestAttribute String timezone)
  {
    return cartService.fetchReadyToDeliverCarts(facilityId, timezone);
  }
  
  @GetMapping(value = "/fetchReadyToHighRiskCheckCarts")
  public List<UICart> fetchReadyToHighRiskCheckCarts(@RequestAttribute Long facilityId, @RequestAttribute String timezone)
  {
    return cartService.fetchReadyToHighRiskCheckCarts(facilityId, timezone);
  }

  @PostMapping(value="/markCartChecked")
  public @ResponseBody ResponseEntity<?> markCartChecked(@RequestAttribute Long userId, @RequestBody UICart uiCart)
  {
    cartService.markCartChecked(uiCart, userId);
    return ResponseEntity.ok(HttpStatus.OK);
  }

}

