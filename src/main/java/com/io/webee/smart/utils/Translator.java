package com.io.webee.smart.utils;

import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Translator {

	private final static HashMap<String,String> dictionary = new HashMap<String,String>();
	
	public Translator() {
		dictionary.put("0", "Success");
		dictionary.put("3000", "Connection Error to firestore");
		dictionary.put("3001", "The device does not exist");
		dictionary.put("3002", "There is no registered devices");
		dictionary.put("3003", "Invalid MAC Address");
		dictionary.put("3004", "Reserved");
	}
    
	public String PrintJsonResponse(HashMap<String, String> map) {
		
	    JSONObject response = new JSONObject();
	    response.put("code", "0");
	    response.put("description", dictionary.get("0"));
	    
	    JSONArray devices = new JSONArray();
	    JSONObject device = new JSONObject();
	    for(Entry<String, String> entry : map.entrySet()) {
	    	device.put("MacAddress", entry.getKey());
	    	device.put("Timestamp", entry.getValue());
	    	devices.add(device);
	    }
	    
	    response.put("devices",devices);
	    
	    return (String) response.toJSONString();
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
	
	public static boolean isValidMACAddress(String str) { 
        // Regex to check valid 
        // MAC address 
        String regex 
            = "^([0-9A-Fa-f]{2}[:-])"
              + "{5}([0-9A-Fa-f]{2})|"
              + "([0-9a-fA-F]{4}\\."
              + "[0-9a-fA-F]{4}\\."
              + "[0-9a-fA-F]{4})$"; 
  
        // Compile the ReGex 
        Pattern p = Pattern.compile(regex); 
  
        // If the string is empty 
        // return false 
        if (str == null) { 
            return false; 
        } 
  
        // Find match between given string 
        // and regular expression 
        // uSing Pattern.matcher() 
  
        Matcher m = p.matcher(str); 
  
        // Return if the string 
        // matched the ReGex 
        return m.matches(); 
    } 
}
