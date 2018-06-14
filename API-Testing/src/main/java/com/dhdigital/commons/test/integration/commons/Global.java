package com.dhdigital.commons.test.integration.commons;

import java.util.List;

/**
 * Global configuration object which store configuration related to the
 * application. It would include application url, credentials and assertions to
 * run globally.
 * 
 * @author joshi
 *
 */
public class Global {

	private String testSuiteName;
	private String url;
	private String userName;
	private String password;
	private boolean isLoggingEnabled;

	public String getTestSuiteName() {
		return testSuiteName;
	}

	public void setTestSuiteName(String testSuiteName) {
		this.testSuiteName = testSuiteName;
	}

	public boolean isLoggingEnabled() {
		return isLoggingEnabled;
	}

	public void setLoggingEnabled(boolean isLoggingEnabled) {
		this.isLoggingEnabled = isLoggingEnabled;
	}

	private List<Assertions> assertions;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Assertions> getAssertions() {
		return assertions;
	}

	public void setAssertions(List<Assertions> assertions) {
		this.assertions = assertions;
	}

}
