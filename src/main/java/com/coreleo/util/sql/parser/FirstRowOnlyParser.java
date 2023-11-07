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
 *         Parses only the first row of a result set using the provided row
 *         parser
 *
 */
public class FirstRowOnlyParser<T> implements ResultSetParser<T> {
	private final RowParser<T> rowParser;

	public FirstRowOnlyParser(final RowParser<T> rowParser) {
		super();
		this.rowParser = rowParser;
	}

	@Override
	public T parse(final Connection con, final ResultSet rs) throws SQLException {
		if (rs.next()) {
			return rowParser.parse(con, rs, 0);
		}

		return null;
	}

}
