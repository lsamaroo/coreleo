package com.coreleo.util.sql.call;

import java.sql.CallableStatement;
import java.sql.SQLException;

public interface OutParameter extends Parameter{
	
	public void setValue(CallableStatement cstmt) throws SQLException;
	
	public void setIndex( int index );

	public int getIndex();
	
	public Object getValue();
}
