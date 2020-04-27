package com.pointclickcare.nutrition.converter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.momentum.dms.domain.Cart;
import com.momentum.dms.domain.CartStatus;
import com.pointclickcare.nutrition.JPABean.CartMealOrderJPABean;
import com.pointclickcare.nutrition.bean.UICart;
import com.pointclickcare.nutrition.bean.UIMealOrder;
import com.pointclickcare.nutrition.util.CartComparator;
import com.pointclickcare.nutrition.util.TimeUtil;

@Component
public class CartConverter
{
  @Autowired
  MealOrderConverter moConverter;
  
  @Autowired
  CartComparator cartComparator;

  public List<UICart> getUICarts(List<Cart> carts)
  {
	  List<UICart> uiCarts = new ArrayList<UICart>();
	  carts.forEach(cart -> {
		  UICart uiCart = new UICart();
		  uiCart.setId(cart.getId());
		  uiCart.setMealOrders(moConverter.getUIMealOrders(cart.getMealOrders()));
		  uiCart.setStatus(cart.getStatus());
		  uiCart.setZone(cart.getZone());
		  uiCarts.add(uiCart);
	  });
	  return uiCarts;
  }
  
  public Map<Long, UICart> getUICartMap(List<CartMealOrderJPABean> cartList, String timezone, String status)
  {
    Map<Long, UICart> cartMap = new LinkedHashMap<>();
    if (cartList != null)
    {
      for (CartMealOrderJPABean cartMealOrder : cartList)
      {
        if(cartMealOrder.getInCartDateTime() != null && deliveredWithin3Hours(cartMealOrder.getInCartDateTime()) < 0) 
        {
          continue;
        }
        UIMealOrder uiMealOrder = moConverter.getUIMealOrder(cartMealOrder, timezone);
        UICart cart = null;
        if (cartMap.containsKey(cartMealOrder.getId()))
        {
          cart = cartMap.get(cartMealOrder.getId());
          if(cartMealOrder.getCartStatus().equals(CartStatus.IN_PROGRESS.getName()))
          {
            cart.getMealOrders().add(0, uiMealOrder);
          }
          else
          {
			if (TimeUtil.getLocalTime(uiMealOrder.getDeliveryTime())
					.isBefore(TimeUtil.getLocalTime(cart.getMinimumDeliveryTime())))
        	{
              cart.setMinimumDeliveryTime(uiMealOrder.getDeliveryTime());	  
        	}
            cart.getMealOrders().add(uiMealOrder);
          }
        }
        else
        {
          cart = getUICart(cartMealOrder, uiMealOrder);
        }
        cartMap.put(cartMealOrder.getId(), cart);
      }
    }
   
    
    if(status.equals(CartStatus.CHECKED.getName()) || status.equals(CartStatus.COMPLETED.getName()))
    {
      Map<Long, UICart> cartData = cartMap.entrySet()
          .stream()
          .sorted(cartComparator)
          .collect(Collectors.toMap(
              Map.Entry::getKey,Map.Entry::getValue,
              (oldValue, newValue) -> oldValue, LinkedHashMap::new));
      
      return cartData;
    }
    
    return cartMap;
  }
  
  public List<UICart> getUICart(List<CartMealOrderJPABean> cartList, String timezone, String status)
  {
    return new ArrayList<UICart>(getUICartMap(cartList, timezone, status).values());
  }

  public UICart getUICart(CartMealOrderJPABean cartMealOrder, UIMealOrder uiMealOrder)
  {
    UICart cart = new UICart();
    cart.setId(cartMealOrder.getId());
    cart.setZone(cartMealOrder.getZone());
    cart.setStatus(cartMealOrder.getCartStatus());
    ArrayList<UIMealOrder> moList = new ArrayList<>();
    moList.add(uiMealOrder);
    cart.setMealOrders(moList);
    cart.setFirstMealOrderTime(TimeUtil.getTimeInMinutes(cartMealOrder.getInCartDateTime()));
    cart.setMinimumDeliveryTime(uiMealOrder.getDeliveryTime());
    return cart;
  }
  
  private int deliveredWithin3Hours(Date statusDate)
  {
    long time = System.currentTimeMillis() - Duration.ofHours(3).toMillis();
    Date now = new java.sql.Timestamp(time);
    return statusDate.compareTo(now);
  }
}
