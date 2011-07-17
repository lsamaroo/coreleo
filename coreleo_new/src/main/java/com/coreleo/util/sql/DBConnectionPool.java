package com.coreleo.util.sql;

import java.io.PrintWriter;
import java.sql.*;
import javax.sql.DataSource;
import com.coreleo.util.pool.ObjectPool;
import com.coreleo.util.*;

/**
 * 
 * @see ObjectPool
 * 
 */
public class DBConnectionPool extends ObjectPool implements DataSource {
	/**
	 * @deprecated - Will be removed from this class
	 */
	public final static String ORACLE_VALIDATION_QUERY = "SELECT 1 FROM DUAL"; 
	public final static int DEFAULT_VALIDATION_QUERY_TIMEOUT = 15; // 15 seconds
	private final int initialConnectionsInPool;
	private final String url, username, password;
	
	private String validationQuery;
	private int validationQueryTimeout = DEFAULT_VALIDATION_QUERY_TIMEOUT;
	private Driver driver;
	
	
	
	public DBConnectionPool(String name, String url, String usr, String pwd, long idleTimeOut, long connectionLife, long reapTime, int initialConnections, int minConnections, int maxConnections) {
		super(name, idleTimeOut, connectionLife, reapTime, minConnections, maxConnections);
		this.url = url;
		this.username = usr;
		this.password = pwd;
		this.initialConnectionsInPool = initialConnections;


		try {
			super.initializeObjects(this.initialConnectionsInPool);
		}
		catch (Exception e) {
			LogUtil.warn(this, "Unable to initialize the initial number of connections for " + name, e);
		}
	}
	
	
	public String getUsername() {
		return username;
	}


	
	public String getPassword() {
		return password;
	}


	public String getUrl() {
		return this.url;
	}


	public synchronized void setDriverClassName(String driverClassName) {
		driver = DBUtil.registerDriver(driverClassName);
	}	
	
	

	public int getInitialConnectionsInPool() {
		return this.initialConnectionsInPool;
	}

	
	
	public synchronized String getValidationQuery() {
		return validationQuery;
	}

	

	public synchronized void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}
	
	
	public synchronized int getValidationQueryTimeout() {
		return validationQueryTimeout;
	}


	public synchronized void setValidationQueryTimeout(int seconds) {
		this.validationQueryTimeout = seconds;
	}
	
	

	
	
	/**
	 * A wrapper around the checkout call.
	 * Used to implement the DataSource interface.
	 * Internal use only.
	 * 
	 * @return the connection
	 * @throws SQLException
	 * 
	 */
	private Connection checkOutConnection() throws SQLException {
		LogUtil.trace(this, "DBConnectionPool:checkOutConnection");
		try {
			return ((Connection) super.checkOut());
		}
		catch (SQLException sqle) {
			LogUtil.fatal(this, "DBConnectionPool:checkOutConnection - Unable to borrow connection from pool", sqle);
			throw sqle;
		}
		catch (Exception e) {
			LogUtil.fatal(this, "DBConnectionPool:checkOutConnection - Unable to borrow connection from pool", e);
			throw new SQLException("Generic exeption (wrapped in SQLException) " + e.getMessage());
		}
	}

	


	protected void finalize() throws Throwable {
		super.finalize();
		DBUtil.deregisterDriver(driver);
	}

	


    // -------------------------------------------------------------------------------------------
    // Implementation of class ObjectPool
    // -------------------------------------------------------------------------------------------

	
	/**
	 * Creates a new connection object.
	 */
	protected Object create() throws SQLException {
		LogUtil.trace(this, "DBConnectionPool:create");
		return new DBConnectionWrapper( this, (DriverManager.getConnection(url, username, password)) );
	}


	/**
	 * Validates the connection before checking it out from the pool. This is
	 * done by executing a commit and/or executing a query, if a error is thrown, the connection is
	 * invalid. As of 2005, the query is the only guaranteed method of validating a
	 * connection.
	 * 
	 * @param obj -
	 *            the Connection to validate.
	 */
	protected boolean validate(Object obj) {
		LogUtil.trace(this, "DBConnectionPool:validate");
		Statement stmt = null;
		try {
			Connection con = (Connection) obj;
			DBUtil.commit( con  ); // validation 1
			
			if (!con.isClosed()) { // validation 2
				if( StringUtil.isNotEmpty(validationQuery) ){ // validation 3
					stmt = con.createStatement();
					stmt.setQueryTimeout(validationQueryTimeout);
					LogUtil.trace(this, "DBConnectionPool:validationQuery=" + validationQuery );
					stmt.execute(validationQuery);
					DBUtil.close(stmt);
				}
				
				con.clearWarnings(); 
				con.setAutoCommit(true); 

				LogUtil.trace(this, "DBConnectionPool:validate - true");
				return true;
			}
			else {
				LogUtil.trace(this, "DBConnectionPool:validate - false");
				return false;
			}
		}
		catch (SQLException sqle) {
			LogUtil.warn(this, "DBConnectionPool:validate - SQL error, invalid connection, discarding from pool", sqle);
			LogUtil.trace(this, "DBConnectionPool:validate - false");
			return false;
		}
		catch (Exception e) {
			LogUtil.warn(this, "DBConnectionPool:validate - Unknown error, invalid connection, discarding from pool", e);
			LogUtil.trace(this, "DBConnectionPool:validate - false");
			return false;
		}
		finally {
			DBUtil.close(stmt);
		}
	}


	/**
	 * Closes the connection.
	 */
	protected void expire(Object obj) {
		LogUtil.trace(this, "DBConnectionPool:expire");

		if (obj != null) {
			try {
				DBUtil.commit((DBConnectionWrapper) obj);
			} 
			catch (Exception e) {
				LogUtil.warn( this, "DBConnectionPool:expire - unable to commit connection before close", e);
			}
			
			try {
				((DBConnectionWrapper) obj).closePhysicalConnection();
			} 
			catch (Exception e) {
				LogUtil.warn( this, "DBConnectionPool:expire - unable to close connection", e);
			}
		
		}
	}




    // -------------------------------------------------------------------------------------------
    // Implementation of interface DataSource
    // -------------------------------------------------------------------------------------------


	
	public int getLoginTimeout() throws SQLException {
		throw new SQLException("Unsupported method - getLoginTimeout");
	}


	
	public PrintWriter getLogWriter() throws SQLException {
		throw new SQLException("Unsupported method - getLogWriter");
	}


	
	public void setLoginTimeout(int seconds) throws SQLException {
		throw new SQLException("Unsupported method - setLoginTimeout");
	}


	
	public void setLogWriter(PrintWriter out) throws SQLException {
		throw new SQLException("Unsupported method - setLogWriter");
	}


	
	public boolean isWrapperFor(Class iface) throws SQLException {
		return false;
	}


	
	public Object unwrap(Class iface) throws SQLException {
		throw new SQLException("DBConnectionPool is not a wrapper.");
	}


	
	public Connection getConnection() throws SQLException {
		return checkOutConnection();
	}


	
	public Connection getConnection(String username, String password) throws SQLException {
		return checkOutConnection();
	}



	
}
