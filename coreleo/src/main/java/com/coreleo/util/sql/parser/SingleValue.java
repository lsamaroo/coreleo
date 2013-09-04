package com.coreleo.util.sql.parser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SingleValue implements RowParser {
	private String columnName;
	private int columnIndex;
	private boolean convertValueToString;
	
	public SingleValue(String columnNameToUseAsKey) {
		super();
		this.columnName = columnNameToUseAsKey;
		this.convertValueToString = false;
	}

	public SingleValue(int columnIndexToUseAsKey) {
		super();
		this.columnIndex = columnIndexToUseAsKey;
		this.convertValueToString = false;
	}


	public SingleValue(String columnName, boolean convertValueToString) {
		super();
		this.columnName = columnName;
		this.convertValueToString = convertValueToString;
	}
	
	
	public SingleValue(int columnIndex, boolean convertValueToString) {
		super();
		this.columnIndex = columnIndex;
		this.convertValueToString = convertValueToString;
	}
	

	public Object parse(Connection con, ResultSet rs, int rowNum) throws SQLException {
		if( columnName != null ){
			return convertValueToString ? rs.getString( columnName ) : rs.getObject( columnName );
		}
		else{
			return convertValueToString ? rs.getString( columnIndex ) : rs.getObject( columnIndex );				
		}
	}

}
