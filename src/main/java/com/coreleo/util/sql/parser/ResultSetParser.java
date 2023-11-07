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
 */
public interface ResultSetParser<T> {

	/**
	 *
	 * Implement this method to define how to parse the ResultSet.
	 *
	 */
	public T parse(Connection con, ResultSet rs) throws SQLException;

}
