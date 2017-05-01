/**
 * 
 */
package com.mabsisa.dao;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

/**
 * @author abhinab
 *
 */
@Configuration
@ComponentScan(basePackages="com.mabsisa.dao")
@PropertySource(value = { "classpath:datasource.properties" })
public class ApplicationDaoContext {
	
	@Bean
	@Primary
	@ConfigurationProperties(prefix="datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

}
