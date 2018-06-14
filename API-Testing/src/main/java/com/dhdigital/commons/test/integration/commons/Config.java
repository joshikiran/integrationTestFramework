package com.dhdigital.commons.test.integration.commons;

import java.util.List;

/**
 * Configuration object which holds entire test suite. This POJO will be used by
 * ObjectMapper to de-serialize the json file. It would store both the
 * configuration details and as well as the test case configuration. The
 * application would try to run all the test cases along with assertions in each
 * of the test case.
 * 
 * @author joshi
 *
 */
public class Config {
	private Global globals;
	private List<TestCase> testcases;

	public Global getGlobals() {
		return globals;
	}

	public void setGlobals(Global globals) {
		this.globals = globals;
	}

	public List<TestCase> getTestcases() {
		return testcases;
	}

	public void setTestcases(List<TestCase> testcases) {
		this.testcases = testcases;
	}

}
