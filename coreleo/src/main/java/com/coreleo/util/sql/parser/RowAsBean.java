package com.coreleo.util.sql.parser;

import java.sql.*;
import java.util.*;

import org.apache.commons.lang.WordUtils;

import com.coreleo.util.BeanUtil;
import com.coreleo.util.StringUtil;
import com.coreleo.util.sql.DBUtil;

/**
 * 
 * By default will convert the underscore naming convention
 * to java camel case in order to map properties correctly to the bean. 
 * E.g. converts the column name "last_name" to "lastName" in order to find the method "setLastName".
 *  
 */
@SuppressWarnings({"rawtypes", "unchecked", "unused"})
public class RowAsBean implements RowParser {
	private boolean underscoreToCamelCase;
	private Class clazz;
	private Object bean;
	private TimeZone timeZone;


	/**
	 * Will create new intances of this clazz and poplulate with row values.
	 */
	public RowAsBean( Class clazz ) {
		super();
		underscoreToCamelCase = true;
		this.clazz = clazz;
	}
	
	
	/**
	 * Will populate the passed in bean object with the values in the row.  Be careful as a 
	 * result set with multiple rows will cause the bean instance to have its properties overwritten with the values of the last row.
	 */
	public RowAsBean( Object bean ) {
		super();
		underscoreToCamelCase = true;
		this.bean = bean;
	}
	
	/**
	 * 
	 * @param underScoreToCamelCase - default is true
	 * E.g. converts the column name last_name to lastName.
	 *  
	 */
	public RowAsBean(Class clazz, boolean underscoreToCamelCase) {
		this(clazz);
		this.underscoreToCamelCase = underscoreToCamelCase;
	} 
	
	
	/**
	 * Will create new intances of this clazz and poplulate with row values.
	 * @param timeZone - the timezone to use when getting timestamps from the database.
	 */
	public RowAsBean( Class clazz, TimeZone timeZone ) {
		super();
		underscoreToCamelCase = true;
		this.clazz = clazz;
		this.timeZone = timeZone;
	}
	
	
	/**
	 * Will populate the passed in bean object with the values in the row.  Be careful as a 
	 * result set with multiple rows will cause the bean instance to have its properties overwritten with the values of the last row.
	 * @param timeZone - the timezone to use when getting timestamps from the database. 
	 */
	public RowAsBean( Object bean, TimeZone timeZone ) {
		super();
		underscoreToCamelCase = true;
		this.bean = bean;
		this.timeZone = timeZone;
	}
	
	/**
	 * 
	 * @param underScoreToCamelCase - default is true
	 * E.g. converts the column name last_name to lastName.
	 * 
	 * @param timeZone - the timezone to use when getting timestamps from the database. 
	 */
	public RowAsBean(Class clazz, boolean underscoreToCamelCase, TimeZone timeZone) {
		this(clazz);
		this.underscoreToCamelCase = underscoreToCamelCase;
		this.timeZone = timeZone;
	} 

	public Object parse(Connection con, ResultSet rs, int rowNum) throws SQLException {
        ResultSetMetaData metaData  = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
		Map row = new HashMap();
        for (int i = 1; i <= columnCount; i++) {
            row.put( underscoreToCamelCase ? underScoreToCamelCase(metaData.getColumnLabel(i)) : metaData.getColumnName(i), DBUtil.getObject(metaData, rs, i, timeZone) );
        }      
        
        if( clazz != null ){
        	return BeanUtil.populateBean(clazz, row);
        }
        else {
        	return BeanUtil.populateBean(bean, row);
        }
        
	}
	

	
	

	private String underScoreToCamelCase( String s ){
		return WordUtils.capitalizeFully(s, new char[]{'_'}).replaceAll("_", "");
	}

}
