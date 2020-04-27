package com.pointclickcare.nutrition.repositoryImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.momentum.dms.domain.Cart;
import com.pointclickcare.nutrition.repository.CustomCartRepository;

public class CustomCartRepositoryImpl implements CustomCartRepository
{

  @Autowired
  private EntityManager em;
  
  @SuppressWarnings("unchecked")
  @Override
  public List<Cart> fetchByStatusAndFacilityId(String status, Long facilityId)
  {
    String queryString = "from Cart cart left join cart.mealOrders mo left join mo.kitchen k left join k.facility f"
        + " where status = :status and f.id = :facilityId";
    Query query = em.createQuery(queryString).setParameter("status", status).setParameter("facilityId", facilityId);
    return query.getResultList();
  }
}
