package com.pointclickcare.nutrition.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.momentum.dms.domain.Cart;
import com.momentum.dms.domain.CartStatus;
import com.momentum.dms.domain.MealOrder;
import com.momentum.dms.domain.TrayEventType;
import com.momentum.dms.domain.TrayStatus;
import com.pointclickcare.nutrition.JPABean.CartMealOrderJPABean;
import com.pointclickcare.nutrition.bean.UICart;
import com.pointclickcare.nutrition.bean.UIMealOrder;
import com.pointclickcare.nutrition.converter.CartConverter;
import com.pointclickcare.nutrition.repository.CartRepository;
import com.pointclickcare.nutrition.repository.MealOrderRepository;
import com.pointclickcare.nutrition.util.TimeUtil;

@Service
@Transactional
public class CartService
{
  @Autowired
  CartRepository cartRepository;
  @Autowired
  MealOrderRepository mealOrderRepository;
  @Autowired
  CartConverter cartConverter;
  @Autowired
  MealOrderService mealOrderService;

  public Long createCartAndAddMealOrder(Integer zone, Long mealOrderId, Long userId)
  {
    List<MealOrder> moSet = new ArrayList<>();
    moSet.add(
        mealOrderService.updateMealOrderTrackingStatus(mealOrderId, userId, TrayEventType.INCART));
    Cart c = new Cart();
    c.setZone(zone);
    c.setMealOrders(moSet);
    return cartRepository.save(c).getId();
  }

  public Long addMealOrderToCart(Long cartId, Long mealOrderId, Long userId)
  {
    Cart c = cartRepository.findById(cartId).orElse(null);
    c.getMealOrders().add(
        mealOrderService.updateMealOrderTrackingStatus(mealOrderId, userId, TrayEventType.INCART));
    return cartRepository.save(c).getId();
  }

  public List<UICart> fetchInProgressCarts(Long facilityId, String timezone)
  {
    cartRepository.updateOldInProgressCartsToUnused(TimeUtil.getTwelveHoursOldDate(),TimeUtil.getThreeHoursOldDate(),
        CartStatus.UNUSED.getName(), facilityId, CartStatus.IN_PROGRESS.toString());
    return fetchCartsByStatusAndFacilityId(facilityId,CartStatus.IN_PROGRESS.getName(),timezone);
  }

  public Cart removeTrayFromCart(Long cartId, MealOrder mo, Set<TrayStatus> tsSet)
  {
    Cart c = cartRepository.findById(cartId).orElse(null);
    c.getMealOrders().remove(mealOrderService.updateMealOrderTrackingStatusToPrevious(mo, tsSet,
        TrayEventType.INCART, TrayEventType.TICKET_PRINTED));
    return cartRepository.save(c);
  }

  public Cart markCartAsComplete(Long cartId)
  {
    Cart c = cartRepository.findById(cartId).orElse(null);
    c.setStatus(CartStatus.COMPLETED.getName());
    return cartRepository.save(c);
  }

  public void deleteCart(Cart c)
  {
    cartRepository.delete(c);
  }

  public List<UICart> fetchReadyToDepartCarts(Long facilityId, String timezone)
  {
    return fetchCartsByStatusAndFacilityId(facilityId,CartStatus.CHECKED.getName(),timezone);
  }

  public void markCartAsDeparted(UICart uiCart, Long userId)
  {
    cartRepository.updateCartStatus(CartStatus.DEPARTED.toString(), uiCart.getId());
    Cart cart = cartRepository.findById(uiCart.getId()).orElse(null);
    for (MealOrder mealOrder : cart.getMealOrders())
    {
      mealOrderService.updateMealOrderTrackingStatus(mealOrder.getId(), userId,
          TrayEventType.DEPARTED);
    }
  }

  public List<UICart> fetchReadyToDeliverCarts(Long facilityId, String timezone)
  {

    List<CartMealOrderJPABean> carts = cartRepository
        .fetchCartsByStatusAndFacilityId(TimeUtil.getThreeHoursOldDate(),
            TrayEventType.INCART.toString(), CartStatus.DEPARTED.getName(),
            facilityId, TrayEventType.DEPARTED.toString());
    Map<Long, UICart> uiCartsMap = cartConverter.getUICartMap(carts, timezone, CartStatus.DEPARTED.getName());
    ArrayList<UICart> uiCartsList = new ArrayList<UICart>();
    for (Entry<Long, UICart> entry : uiCartsMap.entrySet())
    {
      boolean allDelivered = true;
      for (UIMealOrder uiMealOrder : entry.getValue().getMealOrders())
      {
        if (uiMealOrder.getTrackingStatus().equals(TrayEventType.DEPARTED))
        {
          allDelivered = false;
        }
      }
      if (!allDelivered)
      {
        uiCartsList.add(entry.getValue());
      }
    }
    return uiCartsList;
  }

  public boolean checkMealOrderCartExistsForFacility(UICart uiCart, Long facilityId)
  {
    List<Long> carts = cartRepository.checkMealOrderCartExistsForFacility(uiCart.getMealOrderId(),
        uiCart.getZone(), CartStatus.IN_PROGRESS.getName(), facilityId);
    return carts.size() > 0;
  }
  
  public List<UICart> fetchReadyToHighRiskCheckCarts(Long facilityId, String timezone)
  {
    return fetchCartsByStatusAndFacilityId(facilityId,CartStatus.COMPLETED.getName(),timezone);
  }
  
  public void markCartChecked(UICart uiCart, Long userId)
  {
    cartRepository.updateCartStatus(CartStatus.CHECKED.toString(), uiCart.getId());
  }
  
  public List<UICart> fetchCartsByStatusAndFacilityId(Long facilityId, String status, String timezone)
  {
    List<CartMealOrderJPABean> carts = cartRepository
        .fetchCartsByStatusAndFacilityId(TimeUtil.getThreeHoursOldDate(),
            TrayEventType.INCART.toString(), status,
            facilityId, TrayEventType.INCART.toString());
    return cartConverter.getUICart(carts, timezone, status);
  }

}
