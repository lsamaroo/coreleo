/**
 * 
 */
package com.coreleo.util;

import java.net.URLDecoder;
import java.util.*;

/**
 * @author Leon Samaroo
 * 
 */
public final class MapUtil {

    private MapUtil() {
        super();
    }


    /**
     * Sorts the map according to the key's natural ordering.
     */
    public static final Map sort(Map map) {
        return new TreeMap(map);
    }
    
    
    public static final void clear( Map map ){
    	if( map != null ){
    		map.clear();
    	}
    }

    
    public static final boolean isEmpty(Object x) {
        if (x == null) {
            return true;
        }

        if (x instanceof Map) {
            return ((Map) x).size() <= 0;
        }

        return true;
    }


    public static final boolean isNotEmpty(Object x) {
        return !isEmpty(x);
    }


    public static final boolean containsKey(Map map, Object key) {
        if (map != null) {
            return map.containsKey(key);
        }

        return false;
    }


    public static final boolean containsValue(Map map, Object value) {
        if (map != null) {
            return map.containsValue(value);
        }

        return false;
    }


    /**
     * 
     * @return - the value as an Object[], or null if the value
     * does not exist.
     * 
     */
    public static final Object[] getObjectArray(Map map, Object key) {
        Object obj = map.get(key);

        if (obj instanceof String) {
            return new String[] { (String) obj };
        }
        if (obj instanceof String[]) {
            return (String[]) obj;
        }
        else if (obj instanceof Object[]) {
            return (Object[]) obj;
        }
        else if (obj instanceof Collection && obj != null) {
            return ((Collection) obj).toArray();
        }
        else if (obj != null) {
            return new Object[] { obj };
        }
        else {
            return null;
        }
    }


    /**
     * 
     * An object[] containing your parameters or null if the parameter does not
     * exist.
     * 
     * @deprecated - use get ObjectArray instead.
     * 
     */
    public static final Object[] getParameters(String key, Map map) {
        return getObjectArray(map, key);
    }


    /**
     * 
     * @param map
     * @param key -
     *            the key to use for the map lookup
     * @return
     */
    public static final Object getFirstObject(Map map, Object key) {
        if (map == null || key == null) {
            return null;
        }

        Object obj = map.get(key);

        if (obj instanceof Object[]) {
            Object[] paramArray = (Object[]) obj;
            if (paramArray != null && paramArray.length > 0) {
                return paramArray[0] == null ? null : paramArray[0];
            }
        }
        else if (obj instanceof List) {
            List list = (List) obj;
            if (list != null && list.size() > 0) {
                return list.get(0) == null ? null : list.get(0);
            }
        }
        else {
            return (obj == null) ? null : obj;
        }

        return null;
    }


    /**
     * 
     * @deprecated - use getObject instead
     * @param key
     *            the name of the parameter to find.
     * @param a
     *            map
     * 
     * @return the paramater or null if the parameter cannot be found.
     */
    public static final Object getParameter(String key, Map map) {
        return getFirstObject(map, key);
    }


    public static final String getString(Map map, Object key) {
        return getString(map, key, null);
    }


    public static final String getString(Map map, Object key, String defaultValue) {
        Object value = getFirstObject(map, key);
        if (value != null) {
            return value.toString();
        }
        else {
            return defaultValue;
        }
    }


    public static final String[] getStringArray(Map map, Object key) {
        Object[] values = getObjectArray(map, key);

        if (values instanceof String[]) {
            return (String[]) values;
        }
        else {
            return ArrayUtil.toStringArray(values);
        }
    }


    public static final int[] getIntegerArray(Map map, Object key) {
        Object[] values = getObjectArray(map, key);

        if (values instanceof String[]) {
            return ArrayUtil.toIntegerArray(values);
        }
        else if (values instanceof Integer[]) {
            return ArrayUtil.toIntegerArray(values);
        }
        else {
            return ArrayUtil.toIntegerArray(values);
        }
    }


    public static final int[] getIntegerArray(Map map, Object key, int[] defaultValues) {
        try {
            return getIntegerArray(map, key);
        }
        catch (Exception e) {
            return defaultValues;
        }
    }


    public static final Integer[] getIntegerObjectArray(Map map, Object key) {
        Object[] values = getObjectArray(map, key);

        if (values instanceof String[]) {
            return ArrayUtil.toIntegerObjectArray(values);
        }
        else if (values instanceof Integer[]) {
            return (Integer[]) values;
        }
        else {
            return ArrayUtil.toIntegerObjectArray(values);
        }
    }


    public static final Integer[] getIntegerObjectArray(Map map, Object key, Integer[] defaultValues) {
        try {
            return getIntegerObjectArray(map, key);
        }
        catch (Exception e) {
            return defaultValues;
        }
    }


    public static final String getString(Map map, Object key, Object defaultValue) {
        Object value = getFirstObject(map, key);
        if (Validator.isNotNull(value)) {
            return value.toString();
        }
        else {
            return String.valueOf(defaultValue);
        }
    }


    public static final int getInteger(Map map, Object key) {
        Object value = getFirstObject(map, key);
        return NumberUtil.toInteger(value);
    }


    public static final int getInteger(Map map, Object key, int defaultValue) {
        Object value = getFirstObject(map, key);
        return NumberUtil.toInteger(value, defaultValue);
    }


    public static final int getInteger(Map map, Object key, Integer defaultValue) {
        Object value = getFirstObject(map, key);
        return NumberUtil.toInteger(value, defaultValue);
    }


    public static final Integer getIntegerObject(Map map, Object key) {
        return getIntegerObject(map, key, null);
    }


    public static final Integer getIntegerObject(Map map, Object key, Integer defaultValue) {
        Object value = getFirstObject(map, key);
        return NumberUtil.toIntegerObject(value, defaultValue);
    }


    public static final Integer getIntegerObject(Map map, Object key, int defaultValue) {
        Object value = getFirstObject(map, key);
        return NumberUtil.toIntegerObject(value, defaultValue);
    }


    public static final boolean getBoolean(Map map, Object key, boolean defaultValue) {
        Object value = getFirstObject(map, key);
        return BooleanUtil.toBoolean(value, defaultValue);
    }


    public static final Boolean getBooleanObject(Map map, Object key, boolean defaultValue) {
        Object value = getFirstObject(map, key);
        return BooleanUtil.toBooleanObject(value, defaultValue);
    }


    public static final Boolean getBooleanObject(Map map, Object key, Boolean defaultValue) {
        Object value = getFirstObject(map, key);
        return BooleanUtil.toBooleanObject(value, defaultValue);
    }


    public static final double getDouble(Map map, Object key, double defaultValue) {
        Object value = getFirstObject(map, key);
        return NumberUtil.toDouble(value, defaultValue);
    }


    public static final Double getDoubleObject(Map map, Object key, Double defaultValue) {
        Object value = getFirstObject(map, key);
        return NumberUtil.toDoubleObject(value, defaultValue);
    }


    public static final Double getDoubleObject(Map map, Object key, double defaultValue) {
        Object value = getFirstObject(map, key);
        return NumberUtil.toDoubleObject(value, defaultValue);
    }
    
    
    public static final long getLong(Map map, Object key, long defaultValue) {
        Object value = getFirstObject(map, key);
        return NumberUtil.toLong(value, defaultValue);
    }


    public static final Long getLongObject(Map map, Object key, Long defaultValue) {
        Object value = getFirstObject(map, key);
        return NumberUtil.toLongObject(value, defaultValue);
    }


    public static final Long getLongObject(Map map, Object key, long defaultValue) {
        Object value = getFirstObject(map, key);
        return NumberUtil.toLongObject(value, defaultValue);
    }



    public static final Map queryStringtoMap(Object obj) {
        String queryString = StringUtil.toString(obj, "");

        try {
            queryString = URLDecoder.decode(queryString, Constants.UTF8);
        }
        catch (Exception e) {
            // ignore
        }

        HashMap map = new HashMap();

        if (queryString == null) {
            return map;
        }

        Enumeration enumerator = new StringTokenizer(queryString, "&");
        while (enumerator.hasMoreElements()) {
            String[] array = StringUtil.split(enumerator.nextElement(), "=");
            String key = StringUtil.toString(ArrayUtil.get(array, 0));
            String value = StringUtil.toString(ArrayUtil.get(array, 1));

            Object previousValue = map.get(key);
            if (previousValue == null) {
                map.put(key, value);
            }
            else {
                if (previousValue instanceof Object[]) {
                    map.put(key, ArrayUtil.add((String[]) previousValue, value));
                }
                else {
                    map.put(key, new String[] { StringUtil.toString(previousValue), value });
                }
            }
        }

        return map;
    }


    /**
     * 
     * @return a Map containing the delimited values as the keys of the map.
     */
    public static final Map commaDelimitedStringToMap(String x) {
        return delimitedStringToMap(x, ",");
    }


    /**
     * 
     * @return a Map containing the delimited values as the keys of the map.
     */
    public static final Map delimitedStringToMap(String x, String delimiter) {
        HashMap map = new HashMap();

        if (x == null || delimiter == null) {
            return map;
        }

        Enumeration enumerator = new StringTokenizer(x, delimiter);
        while (enumerator.hasMoreElements()) {
            map.put(enumerator.nextElement(), null);
        }

        return map;
    }

}
