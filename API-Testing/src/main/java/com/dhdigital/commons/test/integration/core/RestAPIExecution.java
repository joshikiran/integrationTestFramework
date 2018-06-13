package com.dhdigital.commons.test.integration.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import com.dhdigital.commons.test.integration.commons.Assertions;
import com.dhdigital.commons.test.integration.commons.Commons;
import com.dhdigital.commons.test.integration.commons.Global;
import com.dhdigital.commons.test.integration.commons.Logger;
import com.dhdigital.commons.test.integration.commons.TestCase;
import com.jayway.jsonpath.JsonPath;

public class RestAPIExecution {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(RestAPIExecution.class);

	public Map<String, String> callGetRequest(Commons commons, Logger log, Global global, TestCase testCase) {
		TestRestTemplate testRestTemplate = null;
		String url = null;
		ResponseEntity<String> responseStr = null;
		Map<String, String> assertionsResponse = null;
		String response = null;
		try {
			log.log(global, testCase, "Getting rest template for Get Request");
			testRestTemplate = commons.getTestRestTemplate(global, testCase);
			url = getUrl(commons, log, global, testCase);
			responseStr = testRestTemplate.getForEntity(url, String.class);
			response = responseStr.getBody().toString();
			log.log(global, testCase, "Response Object is {} ", response);
			assertionsResponse = validateResponse(log, global, testCase, response);
		} catch (Exception e) {
			assertionsResponse = new HashMap<>();
			logger.error("Exception while calling get request with details {}", e);
			throw e;
		}
		return assertionsResponse;
	}

	public Map<String, String> callPostRequest(Commons commons, Logger log, Global global, TestCase testCase) {
		TestRestTemplate testRestTemplate = null;
		String url = null;
		ResponseEntity<String> responseStr = null;
		Map<String, String> assertionsResponse = null;
		try {
			log.log(global, testCase, "Getting rest template for Get Request");
			testRestTemplate = commons.getTestRestTemplate(global, testCase);
			url = getUrl(commons, log, global, testCase);
			log.log(global, testCase, "Request Mapping url is {}", url);
			responseStr = testRestTemplate.getForEntity(url, String.class);
			assertionsResponse = validateResponse(log, global, testCase, responseStr.getBody());
		} catch (Exception e) {
			logger.error("Exception while calling get request with details {}", e);
			throw e;
		}
		return assertionsResponse;
	}

	private String getUrl(Commons commons, Logger log, Global global, TestCase testCase) {
		String url = null;
		String reqMappingUrl = null;
		try {
			url = commons.getUrl(global, testCase);
			log.log(global, testCase, "App Url is {}", url);
			reqMappingUrl = Optional.of(testCase.getRequestMappingUrl()).orElse("");
			url = url + reqMappingUrl;
			log.log(global, testCase, "Request Mapping url is {}", url);
		} catch (Exception e) {
			logger.error("Exception while calling get request with details {}", e);
			throw e;
		}
		return url;
	}

	private Map<String, String> validateResponse(Logger log, Global global, TestCase testCase, String responseObj) {
		String result = null;
		List<Assertions> assertions = new ArrayList<Assertions>();
		Map<String, String> assertionsResponse = null;
		try {
			log.log(global, testCase, "Converting the object into Json");
			assertions.addAll(global.getAssertions());
			assertions.addAll(testCase.getAssertions());
			if (null == assertions || assertions.size() == 0) {
				log.log(global, testCase, "No Assertions are configured for test case {} ", testCase.getName());
			} else {
				assertionsResponse = new HashMap<>();
				for (Assertions assertion : assertions) {
					result = validateAssertion(log, global, testCase, assertion, responseObj);
					assertionsResponse.put(assertion.getName(), result);
				}
			}
		} catch (Exception e) {
			logger.error("Exception while validating the response with details {}", e);
			result = e.getMessage();
		}
		return assertionsResponse;
	}

	private String validateAssertion(Logger log, Global global, TestCase testCase, Assertions assertion,
			String jsonString) {
		String response = null;
		String jsonPath = null;
		Object obj = null;
		try {
			log.log(global, testCase, "Validating assertion {} ", assertion.getName());
			jsonPath = assertion.getJsonPath();
			obj = JsonPath.read(jsonString, jsonPath);
			if (null == obj)
				response = "FAILURE";
			else
				response = "SUCCESS";
		} catch (Exception e) {
			logger.error("Exception while validating assertion with details {} ", e);
			response = e.getLocalizedMessage();
		}
		return response;
	}
}
