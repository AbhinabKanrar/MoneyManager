/**
 * 
 */
package com.mabsisa.web.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.mabsisa.common.utils.CommonConstant;

/**
 * @author abhinab
 *
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(CommonConstant.URL_LOGIN).setViewName("login");
    }
	
}
