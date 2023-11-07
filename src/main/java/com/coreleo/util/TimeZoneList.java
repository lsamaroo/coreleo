package com.coreleo.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class TimeZoneList {

	private static final List<TimeZoneMapping> ZONEMAPPINGS = new ArrayList<TimeZoneMapping>();
	private static final Map<String, String> WINDOWSSTANDARDTIMEZONENAMES_TO_JAVA_TIMEZONE_IDS = new HashMap<String, String>();
	private static final Map<String, String> JAVA_TIMEZONE_IDS_TO_WINDOWSSTANDARDTIMEZONENAMES = new HashMap<String, String>();

	static {
		ZONEMAPPINGS.add(new TimeZoneMapping("Afghanistan Standard Time", "Asia/Kabul", "(UTC +04:30) Kabul"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Alaskan Standard Time", "America/Anchorage", "(UTC -09:00) Alaska"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Arab Standard Time", "Asia/Riyadh", "(UTC +03:00) Kuwait, Riyadh"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Arabian Standard Time", "Asia/Dubai", "(UTC +04:00) Abu Dhabi, Muscat"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Arabic Standard Time", "Asia/Baghdad", "(UTC +03:00) Baghdad"));
		ZONEMAPPINGS.add(
		        new TimeZoneMapping("Argentina Standard Time", "America/Buenos_Aires", "(UTC -03:00) Buenos Aires"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Atlantic Standard Time", "America/Halifax",
		        "(UTC -04:00) Atlantic Time (Canada)"));
		ZONEMAPPINGS.add(new TimeZoneMapping("AUS Central Standard Time", "Australia/Darwin", "(UTC +09:30) Darwin"));
		ZONEMAPPINGS.add(new TimeZoneMapping("AUS Eastern Standard Time", "Australia/Sydney",
		        "(UTC +10:00) Canberra, Melbourne, Sydney"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Azerbaijan Standard Time", "Asia/Baku", "(UTC +04:00) Baku"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Azores Standard Time", "Atlantic/Azores", "(UTC -01:00) Azores"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Bangladesh Standard Time", "Asia/Dhaka", "(UTC +06:00) Dhaka"));
		ZONEMAPPINGS.add(
		        new TimeZoneMapping("Canada Central Standard Time", "America/Regina", "(UTC -06:00) Saskatchewan"));
		ZONEMAPPINGS.add(
		        new TimeZoneMapping("Cape Verde Standard Time", "Atlantic/Cape_Verde", "(UTC -01:00) Cape Verde Is."));
		ZONEMAPPINGS.add(new TimeZoneMapping("Caucasus Standard Time", "Asia/Yerevan", "(UTC +04:00) Yerevan"));
		ZONEMAPPINGS.add(
		        new TimeZoneMapping("Cen. Australia Standard Time", "Australia/Adelaide", "(UTC +09:30) Adelaide"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Central America Standard Time", "America/Guatemala",
		        "(UTC -06:00) Central America"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Central Asia Standard Time", "Asia/Almaty", "(UTC +06:00) Astana"));
		ZONEMAPPINGS
		        .add(new TimeZoneMapping("Central Brazilian Standard Time", "America/Cuiaba", "(UTC -04:00) Cuiaba"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Central Europe Standard Time", "Europe/Budapest",
		        "(UTC +01:00) Belgrade, Bratislava, Budapest, Ljubljana, Prague"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Central European Standard Time", "Europe/Warsaw",
		        "(UTC +01:00) Sarajevo, Skopje, Warsaw, Zagreb"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Central Pacific Standard Time", "Pacific/Guadalcanal",
		        "(UTC +11:00) Solomon Is., New Caledonia"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Central Standard Time (Mexico)", "America/Mexico_City",
		        "(UTC -06:00) Guadalajara, Mexico City, Monterrey"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Central Standard Time", "America/Chicago",
		        "(UTC -06:00) Central Time (US & Canada)"));
		ZONEMAPPINGS.add(new TimeZoneMapping("China Standard Time", "Asia/Shanghai",
		        "(UTC +08:00) Beijing, Chongqing, Hong Kong, Urumqi"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Dateline Standard Time", "Etc/UTC+12",
		        "(UTC -12:00) International Date Line West"));
		ZONEMAPPINGS.add(new TimeZoneMapping("E. Africa Standard Time", "Africa/Nairobi", "(UTC +03:00) Nairobi"));
		ZONEMAPPINGS
		        .add(new TimeZoneMapping("E. Australia Standard Time", "Australia/Brisbane", "(UTC +10:00) Brisbane"));
		ZONEMAPPINGS.add(new TimeZoneMapping("E. Europe Standard Time", "Europe/Minsk", "(UTC +02:00) Minsk"));
		ZONEMAPPINGS.add(
		        new TimeZoneMapping("E. South America Standard Time", "America/Sao_Paulo", "(UTC -03:00) Brasilia"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Eastern Standard Time", "America/New_York",
		        "(UTC -05:00) Eastern Time (US & Canada)"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Egypt Standard Time", "Africa/Cairo", "(UTC +02:00) Cairo"));
		ZONEMAPPINGS.add(
		        new TimeZoneMapping("Ekaterinburg Standard Time", "Asia/Yekaterinburg", "(UTC +05:00) Ekaterinburg"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Fiji Standard Time", "Pacific/Fiji", "(UTC +12:00) Fiji, Marshall Is."));
		ZONEMAPPINGS.add(new TimeZoneMapping("FLE Standard Time", "Europe/Kiev",
		        "(UTC +02:00) Helsinki, Kyiv, Riga, Sofia, Tallinn, Vilnius"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Georgian Standard Time", "Asia/Tbilisi", "(UTC +04:00) Tbilisi"));
		ZONEMAPPINGS.add(
		        new TimeZoneMapping("UTC Standard Time", "Europe/London", "(UTC) Dublin, Edinburgh, Lisbon, London"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Greenland Standard Time", "America/Godthab", "(UTC -03:00) Greenland"));
		ZONEMAPPINGS
		        .add(new TimeZoneMapping("Greenwich Standard Time", "Atlantic/Reykjavik", "(UTC) Monrovia, Reykjavik"));
		ZONEMAPPINGS.add(new TimeZoneMapping("GTB Standard Time", "Europe/Istanbul",
		        "(UTC +02:00) Athens, Bucharest, Istanbul"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Hawaiian Standard Time", "Pacific/Honolulu", "(UTC -10:00) Hawaii"));
		ZONEMAPPINGS.add(new TimeZoneMapping("India Standard Time", "Asia/Calcutta",
		        "(UTC +05:30) Chennai, Kolkata, Mumbai, New Delhi"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Iran Standard Time", "Asia/Tehran", "(UTC +03:30) Tehran"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Israel Standard Time", "Asia/Jerusalem", "(UTC +02:00) Jerusalem"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Jordan Standard Time", "Asia/Amman", "(UTC +02:00) Amman"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Kamchatka Standard Time", "Asia/Kamchatka",
		        "(UTC +12:00) Petropavlovsk-Kamchatsky - Old"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Korea Standard Time", "Asia/Seoul", "(UTC +09:00) Seoul"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Magadan Standard Time", "Asia/Magadan", "(UTC +11:00) Magadan"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Mauritius Standard Time", "Indian/Mauritius", "(UTC +04:00) Port Louis"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Mid-Atlantic Standard Time", "Etc/UTC+2", "(UTC -02:00) Mid-Atlantic"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Middle East Standard Time", "Asia/Beirut", "(UTC +02:00) Beirut"));
		ZONEMAPPINGS
		        .add(new TimeZoneMapping("Montevideo Standard Time", "America/Montevideo", "(UTC -03:00) Montevideo"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Morocco Standard Time", "Africa/Casablanca", "(UTC) Casablanca"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Mountain Standard Time (Mexico)", "America/Chihuahua",
		        "(UTC -07:00) Chihuahua, La Paz, Mazatlan"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Mountain Standard Time", "America/Denver",
		        "(UTC -07:00) Mountain Time (US & Canada)"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Myanmar Standard Time", "Asia/Rangoon", "(UTC +06:30) Yangon (Rangoon)"));
		ZONEMAPPINGS.add(
		        new TimeZoneMapping("N. Central Asia Standard Time", "Asia/Novosibirsk", "(UTC +06:00) Novosibirsk"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Namibia Standard Time", "Africa/Windhoek", "(UTC +02:00) Windhoek"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Nepal Standard Time", "Asia/Katmandu", "(UTC +05:45) Kathmandu"));
		ZONEMAPPINGS.add(new TimeZoneMapping("New Zealand Standard Time", "Pacific/Auckland",
		        "(UTC +12:00) Auckland, Wellington"));
		ZONEMAPPINGS.add(
		        new TimeZoneMapping("Newfoundland Standard Time", "America/St_Johns", "(UTC -03:30) Newfoundland"));
		ZONEMAPPINGS.add(new TimeZoneMapping("North Asia East Standard Time", "Asia/Irkutsk", "(UTC +08:00) Irkutsk"));
		ZONEMAPPINGS
		        .add(new TimeZoneMapping("North Asia Standard Time", "Asia/Krasnoyarsk", "(UTC +07:00) Krasnoyarsk"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Pacific SA Standard Time", "America/Santiago", "(UTC -04:00) Santiago"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Pacific Standard Time (Mexico)", "America/Tijuana",
		        "(UTC -08:00) Baja California"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Pacific Standard Time", "America/Los_Angeles",
		        "(UTC -08:00) Pacific Time (US & Canada)"));
		ZONEMAPPINGS
		        .add(new TimeZoneMapping("Pakistan Standard Time", "Asia/Karachi", "(UTC +05:00) Islamabad, Karachi"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Paraguay Standard Time", "America/Asuncion", "(UTC -04:00) Asuncion"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Romance Standard Time", "Europe/Paris",
		        "(UTC +01:00) Brussels, Copenhagen, Madrid, Paris"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Russian Standard Time", "Europe/Moscow",
		        "(UTC +03:00) Moscow, St. Petersburg, Volgograd"));
		ZONEMAPPINGS.add(
		        new TimeZoneMapping("SA Eastern Standard Time", "America/Cayenne", "(UTC -03:00) Cayenne, Fortaleza"));
		ZONEMAPPINGS.add(
		        new TimeZoneMapping("SA Pacific Standard Time", "America/Bogota", "(UTC -05:00) Bogota, Lima, Quito"));
		ZONEMAPPINGS.add(new TimeZoneMapping("SA Western Standard Time", "America/La_Paz",
		        "(UTC -04:00) Georgetown, La Paz, Manaus, San Juan"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Samoa Standard Time", "Pacific/Apia", "(UTC -11:00) Samoa"));
		ZONEMAPPINGS.add(
		        new TimeZoneMapping("SE Asia Standard Time", "Asia/Bangkok", "(UTC +07:00) Bangkok, Hanoi, Jakarta"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Singapore Standard Time", "Asia/Singapore",
		        "(UTC +08:00) Kuala Lumpur, Singapore"));
		ZONEMAPPINGS.add(new TimeZoneMapping("South Africa Standard Time", "Africa/Johannesburg",
		        "(UTC +02:00) Harare, Pretoria"));
		ZONEMAPPINGS.add(
		        new TimeZoneMapping("Sri Lanka Standard Time", "Asia/Colombo", "(UTC +05:30) Sri Jayawardenepura"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Syria Standard Time", "Asia/Damascus", "(UTC +02:00) Damascus"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Taipei Standard Time", "Asia/Taipei", "(UTC +08:00) Taipei"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Tasmania Standard Time", "Australia/Hobart", "(UTC +10:00) Hobart"));
		ZONEMAPPINGS
		        .add(new TimeZoneMapping("Tokyo Standard Time", "Asia/Tokyo", "(UTC +09:00) Osaka, Sapporo, Tokyo"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Tonga Standard Time", "Pacific/Tongatapu", "(UTC +13:00) Nuku'alofa"));
		ZONEMAPPINGS
		        .add(new TimeZoneMapping("Ulaanbaatar Standard Time", "Asia/Ulaanbaatar", "(UTC +08:00) Ulaanbaatar"));
		ZONEMAPPINGS.add(
		        new TimeZoneMapping("US Eastern Standard Time", "America/Indianapolis", "(UTC -05:00) Indiana (East)"));
		ZONEMAPPINGS.add(new TimeZoneMapping("US Mountain Standard Time", "America/Phoenix", "(UTC -07:00) Arizona"));
		ZONEMAPPINGS.add(new TimeZoneMapping("UTC ", "Etc/UTC", "(UTC) Coordinated Universal Time"));
		ZONEMAPPINGS.add(new TimeZoneMapping("UTC +12", "Etc/UTC-12", "(UTC +12:00) Coordinated Universal Time+12"));
		ZONEMAPPINGS.add(new TimeZoneMapping("UTC -02", "Etc/UTC+2", "(UTC -02:00) Coordinated Universal Time-02"));
		ZONEMAPPINGS.add(new TimeZoneMapping("UTC -11", "Etc/UTC+11", "(UTC -11:00) Coordinated Universal Time-11"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Venezuela Standard Time", "America/Caracas", "(UTC -04:30) Caracas"));
		ZONEMAPPINGS
		        .add(new TimeZoneMapping("Vladivostok Standard Time", "Asia/Vladivostok", "(UTC +10:00) Vladivostok"));
		ZONEMAPPINGS.add(new TimeZoneMapping("W. Australia Standard Time", "Australia/Perth", "(UTC +08:00) Perth"));
		ZONEMAPPINGS.add(new TimeZoneMapping("W. Central Africa Standard Time", "Africa/Lagos",
		        "(UTC +01:00) West Central Africa"));
		ZONEMAPPINGS.add(new TimeZoneMapping("W. Europe Standard Time", "Europe/Berlin",
		        "(UTC +01:00) Amsterdam, Berlin, Bern, Rome, Stockholm, Vienna"));
		ZONEMAPPINGS.add(new TimeZoneMapping("West Asia Standard Time", "Asia/Tashkent", "(UTC +05:00) Tashkent"));
		ZONEMAPPINGS.add(new TimeZoneMapping("West Pacific Standard Time", "Pacific/Port_Moresby",
		        "(UTC +10:00) Guam, Port Moresby"));
		ZONEMAPPINGS.add(new TimeZoneMapping("Yakutsk Standard Time", "Asia/Yakutsk", "(UTC +09:00) Yakutsk"));
	}

	private static final TimeZoneList INSTANCE = new TimeZoneList();

	public static final TimeZoneList getInstance() {
		return INSTANCE;
	}

	private final List<TimeZoneWithDisplayNames> timeZones = new ArrayList<TimeZoneWithDisplayNames>();

	private TimeZoneList() {
		final HashSet<String> availableIdsSet = new HashSet<String>();
		for (final String availableId : TimeZone.getAvailableIDs()) {
			availableIdsSet.add(availableId);
		}
		for (final TimeZoneMapping zoneMapping : ZONEMAPPINGS) {
			final String id = zoneMapping.getOlsonName();
			if (availableIdsSet.contains(id)) {
				final TimeZone timeZone = TimeZone.getTimeZone(id);
				timeZones.add(new TimeZoneWithDisplayNames(timeZone, zoneMapping.getWindowsDisplayName(),
				        zoneMapping.getWindowsStandardName()));
				WINDOWSSTANDARDTIMEZONENAMES_TO_JAVA_TIMEZONE_IDS.put(zoneMapping.getWindowsStandardName(),
				        timeZone.getID());
				JAVA_TIMEZONE_IDS_TO_WINDOWSSTANDARDTIMEZONENAMES.put(timeZone.getID(),
				        zoneMapping.getWindowsStandardName());
			}
			else {
				LogUtil.trace("TimeZoneList: Unknown ID [" + id + "]");
			}
		}
		Collections.sort(timeZones, new Comparator<TimeZoneWithDisplayNames>() {
			@Override
			public int compare(final TimeZoneWithDisplayNames a, final TimeZoneWithDisplayNames b) {
				final int diff = a.getTimeZone().getRawOffset() - b.getTimeZone().getRawOffset();
				if (diff < 0) {
					return -1;
				}
				else if (diff > 0) {
					return 1;
				}
				else {
					return a.getDisplayName().compareTo(b.getDisplayName());
				}
			}
		});
	}

	public List<TimeZoneWithDisplayNames> getTimeZones() {
		return timeZones;
	}

	public String getJavaTimeZoneId(final String windowStandardName) {
		return WINDOWSSTANDARDTIMEZONENAMES_TO_JAVA_TIMEZONE_IDS.get(windowStandardName);
	}

	public String getWindowsStandardName(final String javaTimeZoneId) {
		return JAVA_TIMEZONE_IDS_TO_WINDOWSSTANDARDTIMEZONENAMES.get(javaTimeZoneId);
	}

	/**
	 * Get this hosts default time zone and converts it to windows standard name
	 * 
	 * @return default time zone
	 */
	public String getWindowStandardNameDefaultTimezone() {
		return getWindowsStandardName(TimeZone.getDefault().getID());
	}

	public static boolean isValidWindowsStandardName(final String windowsStandardName) {
		final List<TimeZoneWithDisplayNames> returnedZones = TimeZoneList.getInstance().getTimeZones();
		for (final TimeZoneWithDisplayNames zone : returnedZones) {
			if (StringUtil.equals(zone.getStandardDisplayName(), windowsStandardName)) {
				return true;
			}
		}
		return false;
	}

	public static final class TimeZoneWithDisplayNames {
		private final TimeZone timeZone;
		private final String displayName;
		private final String standardDisplayName;

		public TimeZoneWithDisplayNames(final TimeZone timeZone, final String displayName,
		        final String standardDisplayName) {
			this.timeZone = timeZone;
			this.displayName = displayName;
			this.standardDisplayName = standardDisplayName;
		}

		public TimeZone getTimeZone() {
			return timeZone;
		}

		public String getDisplayName() {
			return displayName;
		}

		public String getStandardDisplayName() {
			return standardDisplayName;
		}
	}

	private static final class TimeZoneMapping {
		private final String windowsStandardName;
		private final String olsonName;
		private final String windowsDisplayName;

		public TimeZoneMapping(final String windowsStandardName, final String olsonName,
		        final String windowsDisplayName) {
			this.windowsStandardName = windowsStandardName;
			this.olsonName = olsonName;
			this.windowsDisplayName = windowsDisplayName;
		}

		public String getWindowsStandardName() {
			return windowsStandardName;
		}

		public String getOlsonName() {
			return olsonName;
		}

		public String getWindowsDisplayName() {
			return windowsDisplayName;
		}
	}

	public static void main(final String[] args) {
		final List<TimeZoneWithDisplayNames> returnedZones = TimeZoneList.getInstance().getTimeZones();
		for (final TimeZoneWithDisplayNames zone : returnedZones) {
			System.out.println(zone.getDisplayName() + " olsen=" + zone.getTimeZone().getID());
		}
	}

}