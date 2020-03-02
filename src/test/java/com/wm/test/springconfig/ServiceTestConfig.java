package com.wm.test.springconfig;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.wm.test.dao.ApplicationDao;

/**
 * Bootstrap for service layer.
 * 
 * @author Sachin Garg
 */
@Configuration
@EnableTransactionManagement

//@ComponentScan(basePackages = { "com.wm.test.service" })
public class ServiceTestConfig {

	/**
	 * Mock ticket dao.
	 * 
	 * 
	 */
	@Bean
	public ApplicationDao mockTicketDao() {
		return Mockito.mock(ApplicationDao.class);
	}

}
