package com.pointclickcare.nutrition.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.momentum.dms.domain.Kitchen;
import com.pointclickcare.nutrition.bean.UIKitchen;
import com.pointclickcare.nutrition.bean.UIServiceStyle;
import com.pointclickcare.nutrition.service.KitchenService;

@RestController
public class KitchenController
{
  @Autowired
  KitchenService kitchenService;

  @RequestMapping(value = "fetchServiceStyles", method = RequestMethod.GET)
  public List<UIServiceStyle> fetchServiceStyles(@RequestAttribute Long facilityId)
  {
    return kitchenService.getServiceStyleForFacility(facilityId);
  }

  @RequestMapping(value = "fetchKitchens", method = RequestMethod.GET)
  public List<UIKitchen> fetchKitchensByFacilityAndServiceStyle(@RequestParam String serviceStyle,
      @RequestAttribute Long facilityId)
  {
    List<Kitchen> kitchenList = kitchenService.findByFacilityId(serviceStyle, facilityId);
    return kitchenService.fetchKitchensByFacilityAndServiceStyle(kitchenList);
  }
  
  @GetMapping("fetchAllServiceStylesAndKitchens")
  public List<UIKitchen> fetchAllServiceStylesAndKitchensForFacility (@RequestAttribute Long facilityId){
    return kitchenService.fetchAllServiceStylesAndKitchensForFacility(facilityId);
  }
  
  @RequestMapping(value = "fetchKitchensWithUnitTrackingEnabled", method = RequestMethod.GET)
  public List<UIKitchen> fetchKitchensWithUnitTrackingEnabled(@RequestAttribute Long facilityId)
  {
    return kitchenService.fetchKitchensWithUnitTrackingEnabled(facilityId);
  }

}
