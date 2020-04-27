package com.pointclickcare.nutrition.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.momentum.dms.domain.Kitchen;
import com.momentum.dms.domain.ServiceStyleType;
import com.pointclickcare.nutrition.bean.UIKitchen;
import com.pointclickcare.nutrition.bean.UIServiceStyle;

@Component
public class KitchenConverter
{

  public List<UIServiceStyle> getUIServiceStyle(List<ServiceStyleType> serviceStylesForFacility)
  {
    if (serviceStylesForFacility == null || serviceStylesForFacility.isEmpty())
      return null;
    List<UIServiceStyle> serviceStyles = new ArrayList<>();
    for (ServiceStyleType st : serviceStylesForFacility)
    {
      UIServiceStyle serviceStyle = new UIServiceStyle();
      serviceStyle.setId(st.getId());
      serviceStyle.setName(st.getName());
      serviceStyles.add(serviceStyle);
    }
    return serviceStyles;
  }
  
  public UIKitchen getUIKitchen(Kitchen k)
  {
    UIKitchen uiKitchen = new UIKitchen();
    if (k == null)
      return null;
    else
    {
      uiKitchen.setId(k.getId());
      uiKitchen.setName(k.getName());
      return uiKitchen;
    }

  }

  public List<UIKitchen> getUIKitchens(List<Kitchen> kitchens)
  {
    List<UIKitchen> uiKitchens = new ArrayList<UIKitchen>();
    kitchens.forEach((k) -> {
      uiKitchens.add(getUIKitchenWithServiceStyle(k).orElse(null));
    });
    return uiKitchens;
  }

  private Optional<UIKitchen> getUIKitchenWithServiceStyle(Kitchen k)
  {
    if(k==null){
      return Optional.empty();
    }
    UIKitchen uiKitchen = new UIKitchen();
    uiKitchen.setId(k.getId());
    uiKitchen.setName(k.getName());
    ServiceStyleType serviceStyle = k.getServiceStyle();
    uiKitchen.setServiceStyleId(serviceStyle.getId());
    uiKitchen.setServiceStyleName(serviceStyle.getName());
    return Optional.of(uiKitchen);
  }

}
