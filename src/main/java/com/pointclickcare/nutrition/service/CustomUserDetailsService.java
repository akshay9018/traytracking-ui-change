package com.pointclickcare.nutrition.service;

import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.directory.InitialDirContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.momentum.dms.domain.Role;
import com.momentum.dms.domain.SystemConfig;
import com.momentum.dms.domain.User;
import com.momentum.dms.domain.UserLoginToken;
import com.pointclickcare.nutrition.bean.JwtAuthenticationResponse;
import com.pointclickcare.nutrition.bean.UIUser;
import com.pointclickcare.nutrition.bean.UserPrincipal;
import com.pointclickcare.nutrition.jwtconfig.JwtTokenProvider;
import com.pointclickcare.nutrition.repository.SystemConfigRepository;
import com.pointclickcare.nutrition.repository.UserLoginTokenRepository;
import com.pointclickcare.nutrition.repository.UserRepository;

@Service("userDetailsService")
@Transactional
public class CustomUserDetailsService implements UserDetailsService
{
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserLoginTokenRepository userLoginTokenRepository;

	@Autowired
  AuthenticationManager authenticationManager;
	
	@Autowired
	SystemConfigRepository systemConfigRepository;
	
	 @Autowired
	 JwtTokenProvider tokenProvider;
	 private static final String LDAP_DOMAIN = "ldap.domain";
	 
	 @PersistenceContext  private EntityManager em;  
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username);
		return new UserPrincipal(user);
	}
	
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return new UserPrincipal(user);
    }
    
    public JwtAuthenticationResponse validateUser(UIUser uiUser) throws Exception
    {
      try{
        SystemConfig systemConfig = systemConfigRepository.findByPropertyName(LDAP_DOMAIN);
        String userName = systemConfig.getValue() + "\\\\"+uiUser.getUserName();
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                userName,
                uiUser.getPassword()
                )
            );  
        
        SecurityContextHolder.getContext().setAuthentication(authentication); 
        User user = ((UserPrincipal)authentication.getPrincipal()).getUser();
        for (Role role : user.getRoles())
        {
          if(role.getId().equals(15l))
          {
            String jwt = tokenProvider.generateJwtToken(authentication);
            userLoginTokenRepository.save(new UserLoginToken(tokenProvider.getJwtMD5Hash(jwt), user.getId()));
            return new JwtAuthenticationResponse(jwt, user.getFacility().getName());
          }
        }
        throw new Exception();
      }
      catch(BadCredentialsException e)
      {
        throw e;
      }
      catch (Exception e) 
      {
        throw e;
      }
  }

	public void doLogout(Long userId, String token) throws NoSuchAlgorithmException
	{
		clearHibernateCache();
		userLoginTokenRepository.deleteByUserIdAndToken(userId, tokenProvider.getJwtMD5Hash(token));
	}
	
	

	public void clearHibernateCache() 
	{     
	 Session s = (Session)em.getDelegate();      
	 SessionFactory sf = s.getSessionFactory();  
	 sf.getCache().evictAllRegions();
	}

}
