package com.dhdigital.commons.test.integration.commons;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Common functionalities related to the application are implemented here.
 * 
 * @author joshi
 *
 */
public class Commons {
	private static final String DEFAULT_APP_CONFIG = "test-suite.json";
	private static Logger logger = LoggerFactory.getLogger(Commons.class);
	private ObjectMapper objectMapper = new ObjectMapper();
	private static com.dhdigital.commons.test.integration.commons.Logger log = new com.dhdigital.commons.test.integration.commons.Logger();
	private static Map<String, TestRestTemplate> getTRTemplates = new HashMap<String, TestRestTemplate>();;

	/**
	 * Method which takes care of de-serializing the configuration file. <br/>
	 * Configuration file must be a json, by default it would search for
	 * test-suite.json file on the classpath.
	 * 
	 * @param fileName
	 * @return
	 */
	public Config getConfiguration(String fileName) {
		Config config = null;
		try {
			if (null == fileName || "".equals(fileName))
				fileName = DEFAULT_APP_CONFIG;
			logger.debug("Deserializing {} to Config object", fileName);
			config = objectMapper.readValue(new File(fileName), Config.class);
		} catch (Exception e) {
			logger.error("Exception while mapping configuration file to PoJo with details {}", e);
			throw new RuntimeException("Exception while mapping configuration file to PoJo");
		}
		return config;
	}

	/**
	 * Gets the {@link TestRestTemplate} Object which would be used for HTTP methods
	 * GET, POST which are configured in each test case.
	 * 
	 * @param global
	 * @param testCase
	 * @return
	 */
	public TestRestTemplate getTestRestTemplate(Global global, TestCase testCase) {
		String userName;
		String password;
		userName = getUserName(global, testCase);
		password = getPassord(global, testCase);
		return getRestTemplateUsingCredentials(userName, password);
	}

	/**
	 * Get's the application base url which needs to be used to trigger HTTP methods
	 * in each test case. The base url can be kept in global object and can be
	 * overwritten by every test case. Hence appUrl which is configured in globals
	 * would be return unless it is overwritten in the testcase mentioned by the
	 * object.
	 * 
	 * @param global
	 * @param testCase
	 * @return
	 */
	public String getUrl(Global global, TestCase testCase) {
		String url = null;
		url = testCase.getAppUrl();
		log.log(global, testCase, "URL configured for test case is {}", url);
		if (null == url || "".equals(url)) {
			url = global.getUrl();
			log.log(global, testCase, "Since TC Url is not present, URL configured for global is {}", url);
		}
		if (null == url || "".equals(url)) {
			logger.error("Don't really know the server to hit the request. The Url configured is null");
			throw new RuntimeException("Don't really know the server to hit the request. The Url configured is null");
		}
		return url;
	}

	/**
	 * Get's the application userName which needs to be used to trigger HTTP methods
	 * in each test case. The userName can be kept in global object and can be
	 * overwritten by every test case. Hence userName which is configured in globals
	 * would be return unless it is overwritten in the testcase mentioned by the
	 * object.
	 * 
	 * @param global
	 * @param testCase
	 * @return
	 */
	private String getUserName(Global global, TestCase testCase) {
		String userName = null;
		userName = testCase.getUserName();
		log.log(global, testCase, "userName configured for test case is {}", userName);
		if (null == userName || "".equals(userName)) {
			userName = global.getUserName();
			log.log(global, testCase, "Since TC userName is not present, userName configured for test case is {}",
					userName);
		}
		return userName;
	}

	/**
	 * Get's the application password (in plaintext) which needs to be used to
	 * trigger HTTP methods in each test case. The password can be kept in global
	 * object and can be overwritten by every test case. Hence password which is
	 * configured in globals would be return unless it is overwritten in the
	 * testcase mentioned by the object.
	 * 
	 * @param global
	 * @param testCase
	 * @return
	 */
	private String getPassord(Global global, TestCase testCase) {
		String password = null;
		password = testCase.getPassword();
		log.log(global, testCase, "password configured for test case is {}", password);
		if (null == password || "".equals(password)) {
			password = global.getPassword();
			log.log(global, testCase, "Since TC password is not present, password configured for test case is {}",
					password);
		}
		return password;
	}

	/**
	 * It would take care of preparing {@link TestRestTemplate} object for userName
	 * configured. It would store the object in a hashMap for userName so that a new
	 * object is not prepared every time a test case is executed.
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	private TestRestTemplate getRestTemplateUsingCredentials(String userName, String password) {
		TestRestTemplate trTemplate = null;
		synchronized (getTRTemplates) {
			if (getTRTemplates.containsKey(userName)) {
				trTemplate = getTRTemplates.get(userName);
			} else {
				trTemplate = new TestRestTemplate(userName, password);
				getTRTemplates.put(userName, trTemplate);
			}
		}
		return trTemplate;
	}

}
