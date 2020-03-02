package com.wm.test.bootstrap;

import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.WebApplicationInitializer;

/**
 * Database configuration. Note the 'Import' annotation in RootConfig that activates this. PropertySource and
 * Environment pair allows externalized settings.
 * 
 * @author Sachin Garg
 * 
 */
@Configuration
@EnableTransactionManagement
public class DatabaseConfig {
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(WebApplicationInitializer.class);

	/**
	 * Data source.
	 *
	 * @return the data source
	 */
	@Bean
	public DataSource dataSource() {
		logger.info("Create DataSource");
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		return builder.setType(EmbeddedDatabaseType.HSQL).build();		
	}

	/**
	 * Entity manager factory.
	 *
	 * @return the local container entity manager factory bean
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		logger.info("Enter entityManagerFactory");
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.HSQL);
		vendorAdapter.setGenerateDdl(true);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		Properties props = new Properties();        
		props.setProperty("hibernate.hbm2ddl.auto", "update");

		factory.setJpaProperties(props);
		factory.setJpaVendorAdapter(vendorAdapter);
		// factory.setPackagesToScan(ApplicationDaoImpl.class.getPackage().getName());
		factory.setDataSource(dataSource());
		logger.info("Exit entityManagerFactory");
		return factory;
	}

	/**
	 * Transaction manager.
	 *
	 * @return the platform transaction manager
	 */
	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return txManager;
	}

}
