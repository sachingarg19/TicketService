package com.wm.test.springconfig;

import javax.persistence.EntityManager;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.wm.test.bootstrap.DatabaseConfig;
import com.wm.test.service.TicketServiceImpl;

/**
 * Bootstrap for service layer.
 * 
 * @author Sachin Garg
 */
@Configuration
@Import(DatabaseConfig.class)
@ComponentScan(basePackages = { "com.wm.test.service", "com.wm.test.dao" })
public class DaoTestConfig {

	/**
	 * Mock ticket service.
	 *
	 * @return the ticket service
	 */
	@Bean
	//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public TicketServiceImpl mockTicketService() {
		return Mockito.mock(TicketServiceImpl.class);
	}

	/**
	 * Mock ticket service.
	 *
	 * @return the ticket service
	 */
	@Bean
	//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public EntityManager mockEntityManager() {
		return Mockito.mock(EntityManager.class);
	}

}
