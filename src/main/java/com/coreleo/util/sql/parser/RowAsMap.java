package com.coreleo.util.sql.parser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import com.coreleo.util.StringUtil;
import com.coreleo.util.sql.DBUtil;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class RowAsMap implements RowParser {
	private final boolean useLowerCaseColumnNames;
	private TimeZone timeZone;

	public RowAsMap() {
		super();
		useLowerCaseColumnNames = false;
	}

	public RowAsMap(final TimeZone timeZone) {
		super();
		useLowerCaseColumnNames = false;
		this.timeZone = timeZone;
	}

	/**
	 *
	 * @param useLowerCaseColumnNames
	 *            - default is false
	 */
	public RowAsMap(final boolean useLowerCaseColumnNames) {
		super();
		this.useLowerCaseColumnNames = useLowerCaseColumnNames;
	}

	public RowAsMap(final boolean useLowerCaseColumnNames, final TimeZone timeZone) {
		super();
		this.useLowerCaseColumnNames = useLowerCaseColumnNames;
		this.timeZone = timeZone;
	}

	@Override
	public Object parse(final Connection con, final ResultSet rs, final int rowNum) throws SQLException {
		final ResultSetMetaData metaData = rs.getMetaData();
		final int columnCount = metaData.getColumnCount();
		final Map row = new HashMap();
		for (int i = 1; i <= columnCount; i++) {
			row.put(useLowerCaseColumnNames ? StringUtil.toLowerCase(metaData.getColumnName(i)) : metaData
			        .getColumnName(i), DBUtil.getObject(metaData, rs, i, timeZone));
		}
		return row;
	}

}
