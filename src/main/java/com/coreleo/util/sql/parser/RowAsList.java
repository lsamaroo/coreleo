/**
 * 
 */
package com.coreleo.util.sql.parser;

import java.util.*;
import java.sql.*;


/**
 * @author Leon Samaroo
 *
 */
public class RowAsList implements RowParser {


	public RowAsList() {
		super();
	}
	
	
	/**
	 * @return - an List containing the values of the row in the ResultsSet.  Values are filled in via the getObject(x) method. 
	 */
	public Object parse(Connection con, ResultSet rs, int rowNum) throws SQLException {
		ResultSetMetaData meta = rs.getMetaData();
        int cols = meta.getColumnCount();
        List<Object> list = new ArrayList<Object>(cols);

        for (int i = 0; i < cols; i++) {
            list.add( rs.getObject(i + 1) );
        }

        return list;
	}
	


}
