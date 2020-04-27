package com.pointclickcare.nutrition.repository;

import java.util.List;

import com.momentum.dms.domain.Cart;

public interface CustomCartRepository
{
  public List<Cart> fetchByStatusAndFacilityId(String status,Long id);
}
