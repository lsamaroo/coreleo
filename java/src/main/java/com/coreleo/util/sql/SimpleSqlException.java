package com.coreleo.util.sql;

import java.sql.SQLException;

import com.coreleo.SimpleException;

public class SimpleSqlException extends SimpleException {
    private static final long serialVersionUID = 1L;

    public SimpleSqlException() {
    }

    public SimpleSqlException(final String message) {
        super(message);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public SimpleSqlException(final Throwable cause) {
        super(cause);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public SimpleSqlException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public SimpleSqlException(final SQLException cause) {
        super(cause);
    }

    public SimpleSqlException(final String message, final SQLException cause) {
        super(message, cause);
    }

    public int getErrorCode() {
        if (super.getCause() instanceof SQLException) {
            return ((SQLException) super.getCause()).getErrorCode();
        }

        return 0;
    }

}
