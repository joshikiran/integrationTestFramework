package com.dhdigital.commons.test.integration.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dhdigital.commons.test.integration.commons.Commons;
import com.dhdigital.commons.test.integration.commons.Config;
import com.dhdigital.commons.test.integration.commons.Global;
import com.dhdigital.commons.test.integration.commons.TestCase;

public class RunTestSuite {

	private static Commons commons = new Commons();
	private static com.dhdigital.commons.test.integration.commons.Logger log = new com.dhdigital.commons.test.integration.commons.Logger();
	private static RestAPIExecution rpe = new RestAPIExecution();
	private static Logger logger = LoggerFactory.getLogger(RunTestSuite.class);

	public static void main(String args[]) {
		RunTestSuite rts = new RunTestSuite();
		if (null == args || args.length == 0)
			rts.runTestSuite("");
		else
			rts.runTestSuite(args[0]);
	}

	private void runTestSuite(String testSuiteConfig) {
		Config config = null;
		List<TestCase> testCases = null;
		Global globals = null;
		Map<String, Object> tcResults = null;
		Object tcResult = null;
		try {
			/*
			 * Get the configuration object for testSuiteConfig
			 */
			config = commons.getConfiguration(testSuiteConfig);
			logger.debug("Retrieved Config Object {}", config.toString());
			globals = config.getGlobals();
			logger.debug("Retrieved Global information {}", globals.toString());
			/*
			 * Collect all Test Cases along with global configurations
			 */
			testCases = config.getTestcases();
			if (null == testCases || testCases.size() == 0) {
				logger.info("No test cases to run. Application exits.");
			}
			/*
			 * Run each test case by sending global config along with test case as well
			 */
			// Initializing tcResults
			tcResults = new HashMap<String, Object>();
			for (TestCase tc : testCases) {
				tcResult = runTestCase(globals, tc);
				tcResults.put(tc.getName(), tcResult);
			}
			log.log(globals, "Printing the Results of Test Cases");
			printResults(tcResults);
		} catch (Exception e) {
			logger.error("Exception while running the test suite {}", testSuiteConfig);
			throw new RuntimeException("Exception while running the test suite with details ", e);
		}
	}

	private Object runTestCase(Global global, TestCase testCase) {
		Object tcResult = null;
		String reqMethod = null;
		reqMethod = Optional.of(testCase.getRequestMethod()).orElse("GET");
		log.log(global, testCase, "-----Running the test case {}-----", testCase.getName());
		if (!"POST".equalsIgnoreCase(reqMethod))
			tcResult = rpe.callGetRequest(commons, log, global, testCase);
		else
			tcResult = rpe.callPostRequest(commons, log, global, testCase);
		log.log(global, testCase, "-----Ending the test case {}-----", testCase.getName());
		return tcResult;
	}

	@SuppressWarnings("unchecked")
	private void printResults(Map<String, Object> tcResults) {
		Set<String> tcList = tcResults.keySet();
		Map<String, String> assertion = null;
		Set<String> assertionKeys = null;
		logger.info("*****************");
		for (String tc : tcList) {
			logger.info("{}", tc);
			logger.info("------------------");
			assertion = (Map<String, String>) tcResults.get(tc);
			assertionKeys = assertion.keySet();
			for (String aKey : assertionKeys) {
				logger.info("Assertion Status for \"{}\" ==> \"[{}]\"", aKey, assertion.get(aKey));
			}
			logger.info("------------------");
		}
		logger.info("*****************");
	}
}
