package com.pointclickcare.nutrition.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.momentum.dms.domain.Kitchen;
import com.momentum.dms.domain.ServiceStyleType;
import com.pointclickcare.nutrition.bean.UIKitchen;
import com.pointclickcare.nutrition.bean.UIServiceStyle;
import com.pointclickcare.nutrition.converter.KitchenConverter;
import com.pointclickcare.nutrition.repository.KitchenRepository;

@Service
@Transactional
public class KitchenService
{
  @Autowired
  KitchenRepository kitchenRepository;
  @Autowired
  KitchenConverter kitchenConverter;

  public List<UIKitchen> fetchKitchensByFacilityAndServiceStyle(List<Kitchen> kitchenList)
  {
    List<UIKitchen> uiKitchenList = new ArrayList<UIKitchen>();
    if (kitchenList != null && !kitchenList.isEmpty())
    {
      for (Kitchen k : kitchenList)
        uiKitchenList.add(kitchenConverter.getUIKitchen(k));
    }
    return uiKitchenList;
  }

  public List<Kitchen> findByFacilityId(String serviceStyle, Long facilityId)
  {
    if (serviceStyle.equals("-1"))
      return kitchenRepository.findByFacilityIdOrderByName(facilityId);
    return kitchenRepository.findByFacilityIdAndServiceStyleOrderByName(facilityId,
        ServiceStyleType.getServiceStyleType(serviceStyle));
  }

  public List<UIServiceStyle> getServiceStyleForFacility(Long facilityId)
  {
    return kitchenConverter.getUIServiceStyle(kitchenRepository.getServiceStylesForFacility(facilityId));
  }

  public List<UIKitchen> fetchAllServiceStylesAndKitchensForFacility(Long facilityId)
  {
    List<Kitchen> kitchens = kitchenRepository.findByFacilityIdOrderByName(facilityId);
    return kitchenConverter.getUIKitchens(kitchens);
  }

  public List<UIKitchen> fetchKitchensWithUnitTrackingEnabled(Long facilityId)
  {
    List<Kitchen> kitchenList = kitchenRepository.fetchKitchensWithUnitTrackingEnabled(facilityId);
    return fetchKitchensByFacilityAndServiceStyle(kitchenList);
  }

}
