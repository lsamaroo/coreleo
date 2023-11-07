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
 * @param T
 *            the type of object returned on parsing the row
 *
 */
public interface RowParser<T extends Object> {

	/**
	 *
	 * Implement this method to define how to process a row of the ResultSet.
	 *
	 * @return the processed row.
	 */
	public T parse(Connection con, ResultSet rs, int rowNum) throws SQLException;

}
