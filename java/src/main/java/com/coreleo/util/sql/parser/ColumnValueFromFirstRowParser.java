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
 *         Parses only the first row of a result set using the given RowParser
 *
 */
public class ColumnValueFromFirstRowParser implements ResultSetParser<Object> {
	private String columnName;
	private int columnIndex;

	public ColumnValueFromFirstRowParser(final String columnName) {
		super();
		this.columnName = columnName;
	}

	public ColumnValueFromFirstRowParser(final int columnIndex) {
		super();
		this.columnIndex = columnIndex;
	}

	@Override
	public Object parse(final Connection con, final ResultSet rs) throws SQLException {
		if (rs.next()) {
			if (columnName != null) {
				return rs.getObject(columnName);
			}
			else {
				return rs.getObject(columnIndex);
			}
		}
		return null;
	}

}
