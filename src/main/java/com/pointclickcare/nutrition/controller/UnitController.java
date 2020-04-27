package com.pointclickcare.nutrition.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pointclickcare.nutrition.bean.UIUnit;
import com.pointclickcare.nutrition.service.UnitService;

@RestController
public class UnitController
{
  @Autowired
  UnitService unitService;

  @RequestMapping(value = "fetchUnits", method = RequestMethod.GET)
  public List<UIUnit> fetchUnitsByFacilityServiceStyleAndKitchen(@RequestParam String serviceStyle, @RequestParam Long kitchenId, 
      @RequestAttribute Long facilityId)
  {
    return unitService.fetchUnitsWithUnitTrackingDisabled(serviceStyle, facilityId, kitchenId);
  }
  
}
