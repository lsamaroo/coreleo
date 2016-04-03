/**
 *
 */
package com.coreleo.util.sql.parser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

/**
 *
 *
 * @author Leon Samaroo
 *
 */
public class LinkedHashMapParser implements ResultSetParser<LinkedHashMap<?, ?>> {
	private final RowParser<?> rowParser;
	private final RowParser<?> keyParser;

	public LinkedHashMapParser(final RowParser<?> rowParser, final RowParser<?> keyParser) {
		super();
		this.rowParser = rowParser;
		this.keyParser = keyParser;
	}

	public LinkedHashMapParser(final RowParser<?> rowParser, final String nameOfColumnToUseAsKey) {
		super();
		this.rowParser = rowParser;
		this.keyParser = new SingleValue(nameOfColumnToUseAsKey);
	}

	public LinkedHashMapParser(final RowParser<?> rowParser, final int indexOfColumnToUseAsKey) {
		super();
		this.rowParser = rowParser;
		this.keyParser = new SingleValue(indexOfColumnToUseAsKey);
	}

	public LinkedHashMapParser(final RowParser<?> rowParser, final String nameOfColumnToUseAsKey,
	        final boolean keyAsString) {
		super();
		this.rowParser = rowParser;
		this.keyParser = keyAsString ? new SingleStringValue(nameOfColumnToUseAsKey)
		        : new SingleValue(nameOfColumnToUseAsKey);
	}

	public LinkedHashMapParser(final RowParser<?> rowParser, final int indexOfColumnToUseAsKey,
	        final boolean keyAsString) {
		super();
		this.rowParser = rowParser;
		this.keyParser = keyAsString ? new SingleStringValue(indexOfColumnToUseAsKey)
		        : new SingleValue(indexOfColumnToUseAsKey);
	}

	/**
	 * The values of the map are the rows that have been processed via
	 * RowProcesser.processRow(rs). The keys are retrieved from the ResultSet
	 * via ResultSet.getObject(columnNameToUseAsKey). A LinkedHashMap is used in
	 * order to preserve the iteration order of this Map, which is normally the
	 * order in which the keys are inserted into the Map.
	 *
	 */
	@Override
	public LinkedHashMap<?, ?> parse(final Connection con, final ResultSet rs) throws SQLException {
		final LinkedHashMap<Object, Object> map = new LinkedHashMap<Object, Object>();
		int rowNum = 0;

		while (rs.next()) {
			final Object value = rowParser.parse(con, rs, rowNum);
			final Object key = keyParser.parse(con, rs, rowNum);
			map.put(key, value);
			rowNum++;
		}
		return map;
	}

}
