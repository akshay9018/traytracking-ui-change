package com.pointclickcare.nutrition.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.momentum.dms.domain.Facility;
import com.momentum.dms.domain.User;
import com.pointclickcare.nutrition.bean.UIFacility;
import com.pointclickcare.nutrition.converter.FacilityConverter;
import com.pointclickcare.nutrition.repository.FacilityRepository;
import com.pointclickcare.nutrition.repository.UserRepository;

@Service
@Transactional
public class UserService
{
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private FacilityConverter facilityConverter;
  @Autowired
  private FacilityRepository facilityRepository;
  @PersistenceContext  private EntityManager em;  
  
  public List<UIFacility> getAssignedFacilities(Long userId, Long facilityId)
  {
    User user = userRepository.findById(userId).get();
    List<UIFacility> uiFacilities = facilityConverter.getUIFacilities(user.getFacilities(), facilityId);
    return uiFacilities;
  }

  public void saveDafaultFacility(UIFacility uiFacility, Long userId)
  {
//    clearHibernateCache();
    Facility facility = facilityRepository.findById(uiFacility.getId()).get();
    userRepository.updateDefaultFacility(facility, userId);
  }
  
  public void clearHibernateCache() 
  {     
   Session s = (Session)em.getDelegate();      
   SessionFactory sf = s.getSessionFactory();  
   sf.getCache().evictQueryRegion("defaultFacilityByUserId");
  }
}
