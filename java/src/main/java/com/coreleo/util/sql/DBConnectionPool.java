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

import com.coreleo.util.ArrayUtil;
import com.coreleo.util.LogUtil;
import com.coreleo.util.StringUtil;
import com.coreleo.util.pool.ObjectPool;

/**
 *
 * @see ObjectPool
 *
 */

public class DBConnectionPool extends ObjectPool<Connection> implements DataSource {

    public static final int DEFAULT_VALIDATION_QUERY_TIMEOUT = 15; // 15 seconds
    private final int initialConnectionsInPool;
    private String url;
    private String username;
    private String password;
    private Driver driver;
    private String[] executeSqlOnCreate;

    private String validationQuery;
    private int validationQueryTimeout = DEFAULT_VALIDATION_QUERY_TIMEOUT;
    private String driverClassName;

    public DBConnectionPool() {
        super(18000, 18000, 21000, 0, 0);
        this.initialConnectionsInPool = 0;
    }

    public DBConnectionPool(final String name, final String driverClassName, final String url, final String usr,
            final String pwd, final int maxConnections) {
        this(name, driverClassName, url, usr, pwd, 18000, 18000, 21000, 0, 0, maxConnections, null);
    }

    public DBConnectionPool(final String name, final String driverClassName, final String url, final String usr,
            final String pwd, final long idleTimeOut, final long connectionLife, final long reapTime,
            final int initialConnections, final int minConnections, final int maxConnections) {
        this(name, driverClassName, url, usr, pwd, idleTimeOut, connectionLife, reapTime, initialConnections,
                minConnections, maxConnections, null);
    }

    public DBConnectionPool(final String name, final String driverClassName, final String url, final String usr,
            final String pwd, final long idleTimeOut, final long connectionLife, final long reapTime,
            final int initialConnections, final int minConnections, final int maxConnections,
            final String[] executeSqlOnCreate) {
        super(name, idleTimeOut, connectionLife, reapTime, minConnections, maxConnections);
        this.url = url;
        this.username = usr;
        this.password = pwd;
        this.initialConnectionsInPool = initialConnections;
        this.setDriverClassName(driverClassName);
        this.executeSqlOnCreate = executeSqlOnCreate;
        init();
    }

    private void init() {
        try {
            super.initializeObjects(this.initialConnectionsInPool);
        } catch (final Exception e) {
            LogUtil.warn(this, "Unable to initialize the initial number of connections for pool " + super.getName(), e);
        }
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUrl() {
        return this.url;
    }

    public String getDriverClassName() {
        return this.driverClassName;
    }

    public void setDriverClassName(final String driverClassName) {
        this.driverClassName = driverClassName;
        this.driver = DBUtil.registerDriver(this.driverClassName);
    }

    public void setDriverClass(final String driverClassName) {
        setDriverClassName(driverClassName);
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setUser(final String username) {
        this.setUsername(username);
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public int getInitialConnectionsInPool() {
        return this.initialConnectionsInPool;
    }

    public synchronized String getValidationQuery() {
        return this.validationQuery;
    }

    public synchronized void setValidationQuery(final String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public synchronized int getValidationQueryTimeout() {
        return this.validationQueryTimeout;
    }

    public synchronized void setValidationQueryTimeout(final int seconds) {
        this.validationQueryTimeout = seconds;
    }

    /**
     * A wrapper around the checkout call. Used to implement the DataSource
     * interface. Internal use only.
     *
     * @return the connection
     * @throws SQLException
     *
     */
    private Connection checkOutConnection() throws SQLException {
        LogUtil.trace(this, "DBConnectionPool:checkOutConnection");
        try {
            return super.checkOut();
        } catch (final SQLException sqle) {
            LogUtil.error(this, "DBConnectionPool:checkOutConnection - Unable to borrow connection from pool", sqle);
            throw sqle;
        } catch (final Exception e) {
            LogUtil.error(this, "DBConnectionPool:checkOutConnection - Unable to borrow connection from pool", e);
            throw new SQLException("Generic exeption (wrapped in SQLException) " + e.getMessage());
        }
    }

    @Override
    public synchronized void close() {
        super.close();
        DBUtil.deregisterDriver(this.driver);
    }

    // -------------------------------------------------------------------------------------------
    // Implementation of class ObjectPool
    // -------------------------------------------------------------------------------------------

    /**
     * Creates a new connection object.
     */
    @Override
    protected Connection create() throws SQLException {
        LogUtil.trace(this, "DBConnectionPool:create");
        final var con = DriverManager.getConnection(this.url, this.username, this.password);
        if (ArrayUtil.isNotEmpty(this.executeSqlOnCreate)) {
            for (final String sql : this.executeSqlOnCreate) {
                try {
                    DBUtil.update(con, sql);
                } catch (final SimpleSqlException sqle) {
                    LogUtil.warn(this, sqle);
                }
            }
        }
        return new DBConnectionWrapper(this, con);
    }

    /**
     * Validates the connection before checking it out from the pool. This is done
     * by executing a commit and/or executing a query, if a error is thrown, the
     * connection is invalid. As of 2005, the query is the only guaranteed method of
     * validating a connection.
     *
     * @param con - the Connection to validate.
     */
    @Override
    protected boolean validate(final Connection con) {
        LogUtil.trace(this, "DBConnectionPool:validate");
        if (con == null) {
            return false;
        }

        Statement stmt = null;
        try {

            DBUtil.turnOffAutoCommit(con);
            DBUtil.commit(con); // validation 1

            if (!con.isValid(this.validationQueryTimeout) || con.isClosed()) {
                LogUtil.trace(this, "DBConnectionPool:validate - false");
                return false;
            }
            // 2
            if (StringUtil.isNotEmpty(this.validationQuery)) { // validation 3
                stmt = con.createStatement();
                stmt.setQueryTimeout(this.validationQueryTimeout);
                LogUtil.trace(this, "DBConnectionPool:validationQuery=" + this.validationQuery);
                stmt.execute(this.validationQuery);
                DBUtil.close(stmt);
            }

            con.clearWarnings();
            DBUtil.turnOnAutoCommit(con);

            LogUtil.trace(this, "DBConnectionPool:validate - true");
            return true;
        } catch (final SQLException sqle) {
            LogUtil.debug(this, "DBConnectionPool:validate - SQL error, invalid connection", sqle);
            return false;
        } catch (final Exception e) {
            LogUtil.debug(this, "DBConnectionPool:validate - Unknown error, invalid connection", e);
            return false;
        } finally {
            DBUtil.close(stmt);
        }
    }

    /**
     * Closes the connection.
     */
    @Override
    protected void expire(final Connection con) {
        LogUtil.trace(this, "DBConnectionPool:expire");

        if (con != null) {
            try {
                DBUtil.turnOffAutoCommit(con);
                DBUtil.commit(con);
                DBUtil.turnOnAutoCommit(con);
            } catch (final Exception e) {
                LogUtil.debug(this, "DBConnectionPool:expire - unable to commit connection before close", e);
            }

            try {
                ((DBConnectionWrapper) con).closePhysicalConnection();
            } catch (final Exception e) {
                LogUtil.debug(this, "DBConnectionPool:expire - unable to close connection", e);
            }

        }
    }

    // -------------------------------------------------------------------------------------------
    // Implementation of interface DataSource
    // -------------------------------------------------------------------------------------------

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new SQLException("Unsupported method - getLoginTimeout");
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new SQLException("Unsupported method - getLogWriter");
    }

    @Override
    public void setLoginTimeout(final int seconds) throws SQLException {
        throw new SQLException("Unsupported method - setLoginTimeout");
    }

    @Override
    public void setLogWriter(final PrintWriter out) throws SQLException {
        throw new SQLException("Unsupported method - setLogWriter");
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean isWrapperFor(final Class iface) throws SQLException {
        return false;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Object unwrap(final Class iface) throws SQLException {
        throw new SQLException("DBConnectionPool is not a wrapper.");
    }

    @Override
    public Connection getConnection() throws SQLException {
        return checkOutConnection();
    }

    @Override
    public Connection getConnection(final String username, final String password) throws SQLException {
        return checkOutConnection();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
    }

    public String[] getExecuteSqlOnCreate() {
        return this.executeSqlOnCreate;
    }

    public void setExecuteSqlOnCreate(final String[] executeSqlOnCreate) {
        this.executeSqlOnCreate = executeSqlOnCreate;
    }

}
