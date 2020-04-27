package com.pointclickcare.nutrition.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.momentum.dms.domain.Kitchen;
import com.momentum.dms.domain.ServiceStyleType;

@Repository
public interface KitchenRepository extends CrudRepository<Kitchen, Long>
{
  public List<Kitchen> findByFacilityIdOrderByName(Long facilityId);
  
  public List<Kitchen> findByFacilityIdAndServiceStyleOrderByName(Long facilityId, ServiceStyleType serviceStyle);
  
  @Query("SELECT serviceStyle FROM Kitchen WHERE facility.id = :facilityId GROUP BY serviceStyle ORDER BY serviceStyle desc")
  public List<ServiceStyleType> getServiceStylesForFacility(@Param("facilityId") Long id);

  @Query("SELECT distinct k from Kitchen k join fetch k.servedUnits u where k.facility.id = :facilityId and u.unitTracking = true order by k.name")
  public List<Kitchen> fetchKitchensWithUnitTrackingEnabled(@Param("facilityId") Long facilityId);
  
}
