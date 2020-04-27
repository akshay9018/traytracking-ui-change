package com.pointclickcare.nutrition.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pointclickcare.nutrition.bean.UIUser;
import com.pointclickcare.nutrition.service.CustomUserDetailsService;

@Controller
public class LoginController
{
	  @Autowired
	  CustomUserDetailsService userDetailsService;

  @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<?> doLogin(@RequestBody UIUser uiUser)
  {
	  try
	  {
		  return ResponseEntity.ok(userDetailsService.validateUser(uiUser));
	  }
	  catch (NullPointerException|BadCredentialsException e) {
	    return new ResponseEntity<>(
	        "Invalid Username/Password", 
	        HttpStatus.UNAUTHORIZED);
    }
	  catch (Exception e) {
	    return new ResponseEntity<>(
	        "You are not authorized to access the application", 
	        HttpStatus.UNAUTHORIZED);
	  }
  }
  
  @RequestMapping(value = "/doLogout", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<?> doLogout(@RequestAttribute Long userId, @RequestHeader("Authorization") String authHeader)
  {
    try
    {
      userDetailsService.doLogout(userId, authHeader.replace("Bearer ", ""));
      return ResponseEntity.ok(HttpStatus.OK);
    }
    catch(NoSuchAlgorithmException ex)
    {
      return (ResponseEntity<?>) ResponseEntity.badRequest();
    }
  }
}
