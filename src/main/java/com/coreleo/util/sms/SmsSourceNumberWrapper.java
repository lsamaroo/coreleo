package com.coreleo.util.sms;

public class SmsSourceNumberWrapper implements SourceNumber {
    private final String sourceNumber;
    private final SmsSourceNumberPool pool;

    public SmsSourceNumberWrapper(final SmsSourceNumberPool pool, final String sourceNumber) {
        this.sourceNumber = sourceNumber;
        this.pool = pool;
    }

    // -------------------------------------------------------------------------------------------
    // Implementation of interface SourceNumber
    // -------------------------------------------------------------------------------------------

    @Override
    public void close() {
        // pool.checkIn(this);
    }

    @Override
    public boolean isValid() {
        return true;
    }

    public String getSourceNumber() {
        return sourceNumber;
    }

}
