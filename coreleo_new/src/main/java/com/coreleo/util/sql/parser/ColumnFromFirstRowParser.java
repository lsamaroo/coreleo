/**
 * 
 */
package com.coreleo.util.sql.parser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @author Leon Samaroo
 *
 *	Parses only the first row of a resultset using the given RowParser
 *
 */
public class ColumnFromFirstRowParser implements ResultSetParser {
	private String columnName;
	private int columnIndex;
	
	
	public ColumnFromFirstRowParser(String columnName ) {
		super();
		this.columnName = columnName;
	}
	
	public ColumnFromFirstRowParser(int columnIndex ) {
		super();
		this.columnIndex = columnIndex;
	}


	public Object parse(Connection con, ResultSet rs) throws SQLException {
			Object value = null;

			if (rs.next()) {
				if( columnName != null ){
					value = rs.getObject(columnName);
				}
				else{
					value = rs.getObject(columnIndex);
				}
			}
			return value;
	}

}
