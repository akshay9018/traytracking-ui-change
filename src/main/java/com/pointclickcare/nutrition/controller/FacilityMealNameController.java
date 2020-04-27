package com.pointclickcare.nutrition.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pointclickcare.nutrition.bean.UIFacilityMealName;
import com.pointclickcare.nutrition.service.FacilityMealNameService;

@RestController
public class FacilityMealNameController
{
  @Autowired
  FacilityMealNameService facilityMealNameService;

  @RequestMapping(value = "fetchFacilityMealNamesForSelectedFacility", method = RequestMethod.GET)
  public List<UIFacilityMealName> fetchFacilityMealNamesForSelectedFacility(@RequestAttribute Long facilityId)
  {
    return facilityMealNameService.fetchFacilityMealNamesForSelectedFacility(facilityId);
  }
  
}
