package com.pointclickcare.nutrition.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pointclickcare.nutrition.bean.UIFacility;
import com.pointclickcare.nutrition.service.UserService;

@RestController
public class UserController
{

  @Autowired
  UserService userService;
  
  @RequestMapping(value="/fetchAssignedFacilities", method=RequestMethod.GET)
  public List<UIFacility> getAssignedFacilities(@RequestAttribute Long userId, @RequestAttribute Long facilityId)
  {
    return userService.getAssignedFacilities(userId, facilityId);
  }
  
  @RequestMapping(value="/saveDafaultFacility", method=RequestMethod.POST)
  public @ResponseBody ResponseEntity<?> saveDafaultFacility(@RequestBody UIFacility uiFacility, @RequestAttribute Long userId)
  {
    try{
      userService.saveDafaultFacility(uiFacility, userId);
      return new ResponseEntity<>(
          "Facility updated successfully.", 
          HttpStatus.ACCEPTED);
    }
    catch (Exception e) {
      return new ResponseEntity<>(
          e.getMessage(), 
          HttpStatus.UNAUTHORIZED);
    }
    
  }
}
