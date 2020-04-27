package com.pointclickcare.nutrition.util;

import javax.servlet.http.HttpServletRequest;

public class Utils
{
  
  public static final String getJwt(HttpServletRequest request)
  {
    String authHeader = request.getHeader("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer "))
    {
      return authHeader.replace("Bearer ", "");
    }

    return null;
  }
  
}
