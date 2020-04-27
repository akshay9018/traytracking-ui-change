package com.pointclickcare.nutrition.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.momentum.dms.domain.Kitchen;
import com.momentum.dms.domain.ServiceStyleType;
import com.momentum.dms.domain.Unit;
import com.pointclickcare.nutrition.bean.UIUnit;
import com.pointclickcare.nutrition.converter.UnitConverter;
import com.pointclickcare.nutrition.repository.KitchenRepository;
import com.pointclickcare.nutrition.repository.UnitRepository;

@Service
@Transactional
public class UnitService
{
  @Autowired
  UnitRepository unitRepository;
  @Autowired
  KitchenRepository kitchenRepository;
  @Autowired
  UnitConverter unitConverter;

  public List<Unit> findByKitchenList(List<Kitchen> kitchenList, boolean unitTracking)
  {
    return unitRepository.findByKitchenInAndUnitTrackingOrderByName(kitchenList, unitTracking);
  }

  public List<Unit> findUnitsByKitchenId(Long kitchenId, boolean unitTracking)
  {
    return unitRepository.findByKitchenIdAndUnitTrackingOrderByName(kitchenId, unitTracking);
  }

  public List<UIUnit> fetchUnitsWithUnitTrackingDisabled(String serviceStyle, Long facilityId, Long kitchenId)
  {
    List<Unit> unitList;
    if (kitchenId.equals(-1l))
    {
      List<Kitchen> kitchens;
      if (serviceStyle.equals("-1"))
      {
        kitchens = kitchenRepository.findByFacilityIdOrderByName(facilityId);
      }
      else
      {
        kitchens = kitchenRepository.findByFacilityIdAndServiceStyleOrderByName(facilityId,
            ServiceStyleType.getServiceStyleType(serviceStyle));
      }
      unitList = findByKitchenList(kitchens, false);
    }
    else
      unitList = findUnitsByKitchenId(kitchenId, false);
    return unitConverter.getUIUnitFromUnis(unitList);
  }
}
