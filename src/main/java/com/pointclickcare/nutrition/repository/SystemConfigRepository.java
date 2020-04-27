package com.pointclickcare.nutrition.repository;

import org.springframework.data.repository.CrudRepository;

import com.momentum.dms.domain.SystemConfig;

public interface SystemConfigRepository extends CrudRepository<SystemConfig, Long> {
  
  public SystemConfig findByPropertyName(String propertyName);
}
