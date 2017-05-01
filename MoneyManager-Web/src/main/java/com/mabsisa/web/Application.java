/**
 * 
 */
package com.mabsisa.web;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.mabsisa.service.ApplicationServiceContext;

/**
 * @author abhinab
 *
 */
@SpringBootApplication
@ComponentScan(basePackages="com.mabsisa.web")
@Import(ApplicationServiceContext.class)
public class Application implements CommandLineRunner {

	/* (non-Javadoc)
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	@Override
	public void run(String... args) throws Exception {
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
