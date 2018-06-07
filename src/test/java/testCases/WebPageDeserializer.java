package testCases;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class WebPageDeserializer extends JsonDeserializer<WebPage> {

	@Override
	public WebPage deserialize(JsonParser arg0, DeserializationContext arg1)
			throws IOException, JsonProcessingException {
		
			ObjectCodec obj =  arg0.getCodec();
			JsonNode node = obj.readTree(arg0);
			
			
		
		return null;
	}

}
