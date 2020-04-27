package com.pointclickcare.nutrition.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.momentum.dms.domain.Facility;
import com.momentum.dms.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	public User findByUserName(String userName);
	
	@Modifying(clearAutomatically = true)
  @Query("update User user set facility =:facility where id =:userId")
  public void updateDefaultFacility(@Param("facility") Facility facility, @Param("userId") Long userId);
	
}
