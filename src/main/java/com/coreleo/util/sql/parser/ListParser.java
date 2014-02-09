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
public class ListParser implements ResultSetParser
{
	private final RowParser rowParser;

	public ListParser(RowParser rowParser)
	{
		super();
		this.rowParser = rowParser;
	}

	/**
	 * @return A List containing the rows of the ResultSet, a empty list if the ResultSet is empty.
	 */
	@Override
	public Object parse(Connection con, ResultSet rs) throws SQLException
	{
		final List<Object> list = new ArrayList<Object>(25);

		int rowNum = 0;
		while (rs.next())
		{
			list.add(rowParser.parse(con, rs, rowNum++));
		}
		return list;
	}

}
