package testCases;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import resources.WebPage;

public class ApiValidation {

	/***
	 * @author tanumukherjee The runner method is validationRunner(). This requests
	 *         a set of URLs to the getApiValidation method which does the
	 *         following- Connection Management - HTTP Connection Management API of
	 *         HttpClient to handle the entire process of managing connections –
	 *         from opening and allocating them, through managing their concurrent
	 *         use by multiple agents, to finally closing them. Logging- log4J will
	 *         collect all the logs
	 */

	public static Logger log = LogManager.getLogger(ApiValidation.class.getName());

	@Test
	public void validationRunner() throws ProcessingException, IOException {
		String[] urls = { "https://git.io/vpg9V", "https://git.io/vpg95" };
		for (int i = 0; i < urls.length; i++) {
			getApiValidation(urls[i]);
		}
	}

	public static void getApiValidation(String url) throws ProcessingException, IOException {

		/***
		 * Schema Validation-Jackson's serialization and deserialization feature is
		 * being used. So if any of the keys get missing, the test case will fail citing
		 * proper reasons on console and logs Schema validation Test cases- #
		 * FAIL_ON_MISSING_CREATOR_PROPERTIES - Test will fail if say country goes
		 * missing # FAIL_ON_UNKNOWN_PROPERTIES - Test will fail if say some invalid key
		 * ("abcd") gets added
		 */

		HttpClientConnectionManager poolingConnManager = new PoolingHttpClientConnectionManager();
		final CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(poolingConnManager).build();
		HttpGet httpGet = new HttpGet(url);
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		try (CloseableHttpResponse response = httpclient.execute(httpGet);
				InputStream is = response.getEntity().getContent();) {
			JsonToken token = null;
			JsonParser js = new ObjectMapper()
					.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES,
							true)
					.configure(Feature.ALLOW_MISSING_VALUES, true)
					.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)

					.getFactory().createParser(is);
			token = js.nextToken();
			if (token != null && token == JsonToken.START_ARRAY) {
				int i = 1;
				while (js.nextToken() != JsonToken.END_ARRAY) {
					try {
						WebPage webpage = js.readValueAs(WebPage.class);
						i++;
						getValidationResults(webpage, map);
					} catch (Exception e) {
						log.info("error while parsing webpage object", e);
						String error = e.getMessage();
						if (error != null)
							errorCounter(map, error.split("\n")[0]);

					}
				}

				// printing the map and displaying the results on console
				System.out.println("=======Result for URL:" + url + "===========");
				for (Map.Entry<String, Integer> entry : map.entrySet()) {
					System.out.println(entry.getKey() + " : " + entry.getValue());
				}
				System.out.println("Total number of objects parsed: " + i);

				System.out.println("===================================");

			} else {
				System.out.println("The data provided is not an array!");

			}
		}
	}

	public static void getValidationResults(WebPage page, HashMap<String, Integer> map) {

		/***
		 * Data validation Test Cases - All the responses are parsed using jackson and
		 * then the objects are individually validated against the keys. Regex has been
		 * used for data format and sanity checks. If any of the value doesn't match
		 * appropriate regex it will be catched into the error counter to display the
		 * type of the error it had encountered and its number of occurences It also
		 * catches the null count for any key and its number of occurences
		 */

		// Starting validations

		if (page.getCountry() != null) {
			String country = page.getCountry();
			try {
				Assert.assertTrue(country.matches("[a-zA-Z -,]+"));
			} catch (AssertionError | AssertionFailedError e) {
				log.info("Country provided is invalid!!");
				errorCounter(map, "country_Errors");

			}
		} else {

			errorCounter(map, "nullCountry");

		}

		if (page.getDomains() != null) {
			List<String> domainList = page.getDomains();
			for (String domain : domainList) {
				try {
					Assert.assertTrue(domain.matches("^[a-z0-9_]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,6}$"));
				} catch (AssertionError | AssertionFailedError e) {
					log.info("Invalid domain!!");
					errorCounter(map, "domain_Errors");

				}
			}
		} else {
			errorCounter(map, "domainNullCount");

		}

		if (page.getAlphaTwoCode() != null) {
			String alpha_two_code = page.getAlphaTwoCode();
			try {
				Assert.assertTrue(alpha_two_code.matches("^([a-zA-Z]{2,3})$"));
			} catch (AssertionError | AssertionFailedError e) {
				log.info("Invalid AlphaTwoCode!!");
				errorCounter(map, "alpha_two_code_Errors");

			}

		} else {
			errorCounter(map, "getAlphaTwoCodeNullCount");
		}

		if (page.getName() != null) {
			String name = page.getName();
			try {
				Assert.assertTrue(name.matches(".+"));
			} catch (AssertionError | AssertionFailedError e) {
				errorCounter(map, "name_Errors");

			}

		} else {
			errorCounter(map, "nameNullCount");

		}
		if (page.getWebPages() != null) {
			List<String> pageList = page.getWebPages();
			for (String webPage : pageList) {
				try {
					// Assert.assertTrue(urlValidator.isValid(webPage));
					Assert.assertTrue(webPage.matches(
							"^((https?|http?|smtp):\\/\\/)?(www\\.)?[A-Za-z0-9-_]+(\\.[A-Za-z-_]+)+(\\/[a-zA-Z0-9\\/\\.]*?)*$"));
				} catch (AssertionError | AssertionFailedError e) {
					log.info("Invalid webPage");
					errorCounter(map, "webPage_Errors");

				}

			}
		} else {

			errorCounter(map, "webPageNullCount");
		}
		if (page.getStateProvince() != null) {
			String state_province = page.getStateProvince();

			try {
				Assert.assertTrue(state_province.matches(".+"));
			} catch (AssertionError | AssertionFailedError e) {
				log.info("Invalid state province");
				errorCounter(map, "stateProvince_Errors");
			}

		} else {
			errorCounter(map, "stateProvinceNullCount");
		}

	}

	/*
	 * errorCounter catches the type of error and its occurences
	 */

	public static void errorCounter(HashMap<String, Integer> map, String errorMessage) {

		int errorCount = 0;
		if (map.containsKey(errorMessage)) {
			errorCount = map.get(errorMessage);
		}
		map.put(errorMessage, errorCount + 1);

	}

}
