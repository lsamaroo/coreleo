package com.coreleo.util;

public class HtmlUtil {

	public static String toHtmlEntityString(Object x) {
		if( x == null ){
			return null;
		}
		
		return toHtmlEntityString( StringUtil.toString(x) );
	}
	
	
	public static String toHtmlEntityString(String x) {
		if( StringUtil.isEmpty(x) ){
			return x;
		}
		
	    StringBuffer sb = new StringBuffer(x.length());
	    // true if last char was blank
	    boolean lastWasBlankChar = false;
	    int len = x.length();
	    char c;

	    for (int i = 0; i < len; i++)
	        {
	        c = x.charAt(i);
	        if (c == ' ') {
	            // blank gets extra work,
	            // this solves the problem you get if you replace all
	            // blanks with &nbsp;, if you do that you loss 
	            // word breaking
	            if (lastWasBlankChar) {
	                lastWasBlankChar = false;
	                sb.append("&nbsp;");
	                }
	            else {
	                lastWasBlankChar = true;
	                sb.append(' ');
	                }
	            }
	        else {
	            lastWasBlankChar = false;
	            //
	            // HTML Special Chars
	            if (c == '"')
	                sb.append("&quot;");
	            else if (c == '&')
	                sb.append("&amp;");
	            else if (c == '<')
	                sb.append("&lt;");
	            else if (c == '>')
	                sb.append("&gt;");
	            else if (c == '\n')
	                // Handle Newline
	                sb.append("&lt;br/&gt;");
	            else {
	                int ci = 0xffff & c;
	                if (ci < 160 )
	                    // nothing special only 7 Bit
	                    sb.append(c);
	                else {
	                    // Not 7 Bit use the unicode system
	                    sb.append("&#");
	                    sb.append(new Integer(ci).toString());
	                    sb.append(';');
	                    }
	                }
	            }
	        }
	    return sb.toString();
	}


}
