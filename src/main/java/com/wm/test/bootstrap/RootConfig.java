package com.wm.test.bootstrap;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Bootstrap for service layer.
 * @author Sachin Garg
 */
@Configuration
@Import(DatabaseConfig.class)
@EnableScheduling
@ComponentScan(basePackages = { "com.wm.test.service", "com.wm.test.dao" })
public class RootConfig {

}
