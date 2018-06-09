# RestApiValidation

#Schema Validation - schema validation is done using Jackson's annoatation feature

  The Jackson library provides annotations that you can
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
   
      Jackson's serialization and deserialization feature is
		  being used. So if any of the keys get missing, the test case will fail citing
		  proper reasons on console and logs Schema validation Test cases- #
		  FAIL_ON_MISSING_CREATOR_PROPERTIES - Test will fail if say country goes
		  missing # FAIL_ON_UNKNOWN_PROPERTIES - Test will fail if say some invalid key
		  ("abcd") gets added
      
      Examlple-
      This will validate againsts these cases-
        1.Empty String
        2.Missing key -Should fail and show error like-Missing required creator property 'alpha_two_code'
        3.Incorrect key - Any unacceptable key/json property
   
   #Data (response) validation- 
   
   
    Data validation Test Cases - All the responses are parsed using jackson and
		 * then the objects are individually validated against the keys. Regex has been
		 * used for data format and sanity checks. If any of the value doesn't match
		 * appropriate regex it will be catched into the error counter to display the
		 * type of the error it had encountered and its number of occurences It also
		 * catches the null count for any key and its number of occurences
   
   
   
   #Runner - 
            The runner method is validationRunner(). This requests
	          a set of URLs to the getApiValidation method which does the
	          following- Connection Management - HTTP Connection Management API of
	          HttpClient to handle the entire process of managing connections –
	          from opening and allocating them, through managing their concurrent
	          use by multiple agents, to finally closing them. Logging- log4J will
	          collect all the logs
            
            
    #Project management/dependency - Run this project using maven (console - mvn test) or eclipse maven goals
    
    IMPORTANT NOTE: Second URL provided returns many incorrect keys/responses. So it would fail. For instance it fails for
    country as the key returned is "county",
    Also the validation is based upon regexes. So not all the responses pass the regex test as some the of the returned values are
    invalid. If those are valid then we can just include those by modifying the regexes. And as per requirement the nulls have 
    been taken as an accepted value, so we are just returing the number of occurences for any object 
    
    
