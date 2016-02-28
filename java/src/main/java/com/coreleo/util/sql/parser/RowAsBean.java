package com.coreleo.util.sql.parser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import com.coreleo.util.BeanUtil;
import com.coreleo.util.sql.DBUtil;
import com.coreleo.util.sql.SqlUtil;

/**
 * 
 * By default will convert the underscore naming convention to java camel case
 * in order to map properties correctly to the bean. E.g. converts the column
 * name "last_name" to "lastName" in order to find the method "setLastName".
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RowAsBean implements RowParser {
	private boolean underscoreToCamelCase;
	private Class clazz;
	private Object bean;
	private TimeZone timeZone;

	/**
	 * Will create new intances of this clazz and poplulate with row values.
	 */
	public RowAsBean(final Class clazz) {
		super();
		underscoreToCamelCase = true;
		this.clazz = clazz;
	}

	/**
	 * Will populate the passed in bean object with the values in the row. Be
	 * careful as a result set with multiple rows will cause the bean instance
	 * to have its properties overwritten with the values of the last row.
	 */
	public RowAsBean(final Object bean) {
		super();
		underscoreToCamelCase = true;
		this.bean = bean;
	}

	/**
	 * 
	 * @param underScoreToCamelCase
	 *            - default is true E.g. converts the column name last_name to
	 *            lastName.
	 * 
	 */
	public RowAsBean(final Class clazz, final boolean underscoreToCamelCase) {
		this(clazz);
		this.underscoreToCamelCase = underscoreToCamelCase;
	}

	/**
	 * Will create new intances of this clazz and poplulate with row values.
	 * 
	 * @param timeZone
	 *            - the timezone to use when getting timestamps from the
	 *            database.
	 */
	public RowAsBean(final Class clazz, final TimeZone timeZone) {
		super();
		underscoreToCamelCase = true;
		this.clazz = clazz;
		this.timeZone = timeZone;
	}

	/**
	 * Will populate the passed in bean object with the values in the row. Be
	 * careful as a result set with multiple rows will cause the bean instance
	 * to have its properties overwritten with the values of the last row.
	 * 
	 * @param timeZone
	 *            - the timezone to use when getting timestamps from the
	 *            database.
	 */
	public RowAsBean(final Object bean, final TimeZone timeZone) {
		super();
		underscoreToCamelCase = true;
		this.bean = bean;
		this.timeZone = timeZone;
	}

	/**
	 * 
	 * @param underScoreToCamelCase
	 *            - default is true E.g. converts the column name last_name to
	 *            lastName.
	 * 
	 * @param timeZone
	 *            - the timezone to use when getting timestamps from the
	 *            database.
	 */
	public RowAsBean(final Class clazz, final boolean underscoreToCamelCase, final TimeZone timeZone) {
		this(clazz);
		this.underscoreToCamelCase = underscoreToCamelCase;
		this.timeZone = timeZone;
	}

	@Override
	public Object parse(final Connection con, final ResultSet rs, final int rowNum) throws SQLException {
		final ResultSetMetaData metaData = rs.getMetaData();
		final int columnCount = metaData.getColumnCount();
		final Map rowAsMap = new HashMap();
		for (int i = 1; i <= columnCount; i++) {
			final String key = underscoreToCamelCase ? SqlUtil.underScoreToCamelCase(metaData.getColumnLabel(i)) : metaData.getColumnName(i);
			rowAsMap.put(key, DBUtil.getObject(metaData, rs, i, timeZone));
		}

		if (clazz != null) {
			return BeanUtil.populateBean(clazz, rowAsMap);
		}
		else {
			return BeanUtil.populateBean(bean, rowAsMap);
		}

	}

}
