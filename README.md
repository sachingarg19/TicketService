# TicketService
Walmart Test
========

###Technology stack

1. Spring 4.2.4, REST service with JSON payload. Uses spring-test for integration tests.
2. PostMan will be used on demonstration of consuming REST service.
3. Embedded HSQL database.

Additionally, this project contains no XML. Spring config is fully annotation driven.

### System requirement to install
1) Maven needs to installed either to use through commandline or using eclipse.
2) PostMan should be installed. If not installed, please install it from below link.
## Postman installation link
https://chrome.google.com/webstore/detail/postman/fhbjgbiflinjbdggehcddcbncdddomop?hl=en


###Installation Steps
0) Import maven project in IDE - eclipse with JDK -1.8 or directly start from step 1 by going to project folder directly.
1) mvn clean install.
2) mvn jetty::run.
3) Launch Postman.
4) click import in postman which is on the upper side of the window.
5) Drag and Drop Postman Walmart Test.json.
6) Rest functionality will get imported and ready to use.

###Resources

* http://www.rockhoppertech.com/blog/spring-mvc-configuration-without-xml/
* http://www.java-allandsundry.com/2012/11/spring-test-mvc-with-spring-32rc1.html
* http://www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-normal-controllers/
* http://www.javacodegeeks.com/2012/10/junit-testing-spring-service-and-dao.html

