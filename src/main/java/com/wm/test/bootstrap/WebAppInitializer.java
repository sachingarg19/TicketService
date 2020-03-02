package com.wm.test.bootstrap;

import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

// TODO: Auto-generated Javadoc
/**
 * This is the main bootstrap. Note the special interface, which is called on
 * startup. This declares the Spring contexts (root and mvc) and binds the
 * dispatcher servlet.
 * @author Sachin Garg
 */
public class WebAppInitializer implements WebApplicationInitializer {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(WebApplicationInitializer.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.WebApplicationInitializer#onStartup(javax.servlet
	 * .ServletContext)
	 */
	public void onStartup(ServletContext servletContext) throws ServletException {
		logger.info("---------Initialization Start----------");
		// Create the root appcontext
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(RootConfig.class);
		logger.info("---------RootConfig Initialization Completed----------");
		// since we registered RootConfig instead of passing it to the
		// constructor
		// rootContext.refresh();

		// Manage the lifecycle of the root appcontext
		servletContext.addListener(new ContextLoaderListener(rootContext));
		servletContext.setInitParameter("defaultHtmlEscape", "true");

		// now the config for the Dispatcher servlet
		AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
		mvcContext.register(MvcConfig.class);
		logger.info("---------MvcConfig Initialization Completed----------");
		// The main Spring MVC servlet.
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher",
				new DispatcherServlet(mvcContext));
		dispatcher.setLoadOnStartup(1);
		Set<String> mappingConflicts = dispatcher.addMapping("/api/*");

		if (!mappingConflicts.isEmpty()) {
			for (String s : mappingConflicts) {
				logger.error("Mapping conflict: " + s);
			}
			throw new IllegalStateException("'dispatcher' cannot be mapped to '/' under Tomcat versions <= 7.0.14");
		}
		logger.info("---------Initialization Completed----------");
	}

}
