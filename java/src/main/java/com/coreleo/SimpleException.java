/**
 * 
 */
package com.coreleo;


/**
 * 
 * @author Leon Samaroo
 * 
 */
public class SimpleException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public SimpleException() {
        super();
    }


    public SimpleException(String message) {
        super(message);
    }


    public SimpleException(Throwable cause) {
    	super( cause != null ? cause.getMessage(): "" );
        initCause(cause);
    }


    public SimpleException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }


	public synchronized Throwable initCause(Throwable cause) {
		if( cause instanceof SimpleException ){
			Throwable t = cause.getCause();
			if( t != null ){
				cause = t;
			}
		}

		return super.initCause(cause);
	}
    
    

}
