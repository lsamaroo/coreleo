package com.coreleo.util;

import java.util.*;
import org.json.*;

import com.coreleo.SimpleException;

public class JsonUtil {

	private JsonUtil() {
		super();
	}
	
	
    public static final JSONObject put(JSONObject jsonObj, String key, Object x) {
        if (jsonObj == null) {
            return null;
        }
        try {
            return jsonObj.put(key, x);
        }
        catch (JSONException jsone) {
            return jsonObj;
        }
    }
    
    
    public static final JSONObject put(JSONObject jsonObj, String key, char x) {
        if (jsonObj == null) {
            return null;
        }
        try {
            return jsonObj.put(key, Character.valueOf(x) );
        }
        catch (JSONException jsone) {
            return jsonObj;
        }
    }
    
    
    public static final JSONObject put(JSONObject jsonObj, String key, int x) {
        if (jsonObj == null) {
            return null;
        }
        try {
            return jsonObj.put(key,NumberUtil.toIntegerObject(x));
        }
        catch (JSONException jsone) {
            return jsonObj;
        }
    }
    
    
    public static final JSONObject put(JSONObject jsonObj, String key, double x) {
        if (jsonObj == null) {
            return null;
        }
        try {
            return jsonObj.put(key, new Double(x) );
        }
        catch (JSONException jsone) {
            return jsonObj;
        }
    }
    
    
    
    public static final JSONObject put(JSONObject jsonObj, String key, float x) {
        if (jsonObj == null) {
            return null;
        }
        try {
            return jsonObj.put(key, new Float(x) );
        }
        catch (JSONException jsone) {
            return jsonObj;
        }
    }
    
    
    public static final JSONObject put(JSONObject jsonObj, String key, long x) {
        if (jsonObj == null) {
            return null;
        }
        try {
            return jsonObj.put(key, NumberUtil.toLongObject(x) );
        }
        catch (JSONException jsone) {
            return jsonObj;
        }
    }
    
    
    
    public static final JSONObject put(JSONObject jsonObj, String key, boolean x) {
        if (jsonObj == null) {
            return null;
        }
        try {
            return jsonObj.put(key, Boolean.valueOf(x) );
        }
        catch (JSONException jsone) {
            return jsonObj;
        }
    }
    
    
    
    public static final JSONArray toJsonArray( Collection x, boolean returnEmptyArrayOnNull ){
    	if( x == null ){
    		return returnEmptyArrayOnNull ? new JSONArray() : null;
    	}
    	
    	return new JSONArray(x);
    }
    
    
    public static final JSONArray toJsonArray( Collection x ){
    	return toJsonArray( x, false );
    }

    
	public static final Map toMap( String jsonString ) {
		if( jsonString == null ){
			return null;
		}
		
		try {
			toMap( new JSONObject( jsonString ) );
		}
		catch( JSONException jsone ) {
			return null;
		}
		
		return new HashMap();
	}
	
	public static final Map toMap( JSONObject jsonObject ) {
		if( jsonObject == null ){
			return null;
		}		
		
		Map map = new LinkedHashMap();
		
		JSONArray names = jsonObject.names();
		for( int i=0; i < names.length(); i++ ) {
			try {
				String key = names.getString(i);
				map.put( key, jsonObject.get(key) );
			}
			catch( JSONException jsone ) { 
				throw new SimpleException(jsone);
            }
		}
		
		return map;
	}
}
