package testCases;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;

import junit.framework.Assert;

public class ApiValidation {

	public static void main(String args[]) throws ProcessingException, IOException {

		 HttpClientConnectionManager poolingConnManager = new
		 PoolingHttpClientConnectionManager();
		
		 final CloseableHttpClient httpclient =
		 HttpClients.custom().setConnectionManager(poolingConnManager).build();
		 String url =
		 "https://gist.githubusercontent.com/ab9-er/27a903b8636e745fb820a7d310177c46/raw/9315383f0a0f8da2f0931ba69ab7be2d1f7b95b5/world_universities_and_domains.json";
		 HttpGet httpGet = new HttpGet(url);
		 try (CloseableHttpResponse response = httpclient.execute(httpGet);
		 InputStream is = response.getEntity().getContent();) {
		 JsonToken token = null;
		 JsonParser js = new ObjectMapper()
		 .configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES,
		 true)
		 .configure(Feature.ALLOW_MISSING_VALUES, true)
		 .configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
		 true).
		 getFactory().createParser(is);
		 token = js.nextToken();
		 if (token != null && token == JsonToken.START_ARRAY) {
		 int i=0;
		 while (js.nextToken() != JsonToken.END_ARRAY) {
		 try {
		 WebPage webpage = js.readValueAs(WebPage.class);
		 getValidationResults(webpage);
		 System.out.println(i++);
		 System.out.println(webpage.toString());
		 } catch (Exception e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }
		 }
		
		 } else {
		 System.out.println("The data provided is not an array!");
		 
		 }
		 }
		 }

	// int i = 1;
	//
	// try (
	//
	// InputStream is = new FileInputStream(
	// "/Users/tanumukherjee/eclipse-workspace/RestApiValidation/src/main/java/resources/input.json"))
	// {
	// // JsonToken token = null;
	// JsonParser js = new ObjectMapper()
	// .configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_INVALID_SUBTYPE,
	// true)
	// .configure(Feature.ALLOW_MISSING_VALUES, true).getFactory().createParser(is);
	// WebPage webpage = js.readValueAs(WebPage.class);
	// //getValidationResults(webpage);
	// System.out.println(i++);
	// System.out.println(webpage.toString());
	// }
	// // getValidationResults( webpage);
	//
	// }

	public static void getValidationResults(WebPage page) {

		if (page.getCountry() != null) {
			// Starting validations
			String country = page.getCountry();
			System.out.println("country: " + country);
			Assert.assertTrue(country.matches("[a-zA-Z]+"));

		}

		if (page.getDomains() != null) {
			List<String> domainList = page.getDomains();
			for (String domain : domainList) {
				Assert.assertTrue(domain.matches("^[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,6}$"));
			}
		}

		if (page.getAlphaTwoCode() != null) {
			String alpha_two_code = page.getAlphaTwoCode();
			System.out.println(alpha_two_code);
			Assert.assertTrue(alpha_two_code.matches("^([a-zA-Z]{2,3})$"));
		}

		if (page.getName() != null) {
			String name = page.getName();
			System.out.println("name: (ASw) " + name);
			Assert.assertTrue(name.matches("[a-zA-Z0-9 -]+"));
		}
		if (page.getWebPages() != null) {
			List<String> pageList = page.getWebPages();
			for (String webPage : pageList) {
				System.out.println("webPage: " + webPage);
				Assert.assertTrue(webPage.matches("^(http)|(https)://[a-z]{3}.+"));
			}
		}
		if (page.getStateProvince() != null) {
			String state_province = page.getStateProvince();
			Assert.assertTrue(state_province.matches(".+"));
		}

	}

}
