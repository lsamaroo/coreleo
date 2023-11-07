package com.coreleo.util.date;

import static com.coreleo.util.date.DateUtil.after;
import static com.coreleo.util.date.DateUtil.before;
import static com.coreleo.util.date.DateUtil.toDate;
import static com.coreleo.util.date.DateUtil.today;
import static com.coreleo.util.date.DateUtil.tomorrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;




 class DateUtilTest {
    
    @Test
     void testCompareEqualDates() {
        Date date1 = new Date();
        Date date2 = new Date();

        int result = DateUtil.compare(date1, date2);

        assertEquals(0, result);
    }

    @Test
     void testCompareFirstDateNull() {
        Date date1 = null;
        Date date2 = new Date();

        int result = DateUtil.compare(date1, date2);

        assertEquals(-1, result);
    }

    @Test
     void testCompareSecondDateNull() {
        Date date1 = new Date();
        Date date2 = null;

        int result = DateUtil.compare(date1, date2);

        assertEquals(1, result);
    }

    @Test
     void testCompareBothDatesNull() {
        Date date1 = null;
        Date date2 = null;

        int result = DateUtil.compare(date1, date2);

        assertEquals(0, result);
    }

    @Test
     void testCompareDifferentDates() {
        Date date1 = new Date(2022, 1, 1);
        Date date2 = new Date(2023, 1, 1);

        int result = DateUtil.compare(date1, date2);

        assertTrue(result < 0);
    }
}
