package com.coreleo.util.sql;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.coreleo.util.LogUtil;
import com.coreleo.util.StringUtil;
import com.coreleo.util.pool.ObjectPool;

/**
 * 
 * @see ObjectPool
 * 
 */
public class DBConnectionPool extends ObjectPool<Connection> implements DataSource
{

	public final static int DEFAULT_VALIDATION_QUERY_TIMEOUT = 15; // 15 seconds
	private final int initialConnectionsInPool;
	private final String url, username, password;
	private final Driver driver;

	private String validationQuery;
	private int validationQueryTimeout = DEFAULT_VALIDATION_QUERY_TIMEOUT;

	public DBConnectionPool(String name, String driverClassName, String url, String usr, String pwd, long idleTimeOut, long connectionLife, long reapTime,
			int initialConnections, int minConnections, int maxConnections)
	{
		super(name, idleTimeOut, connectionLife, reapTime, minConnections, maxConnections);
		this.url = url;
		this.username = usr;
		this.password = pwd;
		this.initialConnectionsInPool = initialConnections;
		this.driver = DBUtil.registerDriver(driverClassName);

		try
		{
			super.initializeObjects(this.initialConnectionsInPool);
		}
		catch (final Exception e)
		{
			LogUtil.warn(this, "Unable to initialize the initial number of connections for pool " + super.getName(), e);
		}
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

	public String getUrl()
	{
		return this.url;
	}

	public int getInitialConnectionsInPool()
	{
		return this.initialConnectionsInPool;
	}

	public synchronized String getValidationQuery()
	{
		return validationQuery;
	}

	public synchronized void setValidationQuery(String validationQuery)
	{
		this.validationQuery = validationQuery;
	}

	public synchronized int getValidationQueryTimeout()
	{
		return validationQueryTimeout;
	}

	public synchronized void setValidationQueryTimeout(int seconds)
	{
		this.validationQueryTimeout = seconds;
	}

	/**
	 * A wrapper around the checkout call. Used to implement the DataSource interface. Internal use only.
	 * 
	 * @return the connection
	 * @throws SQLException
	 * 
	 */
	private Connection checkOutConnection() throws SQLException
	{
		LogUtil.trace(this, "DBConnectionPool:checkOutConnection");
		try
		{
			return super.checkOut();
		}
		catch (final SQLException sqle)
		{
			LogUtil.error(this, "DBConnectionPool:checkOutConnection - Unable to borrow connection from pool", sqle);
			throw sqle;
		}
		catch (final Exception e)
		{
			LogUtil.error(this, "DBConnectionPool:checkOutConnection - Unable to borrow connection from pool", e);
			throw new SQLException("Generic exeption (wrapped in SQLException) " + e.getMessage());
		}
	}

	@Override
	protected void finalize() throws Throwable
	{
		super.finalize();
		DBUtil.deregisterDriver(driver);
	}

	// -------------------------------------------------------------------------------------------
	// Implementation of class ObjectPool
	// -------------------------------------------------------------------------------------------

	/**
	 * Creates a new connection object.
	 */
	@Override
	protected Connection create() throws SQLException
	{
		LogUtil.trace(this, "DBConnectionPool:create");
		return new DBConnectionWrapper(this, (DriverManager.getConnection(url, username, password)));
	}

	/**
	 * Validates the connection before checking it out from the pool. This is done by executing a commit and/or executing a query,
	 * if a error is thrown, the connection is invalid. As of 2005, the query is the only guaranteed method of validating a
	 * connection.
	 * 
	 * @param obj
	 *            - the Connection to validate.
	 */
	@Override
	protected boolean validate(Connection con)
	{
		LogUtil.trace(this, "DBConnectionPool:validate");
		Statement stmt = null;
		try
		{
			DBUtil.commit(con); // validation 1

			if (!con.isClosed())
			{ // validation 2
				if (StringUtil.isNotEmpty(validationQuery))
				{ // validation 3
					stmt = con.createStatement();
					stmt.setQueryTimeout(validationQueryTimeout);
					LogUtil.trace(this, "DBConnectionPool:validationQuery=" + validationQuery);
					stmt.execute(validationQuery);
					DBUtil.close(stmt);
				}

				con.clearWarnings();
				con.setAutoCommit(true);

				LogUtil.trace(this, "DBConnectionPool:validate - true");
				return true;
			}
			else
			{
				LogUtil.trace(this, "DBConnectionPool:validate - false");
				return false;
			}
		}
		catch (final SQLException sqle)
		{
			LogUtil.warn(this, "DBConnectionPool:validate - SQL error, invalid connection, discarding from pool", sqle);
			LogUtil.trace(this, "DBConnectionPool:validate - false");
			return false;
		}
		catch (final Exception e)
		{
			LogUtil.warn(this, "DBConnectionPool:validate - Unknown error, invalid connection, discarding from pool", e);
			LogUtil.trace(this, "DBConnectionPool:validate - false");
			return false;
		}
		finally
		{
			DBUtil.close(stmt);
		}
	}

	/**
	 * Closes the connection.
	 */
	@Override
	protected void expire(Connection con)
	{
		LogUtil.trace(this, "DBConnectionPool:expire");

		if (con != null)
		{
			try
			{
				DBUtil.commit(con);
			}
			catch (final Exception e)
			{
				LogUtil.warn(this, "DBConnectionPool:expire - unable to commit connection before close", e);
			}

			try
			{
				((DBConnectionWrapper) con).closePhysicalConnection();
			}
			catch (final Exception e)
			{
				LogUtil.warn(this, "DBConnectionPool:expire - unable to close connection", e);
			}

		}
	}

	// -------------------------------------------------------------------------------------------
	// Implementation of interface DataSource
	// -------------------------------------------------------------------------------------------

	@Override
	public int getLoginTimeout() throws SQLException
	{
		throw new SQLException("Unsupported method - getLoginTimeout");
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException
	{
		throw new SQLException("Unsupported method - getLogWriter");
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException
	{
		throw new SQLException("Unsupported method - setLoginTimeout");
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException
	{
		throw new SQLException("Unsupported method - setLogWriter");
	}

	@Override
	public boolean isWrapperFor(Class iface) throws SQLException
	{
		return false;
	}

	@Override
	public Object unwrap(Class iface) throws SQLException
	{
		throw new SQLException("DBConnectionPool is not a wrapper.");
	}

	@Override
	public Connection getConnection() throws SQLException
	{
		return checkOutConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException
	{
		return checkOutConnection();
	}

	
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new SQLFeatureNotSupportedException();
	}
	
	

}
