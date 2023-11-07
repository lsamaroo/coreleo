package com.coreleo.util.sql.parser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SingleValue implements RowParser<Object> {
	private final String columnName;
	private final Integer columnIndex;

	public SingleValue(final String columnName) {
		super();
		this.columnName = columnName;
		this.columnIndex = null;
	}

	public SingleValue(final int columnIndex) {
		super();
		this.columnName = null;
		this.columnIndex = columnIndex;
	}

	@Override
	public Object parse(final Connection con, final ResultSet rs, final int rowNum) throws SQLException {
		return columnName != null ? rs.getObject(columnName) : rs.getObject(columnIndex);
	}

}
