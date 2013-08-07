package com.coreleo.util.sql.parser;

import java.sql.*;
import java.util.*;
import org.apache.commons.lang.WordUtils;
import com.coreleo.util.BeanUtil;

/**
 * 
 * By default will convert the underscore naming convention
 * to java camel case in order to map properties correctly to the bean. 
 * E.g. converts the column name "last_name" to "lastName" in order to find the method "setLastName".
 *  
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class RowAsBean implements RowParser {
	private boolean underscoreToCamelCase;
	private Class clazz;
	private Object bean;


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

	public Object parse(Connection con, ResultSet rs, int rowNum) throws SQLException {
        ResultSetMetaData metaData  = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
		Map row = new HashMap();
        for (int i = 1; i <= columnCount; i++) {
            row.put( underscoreToCamelCase ? underScoreToCamelCase(metaData.getColumnLabel(i)) : metaData.getColumnName(i), rs.getObject(i) );
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
