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
public class LinkedHashMapParser<K, V> implements ResultSetParser<LinkedHashMap<K, V>> {
	private final RowParser<K> keyParser;
	private final RowParser<V> valueParser;

	public LinkedHashMapParser(final RowParser<K> keyParser, final RowParser<V> valueParser) {
		super();
		this.valueParser = valueParser;
		this.keyParser = keyParser;
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
	public LinkedHashMap<K, V> parse(final Connection con, final ResultSet rs) throws SQLException {
		final LinkedHashMap<K, V> map = new LinkedHashMap<>();
		int rowNum = 0;

		while (rs.next()) {
			final V value = valueParser.parse(con, rs, rowNum);
			final K key = keyParser.parse(con, rs, rowNum);
			map.put(key, value);
			rowNum++;
		}
		return map;
	}

}
