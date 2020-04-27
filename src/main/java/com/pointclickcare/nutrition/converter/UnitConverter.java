package com.pointclickcare.nutrition.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.momentum.dms.domain.Unit;
import com.pointclickcare.nutrition.bean.UIUnit;

@Component
public class UnitConverter
{

  public List<UIUnit> getUIUnitFromUnis(List<Unit> unitList)
  {
    List<UIUnit> uiUnitList = new ArrayList<UIUnit>();
    if (unitList != null && !unitList.isEmpty())
    {
      for (Unit u : unitList)
        uiUnitList.add(getUIUnit(u));
    }
    return uiUnitList;
  }
  
  public UIUnit getUIUnit(Unit u)
  {
    UIUnit uiUnit = new UIUnit();
    if (u == null)
      return null;
    else
    {
      uiUnit.setId(u.getId());
      uiUnit.setName(u.getName());
      return uiUnit;
    }

  }

}
