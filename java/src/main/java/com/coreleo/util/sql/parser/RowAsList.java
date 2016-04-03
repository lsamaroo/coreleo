/**
 *
 */
package com.coreleo.util.sql.parser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Leon Samaroo
 *
 */
public class RowAsList implements RowParser<List<Object>> {

	public RowAsList() {
		super();
	}

	/**
	 * @return - an List containing the values of the row in the ResultsSet.
	 *         Values are filled in via the getObject(x) method.
	 */
	@Override
	public List<Object> parse(final Connection con, final ResultSet rs, final int rowNum) throws SQLException {
		final ResultSetMetaData meta = rs.getMetaData();
		final int cols = meta.getColumnCount();
		final List<Object> list = new ArrayList<Object>(cols);

		for (int i = 0; i < cols; i++) {
			list.add(rs.getObject(i + 1));
		}

		return list;
	}

}
