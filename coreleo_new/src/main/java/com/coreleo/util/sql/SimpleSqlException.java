package com.coreleo.util.sql;


import java.sql.SQLException;

import com.coreleo.SimpleException;

public class SimpleSqlException extends SimpleException {
	private static final long serialVersionUID = 1L;

	public SimpleSqlException() {
		super();
	}
	

	public SimpleSqlException(String message) {
		super(message);
	}
	
    /**
     * @deprecated
     */
	public SimpleSqlException(Throwable cause) {
		super(cause);
	}

    /**
     * @deprecated
     */
	public SimpleSqlException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	public SimpleSqlException(SQLException cause) {
		super(cause);
	}
	
	
	public SimpleSqlException(String message, SQLException cause) {
		super(message, cause);
	}


	public int getErrorCode() {
		if( super.getCause() instanceof SQLException ){
			return ((SQLException) super.getCause()).getErrorCode();
		}
		
		return 0;
	}
	
	
	

}
