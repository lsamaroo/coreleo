package com.coreleo.util.sql;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.coreleo.SimpleException;
import com.coreleo.util.ArrayUtil;
import com.coreleo.util.CollectionUtil;
import com.coreleo.util.LogUtil;
import com.coreleo.util.NumberUtil;
import com.coreleo.util.ReflectionUtil;
import com.coreleo.util.StringUtil;
import com.coreleo.util.date.DateAndTimeZone;
import com.coreleo.util.date.DateUtil;
import com.coreleo.util.sql.call.NullParameter;
import com.coreleo.util.sql.call.OutParameter;
import com.coreleo.util.sql.parser.ColumnFromFirstRowParser;
import com.coreleo.util.sql.parser.FirstRowOnlyParser;
import com.coreleo.util.sql.parser.LinkedHashMapParser;
import com.coreleo.util.sql.parser.ListParser;
import com.coreleo.util.sql.parser.ResultSetParser;
import com.coreleo.util.sql.parser.RowAsArray;
import com.coreleo.util.sql.parser.RowParser;

/**
 * 
 * @author Leon Samaroo
 * 
 * <br/>
 *         Convenience functions for dealing with jdbc.
 * 
 */
public final class DBUtil
{
	public final static int TYPES_ORACLECURSOR = -10;
	private final static int[] TYPES = new int[] { Types.INTEGER, Types.VARCHAR, Types.TIMESTAMP, Types.DOUBLE, Types.BIGINT, Types.DATE, Types.FLOAT,
			Types.LONGVARCHAR, Types.TINYINT, Types.NUMERIC, Types.TIME, Types.CHAR, Types.OTHER };
	private final static int INVALID_COLUMN_TYPE = 17004;
	private final static int INVALID_CONVERSION_REQUESTED = 17132;
	public static int QUERY_TIMEOUT = 60 * 10; // 10 minutes

	private DBUtil()
	{
	}

	public final static Driver registerDriver(String driverClassName)
	{
		LogUtil.trace("DBUtil:registerDriver driverClassName=" + driverClassName);
		if (StringUtil.isEmpty(driverClassName))
		{
			throw new SimpleException("driver class name is empty");
		}

		try
		{
			final Driver driver = (Driver) ReflectionUtil.newInstance(driverClassName);
			DriverManager.registerDriver(driver);
			return driver;
		}
		catch (final Exception e)
		{
			throw new SimpleException(e);
		}
	}

	public final static boolean deregisterDriver(Driver driver)
	{
		if (driver == null)
		{
			return false;
		}

		try
		{
			DriverManager.deregisterDriver(driver);
			return true;
		}
		catch (final Exception e)
		{
			return false;
		}
	}

	/**
	 * @deprecated
	 * @see DateUtil
	 */
	@Deprecated
	public static Timestamp toTimestamp(Calendar cal)
	{
		return DateUtil.toTimestamp(cal);
	}

	/**
	 * @deprecated
	 * @see DateUtil
	 */
	@Deprecated
	public static Timestamp toTimestamp(Date date)
	{
		return DateUtil.toTimestamp(date);
	}

	public static final void rollback(Connection con)
	{
		try
		{
			con.rollback();
		}
		catch (final Throwable t)
		{
			LogUtil.error("DBUtil:rollback - Unable to rollback/setAutoCommit to false.", t);
		}
	}

	public static final void commit(Connection con) throws SimpleSqlException
	{
		LogUtil.trace("DBUtil:commit");
		try
		{
			con.commit();
		}
		catch (final SQLException e)
		{
			throw new SimpleSqlException(e);
		}
	}

	public static final void turnOnAutoCommit(Connection con) throws SimpleSqlException
	{
		try
		{
			con.setAutoCommit(true);
		}
		catch (final SQLException e)
		{
			throw new SimpleSqlException(e);
		}
	}

	public static final void turnOffAutoCommit(Connection con) throws SimpleSqlException
	{
		try
		{
			con.setAutoCommit(false);
		}
		catch (final SQLException e)
		{
			throw new SimpleSqlException(e);
		}

	}

	public static final void close(ResultSet rs, Statement stmt) throws SimpleSqlException
	{
		close(rs);
		close(stmt);
	}

	public static final void close(ResultSet rs) throws SimpleSqlException
	{
		if (rs == null)
		{
			return;
		}

		try
		{
			rs.close();
		}
		catch (final SQLException e)
		{
			throw new SimpleSqlException(e);
		}
	}

	public static final void closeResultSetAndParentStatement(ResultSet rs) throws SimpleSqlException
	{
		if (rs == null)
		{
			return;
		}

		try
		{
			final Statement stmt = rs.getStatement();
			close(rs);
			close(stmt);
		}
		catch (final SQLException e)
		{
			throw new SimpleSqlException(e);
		}
	}

	public static final void close(Statement stmt) throws SimpleSqlException
	{
		if (stmt == null)
		{
			return;
		}

		try
		{
			if (stmt != null)
			{
				stmt.close();
			}
		}
		catch (final SQLException e)
		{
			throw new SimpleSqlException(e);
		}
	}

	public static final void close(Connection con) throws SimpleSqlException
	{
		if (con == null)
		{
			return;
		}

		try
		{
			con.close();
		}
		catch (final SQLException e)
		{
			throw new SimpleSqlException(e);
		}
	}

	public static Connection getConnection(Object connectionSource) throws SQLException, SimpleSqlException
	{
		Connection con = null;
		if (connectionSource instanceof Connection)
		{
			con = (Connection) connectionSource;
		}
		else if (connectionSource instanceof DataSource)
		{
			con = ((DataSource) connectionSource).getConnection();
		}

		if (con == null)
		{
			throw new SimpleSqlException("Null connection source");
		}

		return con;
	}

	public static void closeDataSourceConnection(Object connectionSource, Connection con)
	{
		if (connectionSource instanceof DataSource)
		{
			close(con);
		}
	}

	public static final int update(Object connectionSource, String sql) throws SimpleSqlException
	{
		return update(QUERY_TIMEOUT, connectionSource, sql, (Object[]) null);
	}

	//	public static final int update(Object connectionSource, String sql, Object param) throws SimpleSqlException {
	//		return update(QUERY_TIMEOUT, connectionSource, sql, new Object[] { param });
	//	}

	public static final int update(Object connectionSource, String sql, Object... params) throws SimpleSqlException
	{
		return update(QUERY_TIMEOUT, connectionSource, sql, params);
	}

	public static final int update(Object connectionSource, String sql, List<Object> params) throws SimpleSqlException
	{
		return update(QUERY_TIMEOUT, connectionSource, sql, ArrayUtil.toObjectArray(params));
	}

	private static final int update(int queryTimeOut, Object connectionSource, String sql, Object... params) throws SimpleSqlException
	{
		final String log = getLogString("update", -1, -1, queryTimeOut, sql, params);
		LogUtil.trace(log);
		PreparedStatement pstmt = null;
		int result = -1;
		Connection con = null;
		long startTime = 0;

		try
		{
			con = getConnection(connectionSource);
			pstmt = con.prepareStatement(sql);
			pstmt.setQueryTimeout(queryTimeOut);

			if (params != null)
			{
				// set the param for the prepared statement
				for (int i = 0; i < params.length; i++)
				{
					setValueOrNull(pstmt, i + 1, params[i]);
				}
			}

			startTime = System.currentTimeMillis();
			result = pstmt.executeUpdate();
			final long timeToRun = (System.currentTimeMillis() - startTime);
			// close as soon as possible
			//close(pstmt);
			//closeDataSourceConnection(connectionSource, con);
			LogUtil.debug(new StringBuffer(log).append(" [run=").append(timeToRun).append("ms]").toString());
		}
		catch (final SQLException sqle)
		{
			LogUtil.trace("SQLException executing " + log + " " + sqle.getMessage());
			throw new SimpleSqlException("SQLException occurred " + sqle.getMessage(), sqle);
		}
		finally
		{
			close(pstmt);
			closeDataSourceConnection(connectionSource, con);
		}
		LogUtil.debug("Result of update/insert = " + result );
		return result;
	}

	public static final List<?> queryForListOfArrays(Object connectionSource, String sql) throws SimpleSqlException
	{
		return (List<?>) query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql, new ListParser(new RowAsArray()),
			(Object[]) null);
	}

	//	public static final List<?> queryForListOfArrays(Object connectionSource, String sql, Object param) throws SimpleSqlException {
	//		return (List<?>) query(connectionSource, sql, new ListParser(new RowAsArray()), new Object[] { param });
	//	}

	public static final List<?> queryForListOfArrays(Object connectionSource, String sql, Object... params) throws SimpleSqlException
	{
		return (List<?>) query(connectionSource, sql, new ListParser(new RowAsArray()), params);
	}

	public static final List<?> queryForList(Object connectionSource, String sql, RowParser rowParser, Object... params) throws SimpleSqlException
	{
		return (List<?>) query(connectionSource, sql, new ListParser(rowParser), params);
	}

	public static final List<?> queryForList(Object connectionSource, String sql, RowParser rowParser) throws SimpleSqlException
	{
		return (List<?>) query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql, new ListParser(rowParser),
			(Object[]) null);
	}

	//	public static final List<?> queryForList(Object connectionSource, String sql, RowParser rowParser, Object param) throws SimpleSqlException {
	//		return (List<?>) query(connectionSource, sql, new ListParser(rowParser), new Object[] { param });
	//	}

	public static final Map<?, ?> queryForMap(Object connectionSource, String sql, RowParser rowParser, String nameOfColumnToUseAsKey)
			throws SimpleSqlException
	{
		return (Map<?, ?>) query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql, new LinkedHashMapParser(
				rowParser, nameOfColumnToUseAsKey), (Object[]) null);
	}

	public static final Map<?, ?> queryForMap(Object connectionSource, String sql, RowParser rowParser, int indexOfColumnToUseAsKey) throws SimpleSqlException
	{
		return (Map<?, ?>) query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql, new LinkedHashMapParser(
				rowParser, indexOfColumnToUseAsKey), (Object[]) null);
	}

	public static final Map<?, ?> queryForMap(Object connectionSource, String sql, RowParser rowParser, RowParser keyParser) throws SimpleSqlException
	{
		return (Map<?, ?>) query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql, new LinkedHashMapParser(
				rowParser, keyParser), (Object[]) null);
	}

	//	public static final Map<?, ?> queryForMap(Object connectionSource, String sql, RowParser rowParser, String nameOfColumnToUseAsKey, Object param) throws SimpleSqlException {
	//		return (Map<?, ?>) query(connectionSource, sql, new LinkedHashMapParser(rowParser, nameOfColumnToUseAsKey), new Object[] { param });
	//	}

	//	public static final Map<?, ?> queryForMap(Object connectionSource, String sql, RowParser rowParser, int indexOfColumnToUseAsKey, Object param) throws SimpleSqlException {
	//		return (Map<?, ?>) query(connectionSource, sql, new LinkedHashMapParser(rowParser, indexOfColumnToUseAsKey), new Object[] { param });
	//	}

	public static final Map<?, ?> queryForMap(Object connectionSource, String sql, RowParser rowParser, String nameOfColumnToUseAsKey, Object... params)
			throws SimpleSqlException
	{
		return (Map<?, ?>) query(connectionSource, sql, new LinkedHashMapParser(rowParser, nameOfColumnToUseAsKey), params);
	}

	public static final Map<?, ?> queryForMap(Object connectionSource, String sql, RowParser rowParser, int indexOfColumnToUseAsKey, Object... params)
			throws SimpleSqlException
	{
		return (Map<?, ?>) query(connectionSource, sql, new LinkedHashMapParser(rowParser, indexOfColumnToUseAsKey), params);
	}

	public static final Object queryForFirstRow(Object connectionSource, String sql, RowParser rowParser) throws SimpleSqlException
	{
		return query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql, new FirstRowOnlyParser(rowParser),
			(Object[]) null);
	}

	//	public static final Object queryForFirstRow(Object connectionSource, String sql, RowParser rowParser, Object param) throws SimpleSqlException {
	//		return query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql, new FirstRowOnlyParser(rowParser), new Object[] { param });
	//	}

	public static final Object queryForFirstRow(Object connectionSource, String sql, RowParser rowParser, Object... params) throws SimpleSqlException
	{
		return query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql, new FirstRowOnlyParser(rowParser), params);
	}

	//	public static final Object queryForColumn(Object connectionSource, String sql, String columnName, Object param) throws SimpleSqlException {
	//		return query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql, new ColumnFromFirstRowParser(columnName), new Object[]{param});
	//	}

	public static final Object queryForColumn(Object connectionSource, String sql, String columnName, Object... params) throws SimpleSqlException
	{
		return query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql, new ColumnFromFirstRowParser(columnName),
			params);
	}

	//	public static final Object queryForColumn(Object connectionSource, String sql, int columnIndex, Object param) throws SimpleSqlException {
	//		return query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql, new ColumnFromFirstRowParser(columnIndex), new Object[]{param});
	//	}

	public static final Object queryForColumn(Object connectionSource, String sql, int columnIndex, Object... params) throws SimpleSqlException
	{
		return query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql, new ColumnFromFirstRowParser(columnIndex),
			params);
	}

	public static final Object queryForColumn(Object connectionSource, String sql, int columnIndex) throws SimpleSqlException
	{
		return query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql, new ColumnFromFirstRowParser(columnIndex),
			(Object[]) null);
	}

	public static final Object queryForColumn(Object connectionSource, String sql, String columnName) throws SimpleSqlException
	{
		return query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql, new ColumnFromFirstRowParser(columnName),
			(Object[]) null);
	}

	//	public static final Object query(Object connectionSource, String sql, ResultSetParser handler, Object param) throws SimpleSqlException {
	//		return query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql, handler, new Object[]{param});
	//	}

	public static final Object query(Object connectionSource, String sql) throws SimpleSqlException
	{
		return query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql, null, (Object[]) null);
	}

	public static final Object query(Object connectionSource, String sql, ResultSetParser handler, Object... params) throws SimpleSqlException
	{
		return query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql, handler, params);
	}

	public static final Object query(Object connectionSource, String sql, ResultSetParser handler, List<Object> params) throws SimpleSqlException
	{
		return query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql, handler, ArrayUtil.toObjectArray(params));
	}

	public static final Object query(Object connectionSource, String sql, ResultSetParser handler, int resultSetType, int resultSetConcurrency)
			throws SimpleSqlException
	{
		return query(resultSetType, resultSetConcurrency, QUERY_TIMEOUT, connectionSource, sql, handler, (Object[]) null);
	}

	private static final Object query(int resultSetType, int resultSetConcurrency, int queryTimeOut, Object connectionSource, String sql,
			ResultSetParser handler, Object... params) throws SimpleSqlException
	{
		final String log = getLogString("query", resultSetType, resultSetConcurrency, queryTimeOut, sql, params);
		LogUtil.trace(log);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		long startTime = 0;

		try
		{
			con = getConnection(connectionSource);
			pstmt = con.prepareStatement(sql, resultSetType, resultSetConcurrency);
			pstmt.setQueryTimeout(queryTimeOut);

			if (params != null)
			{
				// set the param for the prepared statement
				for (int i = 0; i < params.length; i++)
				{
					setValueOrNull(pstmt, i + 1, params[i]);
				}
			}

			startTime = System.currentTimeMillis();
			rs = pstmt.executeQuery();
			final long timeToRun = (System.currentTimeMillis() - startTime);
			LogUtil.debug(new StringBuffer(log).append(" [run=").append(timeToRun).append("ms]").toString());

			if (handler != null)
			{
				return handler.parse(con, rs);
			}
			else
			{
				return null;
			}
		}
		catch (final SQLException sqle)
		{
			LogUtil.error("SQLException executing " + log + " " + sqle.getMessage());
			throw new SimpleSqlException("SQLException occurred " + sqle.getMessage(), sqle);
		}
		finally
		{
			close(rs);
			close(pstmt);
			closeDataSourceConnection(connectionSource, con);
		}
	}

	public static final List<OutParameter> call(Object connectionSource, String sql) throws SimpleSqlException
	{
		return call(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql, (Object[]) null);
	}

	//	public static final List<OutParameter> call(Object connectionSource, String sql, Object param ) throws SimpleSqlException {
	//		return call(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql, new Object[] { param });
	//	}

	public static final List<OutParameter> call(Object connectionSource, String sql, Object... params) throws SimpleSqlException
	{
		return call(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql, params);
	}

	public static final List<OutParameter> call(Object connectionSource, String sql, List<Object> params) throws SimpleSqlException
	{
		return call(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql, ArrayUtil.toObjectArray(params));
	}

	private static final List<OutParameter> call(int resultSetType, int resultSetConcurrency, int queryTimeOut, Object connectionSource, String sql,
			Object... params) throws SimpleSqlException
	{
		final String log = getLogString("call", resultSetType, resultSetConcurrency, queryTimeOut, sql, params);
		LogUtil.trace(log);

		CallableStatement cstmt = null;
		final ResultSet rs = null;
		Connection con = null;
		final List<OutParameter> outParams = new ArrayList<OutParameter>();
		long startTime = 0;

		try
		{
			con = getConnection(connectionSource);
			cstmt = con.prepareCall(sql, resultSetType, resultSetConcurrency);
			cstmt.setQueryTimeout(queryTimeOut);

			if (params != null)
			{
				for (int i = 0; i < params.length; i++)
				{
					if (params[i] instanceof OutParameter)
					{
						final OutParameter out = (OutParameter) params[i];
						cstmt.registerOutParameter(i + 1, out.getType());
						out.setIndex(i + 1);
						outParams.add(out);
					}
					else if (params[i] instanceof NullParameter)
					{
						cstmt.setNull(i + 1, ((NullParameter) params[i]).getType());
					}
					else
					{
						setValueOrNull(cstmt, i + 1, params[i]);
					}
				}
			}

			startTime = System.currentTimeMillis();
			cstmt.execute();
			final long timeToRun = (System.currentTimeMillis() - startTime);
			LogUtil.debug(new StringBuffer(log).append(" [run=").append(timeToRun).append("ms]").toString());

			if (CollectionUtil.isNotEmpty(outParams))
			{
				for (int i = 0; i < outParams.size(); i++)
				{
					final OutParameter out = outParams.get(i);
					out.setValue(cstmt);
				}
			}

			return outParams;
		}
		catch (final SQLException sqle)
		{
			LogUtil.trace("SQLException executing " + log + " " + sqle.getMessage());
			throw new SimpleSqlException("SQLException occurred " + sqle.getMessage(), sqle);
		}
		finally
		{
			close(rs);
			close(cstmt);
			closeDataSourceConnection(connectionSource, con);
		}
	}

	public static Integer getIntegerObject(ResultSet rs, String columnName, Integer defaultValue) throws SQLException
	{
		return NumberUtil.toIntegerObject(rs.getObject(columnName), defaultValue);
	}

	private static final void setValueOrNull(PreparedStatement pstmt, int index, Object x) throws SQLException
	{
		if (x != null)
		{
			setObject(pstmt, index, x);
		}
		else
		{
			setNull(pstmt, index, x);
		}
	}

	private static final void setObject(PreparedStatement pstmt, int index, Object x) throws SQLException
	{
		try
		{
			if (x instanceof Integer)
			{
				pstmt.setInt(index, (Integer) x);
			}
			else if (x instanceof Double)
			{
				pstmt.setDouble(index, (Double) x);
			}
			else if (x instanceof Float)
			{
				pstmt.setFloat(index, (Float) x);
			}
			else if (x instanceof Long)
			{
				pstmt.setLong(index, (Long) x);
			}
			else if (x instanceof Short)
			{
				pstmt.setLong(index, (Short) x);
			}
			else if (x instanceof BigDecimal)
			{
				pstmt.setBigDecimal(index, (BigDecimal) x);
			}
			else if (x instanceof Number)
			{
				pstmt.setInt(index, NumberUtil.toInteger(x));
			}
			else if (x instanceof Byte)
			{
				pstmt.setByte(index, (Byte) x);
			}
			else if (x instanceof byte[])
			{
				pstmt.setBytes(index, (byte[]) x);
			}
			else if (x instanceof String)
			{
				pstmt.setString(index, StringUtil.toString(x));
			}
			else if (x instanceof Timestamp)
			{
				pstmt.setTimestamp(index, (Timestamp) x);
			}
			else if (x instanceof Time)
			{
				pstmt.setTime(index, (Time) x);
			}
			else if (x instanceof Date)
			{
				pstmt.setDate(index, new java.sql.Date(((Date) x).getTime()));
			}
			else if (x instanceof DateAndTimeZone)
			{
				Timestamp t = new Timestamp( ((DateAndTimeZone) x).getDate().getTime() );
				pstmt.setTimestamp(index, t, Calendar.getInstance(((DateAndTimeZone) x).getTimezone()) );
			}
			else if (x instanceof Array)
			{
				pstmt.setArray(index, (Array) x);
			}
			else if (x instanceof Clob)
			{
				pstmt.setClob(index, (Clob) x);
			}
			else if (x instanceof Blob)
			{
				pstmt.setBlob(index, (Blob) x);
			}
			else if (x instanceof URL)
			{
				pstmt.setURL(index, (URL) x);
			}
			else
			{
				pstmt.setObject(index, x, Types.OTHER);
			}
		}
		catch (final SQLException sqle)
		{
			final int errorCode = sqle.getErrorCode();
			if (errorCode == INVALID_CONVERSION_REQUESTED || errorCode == INVALID_COLUMN_TYPE)
			{
				// last ditch attempt to set the value
				// convert to a string and try setting
				pstmt.setObject(index, StringUtil.toString(x));
				return;
			}
			throw sqle;
		}

	}

	private static final void setNull(PreparedStatement pstmt, int index, Object x) throws SQLException
	{
		for (final int element : TYPES)
		{
			try
			{
				pstmt.setNull(index, element);
				break;
			}
			catch (final SQLException sqle)
			{
				if (sqle.getErrorCode() != INVALID_COLUMN_TYPE)
				{
					throw sqle;
				}
			}
		}
	}

	private static final String getLogString(String sqlType, int resultSetType, int resultSetConcurrency, int queryTimeOut, String sql, Object... params)
	{
		final StringBuffer buff = new StringBuffer();
		buff.append("DBUtil:");
		buff.append(sqlType);
		buff.append(" - sql=");
		buff.append(sql);
		buff.append(" params=");
		buff.append(ArrayUtil.toDelimitedString(params, "|"));
		if (resultSetType != -1)
		{
			buff.append(" [resultSetType=");
			buff.append(resultSetType);
		}

		if (resultSetConcurrency != -1)
		{
			buff.append(" resultSetConcurrency=");
			buff.append(resultSetConcurrency);
		}
		buff.append(" queryTimeOut=");
		buff.append(queryTimeOut);
		buff.append("]");
		return buff.toString();
	}
}