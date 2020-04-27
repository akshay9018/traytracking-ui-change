package com.pointclickcare.nutrition.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.pointclickcare.nutrition.intercepter.AuthInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan("com.pointclickcare.nutrition")
public class WebMvcConfig implements WebMvcConfigurer
{
	
	 @Bean(name = "viewResolver")	
	 public InternalResourceViewResolver getViewResolver() 
	 {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	 }
	 
	 public void addResourceHandlers(ResourceHandlerRegistry registry)
	 {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/**").addResourceLocations("/resources/js/sw.js");
	 }
	 
	 @Bean
	 public AuthInterceptor getAuthInterceptor()
	 {
	   return new AuthInterceptor();
	 }
	 
	 
	 public void addInterceptors(InterceptorRegistry registry) 
	 {
	   registry.addInterceptor(getAuthInterceptor());
	 }
	 
}
