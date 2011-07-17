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
 *	Parses only the first row of a resultset using the given RowParser
 *
 */
public class FirstRowOnlyParser implements ResultSetParser {
	private RowParser rowParser;
	
	public FirstRowOnlyParser( RowParser rowParser ) {
		super();
		this.rowParser = rowParser;
	}


	public Object parse(Connection con, ResultSet rs) throws SQLException {
		if( rs.next() ) {
			return rowParser.parse(con, rs, 0);
		}
		
		return null;
	}

}
