package com.coreleo.util.sql.parser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TimeZone;

import com.coreleo.util.BeanUtil;

/**
 * This parser will take the row values and populate a bean (java POJO object).
 *
 * By default it will convert the database underscore naming convention to java
 * camel case in order to map properties correctly to the bean. E.g. converts
 * the column name "last_name" to "lastName" in order to find the method
 * "setLastName".
 *
 * It will also convert CLOB to String objects by default
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RowAsBean implements RowParser {
	private final Class clazz;
	private final Object bean;

	private boolean underscoreColumnNamesToCamelCase = true;
	private boolean clobValuesToString = true;
	private TimeZone timeZone;

	/**
	 * Will create new instances of this class and populate with the values in
	 * the row.
	 */
	public RowAsBean(final Class clazz) {
		super();
		this.clazz = clazz;
		this.bean = null;
	}

	/**
	 * Will populate the passed in bean object with the values in the row. Be
	 * careful as a result set with multiple rows will cause the bean instance
	 * to have its properties overwritten with the values of the last row.
	 */
	public RowAsBean(final Object bean) {
		super();
		this.bean = bean;
		this.clazz = null;
	}

	/**
	 *
	 * @param underScoreToCamelCase
	 *            default is true E.g. converts the column name "last_name" to
	 *            "lastName".
	 *
	 */
	public RowAsBean(final Class clazz, final boolean underscoreToCamelCase) {
		this(clazz);
		this.underscoreColumnNamesToCamelCase = underscoreToCamelCase;
	}

	/**
	 * Will create new instances of this clazz and populate with the values in
	 * the row.
	 *
	 * @param timeZone
	 *            the time zone to use when getting time stamps from the
	 *            database.
	 */
	public RowAsBean(final Class clazz, final TimeZone timeZone) {
		this(clazz);
		this.timeZone = timeZone;
	}

	/**
	 * Will populate the passed in bean object with the values in the row. Be
	 * careful as a result set with multiple rows will cause the bean instance
	 * to have its properties overwritten with the values of the last row.
	 *
	 * @param timeZone
	 *            the time zone to use when getting time stamps from the
	 *            database.
	 */
	public RowAsBean(final Object bean, final TimeZone timeZone) {
		this(bean);
		this.timeZone = timeZone;
	}

	/**
	 *
	 * @param timeZone
	 *            the time zone to use when retrieving time stamps from the
	 *            database.
	 *
	 * @param underScoreToCamelCase
	 *            default is true E.g. converts the column name "last_name" to
	 *            "lastName".
	 *
	 */
	public RowAsBean(final Class clazz, final TimeZone timeZone, final boolean underscoreToCamelCase) {
		this(clazz);
		this.underscoreColumnNamesToCamelCase = underscoreToCamelCase;
		this.timeZone = timeZone;
	}

	/**
	 * @return Depending on which constructor was used, either a new populated
	 *         Bean object or the existing bean object with populated values.
	 */
	@Override
	public Object parse(final Connection con, final ResultSet rs, final int rowNum) throws SQLException {
		final RowAsMap parser = new RowAsMap();
		parser.setClobValuesToString(clobValuesToString);
		parser.setUnderscoreColumnNamesToCamelCase(underscoreColumnNamesToCamelCase);
		parser.setTimeZone(timeZone);

		final Map<String, Object> row = (Map<String, Object>) parser.parse(con, rs, rowNum);

		if (clazz != null) {
			return BeanUtil.populateBean(clazz, row);
		}
		else {
			return BeanUtil.populateBean(bean, row);
		}
	}

	/**
	 *
	 * @return true if the SQL CLOB should be converted to a String when
	 *         populating the bean
	 */
	public boolean isClobValuesToString() {
		return clobValuesToString;
	}

	/**
	 *
	 * @param convertClobToString
	 *            set to true if you want the CLOB to be converted to a String
	 *            object before setting the value to the Bean. Default to true.
	 */
	public void setClobValuesToString(final boolean convertClobToString) {
		this.clobValuesToString = convertClobToString;
	}

	public boolean isUnderscoreColumnNamesToCamelCase() {
		return underscoreColumnNamesToCamelCase;
	}

	public void setUnderscoreColumnNamesToCamelCase(final boolean underscoreToCamelCaseColumnNames) {
		this.underscoreColumnNamesToCamelCase = underscoreToCamelCaseColumnNames;
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(final TimeZone timeZone) {
		this.timeZone = timeZone;
	}

}
