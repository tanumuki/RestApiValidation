package resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WebPage {

	/***
	 * @author tanumukherjee 
	 * 		   The Jackson library provides annotations that you can
	 *         use in POJOs to control both serialization and deserialization
	 *         between POJOs and JSON The @JsonProperty annotation is used to map
	 *         property names with JSON keys during serialization and
	 *         deserialization. By default, if you try to serialize a POJO, the
	 *         generated JSON will have keys mapped to the fields of the POJO. We can also
	 *         override this behavior by using the @JsonProperty
	 *         annotation on the fields. It takes a String attribute that specifies
	 *         the name that should be mapped to the field during serialization. You
	 *         can also use this annotation during deserialization when the property
	 *         names of the JSON and the field names of the Java object do not match.
	 * 
	 * 
	 * @return
	 */

	public List<String> getWebPages() {
		return webPages;
	}

	public void setWebPages(List<String> webPages) {
		this.webPages = webPages;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlphaTwoCode() {
		return alphaTwoCode;
	}

	public void setAlphaTwoCode(String alphaTwoCode) {
		this.alphaTwoCode = alphaTwoCode;
	}

	public String getStateProvince() {
		return stateProvince;
	}

	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}

	public List<String> getDomains() {
		return domains;
	}

	public void setDomains(List<String> domains) {
		this.domains = domains;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "WebPage [webPages=" + webPages + ", name=" + name + ", alphaTwoCode=" + alphaTwoCode
				+ ", stateProvince=" + stateProvince + ", domains=" + domains + ", country=" + country + "]";
	}

	// @JsonProperty(value="web_pages", required = true)
	private List<String> webPages = null;
	// @JsonProperty(value = "name", required = true)
	private String name;
	// @JsonProperty(value = "alpha_two_code", required = true)
	private String alphaTwoCode;
	// @JsonProperty(value = "state-province", required = true)
	private String stateProvince;
	// @JsonProperty(value="domains",required = true)
	private List<String> domains = null;
	// @JsonProperty(value="country",required = true)
	private String country;

	public WebPage(@JsonProperty(value = "web_pages", required = true) List<String> webPages,
			@JsonProperty(value = "name", required = true) String name,
			@JsonProperty(value = "alpha_two_code", required = true) String alphaTwoCode,
			@JsonProperty(value = "state-province", required = true) String stateProvince,
			@JsonProperty(value = "domains", required = true) List<String> domains,
			@JsonProperty(value = "country", required = true) String country) {

		this.webPages = webPages;
		this.name = name;
		this.alphaTwoCode = alphaTwoCode;
		this.stateProvince = stateProvince;
		this.domains = domains;
		this.country = country;
	}

}