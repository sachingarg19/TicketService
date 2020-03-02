package com.wm.test.springconfig;

import org.mockito.Mockito;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.wm.test.service.TicketService;

// TODO: Auto-generated Javadoc
/**
 * The Class ControllerTestConfig.
 */
@Configuration
//@ComponentScan(basePackages = { "com.wm.test.service" })
public class ControllerTestConfig {

	/**
	 * Mock ticket service.
	 *
	 * @return the ticket service
	 */
	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public TicketService mockTicketService() {
		return Mockito.mock(TicketService.class);
	}

}
