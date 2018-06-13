package com.dhdigital.commons.test.integration.commons;

import java.util.List;

public class TestCase {
	private String name;
	private String description;
	private String appUrl;
	private String userName;
	private String password;
	private String requestMappingUrl;
	private String requestMethod;
	private String requestBody;
	private boolean loggingEnabled;
	private List<Assertions> assertions;

	public boolean isLoggingEnabled() {
		return loggingEnabled;
	}

	public void setLoggingEnabled(boolean loggingEnabled) {
		this.loggingEnabled = loggingEnabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
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

	public String getRequestMappingUrl() {
		return requestMappingUrl;
	}

	public void setRequestMappingUrl(String requestMappingUrl) {
		this.requestMappingUrl = requestMappingUrl;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public List<Assertions> getAssertions() {
		return assertions;
	}

	public void setAssertions(List<Assertions> assertions) {
		this.assertions = assertions;
	}

}
