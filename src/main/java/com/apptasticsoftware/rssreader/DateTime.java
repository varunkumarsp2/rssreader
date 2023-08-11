/*
 * MIT License
 *
 * Copyright (c) 2022, Apptastic Software
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.apptasticsoftware.rssreader;

import com.apptasticsoftware.rssreader.util.ItemComparator;

import java.time.*;
import java.time.format.*;
import java.util.Comparator;
import java.util.Locale;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

/**
 * Class for converting date time strings
 */
public class DateTime implements DateTimeParser {
    private final ZoneId defaultZone;

    public static final DateTimeFormatter BASIC_ISO_DATE;
    public static final DateTimeFormatter ISO_LOCAL_DATE;
    public static final DateTimeFormatter ISO_OFFSET_DATE_TIME;
    public static final DateTimeFormatter ISO_OFFSET_DATE_TIME_SPECIAL;
    public static final DateTimeFormatter ISO_LOCAL_DATE_TIME;
    public static final DateTimeFormatter ISO_LOCAL_DATE_TIME_SPECIAL;

    public static final DateTimeFormatter RFC_1123_DATE_TIME;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_TIMEZONE;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_TIMEZONE2;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_NO_TIMEZONE;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_SPECIAL;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_GMT_OFFSET;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_GMT_AND_OFFSET;
    public static final DateTimeFormatter RFC_822_DATE_TIME;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_SPECIAL_EST;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_SPECIAL_EDT;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_SPECIAL_CST;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_SPECIAL_CDT;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_SPECIAL_MST;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_SPECIAL_MDT;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_SPECIAL_PST;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_SPECIAL_PDT;

    public static final DateTimeFormatter RFC_1123_DATE_TIME_SPECIAL_FULL_EOW;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_GMT_OFFSET_FULL_EOW;
    public static final DateTimeFormatter RFC_822_DATE_TIME_FULL_EOW;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_SPECIAL_EST_FULL_EOW;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_SPECIAL_EDT_FULL_EOW;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_SPECIAL_CST_FULL_EOW;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_SPECIAL_CDT_FULL_EOW;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_SPECIAL_MST_FULL_EOW;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_SPECIAL_MDT_FULL_EOW;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_SPECIAL_PST_FULL_EOW;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_SPECIAL_PDT_FULL_EOW;

    public static final DateTimeFormatter RFC_1123_DATE_TIME_SPECIAL_NO_EOW;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_GMT_OFFSET_NO_EOW;
    public static final DateTimeFormatter RFC_822_DATE_TIME_NO_EOW;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_SPECIAL_EST_NO_EOW;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_SPECIAL_EDT_NO_EOW;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_SPECIAL_CST_NO_EOW;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_SPECIAL_CDT_NO_EOW;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_SPECIAL_MST_NO_EOW;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_SPECIAL_MDT_NO_EOW;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_SPECIAL_PST_NO_EOW;
    public static final DateTimeFormatter RFC_1123_DATE_TIME_SPECIAL_PDT_NO_EOW;

    static {
        BASIC_ISO_DATE = DateTimeFormatter.BASIC_ISO_DATE.withLocale(Locale.ENGLISH);
        ISO_LOCAL_DATE = DateTimeFormatter.ISO_LOCAL_DATE.withLocale(Locale.ENGLISH);
        ISO_OFFSET_DATE_TIME = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withLocale(Locale.ENGLISH);
        ISO_LOCAL_DATE_TIME = DateTimeFormatter.ISO_LOCAL_DATE_TIME.withLocale(Locale.ENGLISH);
        ISO_LOCAL_DATE_TIME_SPECIAL = new DateTimeFormatterBuilder().parseCaseInsensitive().append(ISO_LOCAL_DATE).appendLiteral(' ').append(ISO_LOCAL_TIME).toFormatter().withLocale(Locale.ENGLISH);
        ISO_OFFSET_DATE_TIME_SPECIAL = new DateTimeFormatterBuilder().parseCaseInsensitive().append(DateTimeFormatter.ISO_LOCAL_DATE).appendLiteral('T').append(ISO_LOCAL_TIME).appendOffset("+HHMM", "0000").toFormatter(Locale.ENGLISH);

        RFC_1123_DATE_TIME = DateTimeFormatter.RFC_1123_DATE_TIME.withLocale(Locale.ENGLISH);
        RFC_1123_DATE_TIME_TIMEZONE = DateTimeFormatter.ofPattern("E, d LLL yyyy HH:mm:ss zzz", Locale.ENGLISH);
        RFC_1123_DATE_TIME_TIMEZONE2 = new DateTimeFormatterBuilder().appendPattern("E, d LLL yyyy HH:mm:ss").appendOffset("+H:mm", "+00").toFormatter().withLocale(Locale.ENGLISH);
        RFC_1123_DATE_TIME_NO_TIMEZONE = DateTimeFormatter.ofPattern("E, d LLL yyyy HH:mm:ss", Locale.ENGLISH).withZone(ZoneId.of("UTC"));
        RFC_1123_DATE_TIME_SPECIAL = DateTimeFormatter.ofPattern("E, d LLL yyyy HH:mm:ss z", Locale.ENGLISH);
        RFC_1123_DATE_TIME_GMT_OFFSET = DateTimeFormatter.ofPattern("E, d LLL yyyy HH:mm:ss O", Locale.ENGLISH);
        RFC_1123_DATE_TIME_GMT_AND_OFFSET = DateTimeFormatter.ofPattern("E, dd MMM yyyy HH:mm:ss 'GMT'Z", Locale.ENGLISH);
        RFC_822_DATE_TIME = DateTimeFormatter.ofPattern("E, d LLL yy HH:mm:ss X", Locale.ENGLISH);
        RFC_1123_DATE_TIME_SPECIAL_EDT = DateTimeFormatter.ofPattern("E, d LLL yyyy HH:mm:ss 'EDT'", Locale.ENGLISH).withZone(ZoneOffset.ofHours(-4));
        RFC_1123_DATE_TIME_SPECIAL_EST = DateTimeFormatter.ofPattern("E, d LLL yyyy HH:mm:ss 'EST'", Locale.ENGLISH).withZone(ZoneOffset.ofHours(-5));
        RFC_1123_DATE_TIME_SPECIAL_CDT = DateTimeFormatter.ofPattern("E, d LLL yyyy HH:mm:ss 'CDT'", Locale.ENGLISH).withZone(ZoneOffset.ofHours(-5));
        RFC_1123_DATE_TIME_SPECIAL_CST = DateTimeFormatter.ofPattern("E, d LLL yyyy HH:mm:ss 'CST'", Locale.ENGLISH).withZone(ZoneOffset.ofHours(-6));
        RFC_1123_DATE_TIME_SPECIAL_MDT = DateTimeFormatter.ofPattern("E, d LLL yyyy HH:mm:ss 'MDT'", Locale.ENGLISH).withZone(ZoneOffset.ofHours(-6));
        RFC_1123_DATE_TIME_SPECIAL_MST = DateTimeFormatter.ofPattern("E, d LLL yyyy HH:mm:ss 'MST'", Locale.ENGLISH).withZone(ZoneOffset.ofHours(-7));
        RFC_1123_DATE_TIME_SPECIAL_PDT = DateTimeFormatter.ofPattern("E, d LLL yyyy HH:mm:ss 'PDT'", Locale.ENGLISH).withZone(ZoneOffset.ofHours(-7));
        RFC_1123_DATE_TIME_SPECIAL_PST = DateTimeFormatter.ofPattern("E, d LLL yyyy HH:mm:ss 'PST'", Locale.ENGLISH).withZone(ZoneOffset.ofHours(-8));

        RFC_1123_DATE_TIME_SPECIAL_FULL_EOW = DateTimeFormatter.ofPattern("EEEE, d LLL yyyy HH:mm:ss z", Locale.ENGLISH);
        RFC_1123_DATE_TIME_GMT_OFFSET_FULL_EOW = DateTimeFormatter.ofPattern("EEEE, d LLL yyyy HH:mm:ss O", Locale.ENGLISH);
        RFC_822_DATE_TIME_FULL_EOW = DateTimeFormatter.ofPattern("EEEE, d LLL yyyy HH:mm:ss X", Locale.ENGLISH);
        RFC_1123_DATE_TIME_SPECIAL_EDT_FULL_EOW = DateTimeFormatter.ofPattern("EEEE, d LLL yyyy HH:mm:ss 'EDT'", Locale.ENGLISH).withZone(ZoneOffset.ofHours(-4));
        RFC_1123_DATE_TIME_SPECIAL_EST_FULL_EOW = DateTimeFormatter.ofPattern("EEEE, d LLL yyyy HH:mm:ss 'EST'", Locale.ENGLISH).withZone(ZoneOffset.ofHours(-5));
        RFC_1123_DATE_TIME_SPECIAL_CDT_FULL_EOW = DateTimeFormatter.ofPattern("EEEE, d LLL yyyy HH:mm:ss 'CDT'", Locale.ENGLISH).withZone(ZoneOffset.ofHours(-5));
        RFC_1123_DATE_TIME_SPECIAL_CST_FULL_EOW = DateTimeFormatter.ofPattern("EEEE, d LLL yyyy HH:mm:ss 'CST'", Locale.ENGLISH).withZone(ZoneOffset.ofHours(-6));
        RFC_1123_DATE_TIME_SPECIAL_MDT_FULL_EOW = DateTimeFormatter.ofPattern("EEEE, d LLL yyyy HH:mm:ss 'MDT'", Locale.ENGLISH).withZone(ZoneOffset.ofHours(-6));
        RFC_1123_DATE_TIME_SPECIAL_MST_FULL_EOW = DateTimeFormatter.ofPattern("EEEE, d LLL yyyy HH:mm:ss 'MST'", Locale.ENGLISH).withZone(ZoneOffset.ofHours(-7));
        RFC_1123_DATE_TIME_SPECIAL_PDT_FULL_EOW = DateTimeFormatter.ofPattern("EEEE, d LLL yyyy HH:mm:ss 'PDT'", Locale.ENGLISH).withZone(ZoneOffset.ofHours(-7));
        RFC_1123_DATE_TIME_SPECIAL_PST_FULL_EOW = DateTimeFormatter.ofPattern("EEEE, d LLL yyyy HH:mm:ss 'PST'", Locale.ENGLISH).withZone(ZoneOffset.ofHours(-8));

        RFC_1123_DATE_TIME_SPECIAL_NO_EOW = DateTimeFormatter.ofPattern("d LLL yyyy HH:mm:ss z", Locale.ENGLISH);
        RFC_1123_DATE_TIME_GMT_OFFSET_NO_EOW = DateTimeFormatter.ofPattern("d LLL yyyy HH:mm:ss O", Locale.ENGLISH);
        RFC_822_DATE_TIME_NO_EOW = DateTimeFormatter.ofPattern("d LLL yyyy HH:mm:ss X", Locale.ENGLISH);
        RFC_1123_DATE_TIME_SPECIAL_EDT_NO_EOW = DateTimeFormatter.ofPattern("d LLL yyyy HH:mm:ss 'EDT'", Locale.ENGLISH).withZone(ZoneOffset.ofHours(-4));
        RFC_1123_DATE_TIME_SPECIAL_EST_NO_EOW = DateTimeFormatter.ofPattern("d LLL yyyy HH:mm:ss 'EST'", Locale.ENGLISH).withZone(ZoneOffset.ofHours(-5));
        RFC_1123_DATE_TIME_SPECIAL_CDT_NO_EOW = DateTimeFormatter.ofPattern("d LLL yyyy HH:mm:ss 'CDT'", Locale.ENGLISH).withZone(ZoneOffset.ofHours(-5));
        RFC_1123_DATE_TIME_SPECIAL_CST_NO_EOW = DateTimeFormatter.ofPattern("d LLL yyyy HH:mm:ss 'CST'", Locale.ENGLISH).withZone(ZoneOffset.ofHours(-6));
        RFC_1123_DATE_TIME_SPECIAL_MDT_NO_EOW = DateTimeFormatter.ofPattern("d LLL yyyy HH:mm:ss 'MDT'", Locale.ENGLISH).withZone(ZoneOffset.ofHours(-6));
        RFC_1123_DATE_TIME_SPECIAL_MST_NO_EOW = DateTimeFormatter.ofPattern("d LLL yyyy HH:mm:ss 'MST'", Locale.ENGLISH).withZone(ZoneOffset.ofHours(-7));
        RFC_1123_DATE_TIME_SPECIAL_PDT_NO_EOW = DateTimeFormatter.ofPattern("d LLL yyyy HH:mm:ss 'PDT'", Locale.ENGLISH).withZone(ZoneOffset.ofHours(-7));
        RFC_1123_DATE_TIME_SPECIAL_PST_NO_EOW = DateTimeFormatter.ofPattern("d LLL yyyy HH:mm:ss 'PST'", Locale.ENGLISH).withZone(ZoneOffset.ofHours(-8));
    }

    /**
     * Creates DateTime object for converting timestamps.
     * Using default timezone UTC if no timezone information if found in timestamp.
     */
    public DateTime() {
        defaultZone = ZoneId.of("UTC");
    }

    /**
     * Creates DateTime object for converting timestamps.
     * @param defaultZone time zone to use if no timezone information if found in timestamp
     */
    public DateTime(ZoneId defaultZone) {
        this.defaultZone = defaultZone;
    }

    /**
     * Converts date time string to LocalDateTime object. Note any time zone information in date time string is ignored.
     * @param dateTime date time string
     * @return local date time object
     */
    public LocalDateTime toLocalDateTime(String dateTime) {
        var zonedDateTime = toZonedDateTime(dateTime);
        if (zonedDateTime == null) {
            return null;
        }
        return zonedDateTime.toLocalDateTime();
    }

    /**
     * Converts date time string to ZonedDateTime object. Use if time date string contains time zone information.
     * @param dateTime date time string
     * @return zoned date time object
     */
    @SuppressWarnings("java:S3776")
    public ZonedDateTime toZonedDateTime(String dateTime) {
        if (dateTime == null)
            return null;

        DateTimeFormatter formatter = getDateTimeFormatter(dateTime, false);

        if (formatter == null) {
            throw new IllegalArgumentException("Unknown date time format " + dateTime);
        }

        if (dateTime.length() == 19) {
            // Missing time zone information use default time zone. If not setting any default time zone system default
            // time zone is used.
            LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);
            return ZonedDateTime.of(localDateTime, defaultZone);
        } else if (((dateTime.length() == 29 || dateTime.length() == 32 || dateTime.length() == 35) && dateTime.charAt(10) == 'T') ||
            ((dateTime.length() == 24 || dateTime.length() == 25) && dateTime.charAt(3) == ',')) {
            return ZonedDateTime.parse(dateTime, formatter);
        }

        try {
            if (isDateOnly(formatter)) {
                LocalDate date = LocalDate.parse(dateTime, formatter);
                return ZonedDateTime.of(date, LocalTime.MIDNIGHT, defaultZone);
            } else {
                return ZonedDateTime.parse(dateTime, formatter);
            }
        } catch (DateTimeParseException e) {
            int index = dateTime.indexOf(',');
            if (index != -1 && e.getMessage().contains("Conflict found: Field DayOfWeek")) {
                // Handel date time with incorrect day of week
                String newDateTime = dateTime.substring(index+1).trim();
                try {
                    DateTimeFormatter newFormatter = getDateTimeFormatter(newDateTime, true);
                    return ZonedDateTime.parse(newDateTime, newFormatter);
                } catch (DateTimeParseException e2) {
                    throw e;
                }
            } else {
                throw e;
            }
        }
    }

    private static boolean isDateOnly(DateTimeFormatter formatter) {
        return formatter == ISO_LOCAL_DATE || formatter == BASIC_ISO_DATE;
    }

    @SuppressWarnings("java:S3776")
    private static DateTimeFormatter getDateTimeFormatter(String dateTime, boolean skipEndOfWeekPart) {
        if (skipEndOfWeekPart) {
            return parseRfcDateTimeNoDayOfWeek(dateTime);
        }

        int index = dateTime.indexOf(',');

        if (index == -1) {
            return parseIsoDateTime(dateTime);
        } else if (index <= 3) {
            return parseRfcDateTime(dateTime);
        } else {
            return parseRfcDateTimeFullDayOfWeek(dateTime);
        }
    }

    private static DateTimeFormatter parseIsoDateTime(String dateTime) {
        if (dateTime.length() == 24 && dateTime.charAt(4) == '-' && dateTime.charAt(10) == 'T' && (dateTime.charAt(dateTime.length() - 5) == '-' || dateTime.charAt(dateTime.length() - 5) == '+'))
            return ISO_OFFSET_DATE_TIME_SPECIAL;
        else if (dateTime.length() >= 20 && dateTime.length() <= 35 && dateTime.charAt(4) == '-' && dateTime.charAt(10) == 'T') // && dateTime.charAt(dateTime.length() - 3) == ':')
            return ISO_OFFSET_DATE_TIME;
        else if (dateTime.length() == 19 && dateTime.charAt(10) == 'T')
            return ISO_LOCAL_DATE_TIME;
        else if (dateTime.length() == 19 && dateTime.charAt(10) == ' ')
            return ISO_LOCAL_DATE_TIME_SPECIAL;
        else if (dateTime.length() == 10 && dateTime.charAt(4) == '-' && dateTime.charAt(7) == '-')
            return ISO_LOCAL_DATE;
        else if (dateTime.length() == 8)
            return BASIC_ISO_DATE;
        else
            return null;
    }

    @SuppressWarnings("java:S3776")
    private static DateTimeFormatter parseRfcDateTime(String dateTime) {
        if ((dateTime.length() == 28 || dateTime.length() == 29) && dateTime.charAt(3) == ',' && dateTime.endsWith(" UTC"))
            return RFC_1123_DATE_TIME_SPECIAL;
        else if ((dateTime.length() == 28 || dateTime.length() == 29) && dateTime.charAt(3) == ',' && dateTime.endsWith(" EDT"))
            return RFC_1123_DATE_TIME_SPECIAL_EDT;
        else if ((dateTime.length() == 28 || dateTime.length() == 29) && dateTime.charAt(3) == ',' && dateTime.endsWith(" EST"))
            return RFC_1123_DATE_TIME_SPECIAL_EST;
        else if ((dateTime.length() == 28 || dateTime.length() == 29) && dateTime.charAt(3) == ',' && dateTime.endsWith(" CDT"))
            return RFC_1123_DATE_TIME_SPECIAL_CDT;
        else if ((dateTime.length() == 28 || dateTime.length() == 29) && dateTime.charAt(3) == ',' && dateTime.endsWith(" CST"))
            return RFC_1123_DATE_TIME_SPECIAL_CST;
        else if ((dateTime.length() == 28 || dateTime.length() == 29) && dateTime.charAt(3) == ',' && dateTime.endsWith(" MDT"))
            return RFC_1123_DATE_TIME_SPECIAL_MDT;
        else if ((dateTime.length() == 28 || dateTime.length() == 29) && dateTime.charAt(3) == ',' && dateTime.endsWith(" MST"))
            return RFC_1123_DATE_TIME_SPECIAL_MST;
        else if ((dateTime.length() == 28 || dateTime.length() == 29) && dateTime.charAt(3) == ',' && dateTime.endsWith(" PDT"))
            return RFC_1123_DATE_TIME_SPECIAL_PDT;
        else if ((dateTime.length() == 28 || dateTime.length() == 29) && dateTime.charAt(3) == ',' && dateTime.endsWith(" PST"))
            return RFC_1123_DATE_TIME_SPECIAL_PST;
        else if (dateTime.length() > 31 && dateTime.contains("GMT") && !dateTime.endsWith("GMT") && dateTime.lastIndexOf(':') < 29)
            return RFC_1123_DATE_TIME_GMT_AND_OFFSET;
        else if (dateTime.length() >= 28 && dateTime.length() <= 35 && dateTime.charAt(3) == ',' && dateTime.contains("GMT"))
            return RFC_1123_DATE_TIME_GMT_OFFSET;
        else if ((dateTime.length() == 28 || dateTime.length() == 29) && dateTime.charAt(3) == ',' && (dateTime.charAt(13) == ' ' || dateTime.charAt(14) == ' '))
            return RFC_822_DATE_TIME;
        else if (dateTime.length() >= 28 && dateTime.length() <= 31) {
            if (dateTime.contains(" +") || dateTime.contains(" -"))
                return RFC_1123_DATE_TIME;
            else if (dateTime.contains("+") || dateTime.contains("-"))
                return RFC_1123_DATE_TIME_TIMEZONE2;
            else
                return RFC_1123_DATE_TIME_TIMEZONE;
        }
        else if ((dateTime.length() == 26 || dateTime.length() == 27) && dateTime.charAt(3) == ',' && dateTime.endsWith(" Z"))
            return RFC_1123_DATE_TIME_SPECIAL;
        else if ((dateTime.length() == 24 || dateTime.length() == 25) && dateTime.charAt(3) == ',')
            return RFC_1123_DATE_TIME_NO_TIMEZONE;
        else
            return null;
    }

    @SuppressWarnings("java:S3776")
    private static DateTimeFormatter parseRfcDateTimeFullDayOfWeek (String dateTime) {
        if (dateTime.endsWith(" UTC"))
            return RFC_1123_DATE_TIME_SPECIAL_FULL_EOW;
        else if (dateTime.endsWith(" EDT"))
            return RFC_1123_DATE_TIME_SPECIAL_EDT_FULL_EOW;
        else if (dateTime.endsWith(" EST"))
            return RFC_1123_DATE_TIME_SPECIAL_EST_FULL_EOW;
        else if (dateTime.endsWith(" CDT"))
            return RFC_1123_DATE_TIME_SPECIAL_CDT_FULL_EOW;
        else if (dateTime.endsWith(" CST"))
            return RFC_1123_DATE_TIME_SPECIAL_CST_FULL_EOW;
        else if (dateTime.endsWith(" MDT"))
            return RFC_1123_DATE_TIME_SPECIAL_MDT_FULL_EOW;
        else if (dateTime.endsWith(" MST"))
            return RFC_1123_DATE_TIME_SPECIAL_MST_FULL_EOW;
        else if (dateTime.endsWith(" PDT"))
            return RFC_1123_DATE_TIME_SPECIAL_PDT_FULL_EOW;
        else if (dateTime.endsWith(" PST"))
            return RFC_1123_DATE_TIME_SPECIAL_PST_FULL_EOW;
        else if (dateTime.contains("GMT"))
            return RFC_1123_DATE_TIME_GMT_OFFSET_FULL_EOW;
        else if (dateTime.endsWith(" Z"))
            return RFC_1123_DATE_TIME_SPECIAL_FULL_EOW;
        else if (dateTime.contains("-") ||dateTime.contains("+"))
            return RFC_822_DATE_TIME_FULL_EOW;
        else
            return null;
    }

    @SuppressWarnings("java:S3776")
    private static DateTimeFormatter parseRfcDateTimeNoDayOfWeek (String dateTime) {
        if (dateTime.endsWith(" UTC"))
            return RFC_1123_DATE_TIME_SPECIAL_NO_EOW;
        else if (dateTime.endsWith(" EDT"))
            return RFC_1123_DATE_TIME_SPECIAL_EDT_NO_EOW;
        else if (dateTime.endsWith(" EST"))
            return RFC_1123_DATE_TIME_SPECIAL_EST_NO_EOW;
        else if (dateTime.endsWith(" CDT"))
            return RFC_1123_DATE_TIME_SPECIAL_CDT_NO_EOW;
        else if (dateTime.endsWith(" CST"))
            return RFC_1123_DATE_TIME_SPECIAL_CST_NO_EOW;
        else if (dateTime.endsWith(" MDT"))
            return RFC_1123_DATE_TIME_SPECIAL_MDT_NO_EOW;
        else if (dateTime.endsWith(" MST"))
            return RFC_1123_DATE_TIME_SPECIAL_MST_NO_EOW;
        else if (dateTime.endsWith(" PDT"))
            return RFC_1123_DATE_TIME_SPECIAL_PDT_NO_EOW;
        else if (dateTime.endsWith(" PST"))
            return RFC_1123_DATE_TIME_SPECIAL_PST_NO_EOW;
        else if (dateTime.contains("GMT"))
            return RFC_1123_DATE_TIME_GMT_OFFSET_NO_EOW;
        else if (dateTime.endsWith(" Z"))
            return RFC_1123_DATE_TIME_SPECIAL_NO_EOW;
        else if (dateTime.contains("-") ||dateTime.contains("+"))
            return RFC_822_DATE_TIME_NO_EOW;
        else
            return null;
    }

    /**
     * Convert date time string to time in milliseconds
     * @param dateTime date time string
     * @return time in milliseconds
     */
    public Long toEpochMilli(String dateTime) {
        ZonedDateTime zonedDateTime = toZonedDateTime(dateTime);

        if (zonedDateTime == null)
            return null;

        return zonedDateTime.toInstant().toEpochMilli();
    }

    public Instant toInstant(String dateTime) {
        ZonedDateTime zonedDateTime = toZonedDateTime(dateTime);

        if (zonedDateTime == null)
            return null;

        return zonedDateTime.toInstant();
    }

    /**
     * Comparator comparing publication date of Item class. Sorted in ascending order (oldest first)
     *
     * @deprecated
     * This method be removed in a future version.
     * <p> Use {@link ItemComparator#oldestItemFirst()} instead.
     *
     * @return comparator
     */
    @SuppressWarnings("java:S1133")
    @Deprecated(since="3.3.0", forRemoval=true)
    public static Comparator<Item> pubDateComparator() {
        var dateTime = new DateTime();
        return Comparator.comparing(i -> i.getPubDate().map(dateTime::toInstant).orElse(Instant.EPOCH));
    }

    /**
     * Converts a timestamp in String format to a ZonedDateTime
     * @param timestamp timestamp
     * @return ZonedDateTime
     */
    @Override
    public ZonedDateTime parse(String timestamp) {
        return toZonedDateTime(timestamp);
    }
}
