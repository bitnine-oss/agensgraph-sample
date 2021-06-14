package net.bitnine.agensgraphsample.mybatis.api.util;

import net.bitnine.agensgraph.deps.org.json.simple.JSONObject;
import net.bitnine.agensgraph.deps.org.json.simple.parser.JSONParser;
import net.bitnine.agensgraph.deps.org.json.simple.parser.ParseException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AgensJSONParse {

    public static HashMap<String, Object> createVertex(String requestbody) throws ParseException{
        HashMap rtnMap = new HashMap<String, Object>();
        String propertyStr="";

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(requestbody);
        JSONObject properties = (JSONObject) jsonParser.parse(jsonObject.get("properties").toString());

        Map<String, Object> parsedProperties = properties;

        Iterator<String> propertiesIterator = parsedProperties.keySet().iterator();

        rtnMap.put("vLabel", jsonObject.get("vLabel").toString());

        while (propertiesIterator.hasNext()) {
            String key = propertiesIterator.next();

            if (parsedProperties.get(key) instanceof Long) {
                propertyStr += ( key + ":" + parsedProperties.get(key) );
            }
            if (parsedProperties.get(key) instanceof String) {
                propertyStr += ( key + ":" + "\'" + parsedProperties.get(key) + "\'" );
            }
            if (propertiesIterator.hasNext()) {
                propertyStr += ",";
            }
        }
        rtnMap.put("properties", propertyStr);

        return rtnMap;
    }

    public static HashMap<String, Object> updateVertex(String requestrapam, String requestbody) throws ParseException {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        String propertyStr = "";

        rtnMap.put("vertexid", requestrapam);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(requestbody);
        JSONObject properties = (JSONObject) jsonParser.parse(jsonObject.get("properties").toString());

        Map<String, Object> parsedProperties = properties;
        Iterator<String> propertiesIterator = parsedProperties.keySet().iterator();


        while (propertiesIterator.hasNext()) {
            String key = propertiesIterator.next();

            if (parsedProperties.get(key) instanceof Long) {
                propertyStr += ( key + ":" + parsedProperties.get(key) );
            }
            if (parsedProperties.get(key) instanceof String) {

                propertyStr += ( key + ":" + "\'" + parsedProperties.get(key) + "\'") ;
            }
            if (propertiesIterator.hasNext()) {
                propertyStr += ",";
            }
        }
        rtnMap.put("properties", propertyStr);

        return rtnMap;

    }


    public static HashMap<String, Object> createEdge(String requestbody) throws ParseException {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        String propertyStr = "";
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(requestbody);
        JSONObject properties = (JSONObject) jsonParser.parse(jsonObject.get("properties").toString());

        Map<String, Object> parsedProperties = properties;
        Iterator<String> propertiesIterator = parsedProperties.keySet().iterator();

        String cypherString ="";

        rtnMap.put("elabel", jsonObject.get("eLabel"));
        rtnMap.put("sourceid", jsonObject.get("source"));
        rtnMap.put("targetid", jsonObject.get("target"));

        while (propertiesIterator.hasNext()) {
            String key = propertiesIterator.next();

            if (parsedProperties.get(key) instanceof Long) {
                propertyStr += ( key + ":" + parsedProperties.get(key) );
            }
            if (parsedProperties.get(key) instanceof String) {
                propertyStr += ( key + ":" + "\'" + parsedProperties.get(key) + "\'" );
            }
            if (propertiesIterator.hasNext()) {
                propertyStr += ",";
            }
        }
            rtnMap.put("properties", propertyStr);

        return rtnMap;
    }

    public static HashMap<String,Object> updateEdge(String requestrapam, String requestbody) throws ParseException {
        HashMap rtnMap = new HashMap<String, Object>();
        String propertyStr="";

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(requestbody);
        JSONObject properties = (JSONObject) jsonParser.parse(jsonObject.get("properties").toString());

        Map<String, Object> parsedProperties = properties;
        Iterator<String> propertiesIterator = parsedProperties.keySet().iterator();

        rtnMap.put("edgeid", requestrapam);

        while (propertiesIterator.hasNext()) {
            String key = propertiesIterator.next();

            if (parsedProperties.get(key) instanceof Long) {
                propertyStr += ( key + ":" + parsedProperties.get(key) );
            }
            if (parsedProperties.get(key) instanceof String) {

                propertyStr += ( key + ":" + "\'" + parsedProperties.get(key) + "\'" );
            }
            if (propertiesIterator.hasNext()) {
                propertyStr += ",";
            }
        }
        rtnMap.put("properties", propertyStr);

        return rtnMap;
    }
}
