/**
 * 
 */
package com.coreleo.util.sql.parser;

import java.sql.*;
import java.util.*;

/**
 * 
 * 
 * @author Leon Samaroo
 * 
 */
public class LinkedHashMapParser implements ResultSetParser {
	private RowParser rowParser;
	private RowParser keyParser;


	
	public LinkedHashMapParser(RowParser rowParser, RowParser keyParser) {
		super();
		this.rowParser = rowParser;
		this.keyParser = keyParser;
	}


	public LinkedHashMapParser(RowParser rowParser, String nameOfColumnToUseAsKey) {
		super();
		this.rowParser = rowParser;
		this.keyParser = new SingleValue( nameOfColumnToUseAsKey );
	}


	public LinkedHashMapParser(RowParser rowParser, int indexOfColumnToUseAsKey) {
		super();
		this.rowParser = rowParser;
		this.keyParser = new SingleValue( indexOfColumnToUseAsKey );
	}


	public LinkedHashMapParser(RowParser rowParser, String nameOfColumnToUseAsKey, boolean keyAsString) {
		super();
		this.rowParser = rowParser;
		this.keyParser = new SingleValue( nameOfColumnToUseAsKey, keyAsString );
	}


	public LinkedHashMapParser(RowParser rowParser, int indexOfColumnToUseAsKey, boolean keyAsString) {
		super();
		this.rowParser = rowParser;
		this.keyParser = new SingleValue( indexOfColumnToUseAsKey, keyAsString );
	}

	
	/**
	 * The values of the map are the rows that have been processed via
	 * RowProcesser.processRow(rs). The keys are retrieved from the ResultSet
	 * via ResultSet.getObject(columnNameToUseAsKey). A LinkedHashMap is used in
	 * order to preserve the iteration order of this Map, which is normally the
	 * order in which the keys are inserted into the Map.
	 * 
	 */
	public Object parse(Connection con, ResultSet rs) throws SQLException {
		Map map = new LinkedHashMap();
        int rowNum = 0;
        
		while (rs.next()) {
            Object value = rowParser.parse(con, rs, rowNum );
			Object key = keyParser.parse(con, rs, rowNum);
			map.put( key, value );
            rowNum++;
		}

		return map;
	}


}
