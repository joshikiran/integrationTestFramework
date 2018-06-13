package com.dhdigital.commons.test.integration.commons;

import java.util.List;

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
