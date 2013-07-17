package com.coreleo.util.sql.parser;

import java.sql.*;
import java.util.*;

import com.coreleo.util.StringUtil;

@SuppressWarnings({"rawtypes", "unchecked"})
public class RowAsMap implements RowParser {
	private boolean useLowerCaseColumnNames;


	public RowAsMap() {
		super();
		useLowerCaseColumnNames = false;
	}
	
	/**
	 * 
	 * @param useLowerCaseColumnNames - default is false
	 */
	public RowAsMap(boolean useLowerCaseColumnNames) {
		super();
		this.useLowerCaseColumnNames = useLowerCaseColumnNames;
	} 

	public Object parse(Connection con, ResultSet rs, int rowNum) throws SQLException {
        ResultSetMetaData metaData  = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
		Map row = new HashMap();
        for (int i = 1; i <= columnCount; i++) {
            row.put( useLowerCaseColumnNames ? StringUtil.toLowerCase(metaData.getColumnName(i)) : metaData.getColumnName(i), rs.getObject(i) );
        }      
        return row;
	}

}
