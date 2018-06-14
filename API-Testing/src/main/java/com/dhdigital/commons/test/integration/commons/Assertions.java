package com.dhdigital.commons.test.integration.commons;

/**
 * Assertions POJO. This object holds the assertions details for any test
 * case(s).
 * 
 * @author joshi
 *
 */
public class Assertions {

	private String name;
	private String description;
	private String jsonPath;

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

	public String getJsonPath() {
		return jsonPath;
	}

	public void setJsonPath(String jsonPath) {
		this.jsonPath = jsonPath;
	}

}
