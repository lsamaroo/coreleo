/**
 *
 */
package com.coreleo.util.sql.parser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.coreleo.util.sql.DBUtil;

/**
 *
 *
 *
 */
public class RowAsArray implements RowParser<Object[]> {

	public RowAsArray() {
		super();
	}

	/**
	 * @return - an array containing the values of the row in the ResultsSet.
	 *         Values are filled in via the getObject(x) method.
	 */
	@Override
	public Object[] parse(final Connection con, final ResultSet rs, final int rowNum) throws SQLException {
		final ResultSetMetaData meta = rs.getMetaData();
		final int cols = meta.getColumnCount();
		final Object[] result = new Object[cols];

		for (int i = 0; i < cols; i++) {
			result[i] = DBUtil.getObject(meta, rs, i + 1, null);
		}

		return result;
	}

}
