package net.bitnine.agensgraphsample.basic.api.util;

import net.bitnine.agensgraph.deps.org.json.simple.JSONObject;
import net.bitnine.agensgraph.deps.org.json.simple.parser.JSONParser;
import net.bitnine.agensgraph.deps.org.json.simple.parser.ParseException;

import java.util.Iterator;
import java.util.Map;

public class AgensJSONParse {

    private JSONParser jsonParser;
    private JSONObject jsonObject;
    private JSONObject properties;
    Map<String, Object> parsedProperties;
    Iterator<String> propertiesIterator;

    public AgensJSONParse(){
        jsonParser = new JSONParser();
    }
    public String adjustPropertyQuote(String requestbody){
        String propertyString="";
        String key;
        try {
            jsonObject = (JSONObject) jsonParser.parse(requestbody);
            properties = (JSONObject) jsonParser.parse(jsonObject.get("properties").toString());
            parsedProperties = properties;
            propertiesIterator = parsedProperties.keySet().iterator();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        while (propertiesIterator.hasNext()) {
                key = propertiesIterator.next();

                if (parsedProperties.get(key) instanceof Integer)
                    propertyString = propertyString + key + ":" + parsedProperties.get(key);
                else if (parsedProperties.get(key) instanceof Long)
                    propertyString = propertyString + key + ":" + parsedProperties.get(key);
                else if (parsedProperties.get(key) instanceof String)
                    propertyString = propertyString + key + ":"  + "\'" + parsedProperties.get(key) + "\'";

                if (propertiesIterator.hasNext())
                propertyString = propertyString + ",";
        }
        return propertyString;
    }

}
