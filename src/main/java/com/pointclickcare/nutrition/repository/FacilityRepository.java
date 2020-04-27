package com.pointclickcare.nutrition.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.momentum.dms.domain.Facility;

@Repository
public interface FacilityRepository extends CrudRepository<Facility, Long>
{

//  @Cacheable("defaultFacilityByUserId")
  @Query("Select facility from User user left join user.facility facility where user.id=:userId")
  public Facility findDefaultFacilityByUserId(@Param("userId") Long userId);
}
