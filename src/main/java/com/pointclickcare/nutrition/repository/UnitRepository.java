package com.pointclickcare.nutrition.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.momentum.dms.domain.Kitchen;
import com.momentum.dms.domain.Unit;

@Repository
public interface UnitRepository extends CrudRepository<Unit, Long>
{
  public List<Unit> findByKitchenIdAndUnitTrackingOrderByName(Long kitchenId, boolean unitTracking);
  public List<Unit> findByKitchenInAndUnitTrackingOrderByName(List<Kitchen> kitchenList, boolean unitTracking);
  public List<Unit> findByFacilityIdOrderByName(Long facilityId);
}
