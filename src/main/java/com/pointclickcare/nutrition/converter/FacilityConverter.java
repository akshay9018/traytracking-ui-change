package com.pointclickcare.nutrition.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.momentum.dms.domain.Facility;
import com.pointclickcare.nutrition.bean.UIFacility;

@Component
public class FacilityConverter
{

  public List<UIFacility> getUIFacilities(Set<Facility> facilities, Long facilityId)
  {
    List<UIFacility> uiFacilities = new ArrayList<UIFacility>();
    facilities.forEach(facility -> {
      UIFacility uiFacility = new UIFacility();
      uiFacility.setId(facility.getId());
      uiFacility.setName(facility.getName());
      uiFacility.setSelected(facilityId.equals(facility.getId()));
      uiFacilities.add(uiFacility);
    });
    return uiFacilities;
  }
}
