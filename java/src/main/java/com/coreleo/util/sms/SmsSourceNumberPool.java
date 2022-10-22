package com.coreleo.util.sms;

import java.sql.SQLException;

import com.coreleo.SimpleException;
import com.coreleo.util.LogUtil;
import com.coreleo.util.pool.ObjectPool;

/**
 *
 * @see ObjectPool
 *
 */

public class SmsSourceNumberPool extends ObjectPool<SmsSourceNumberWrapper> {

    public SmsSourceNumberPool(final String name, final String sourceNumbers) {
        super(name, 0, 0, 0, 0, Integer.MAX_VALUE);
    }

    /**
     * A wrapper around the checkout call. Used to implement the DataSource
     * interface. Internal use only.
     *
     * @return the connection
     * @throws SimpleException
     *
     */
    @Override
    public SmsSourceNumberWrapper checkOut() {
        LogUtil.trace(this, "checkOut");
        try {
            return super.checkOut();
        }
        catch (final Exception e) {
            LogUtil.error(this, "checkOut - Unable to borrow number from pool", e);
            throw new SimpleException("Generic exeption " + e.getMessage());
        }
    }

    // -------------------------------------------------------------------------------------------
    // Implementation of class ObjectPool
    // -------------------------------------------------------------------------------------------

    /**
     * Creates a new SmsSourceNumber object.
     */
    @Override
    protected SmsSourceNumberWrapper create() throws SQLException {
        LogUtil.trace(this, "create");
        return new SmsSourceNumberWrapper(this, "");
    }

    /**
     * Validates the number to check if it's usable before checking it out of
     * the pool
     *
     * @param sourceNumber
     *            - the source number to validate.
     */
    @Override
    protected boolean validate(final SmsSourceNumberWrapper sourceNumber) {
        return true;
    }

    /**
     * Does nothing
     */
    @Override
    protected void expire(final SmsSourceNumberWrapper sourceNumber) {
        LogUtil.trace(this, "expire");
    }

}
