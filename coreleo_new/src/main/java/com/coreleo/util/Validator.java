package com.coreleo.util;

import java.util.*;

import com.coreleo.util.date.DateUtil;

/**
 * @author Leon Samaroo <br/> This is a generic validator class - specifically
 *         DATA validation, not business validation.
 *         
 *         @deprecated - Don't use this class
 *         
 * 
 */
public final class Validator {

	// private constructor because this class never needs to be instantiated
	private Validator() {
		super();
	}


	/**
     * 
     * @deprecated
     * 
	 * Checks if this object is null.
	 * 
	 * @param obj -
	 *            the Object to check.
	 * @return true if object is null, false otherwise.
	 */
	public static final boolean isNull(Object obj) {
		return (obj == null);
	}


	/**
     * @deprecated
     * 
	 * The logical opposite of isNull, this returns true when the object is not
	 * null.
	 * 
	 * @param obj -
	 *            the Object to check.
	 * @return true if the object is not null, false otherwise.
	 */
	public static final boolean isNotNull(Object obj) {
		return !isNull(obj);
	}


	/**
	 * @deprecated - use StringUtil.isEmpty
     * 
	 * Returns true if the String is either null or contains only white spaces.
	 * 
	 * @param string -
	 *            the String to check.
	 * @return true if null or empty.
	 */
	public static final boolean isNullOrEmpty(String string) {
		return (isNull(string) || StringUtil.isEmpty(string));
	}
	
	/**
     * 
     * @deprecated - use CollectionUtil.isEmpty
	 */
	public static final boolean isNullOrEmpty( Collection collection ) {
		return (isNull(collection) || collection.isEmpty() );
	}
	
	/**
     * @deprecated - use MapUtil.isEmpty
	 */
	public static final boolean isNullOrEmpty( Map map ) {
		return (isNull(map) || map.isEmpty() );
	}
	
	/**
     * @deprecated
     * 
	 */
	public static final boolean isNullOrEmpty(Object x) {
		if( isNull(x) ) {
			return true;
		}
		else if( x instanceof String ) {
			return StringUtil.isEmpty(x);
		}
		else if( x instanceof Collection ) {
			return ((Collection) x).isEmpty();
		}
		else if( x instanceof Map ) {
			return ((Map) x).isEmpty();
		}
		else {
			return false;
		}
	}

	/**
	 * 
	 * @deprecated - use NumberUtil.isLong instead
	 */
	public static final boolean isLong(String string) {
		return NumberUtil.isLong(string);
	}
	
	
	/**
	 * 
	 * @deprecated - use NumberUtil.isPositiveInteger instead
	 */
	public static final boolean isPositiveInteger( String string ) {
		return NumberUtil.isPositiveInteger(string);
	}
	
	
	/**
	 * 
	 * @deprecated - use NumberUtil.isNegativeInteger instead
	 */
	public static final boolean isNegativeInteger( String string ) {
		return NumberUtil.isNegativeInteger(string);	
	}
	
	
	/**
	 * 
	 * @deprecated - use NumberUtil.isZero instead
	 */
	public static final boolean isZero( String string ) {
		return NumberUtil.isZero(string);		
	}	


	/**
	 * 
	 * @deprecated - use NumberUtil.isInteger instead
	 */
	public static final boolean isInteger(String string) {
		return NumberUtil.isInteger(string);	
	}

	
	/**
	 * 
	 * @deprecated - use NumberUtil.isDouble instead
	 */
	public static final boolean isDouble(String string) {
		return NumberUtil.isDouble(string);	
	}


	/**
	 * 
	 * @deprecated - use NumberUtil.isBoolean instead
	 */
	public static final boolean isBoolean(String string) {
		return BooleanUtil.isTrueFalseString(string);	
	}

	


	
	/**
	 *
	 * @deprecated - use DateUtil.isValidDate instead
	 *
	 */
	public static final boolean isValidDate(String month, String day, String year)
	{
		return DateUtil.isValidDate(month, day, year);
	}

}
