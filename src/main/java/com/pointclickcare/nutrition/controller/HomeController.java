package com.pointclickcare.nutrition.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pointclickcare.nutrition.service.KitchenService;
import com.pointclickcare.nutrition.service.UnitService;

@RestController
public class HomeController
{
  @Autowired
  KitchenService kitchenService;
  @Autowired
  UnitService unitService;

  @RequestMapping("/")
  public ModelAndView loadHomePage()
  {
    return new ModelAndView("home");
  }
}
