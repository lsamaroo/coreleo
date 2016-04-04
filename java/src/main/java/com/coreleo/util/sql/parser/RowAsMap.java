package com.coreleo.util.sql.parser;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import com.coreleo.util.StringUtil;
import com.coreleo.util.sql.DBUtil;

/**
 *
 * This parser represents a row as a Map object. The column names are used as
 * the keys and the column values are used as the values.
 *
 *
 * @author lsamaroo
 *
 */
public class RowAsMap implements RowParser<Map<String, Object>> {
	private boolean userLowerCaseKeys;
	private TimeZone timeZone;
	private boolean underscoreColumnNamesToCamelCase;
	private boolean clobValuesToString;

	public RowAsMap() {
		super();
	}

	/**
	 *
	 * @param timeZone
	 *            the time zone to use when getting time stamps from the result
	 *            set
	 */
	public RowAsMap(final TimeZone timeZone) {
		super();
		this.timeZone = timeZone;
	}

	/**
	 *
	 * @param userLowerCaseKeys
	 *            convert the keys to all lower case, default is false
	 */
	public RowAsMap(final boolean userLowerCaseKeys) {
		super();
		this.userLowerCaseKeys = userLowerCaseKeys;
	}

	/**
	 *
	 * @param keysToLowercase
	 *            convert the keys to all lower case, default is false
	 * @param timeZone
	 *            the time zone to use when getting time stamps from the result
	 *            set
	 *
	 */
	public RowAsMap(final boolean userLowerCaseKeys, final TimeZone timeZone) {
		super();
		this.userLowerCaseKeys = userLowerCaseKeys;
		this.timeZone = timeZone;
	}

	/**
	 * @return a Map<String, Object> representing the row data. Column names are
	 *         used as the key
	 */
	@Override
	public Map<String, Object> parse(final Connection con, final ResultSet rs, final int rowNum) throws SQLException {
		final ResultSetMetaData metaData = rs.getMetaData();
		final int columnCount = metaData.getColumnCount();
		final Map<String, Object> rowAsMap = new HashMap<>();
		for (int i = 1; i <= columnCount; i++) {
			String key = metaData.getColumnLabel(i);
			if (userLowerCaseKeys) {
				key = StringUtil.toLowerCase(key);
			}

			if (underscoreColumnNamesToCamelCase) {
				key = StringUtil.underScoreToCamelCase(key, true);
			}

			final Object value = DBUtil.getObject(metaData, rs, i, timeZone);
			if (value instanceof Clob && clobValuesToString) {
				rowAsMap.put(key, DBUtil.clobToString(value));
			}
			else {
				rowAsMap.put(key, value);
			}
		}
		return rowAsMap;
	}

	public boolean isUserLowerCaseKeys() {
		return userLowerCaseKeys;
	}

	/**
	 *
	 * @param bool
	 *            true to tell the parser to convert keys to lowercase while
	 *            parsing. If underscoreColumnNameToCamelCase is set to true as
	 *            well and your column names have underscores then your keys
	 *            will be in camel case regardless of this flag.
	 */
	public void setUserLowerCaseKeys(final boolean bool) {
		this.userLowerCaseKeys = bool;
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

	/**
	 *
	 * @param timeZone
	 *            set the time zone used when getting time stamp from the result
	 *            set
	 */
	public void setTimeZone(final TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	public boolean isUnderscoreColumnNamesToCamelCase() {
		return underscoreColumnNamesToCamelCase;
	}

	/**
	 *
	 * @param bool
	 *            true to convert underscore separated column names to camel
	 *            case before using as the key to the map
	 */
	public void setUnderscoreColumnNamesToCamelCase(final boolean bool) {
		this.underscoreColumnNamesToCamelCase = bool;
	}

	/**
	 *
	 * @return true if the sql Clob should be converted to a String when
	 *         populating the bean
	 */
	public boolean isClobValuesToString() {
		return clobValuesToString;
	}

	/**
	 *
	 * @param bool
	 *            true to convert CLOB data type to Strings before adding to the
	 *            map
	 */
	public void setClobValuesToString(final boolean bool) {
		this.clobValuesToString = bool;
	}

}
