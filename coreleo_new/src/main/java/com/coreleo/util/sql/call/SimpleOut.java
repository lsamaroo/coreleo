package com.coreleo.util.sql.call;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import com.coreleo.util.NumberUtil;
import com.coreleo.util.StringUtil;

public class SimpleOut implements OutParameter {
	protected int index;
	protected int type;
	protected Object value;

	
	public int getIndex() {
		return index;
	}

	public void setType(int type) {
		this.type = type;
	}

	public SimpleOut( int type ){
		this.type = type;
	}
	
	public int getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}

	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public void setValue(CallableStatement cstmt) throws SQLException {
		if (type == Types.DOUBLE) {
			value = NumberUtil.toDoubleObject(cstmt.getDouble(index));
		}
		else if (type == Types.FLOAT) {
			value = NumberUtil.toFloatObject(cstmt.getFloat(index));
		} 
		else if (NumberUtil.equalsToAny(type, new int[] { Types.INTEGER, Types.BIGINT, Types.TINYINT, Types.NUMERIC })) {
			value = NumberUtil.toIntegerObject(cstmt.getInt(index));
		} 
		else if ( NumberUtil.equalsToAny(type, new int[] { Types.VARCHAR, Types.LONGVARCHAR, Types.CHAR}) ) {
			value = StringUtil.toString(cstmt.getString(index));
		}
		else if ( NumberUtil.equalsToAny(type, new int[] { Types.DATE, Types.TIMESTAMP }) ) {
			value = (cstmt.getTimestamp(index));
		}	
		else{
			value = cstmt.getObject(index);
		}
	}


	public String toString() {
		return "OUT";
	}
	
	
}
