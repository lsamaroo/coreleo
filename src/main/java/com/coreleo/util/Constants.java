/**
 * 
 */
package com.coreleo.util;

/**
 * @author Leon Samaroo
 *
 */
public interface Constants {
	/* Time */
    public final static double ONE_DAY = 86400000;
    public final static double ONE_HOUR = 3600000;
    
    /** @deprecated - use ONE_HOUR instead */
	@Deprecated
	public final static long HOUR = 3600000;
	
	/* MIME Types */
	public final static String MIME_HTML = "text/html";
	public final static String MIME_EXCEL = "application/x-msexcel";

	
	/* Common characters */
	public final static String STAR = "*";
	public final static String PERCENT = "%";
	public final static String UNDERSCORE = "_";
	public final static String EQUAL = "=";
	public final static String AMPERSAND = "&";
	public final static String QUESTIONMARK = "?";
	public final static String POUND = "#";
	public final static String COMMA = ",";
	
	
	public final static String UTF8 = "UTF-8";
	public final static String CSV = "csv";
	public final static String HTML = "HTML";
	public final static String EXCEL = "EXCEL";

	
	public final static String EMPTY_STRING = "";

}
