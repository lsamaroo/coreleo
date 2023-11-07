package com.coreleo.util.sql.parser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SingleStringValue implements RowParser<String> {
	private final String columnName;
	private final Integer columnIndex;

	public SingleStringValue(final String columnName) {
		super();
		this.columnName = columnName;
		this.columnIndex = null;
	}

	public SingleStringValue(final int columnIndex) {
		super();
		this.columnName = null;
		this.columnIndex = columnIndex;
	}

	@Override
	public String parse(final Connection con, final ResultSet rs, final int rowNum) throws SQLException {
		return columnName != null ? rs.getString(columnName) : rs.getString(columnIndex);
	}

}
