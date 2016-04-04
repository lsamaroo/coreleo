package com.coreleo.util.sql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
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
import java.sql.ResultSetMetaData;
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
import java.util.TimeZone;

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
import com.coreleo.util.sql.parser.ColumnValueFromFirstRowParser;
import com.coreleo.util.sql.parser.FirstRowOnlyParser;
import com.coreleo.util.sql.parser.LinkedHashMapParser;
import com.coreleo.util.sql.parser.ListParser;
import com.coreleo.util.sql.parser.ResultSetParser;
import com.coreleo.util.sql.parser.RowAsArray;
import com.coreleo.util.sql.parser.RowAsMap;
import com.coreleo.util.sql.parser.RowParser;
import com.coreleo.util.sql.parser.SingleValue;

/**
 *
 * @author Leon Samaroo
 *
 *         <br/>
 *         Convenience functions for dealing with jdbc.
 *
 */
public final class DBUtil {
	public final static int TYPES_ORACLECURSOR = -10;
	private final static int[] TYPES = new int[] { Types.INTEGER, Types.VARCHAR, Types.TIMESTAMP, Types.DOUBLE,
	        Types.BIGINT, Types.DATE, Types.FLOAT, Types.LONGVARCHAR, Types.TINYINT, Types.NUMERIC, Types.TIME,
	        Types.CHAR, Types.OTHER };
	private final static int INVALID_COLUMN_TYPE = 17004;
	private final static int INVALID_CONVERSION_REQUESTED = 17132;
	public static int QUERY_TIMEOUT = 60 * 10; // 10 minutes

	private DBUtil() {
	}

	public static String clobToString(final Object data) {
		if (data instanceof Clob) {
			return clobToString((Clob) data);
		}

		return null;
	}

	public static String clobToString(final Clob data) {
		final StringBuilder sb = new StringBuilder();

		try {
			final Reader reader = data.getCharacterStream();
			final BufferedReader br = new BufferedReader(reader);

			int b;
			while (-1 != (b = br.read())) {
				sb.append((char) b);
			}

			br.close();
		}
		catch (final SQLException e) {
			LogUtil.error(DBUtil.class, "SQL. Could not convert CLOB to string", e);
			return e.toString();
		}
		catch (final IOException e) {
			LogUtil.error(DBUtil.class, "IO. Could not convert CLOB to string", e);
			return e.toString();
		}

		return sb.toString();
	}

	public static Object getObject(final ResultSetMetaData meta, final ResultSet rs, final int i,
	        final TimeZone timeZone) throws SQLException {
		if (meta.getColumnType(i) == Types.TIMESTAMP && timeZone != null) {
			return rs.getTimestamp(i, Calendar.getInstance(timeZone));
		}
		else {
			return rs.getObject(i);
		}
	}

	public static Driver registerDriver(final String driverClassName) {
		LogUtil.trace("DBUtil:registerDriver driverClassName=" + driverClassName);
		if (StringUtil.isEmpty(driverClassName)) {
			throw new SimpleException("driver class name is empty");
		}

		try {
			final Driver driver = (Driver) ReflectionUtil.newInstance(driverClassName);
			DriverManager.registerDriver(driver);
			return driver;
		}
		catch (final Exception e) {
			throw new SimpleException(e);
		}
	}

	public static boolean deregisterDriver(final Driver driver) {
		if (driver == null) {
			return false;
		}

		try {
			DriverManager.deregisterDriver(driver);
			return true;
		}
		catch (final Exception e) {
			return false;
		}
	}

	/**
	 * @deprecated
	 * @see DateUtil
	 */
	@Deprecated
	public static Timestamp toTimestamp(final Calendar cal) {
		return DateUtil.toTimestamp(cal);
	}

	/**
	 * @deprecated
	 * @see DateUtil
	 */
	@Deprecated
	public static Timestamp toTimestamp(final Date date) {
		return DateUtil.toTimestamp(date);
	}

	public static void rollback(final Connection con) {
		try {
			con.rollback();
		}
		catch (final Throwable t) {
			LogUtil.error("DBUtil:rollback - Unable to rollback/setAutoCommit to false.", t);
		}
	}

	public static void commit(final Connection con) throws SimpleSqlException {
		LogUtil.trace("DBUtil:commit");
		try {
			con.commit();
		}
		catch (final SQLException e) {
			throw new SimpleSqlException(e);
		}
	}

	public static void turnOnAutoCommit(final Connection con) throws SimpleSqlException {
		try {
			con.setAutoCommit(true);
		}
		catch (final SQLException e) {
			throw new SimpleSqlException(e);
		}
	}

	public static void turnOffAutoCommit(final Connection con) throws SimpleSqlException {
		try {
			con.setAutoCommit(false);
		}
		catch (final SQLException e) {
			throw new SimpleSqlException(e);
		}

	}

	public static void close(final ResultSet rs, final Statement stmt) throws SimpleSqlException {
		close(rs);
		close(stmt);
	}

	public static void close(final ResultSet rs) throws SimpleSqlException {
		if (rs == null) {
			return;
		}

		try {
			rs.close();
		}
		catch (final SQLException e) {
			throw new SimpleSqlException(e);
		}
	}

	public static void closeResultSetAndParentStatement(final ResultSet rs) throws SimpleSqlException {
		if (rs == null) {
			return;
		}

		try {
			final Statement stmt = rs.getStatement();
			close(rs);
			close(stmt);
		}
		catch (final SQLException e) {
			throw new SimpleSqlException(e);
		}
	}

	public static void close(final Statement stmt) throws SimpleSqlException {
		if (stmt == null) {
			return;
		}

		try {
			if (stmt != null) {
				stmt.close();
			}
		}
		catch (final SQLException e) {
			throw new SimpleSqlException(e);
		}
	}

	public static void close(final Connection con) throws SimpleSqlException {
		if (con == null) {
			return;
		}

		try {
			con.close();
		}
		catch (final SQLException e) {
			throw new SimpleSqlException(e);
		}
	}

	public static void close(final Object connectionSource, final Connection con) {
		if (connectionSource instanceof DataSource) {
			close(con);
		}
	}

	public static Connection getConnection(final Object connectionSource) throws SQLException, SimpleSqlException {
		Connection con = null;
		if (connectionSource instanceof Connection) {
			con = (Connection) connectionSource;
		}
		else if (connectionSource instanceof DataSource) {
			con = ((DataSource) connectionSource).getConnection();
		}

		if (con == null) {
			throw new SimpleSqlException("Null connection source");
		}

		return con;
	}

	/**
	 * @return an integer representing the insert or update count.
	 */
	public static Object update(final Object connectionSource, final String sql) throws SimpleSqlException {
		return update(false, QUERY_TIMEOUT, connectionSource, sql, (Object[]) null);
	}

	/**
	 * @return an integer representing the insert or update count.
	 */
	public static Object update(final Object connectionSource, final String sql, final Object... params)
	        throws SimpleSqlException {
		return update(false, QUERY_TIMEOUT, connectionSource, sql, params);
	}

	/**
	 * @return an integer representing the insert or update count.
	 */
	public static Object update(final Object connectionSource, final String sql, final List<Object> params)
	        throws SimpleSqlException {
		return update(false, QUERY_TIMEOUT, connectionSource, sql, ArrayUtil.toObjectArray(params));
	}

	/**
	 * @return The generated id's from your insert statement.
	 */
	public static Object updateReturnGeneratedKey(final Object connectionSource, final String sql,
	        final Object... params) throws SimpleSqlException {
		return update(true, QUERY_TIMEOUT, connectionSource, sql, params);
	}

	private static final Object update(final boolean returnGeneratedKey, final int queryTimeOut,
	        final Object connectionSource, final String sql, final Object... params) throws SimpleSqlException {
		final String log = getLogString("update", -1, -1, queryTimeOut, sql, params);
		LogUtil.trace(log);
		PreparedStatement pstmt = null;
		ResultSet generatedKeys = null;
		int result = -1;
		Object generatedKey = -1;
		Connection con = null;
		long startTime = 0;

		try {
			con = getConnection(connectionSource);

			if (returnGeneratedKey) {
				pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			}
			else {
				pstmt = con.prepareStatement(sql);
			}
			pstmt.setQueryTimeout(queryTimeOut);

			if (params != null) {
				// set the param for the prepared statement
				for (int i = 0; i < params.length; i++) {
					setValueOrNull(pstmt, i + 1, params[i]);
				}
			}

			startTime = System.currentTimeMillis();
			result = pstmt.executeUpdate();

			if (returnGeneratedKey) {
				generatedKeys = pstmt.getGeneratedKeys();
				if (result != 0 && generatedKeys.next()) {
					generatedKey = generatedKeys.getObject(1);
				}
			}

			final long timeToRun = (System.currentTimeMillis() - startTime);
			LogUtil.debug(new StringBuffer(log).append(" [run=").append(timeToRun).append("ms]").toString());
		}
		catch (final SQLException sqle) {
			LogUtil.trace("SQLException executing " + log + " " + sqle.getMessage());
			throw new SimpleSqlException("SQLException occurred " + sqle.getMessage(), sqle);
		}
		finally {
			close(generatedKeys);
			close(pstmt);
			close(connectionSource, con);
		}
		LogUtil.debug("Result of update/insert = " + result);
		return returnGeneratedKey ? generatedKey : result;
	}

	public static Object updateBatch(final Object connectionSource, final String sql,
	        final BatchParameter... batchParams) throws SimpleSqlException {
		return updateBatch(false, QUERY_TIMEOUT, connectionSource, sql, batchParams);
	}

	public static Object updateBatchAsTransaction(final Object connectionSource, final String sql,
	        final BatchParameter... batchParams) throws SimpleSqlException {
		return updateBatch(true, QUERY_TIMEOUT, connectionSource, sql, batchParams);
	}

	private static final Object updateBatch(final boolean asTransaction, final int queryTimeOut,
	        final Object connectionSource, final String sql, final BatchParameter... batchParams)
	        throws SimpleSqlException {
		final String log = getLogString("updateBatch", -1, -1, queryTimeOut, sql, (Object[]) batchParams);
		LogUtil.trace(log);

		Connection con = null;
		PreparedStatement pstmt = null;
		int[] result = new int[] { -1 };
		long startTime = 0;

		try {
			con = DBUtil.getConnection(connectionSource);
			if (asTransaction) {
				DBUtil.turnOffAutoCommit(con);
			}

			pstmt = con.prepareStatement(sql);
			pstmt.setQueryTimeout(queryTimeOut);

			for (final BatchParameter params : batchParams) {
				for (int i = 0; i < params.size(); i++) {
					setValueOrNull(pstmt, i + 1, params.get(i));
				}
				pstmt.addBatch();
			}

			startTime = System.currentTimeMillis();
			result = pstmt.executeBatch();

			if (asTransaction) {
				DBUtil.commit(con);
			}

			final long timeToRun = (System.currentTimeMillis() - startTime);
			LogUtil.debug(new StringBuffer(log).append(" [run=").append(timeToRun).append("ms]").toString());
		}
		catch (final Exception e) {
			if (asTransaction) {
				DBUtil.rollback(con);
			}
			throw new SimpleSqlException(e.getMessage());
		}
		finally {
			if (asTransaction) {
				DBUtil.turnOnAutoCommit(con);
			}
			DBUtil.close(pstmt);
			DBUtil.close(connectionSource, con);
		}

		LogUtil.debug("Result of updateBatch = " + result);
		return result;
	}

	/**
	 *
	 * @return A list of rows. Each row values are stored in an array.
	 * @throws SimpleSqlException
	 */
	public static List<Object[]> queryForListOfArrays(final Object connectionSource, final String sql)
	        throws SimpleSqlException {
		return query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql,
		        new ListParser<Object[]>(new RowAsArray()), (Object[]) null);
	}

	/**
	 *
	 * @return A list of rows. Each row values are stored in an array.
	 * @throws SimpleSqlException
	 */
	public static List<Object[]> queryForListOfArrays(final Object connectionSource, final String sql,
	        final Object... params) throws SimpleSqlException {
		return query(connectionSource, sql, new ListParser<>(new RowAsArray()), params);
	}

	public static <T> List<T> queryForList(final Object connectionSource, final String sql,
	        final RowParser<T> rowParser, final Object... params) throws SimpleSqlException {
		return query(connectionSource, sql, new ListParser<>(rowParser), params);
	}

	public static <T> List<T> queryForList(final Object connectionSource, final String sql,
	        final RowParser<T> rowParser) throws SimpleSqlException {
		return query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql,
		        new ListParser<>(rowParser), (Object[]) null);
	}

	/**
	 *
	 * @param useLowerCaseKeyNames
	 *            - convert the column names to lower case before using them as
	 *            the key name in the map.
	 * @return A list of rows. A row is represented as a Map object. The column
	 *         name is the key. The column value is the value of the map.
	 * @throws SimpleSqlException
	 */
	public static List<Map<String, Object>> queryForListOfMaps(final Object connectionSource,
	        final boolean useLowerCaseKeyNames, final String sql, final Object... params) throws SimpleSqlException {
		return query(connectionSource, sql, new ListParser<>(new RowAsMap(useLowerCaseKeyNames)), params);
	}

	/**
	 *
	 * @return each row as an object stored in a map. The key used is the value
	 *         of the column specified in the argument. What that object is
	 *         depends on the row parser passed in.
	 * @throws SimpleSqlException
	 */
	public static <V> Map<Object, V> queryForMapOfObjects(final Object connectionSource, final String sql,
	        final RowParser<V> rowParser, final String nameOfColumnToUseAsKey) throws SimpleSqlException {
		return queryForMapOfObjects(connectionSource, sql, new SingleValue(nameOfColumnToUseAsKey), rowParser);
	}

	/**
	 *
	 * @return A map of row object. The type of row object depends on the row
	 *         parser provided. The key used is the value of the column
	 *         specified in the argument.
	 * @throws SimpleSqlException
	 */
	public static <V> Map<Object, V> queryForMapOfObjects(final Object connectionSource, final String sql,
	        final RowParser<V> rowParser, final int indexOfColumnToUseAsKey) throws SimpleSqlException {
		return queryForMapOfObjects(connectionSource, sql, new SingleValue(indexOfColumnToUseAsKey), rowParser);
	}

	public static <K, V> Map<K, V> queryForMapOfObjects(final Object connectionSource, final String sql,
	        final RowParser<K> keyParser, final RowParser<V> rowParser) throws SimpleSqlException {
		return query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql,
		        new LinkedHashMapParser<>(keyParser, rowParser), (Object[]) null);
	}

	public static <V> Map<Object, V> queryForMapOfObjects(final Object connectionSource, final String sql,
	        final RowParser<V> rowParser, final String nameOfColumnToUseAsKey, final Object... params)
	        throws SimpleSqlException {
		return queryForMapOfObjects(connectionSource, sql, new SingleValue(nameOfColumnToUseAsKey), rowParser, params);
	}

	public static <V> Map<Object, V> queryForMapOfObjects(final Object connectionSource, final String sql,
	        final RowParser<V> rowParser, final int indexOfColumnToUseAsKey, final Object... params)
	        throws SimpleSqlException {
		return queryForMapOfObjects(connectionSource, sql, new SingleValue(indexOfColumnToUseAsKey), rowParser, params);
	}

	public static <K, V> Map<K, V> queryForMapOfObjects(final Object connectionSource, final String sql,
	        final RowParser<K> keyParser, final RowParser<V> rowParser, final Object... params)
	        throws SimpleSqlException {
		return query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql,
		        new LinkedHashMapParser<>(keyParser, rowParser), params);
	}

	/**
	 *
	 * @param connectionSource
	 *            a datasource or connection object
	 * @param useLowerCaseKeyNames
	 *            - convert the column names to lower case before using them as
	 *            the key name in the map.
	 * @param sql
	 *            the sql to execute
	 * @return A row represented as a Map object. The column name is the key.
	 *         The column value is the value of the map.
	 * @throws SimpleSqlException
	 */
	public static Map<String, Object> queryForFirstRowAsMap(final Object connectionSource, final String sql,
	        final boolean useLowerCaseKeyNames) throws SimpleSqlException {
		return query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql,
		        new FirstRowOnlyParser<>(new RowAsMap(useLowerCaseKeyNames)), (Object[]) null);
	}

	/**
	 *
	 * @param connectionSource
	 *            a datasource or connection object
	 * @param useLowerCaseKeyNames
	 *            - convert the column names to lower case before using them as
	 *            the key name in the map.
	 * @param sql
	 *            the sql to execute
	 * @param params
	 *            a list of parameters to pass to the sql
	 * @return A row represented as a Map object. The column name is the key.
	 *         The column value is the value of the map.
	 * @throws SimpleSqlException
	 */
	public static Map<String, Object> queryForFirstRowAsMap(final Object connectionSource, final String sql,
	        final boolean useLowerCaseKeyNames, final Object... params) throws SimpleSqlException {
		return query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql,
		        new FirstRowOnlyParser<>(new RowAsMap(useLowerCaseKeyNames)), params);
	}

	/**
	 *
	 * @return The first row of the result set parsed by the row parser
	 *         provided.
	 * @throws SimpleSqlException
	 */
	public static <T> T queryForFirstRow(final Object connectionSource, final String sql, final RowParser<T> rowParser)
	        throws SimpleSqlException {
		return query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql,
		        new FirstRowOnlyParser<>(rowParser), (Object[]) null);
	}

	/**
	 * @return The first row of the result set parsed by the row parser
	 *         provided.
	 * @throws SimpleSqlException
	 */
	public static <T> T queryForFirstRow(final Object connectionSource, final String sql, final RowParser<T> rowParser,
	        final Object... params) throws SimpleSqlException {
		return query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql,
		        new FirstRowOnlyParser<>(rowParser), params);
	}

	/**
	 *
	 * @return the column value from the first row of the result set.
	 * @throws SimpleSqlException
	 */
	public static Object queryForColumnValue(final Object connectionSource, final String sql, final String columnName,
	        final Object... params) throws SimpleSqlException {
		return query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql,
		        new ColumnValueFromFirstRowParser(columnName), params);
	}

	/**
	 *
	 * @return the column value from the first row of the result set. given.
	 * @throws SimpleSqlException
	 */
	public static Object queryForColumnValue(final Object connectionSource, final String sql, final int columnIndex,
	        final Object... params) throws SimpleSqlException {
		return query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql,
		        new ColumnValueFromFirstRowParser(columnIndex), params);
	}

	/**
	 *
	 * @return the column value from the first row of the result set. given.
	 * @throws SimpleSqlException
	 */
	public static Object queryForColumnValue(final Object connectionSource, final String sql, final int columnIndex)
	        throws SimpleSqlException {
		return query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql,
		        new ColumnValueFromFirstRowParser(columnIndex), (Object[]) null);
	}

	/**
	 *
	 * @return the column value from the first row of the result set. given.
	 * @throws SimpleSqlException
	 */
	public static Object queryForColumnValue(final Object connectionSource, final String sql, final String columnName)
	        throws SimpleSqlException {
		return query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql,
		        new ColumnValueFromFirstRowParser(columnName), (Object[]) null);
	}

	/**
	 *
	 * @param connectionSource
	 *            - a datasource or connection object
	 * @param sql
	 *            - the sql to execute
	 * @param resultSetParser
	 *            - the result set parser to use to parse the result set
	 * @param params
	 *            a list of params to pass to the sql
	 * @return the value parsed using the result set parser
	 * @throws SimpleSqlException
	 */
	public static <T> T query(final Object connectionSource, final String sql, final ResultSetParser<T> resultSetParser,
	        final Object... params) throws SimpleSqlException {
		return query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql,
		        resultSetParser, params);
	}

	public static <T> T query(final Object connectionSource, final String sql, final ResultSetParser<T> resultSetParser,
	        final List<Object> params) throws SimpleSqlException {
		return query(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource, sql,
		        resultSetParser, ArrayUtil.toObjectArray(params));
	}

	public static <T> T query(final Object connectionSource, final String sql, final ResultSetParser<T> resultSetParser,
	        final int resultSetType, final int resultSetConcurrency) throws SimpleSqlException {
		return query(resultSetType, resultSetConcurrency, QUERY_TIMEOUT, connectionSource, sql, resultSetParser,
		        (Object[]) null);
	}

	private static final <T> T query(final int resultSetType, final int resultSetConcurrency, final int queryTimeOut,
	        final Object connectionSource, final String sql, final ResultSetParser<T> resultSetParser,
	        final Object... params) throws SimpleSqlException {
		final String log = getLogString("query", resultSetType, resultSetConcurrency, queryTimeOut, sql, params);
		LogUtil.trace(log);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		long startTime = 0;

		try {
			con = getConnection(connectionSource);
			pstmt = con.prepareStatement(sql, resultSetType, resultSetConcurrency);
			pstmt.setQueryTimeout(queryTimeOut);

			if (params != null) {
				// set the param for the prepared statement
				for (int i = 0; i < params.length; i++) {
					setValueOrNull(pstmt, i + 1, params[i]);
				}
			}

			startTime = System.currentTimeMillis();
			rs = pstmt.executeQuery();
			final long timeToRun = (System.currentTimeMillis() - startTime);
			LogUtil.debug(new StringBuffer(log).append(" [run=").append(timeToRun).append("ms]").toString());

			if (resultSetParser != null) {
				return resultSetParser.parse(con, rs);
			}
			else {
				return null;
			}
		}
		catch (final SQLException sqle) {
			LogUtil.error("SQLException executing " + log + " " + sqle.getMessage());
			throw new SimpleSqlException("SQLException occurred " + sqle.getMessage(), sqle);
		}
		finally {
			close(rs);
			close(pstmt);
			close(connectionSource, con);
		}
	}

	/**
	 * Used to execute stored procedures
	 *
	 * @param connectionSource
	 * @param storedProcSql
	 *            the stored procedure call
	 * @return a list of results
	 * @throws SimpleSqlException
	 */
	public static List<OutParameter> call(final Object connectionSource, final String storedProcSql)
	        throws SimpleSqlException {
		return call(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource,
		        storedProcSql, (Object[]) null);
	}

	public static List<OutParameter> call(final Object connectionSource, final String storedProcSql,
	        final Object... params) throws SimpleSqlException {
		return call(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource,
		        storedProcSql, params);
	}

	public static List<OutParameter> call(final Object connectionSource, final String storedProcSql,
	        final List<Object> params) throws SimpleSqlException {
		return call(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, QUERY_TIMEOUT, connectionSource,
		        storedProcSql, ArrayUtil.toObjectArray(params));
	}

	private static final List<OutParameter> call(final int resultSetType, final int resultSetConcurrency,
	        final int queryTimeOut, final Object connectionSource, final String storedProcSql, final Object... params)
	        throws SimpleSqlException {
		final String log = getLogString("call", resultSetType, resultSetConcurrency, queryTimeOut, storedProcSql,
		        params);
		LogUtil.trace(log);

		CallableStatement cstmt = null;
		final ResultSet rs = null;
		Connection con = null;
		final List<OutParameter> outParams = new ArrayList<OutParameter>();
		long startTime = 0;

		try {
			con = getConnection(connectionSource);
			cstmt = con.prepareCall(storedProcSql, resultSetType, resultSetConcurrency);
			cstmt.setQueryTimeout(queryTimeOut);

			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					if (params[i] instanceof OutParameter) {
						final OutParameter out = (OutParameter) params[i];
						cstmt.registerOutParameter(i + 1, out.getType());
						out.setIndex(i + 1);
						outParams.add(out);
					}
					else if (params[i] instanceof NullParameter) {
						cstmt.setNull(i + 1, ((NullParameter) params[i]).getType());
					}
					else {
						setValueOrNull(cstmt, i + 1, params[i]);
					}
				}
			}

			startTime = System.currentTimeMillis();
			cstmt.execute();
			final long timeToRun = (System.currentTimeMillis() - startTime);
			LogUtil.debug(new StringBuffer(log).append(" [run=").append(timeToRun).append("ms]").toString());

			if (CollectionUtil.isNotEmpty(outParams)) {
				for (int i = 0; i < outParams.size(); i++) {
					final OutParameter out = outParams.get(i);
					out.setValue(cstmt);
				}
			}

			return outParams;
		}
		catch (final SQLException sqle) {
			LogUtil.trace("SQLException executing " + log + " " + sqle.getMessage());
			throw new SimpleSqlException("SQLException occurred " + sqle.getMessage(), sqle);
		}
		finally {
			close(rs);
			close(cstmt);
			close(connectionSource, con);
		}
	}

	public static Integer getIntegerObject(final ResultSet rs, final String columnName, final Integer defaultValue)
	        throws SQLException {
		return NumberUtil.toIntegerObject(rs.getObject(columnName), defaultValue);
	}

	public static void setValueOrNull(final PreparedStatement pstmt, final int index, final Object x)
	        throws SQLException {
		if (x != null) {
			setObject(pstmt, index, x);
		}
		else {
			setNull(pstmt, index, x);
		}
	}

	private static final void setObject(final PreparedStatement pstmt, final int index, final Object x)
	        throws SQLException {
		try {
			if (x instanceof Integer) {
				pstmt.setInt(index, (Integer) x);
			}
			else if (x instanceof Double) {
				pstmt.setDouble(index, (Double) x);
			}
			else if (x instanceof Float) {
				pstmt.setFloat(index, (Float) x);
			}
			else if (x instanceof Long) {
				pstmt.setLong(index, (Long) x);
			}
			else if (x instanceof Short) {
				pstmt.setLong(index, (Short) x);
			}
			else if (x instanceof BigDecimal) {
				pstmt.setBigDecimal(index, (BigDecimal) x);
			}
			else if (x instanceof Number) {
				pstmt.setInt(index, NumberUtil.toInteger(x));
			}
			else if (x instanceof Byte) {
				pstmt.setByte(index, (Byte) x);
			}
			else if (x instanceof byte[]) {
				pstmt.setBytes(index, (byte[]) x);
			}
			else if (x instanceof String) {
				pstmt.setString(index, StringUtil.toString(x));
			}
			else if (x instanceof Timestamp) {
				pstmt.setTimestamp(index, (Timestamp) x);
			}
			else if (x instanceof Time) {
				pstmt.setTime(index, (Time) x);
			}
			else if (x instanceof Date) {
				pstmt.setDate(index, new java.sql.Date(((Date) x).getTime()));
			}
			else if (x instanceof DateAndTimeZone) {
				final Timestamp t = new Timestamp(((DateAndTimeZone) x).getDate().getTime());
				pstmt.setTimestamp(index, t, Calendar.getInstance(((DateAndTimeZone) x).getTimezone()));
			}
			else if (x instanceof Array) {
				pstmt.setArray(index, (Array) x);
			}
			else if (x instanceof Clob) {
				pstmt.setClob(index, (Clob) x);
			}
			else if (x instanceof Blob) {
				pstmt.setBlob(index, (Blob) x);
			}
			else if (x instanceof URL) {
				pstmt.setURL(index, (URL) x);
			}
			else if (x instanceof InputStream) {
				pstmt.setBinaryStream(index, (InputStream) x);
			}
			else {
				pstmt.setObject(index, x, Types.OTHER);
			}
		}
		catch (final SQLException sqle) {
			final int errorCode = sqle.getErrorCode();
			if (errorCode == INVALID_CONVERSION_REQUESTED || errorCode == INVALID_COLUMN_TYPE) {
				// last ditch attempt to set the value
				// convert to a string and try setting
				pstmt.setObject(index, StringUtil.toString(x));
				LogUtil.warn(DBUtil.class,
				        "Unable to find correct type of " + x + " converting to a string and setting.");
				return;
			}
			throw sqle;
		}

	}

	private static final void setNull(final PreparedStatement pstmt, final int index, final Object x)
	        throws SQLException {
		for (final int element : TYPES) {
			try {
				pstmt.setNull(index, element);
				break;
			}
			catch (final SQLException sqle) {
				if (sqle.getErrorCode() != INVALID_COLUMN_TYPE) {
					throw sqle;
				}
			}
		}
	}

	private static final String getLogString(final String sqlType, final int resultSetType,
	        final int resultSetConcurrency, final int queryTimeOut, final String sql, final Object... params) {
		final StringBuffer buff = new StringBuffer();
		buff.append("DBUtil:");
		buff.append(sqlType);
		buff.append(" - sql=");
		buff.append(sql);
		buff.append(" params=");
		buff.append(ArrayUtil.toDelimitedString(params, "|"));
		if (resultSetType != -1) {
			buff.append(" [resultSetType=");
			buff.append(resultSetType);
		}

		if (resultSetConcurrency != -1) {
			buff.append(" resultSetConcurrency=");
			buff.append(resultSetConcurrency);
		}
		buff.append(" queryTimeOut=");
		buff.append(queryTimeOut);
		buff.append("]");
		return buff.toString();
	}
}