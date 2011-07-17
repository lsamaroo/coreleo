/**
 * 
 */
package com.coreleo.util.sql.parser;

import java.sql.*;

/**
 * @author Leon Samaroo
 *
 */
public interface ResultSetParser {
	
	
	/**
	 *
	 * Implement this method to define how to parse the ResultSet.
	 *
	 */
	public Object parse( Connection con, ResultSet rs ) throws SQLException;

}
