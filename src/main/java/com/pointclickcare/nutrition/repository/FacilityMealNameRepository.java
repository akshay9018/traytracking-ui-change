package com.pointclickcare.nutrition.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.momentum.dms.domain.Facility;
import com.momentum.dms.domain.FacilityMealName;
import com.momentum.dms.domain.Kitchen;
import com.momentum.dms.domain.Unit;
import com.pointclickcare.nutrition.JPABean.FacilityMealNameJPABean;

@Repository
public interface FacilityMealNameRepository extends CrudRepository<FacilityMealName, Long>
{

  @Query("Select mealName.id as mealNameId, name as name from FacilityMealName where facilityId=:facilityId and mealName.id in (1,3,5) order by mealName.id")
  public List<FacilityMealNameJPABean> fetchFacilityMealNamesForSelectedFacility(@Param("facilityId") Long facilityId);
  
}
