package com.pointclickcare.nutrition.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.momentum.dms.domain.Facility;
import com.pointclickcare.nutrition.jwtconfig.JwtTokenProvider;
import com.pointclickcare.nutrition.repository.FacilityRepository;
import com.pointclickcare.nutrition.util.Utils;

public class AuthInterceptor implements HandlerInterceptor
{
  @Autowired
  private JwtTokenProvider tokenProvider;
  
  @Autowired
  FacilityRepository facilityRepository;
  
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    String jwt = Utils.getJwt(request);
    if(!StringUtils.isEmpty(jwt))
    {      
      String userId= tokenProvider.getPropertyFromJwtToken("userId", jwt);
      Facility facility = facilityRepository.findDefaultFacilityByUserId(Long.valueOf(userId));
      request.setAttribute("userId", userId);
      request.setAttribute("facilityId", facility.getId());
      request.setAttribute("timezone", facility.getTimezoneString());
    }
    return true;
  }
  
}
