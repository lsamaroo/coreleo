/**
 * 
 */
package com.coreleo.util.sql.parser;

import java.sql.*;


/**
 * 
 *  
 *
 */
public class RowAsArray implements RowParser {


	public RowAsArray() {
		super();
	}


	/**
	 * @return - an array containing the values of the row in the ResultsSet.  Values are filled in via the getObject(x) method. 
	 */
	public Object parse(Connection con, ResultSet rs, int rowNum) throws SQLException {
		ResultSetMetaData meta = rs.getMetaData();
        int cols = meta.getColumnCount();
        Object[] result = new Object[cols];

        for (int i = 0; i < cols; i++) {
            result[i] = rs.getObject(i + 1);
        }

        return result;
	}

}
