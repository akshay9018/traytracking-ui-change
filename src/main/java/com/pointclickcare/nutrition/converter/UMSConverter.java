package com.pointclickcare.nutrition.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.pointclickcare.nutrition.JPABean.UMSJPABean;
import com.pointclickcare.nutrition.bean.UIUMS;

@Component
public class UMSConverter
{

  public List<UIUMS> getUIUnitFromUMS(List<UMSJPABean> umsList)
  {
    List<UIUMS> uiUnitList = new ArrayList<UIUMS>();
    umsList.forEach(ums -> {
      uiUnitList.add(getUIUMS(ums));
    });
    return uiUnitList;
  }

  public UIUMS getUIUMS(UMSJPABean u)
  {
    UIUMS uiUnit = new UIUMS();
    if (u == null)
      return null;
    else
    {
      uiUnit.setId(u.getUnitId());
      uiUnit.setName(u.getUnitName());
      uiUnit.setEventType(Objects.nonNull(u.getUmsId()) ? u.getUmsEvent().getName() : "");
      uiUnit.setEventTime(u.getUmsEventTime());
      uiUnit.setUmsId(u.getUmsId());
      return uiUnit;
    }

  }

}
