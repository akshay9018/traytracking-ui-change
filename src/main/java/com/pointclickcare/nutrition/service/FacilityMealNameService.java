package com.pointclickcare.nutrition.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pointclickcare.nutrition.bean.UIFacilityMealName;
import com.pointclickcare.nutrition.converter.FacilityMealNameConverter;
import com.pointclickcare.nutrition.repository.FacilityMealNameRepository;

@Service
@Transactional
public class FacilityMealNameService
{
  @Autowired
  FacilityMealNameRepository facilityMealNameRepository;
  
  @Autowired
  FacilityMealNameConverter facilityMealNameConverter;
  
  public List<UIFacilityMealName> fetchFacilityMealNamesForSelectedFacility(Long facilityId)
  {
    return facilityMealNameConverter.getUIFacilityMealName(
        facilityMealNameRepository.fetchFacilityMealNamesForSelectedFacility(facilityId));
  }

}
