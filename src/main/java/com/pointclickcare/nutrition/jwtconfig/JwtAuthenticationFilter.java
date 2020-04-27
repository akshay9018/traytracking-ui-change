package com.pointclickcare.nutrition.jwtconfig;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.momentum.dms.domain.UserLoginToken;
import com.pointclickcare.nutrition.repository.UserLoginTokenRepository;
import com.pointclickcare.nutrition.service.CustomUserDetailsService;
import com.pointclickcare.nutrition.util.Utils;


public class JwtAuthenticationFilter extends OncePerRequestFilter
{
  @Autowired
  private JwtTokenProvider tokenProvider;

  @Autowired
  private CustomUserDetailsService userDetailsService;
  
  @Autowired
  private UserLoginTokenRepository userLoginTokenRepository;

  private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException
  {
    try
    {
      String jwt = Utils.getJwt(request);
      String userId = tokenProvider.getPropertyFromJwtToken("userId", jwt);
      String encryptedToken = tokenProvider.getJwtMD5Hash(jwt);
      UserLoginToken userLoginToken = userLoginTokenRepository.findByUserIdAndToken(Long.valueOf(userId), encryptedToken);
      if (jwt != null && tokenProvider.validateJwtToken(jwt) && userLoginToken.getToken().equals(encryptedToken))
      {
        String username = tokenProvider.getUserNameFromJwtToken(jwt);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    catch (Exception e)
    {
      logger.error("Can NOT set user authentication -> Message: {}", e);
    }

    filterChain.doFilter(request, response);
  }

  

}
