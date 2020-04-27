package com.pointclickcare.nutrition.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pointclickcare.nutrition.bean.UIUMS;
import com.pointclickcare.nutrition.service.UnitMealStatusService;

@RestController
public class UnitMealStatusController
{
  @Autowired
  UnitMealStatusService unitMealStatusService;

  @GetMapping("fetchDeliveryUnits")
  public List<UIUMS> fetchDeliveryUnits(@RequestParam Long kitchenId,
      @RequestParam Long mealNameId,
      @RequestAttribute Long facilityId, @RequestAttribute String timezone)
  {
    return unitMealStatusService.fetchDeliveryUnits(facilityId, kitchenId, mealNameId, timezone);
  }
  
  @GetMapping("fetchRecoveryUnits")
  public List<UIUMS> fetchRecoveryUnits(@RequestParam Long kitchenId,
      @RequestParam Long mealNameId,
      @RequestAttribute Long facilityId)
  {
    return unitMealStatusService.fetchRecoveryUnits(facilityId, kitchenId, mealNameId);
  }

  @PostMapping("markUnit")
  public List<UIUMS> markUnit(@RequestBody UIUMS uiUMS, @RequestAttribute Long userId,
      @RequestAttribute Long facilityId, @RequestAttribute String timezone)
  {
    return unitMealStatusService.markUnitAsGivenEvent(uiUMS, userId, facilityId, timezone);
  }

  @PostMapping("undoMarkedUnit")
  public List<UIUMS> undoMarkedUnit(@RequestBody UIUMS uiUMS,
      @RequestAttribute Long facilityId, @RequestAttribute Long userId, @RequestAttribute String timezone)
  {
    return unitMealStatusService.undoMarkedUnit(uiUMS, facilityId, userId, timezone);
  }
}
