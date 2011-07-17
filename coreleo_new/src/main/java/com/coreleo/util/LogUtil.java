/**
 * 
 */
package com.coreleo.util;

import org.slf4j.*;



/**
 * 
 * LogHelper is a convenience class that extends the "ease of use" of the slf4j
 * API. It simply wraps the API in static calls so that the call to the logger becomes 
 * 1 line of code.  E.G.
 * 
 * LogUtil.debug( this, "Some msg" ); 
 * 
 * 
 * @author Leon Samaroo
 * 
 */
public class LogUtil {

    /* ******************************************************************** */
    /* ***************************DEBUG METHODS *************************** */
    /* ******************************************************************** */

	public final static void debug(String msg) {
		final Logger logger = getLogger(null);
		logger.debug(msg);
	}
	 
    public final static void debug(Object msg) {
        final Logger logger = getLogger(null);
        logger.debug( StringUtil.toString(msg));
    }

    public final static void debug(Throwable t) {
        final Logger logger = getLogger(null);
        logger.debug( "", t );
    }

    public final static void debug(String msg, Throwable t) {
		final Logger logger = getLogger(null);
		logger.debug(msg, t);
    }    
    
    public final static void debug(String format, Object... args) {
		final Logger logger = getLogger(null);
		logger.debug(format, args);
	}
	

    public final static void debug(Object src, String msg) {
        final Logger logger = getLogger(src);
        logger.debug( msg );
    }
    
    public final static void debug(Object src, Object msg) {
        final Logger logger = getLogger(src);
        logger.debug( StringUtil.toString(msg));
    }

    public final static void debug(Object src, Throwable t) {
        final Logger logger = getLogger(src);
        logger.debug( "", t);
    }
    
    public final static void debug(Object src, String msg, Throwable t) {	
        final Logger logger = getLogger(src);
        logger.debug( msg, t);
    }

    public final static void debug(Object src, Object msg, Throwable t) {	
        final Logger logger = getLogger(src);
        logger.debug( StringUtil.toString(msg), t);
    }

    public final static void debug(Object src, String format, Object... args) {
    	final Logger logger = getLogger(src);
		logger.debug(format, args);
	}

	
    /* ******************************************************************** */
    /* ***************************INFO METHODS **************************** */
    /* ******************************************************************** */

	public final static void info(String msg) {
		final Logger logger = getLogger(null);
		logger.info(msg);
	}
	 
    public final static void info(Object msg) {
        final Logger logger = getLogger(null);
        logger.info( StringUtil.toString(msg));
    }

    public final static void info(Throwable t) {
        final Logger logger = getLogger(null);
        logger.info( "", t );
    }

    public final static void info(String msg, Throwable t) {
		final Logger logger = getLogger(null);
		logger.info(msg, t);
    }    
    
    public final static void info(String format, Object... args) {
		final Logger logger = getLogger(null);
		logger.info(format, args);
	}
	
	
    public final static void info(Object src, String msg) {
        final Logger logger = getLogger(src);
        logger.info( msg );
    }
    
    public final static void info(Object src, Object msg) {
        final Logger logger = getLogger(src);
        logger.info( StringUtil.toString(msg));
    }

    public final static void info(Object src, Throwable t) {
        final Logger logger = getLogger(src);
        logger.info( "", t);
    }
    
    public final static void info(Object src, String msg, Throwable t) {	
        final Logger logger = getLogger(src);
        logger.info( msg, t);
    }

    public final static void info(Object src, Object msg, Throwable t) {	
        final Logger logger = getLogger(src);
        logger.info( StringUtil.toString(msg), t);
    }

    public final static void info(Object src, String format, Object... args) {
    	final Logger logger = getLogger(src);
		logger.info(format, args);
	}



    /* ******************************************************************** */
    /* ***************************ERROR METHODS *************************** */
    /* ******************************************************************** */


	public final static void error(String msg) {
		final Logger logger = getLogger(null);
		logger.error(msg);
	}
	 
    public final static void error(Object msg) {
        final Logger logger = getLogger(null);
        logger.error( StringUtil.toString(msg));
    }

    public final static void error(Throwable t) {
        final Logger logger = getLogger(null);
        logger.error( "", t );
    }

    public final static void error(String msg, Throwable t) {
		final Logger logger = getLogger(null);
		logger.error(msg, t);
    }    
    
    public final static void error(String format, Object... args) {
		final Logger logger = getLogger(null);
		logger.error(format, args);
	}
	

    public final static void error(Object src, String msg) {
        final Logger logger = getLogger(src);
        logger.error( msg );
    }
    
    public final static void error(Object src, Object msg) {
        final Logger logger = getLogger(src);
        logger.error( StringUtil.toString(msg));
    }

    public final static void error(Object src, Throwable t) {
        final Logger logger = getLogger(src);
        logger.error( "", t);
    }
    
    public final static void error(Object src, String msg, Throwable t) {	
        final Logger logger = getLogger(src);
        logger.error( msg, t);
    }

    public final static void error(Object src, Object msg, Throwable t) {	
        final Logger logger = getLogger(src);
        logger.error( StringUtil.toString(msg), t);
    }

    public final static void error(Object src, String format, Object... args ) {
    	final Logger logger = getLogger(src);
		logger.error(format, args );
	}


    /* ******************************************************************** */
    /* ***************************FATAL METHODS *************************** */
    /* ******************************************************************** */

    @Deprecated
    public static final void fatal(Object msg) {
		final Logger logger = getLogger(null);
		logger.error(StringUtil.toString(msg));
    }

    @Deprecated
    public static final void fatal(Throwable t) {
		final Logger logger = getLogger(null);
		logger.error("", t);
    }

    @Deprecated
    public static final void fatal(Object src, Object msg) {
		final Logger logger = getLogger(src);
		logger.error(StringUtil.toString(msg));
    }

    @Deprecated
    public static final void fatal(Object src, Throwable t) {
		final Logger logger = getLogger(src);
		logger.error( "", t);
    }

    @Deprecated
    public static final void fatal(Object src, Object msg, Throwable t) {
		final Logger logger = getLogger(src);
		logger.error(StringUtil.toString(msg), t);
    }


    /* ******************************************************************** */
    /* ***************************TRACE METHODS *************************** */
    /* ******************************************************************** */

	public final static void trace(String msg) {
		final Logger logger = getLogger(null);
		logger.trace(msg);
	}
	 
    public final static void trace(Object msg) {
        final Logger logger = getLogger(null);
        logger.trace( StringUtil.toString(msg));
    }

    public final static void trace(Throwable t) {
        final Logger logger = getLogger(null);
        logger.trace( "", t );
    }

    public final static void trace(String msg, Throwable t) {
		final Logger logger = getLogger(null);
		logger.trace(msg, t);
    }    
    
    public final static void trace(String format, Object... args) {
		final Logger logger = getLogger(null);
		logger.trace(format, args);
	}
	

    public final static void trace(Object src, String msg) {
        final Logger logger = getLogger(src);
        logger.trace( msg );
    }
    
    public final static void trace(Object src, Object msg) {
        final Logger logger = getLogger(src);
        logger.trace( StringUtil.toString(msg));
    }

    public final static void trace(Object src, Throwable t) {
        final Logger logger = getLogger(src);
        logger.trace( "", t);
    }
    
    public final static void trace(Object src, String msg, Throwable t) {	
        final Logger logger = getLogger(src);
        logger.trace( msg, t);
    }

    public final static void trace(Object src, Object msg, Throwable t) {	
        final Logger logger = getLogger(src);
        logger.trace( StringUtil.toString(msg), t);
    }

    public final static void trace(Object src, String format, Object... args ) {
    	final Logger logger = getLogger(src);
		logger.trace(format, args);
	}



    /* ******************************************************************** */
    /* ***************************WARN METHODS **************************** */
    /* ******************************************************************** */

	public final static void warn(String msg) {
		final Logger logger = getLogger(null);
		logger.warn(msg);
	}
	 
    public final static void warn(Object msg) {
        final Logger logger = getLogger(null);
        logger.warn( StringUtil.toString(msg));
    }

    public final static void warn(Throwable t) {
        final Logger logger = getLogger(null);
        logger.warn( "", t );
    }

    public final static void warn(String msg, Throwable t) {
		final Logger logger = getLogger(null);
		logger.warn(msg, t);
    }    
    
    public final static void warn(String format, Object... args) {
		final Logger logger = getLogger(null);
		logger.warn(format, args);
	}

    public final static void warn(Object src, String msg) {
        final Logger logger = getLogger(src);
        logger.warn( msg );
    }
    
    public final static void warn(Object src, Object msg) {
        final Logger logger = getLogger(src);
        logger.warn( StringUtil.toString(msg));
    }

    public final static void warn(Object src, Throwable t) {
        final Logger logger = getLogger(src);
        logger.warn( "", t);
    }
    
    public final static void warn(Object src, String msg, Throwable t) {	
        final Logger logger = getLogger(src);
        logger.warn( msg, t);
    }

    public final static void warn(Object src, Object msg, Throwable t) {	
        final Logger logger = getLogger(src);
        logger.warn( StringUtil.toString(msg), t);
    }

    public final static void warn(Object src, String format, Object... args ) {
    	final Logger logger = getLogger(src);
		logger.warn(format, args);
	}

        


    private static final Logger getLogger( Object src ){
    	if( src == null ){
    		return LoggerFactory.getLogger( LogUtil.class );
    	}
    	if( src instanceof Class ){
    		return LoggerFactory.getLogger( (Class<?>) src );
    	}
    	else{
    		return LoggerFactory.getLogger( src.getClass());
    	}        
    }
    
}
