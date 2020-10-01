package com.io.webee.smart.utils;

import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Translator {

	private final static HashMap<String,String> dictionary = new HashMap<String,String>();
	
	public Translator() {
		dictionary.put("0", "Success");
		dictionary.put("3000", "Connection Error to firestore");
		dictionary.put("3001", "The device does not exist");
		dictionary.put("3002", "There is no registered devices");
		dictionary.put("3003", "Reserved");
		dictionary.put("3004", "Reserved");
	}
    
	public String PrintJsonResponse(String code) {
		
	    JSONObject response = new JSONObject();
	    response.put("code", code);
	    response.put("description", dictionary.get(code));
	    
	    return (String) response.toJSONString();
	}
			
	public static String PrintJsonResponse(String code, String key, String value) {
		
	    JSONObject response = new JSONObject();
	    response.put("code", code);
	    response.put("description", dictionary.get(code));
	    
	    JSONObject content = new JSONObject();
	    content.put(key, value);

	    response.put("data", content);    
	    return (String) response.toJSONString();
	}
}
