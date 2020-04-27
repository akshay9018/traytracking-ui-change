package com.pointclickcare.nutrition.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.momentum.dms.domain.UserLoginToken;

@Repository
public interface UserLoginTokenRepository extends CrudRepository<UserLoginToken, Long> {
	
	@Cacheable("servicesByUserId")
	public UserLoginToken findByUserIdAndToken(Long userId, String token);
	
	public void deleteByUserIdAndToken(Long userId, String token);

}
