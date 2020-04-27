package com.pointclickcare.nutrition.service;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;

import com.momentum.dms.domain.User;
import com.pointclickcare.nutrition.bean.UserPrincipal;
import com.pointclickcare.nutrition.repository.UserRepository;

public class CustomUserDetailsContextMapper extends LdapUserDetailsMapper {

  @Autowired
  UserRepository userRepository; 

  @Override
  public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {
      username = StringUtils.split(username, "\\")[1];
      User user = userRepository.findByUserName(username);
      if(user != null)
      {
        return new UserPrincipal(user);
      }
      return null;
  }
}
