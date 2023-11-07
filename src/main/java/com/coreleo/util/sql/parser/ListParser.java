/**
 *
 */
package com.coreleo.util.sql.parser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Leon Samaroo
 *
 */
public class ListParser<T> implements ResultSetParser<List<T>> {
	private final RowParser<T> rowParser;

	public ListParser(final RowParser<T> rowParser) {
		super();
		this.rowParser = rowParser;
	}

	/**
	 * @return A List containing the rows of the ResultSet, a empty list if the
	 *         ResultSet is empty.
	 */
	@Override
	public List<T> parse(final Connection con, final ResultSet rs) throws SQLException {
		final List<T> list = new ArrayList<>(25);

		int rowNum = 0;
		while (rs.next()) {
			list.add(rowParser.parse(con, rs, rowNum++));
		}
		return list;
	}

}
