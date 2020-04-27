package com.pointclickcare.nutrition.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.LdapAuthenticator;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.momentum.dms.domain.SystemConfig;
import com.pointclickcare.nutrition.jwtconfig.JWTAuthenticationEntryPoint;
import com.pointclickcare.nutrition.jwtconfig.JwtAuthenticationFilter;
import com.pointclickcare.nutrition.repository.SystemConfigRepository;
import com.pointclickcare.nutrition.service.CustomUserDetailsContextMapper;
import com.pointclickcare.nutrition.service.CustomUserDetailsService;
import com.pointclickcare.nutrition.util.CustomPasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableAspectJAutoProxy(proxyTargetClass = true) 
@ComponentScan("com.pointclickcare.nutrition")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	 

  public static final String LDAP_PROVIDER_URL = "ldap.provider.url";
	
  @Autowired
  CustomUserDetailsService userDetailsService;

  @Autowired
		SystemConfigRepository systemConfigRepository;
		
		@Autowired
	  private JWTAuthenticationEntryPoint unauthorizedHandler;

	    @Bean
	    public JwtAuthenticationFilter authenticationJwtTokenFilter() {
	        return new JwtAuthenticationFilter();
	    }

	    @Override
	    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//	        authenticationManagerBuilder
//	                .userDetailsService(userDetailsService)
//	                .passwordEncoder(passwordEncoder());
	      authenticationManagerBuilder
	      .authenticationProvider(ldapAuthenticationProvider());
	    }
	    
	    @Bean
	    public UserDetailsContextMapper userDetailsContextMapper() {
	        return new CustomUserDetailsContextMapper();
	    }
	    
	    
	    @Bean
	    public LdapAuthenticationProvider ldapAuthenticationProvider() throws Exception {
	        LdapAuthenticationProvider lAP = new LdapAuthenticationProvider(ldapAuthenticator());
	        lAP.setUserDetailsContextMapper(userDetailsContextMapper());
	        return lAP;
	    }
	 
	    @Bean
	    public LdapContextSource ldapContextSource() throws Exception {
	        DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource(systemConfigRepository.findByPropertyName(LDAP_PROVIDER_URL).getValue());
	        return contextSource;
	    }
	 
	    @Bean
	    public LdapAuthenticator ldapAuthenticator() throws Exception {
	        BindAuthenticator authenticator = new CustomAuthenticationProvider(ldapContextSource());
	        authenticator.setUserDnPatterns(new String[] {"CN={0}"});
	        return authenticator;
	    }
	    
	    @Bean
	    @Override
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }
	    

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new CustomPasswordEncoder();
	    }

	    protected void configure(HttpSecurity http) throws Exception {
	        http.cors().and().csrf().disable().
                authorizeRequests()
                .anyRequest().permitAll();
        
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
