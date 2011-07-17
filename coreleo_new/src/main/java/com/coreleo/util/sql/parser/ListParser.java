/**
 * 
 */
package com.coreleo.util.sql.parser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Leon Samaroo
 *
 */
public class ListParser implements ResultSetParser {
	private RowParser rowParser;
	
	
	public ListParser( RowParser rowParser ) {
		super();
		this.rowParser = rowParser;
	}


	/**
	 * @return A List containing the rows of the ResultSet, a empty list if the ResultSet is empty. 
	 */
	public Object parse(Connection con, ResultSet rs) throws SQLException{
			List list = new ArrayList(25);

			int rowNum = 0;
			while (rs.next()) {
				list.add(rowParser.parse(con, rs, rowNum++));
			}
			return list;
	}




}
