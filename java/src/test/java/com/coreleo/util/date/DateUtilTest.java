package com.coreleo.util.date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DateUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCompare() {
		fail("Not yet implemented");
	}

	@Test
	public void testUtcTo() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUnixTimestamp() {
		fail("Not yet implemented");
	}

	@Test
	public void testCloneCalendar() {
		fail("Not yet implemented");
	}

	@Test
	public void testToISOCalendar() {
		fail("Not yet implemented");
	}

	@Test
	public void testToISODate() {
		fail("Not yet implemented");
	}

	@Test
	public void testFormat() {
		fail("Not yet implemented");
	}

	@Test
	public void testParseStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testParseStringStringTimeZone() {
		fail("Not yet implemented");
	}

	@Test
	public void testParseDateFormatString() {
		fail("Not yet implemented");
	}

	@Test
	public void testToTimestampCalendar() {
		fail("Not yet implemented");
	}

	@Test
	public void testToTimestampDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testYesterday() {
		fail("Not yet implemented");
	}

	@Test
	public void testTomorrow() {
		fail("Not yet implemented");
	}

	@Test
	public void testToday() {
		fail("Not yet implemented");
	}

	@Test
	public void testToCalendarDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetTimeCalendarIntIntIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetTimeDateIntIntIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testZeroOutTimeDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testZeroOutTimeCalendar() {
		fail("Not yet implemented");
	}

	@Test
	public void testToCalendarDateIntIntIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testToCalendarDateStringStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testToCalendarIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testToCalendarStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testToCalendarIntIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testToCalendarStringStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testToCalendarStringStringStringStringStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testToCalendarStringStringStringStringStringStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testToCalendarIntIntIntIntIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testToCalendarIntIntIntIntIntIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testToDateCalendar() {
		fail("Not yet implemented");
	}

	@Test
	public void testToDateIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testToDateStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testToDateIntIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testToDateStringStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testToDateIntIntIntIntIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testToDateStringStringStringStringStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testToDateIntIntIntIntIntIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testToDateStringStringStringStringStringStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsValidDateIntIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsValidDateStringStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDayCalendar() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDayDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetYearDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetYearCalendar() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMonthDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMonthCalendar() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetHourOfDayDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetHourOfDayCalendar() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMinuteDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMinuteCalendar() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSecondDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSecondCalendar() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetDateIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetCalendarIntInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetActualMaximum() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetActualMinimum() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetToLastDayOfMonthDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetToLastDayOfMonthCalendar() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNumberOfDaysBetweenCalendarCalendarBoolean() {
		final int addDays = 7;
		final Calendar start = Calendar.getInstance();
		final Calendar end = DateUtil.addDays(start, addDays);
		final int numOfDays = DateUtil.getNumberOfDaysBetween(start, end, false);
		assertEquals(addDays, numOfDays);
	}

	@Test
	public void testGetNumberOfDaysBetweenDateDateBoolean() {
		final int addDays = 7;
		final Date start = new Date();
		final Date end = DateUtil.addDays(start, addDays);
		final int numOfDays = DateUtil.getNumberOfDaysBetween(start, end, false);
		assertEquals(addDays, numOfDays);
	}

	@Test
	public void testGetDailyDatesBetween() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDaysBetweenCalendarCalendarBoolean() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDaysBetweenDateDateBoolean() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFormattedDaysBetweenCalendarCalendarBooleanFormat() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFormattedDaysBetweenDateDateBooleanFormat() {
		fail("Not yet implemented");
	}

	@Test
	public void testChangeDayOfWeekCalendarInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testChangeDayOfWeekDateInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNumberOfWeeksBetweenCalendarCalendar() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNumberOfWeeksBetweenDateDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetWeeksBetweenCalendarCalendarBoolean() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetWeeksBetweenDateDateBoolean() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFormattedWeeksBetweenCalendarCalendarBooleanFormat() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFormattedWeeksBetweenDateDateBooleanFormat() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetISOWeekOfYearCalendar() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetISOWeekOfYearDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetWeekOfYearCalendar() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetWeekOfYearDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNumberOfMonthsBetweenCalendarCalendar() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNumberOfMonthsBetweenDateDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMonthsBetweenCalendarCalendarBoolean() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMonthsBetweenDateDateBoolean() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFormattedMonthsBetweenCalendarCalendarBooleanFormat() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFormattedMonthsBetweenDateDateBooleanFormat() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNumberOfYearsBetweenCalendarCalendar() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNumberOfYearsBetweenDateDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetYearsBetweenCalendarCalendarBoolean() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetYearsBetweenDateDateBoolean() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFormattedYearsBetweenCalendarCalendarBooleanFormat() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFormattedYearsBetweenDateDateBooleanFormat() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetQuarterCalendarCalendar() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetQuarterDateDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetQuarterStartDatesCalendar() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetQuarterStartDatesDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetQuarterEndDatesCalendar() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetQuarterEndDatesDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOffset() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOffsetInHoursBetweenTimeZonesDateTimeZoneTimeZone() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOffsetInHoursBetweenTimeZonesDateStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOffsetInHoursBetweenTimeZonesDateTimeZoneDateTimeZone() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOffsetInHoursBetweenTimeZonesDateStringDateString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOffsetInHoursBetweenTimeZonesStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOffsetInHoursBetweenTimeZonesTimeZoneString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOffsetInHoursBetweenTimeZonesStringTimeZone() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOffsetInHoursBetweenTimeZonesTimeZoneTimeZone() {
		fail("Not yet implemented");
	}

	@Test
	public void testSubtractDaysDateInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testSubtractDaysCalendarInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddSecondsDateInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddSecondsCalendarInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddMinutesDateInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddMinutesCalendarInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddHoursDateInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddHoursCalendarInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddDaysDateInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddDaysCalendarInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddWeeksDateInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddWeeksCalendarInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddMonthsDateInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddMonthsCalendarInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddYearsDateInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddYearsCalendarInt() {
		fail("Not yet implemented");
	}

}
