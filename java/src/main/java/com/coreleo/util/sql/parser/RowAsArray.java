/**
 *
 */
package com.coreleo.util.sql.parser;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.coreleo.util.sql.DBUtil;

/**
 *
 *
 *
 */
public class RowAsArray implements RowParser<Object[]> {
    private boolean clobValuesToString = true;

    public RowAsArray() {
        super();
    }

    /**
     * @return - an array containing the values of the row in the ResultsSet.
     *         Values are filled in via the getObject(x) method.
     */
    @Override
    public Object[] parse(final Connection con, final ResultSet rs, final int rowNum) throws SQLException {
        final ResultSetMetaData meta = rs.getMetaData();
        final int cols = meta.getColumnCount();
        final Object[] result = new Object[cols];

        for (int i = 0; i < cols; i++) {
            final Object value = DBUtil.getObject(meta, rs, i + 1, null);
            if (value instanceof Clob && clobValuesToString) {
                result[i] = DBUtil.clobToString(value);
            }
            else {
                result[i] = value;
            }
        }
        return result;
    }

    /**
     *
     * @return true if the sql Clob should be converted to a String when
     *         populating the bean
     */
    public boolean isClobValuesToString() {
        return clobValuesToString;
    }

    /**
     *
     * @param bool
     *            true to convert CLOB data type to Strings before adding to the
     *            map, false otherwise. Default is true
     */
    public void setClobValuesToString(final boolean bool) {
        this.clobValuesToString = bool;
    }

}
