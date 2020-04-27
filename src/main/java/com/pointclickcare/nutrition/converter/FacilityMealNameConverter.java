package com.pointclickcare.nutrition.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pointclickcare.nutrition.JPABean.FacilityMealNameJPABean;
import com.pointclickcare.nutrition.bean.UIFacilityMealName;

@Component
public class FacilityMealNameConverter
{
  public List<UIFacilityMealName> getUIFacilityMealName(List<FacilityMealNameJPABean> facilityMealNameList)
  {
    List<UIFacilityMealName> uiFacilityMealNameList = new ArrayList<UIFacilityMealName>();
    facilityMealNameList.forEach((jpaBeanFMN)-> 
      uiFacilityMealNameList.add(new UIFacilityMealName(jpaBeanFMN.getMealNameId(), jpaBeanFMN.getName())));
    return uiFacilityMealNameList;
  }
}
