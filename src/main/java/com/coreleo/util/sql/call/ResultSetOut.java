package com.coreleo.util.sql.call;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.coreleo.util.sql.DBUtil;
import com.coreleo.util.sql.parser.ResultSetParser;

public class ResultSetOut extends SimpleOut {
	ResultSetParser<?> parser;

	public ResultSetOut(final int type, final ResultSetParser<?> parser) {
		super(type);
		this.parser = parser;
	}

	@Override
	public void setValue(final CallableStatement cstmt) throws SQLException {
		ResultSet rs = null;
		try {
			rs = (ResultSet) cstmt.getObject(super.index);
			value = parser.parse(cstmt.getConnection(), rs);
		}
		finally {
			DBUtil.close(rs);
		}
	}
}
