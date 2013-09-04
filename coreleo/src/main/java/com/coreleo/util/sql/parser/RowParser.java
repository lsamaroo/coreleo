/**
 * 
 */
package com.coreleo.util.sql.parser;

import java.sql.*;

/**
 * @author Leon Samaroo
 *
 *
 */
public interface RowParser{
	

	/**
	 * 
	 * Implement this method to define how to process a row of the ResultSet.
	 * 
	 * @return the processed row.
	 */
	public Object parse( Connection con, ResultSet rs, int rowNum ) throws SQLException;

}
