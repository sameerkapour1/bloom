package io.github.sameerkapour1.bloom.core.common

import io.github.sameerkapour1.bloom.core.model.TrackingDate
import io.github.sameerkapour1.bloom.core.model.Weekday
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.WeekFields
import java.util.Locale

/**
 * The first day of the week that contains this date (DL-8, DL-14).
 *
 * The week start comes from the active locale, not from a hardcoded assumption:
 * French weeks start on Monday, US weeks on Sunday.
 */
fun TrackingDate.startOfWeek(firstDay: Weekday = Weekday.MONDAY): TrackingDate {
    val isoWeekday = toLocalDate().dayOfWeek.value
    val isoFirstDay = firstDay.ordinal + 1
    val daysSinceStart = (isoWeekday - isoFirstDay + 7) % 7
    return this - daysSinceStart.toLong()
}

/** The last day of the same week. */
fun TrackingDate.endOfWeek(firstDay: Weekday = Weekday.MONDAY): TrackingDate =
    startOfWeek(firstDay) + 6

/** Locale-derived first day of week, used to configure [startOfWeek]. */
fun firstDayOfWeekOf(locale: Locale): Weekday =
    Weekday.entries[WeekFields.of(locale).firstDayOfWeek.value - 1]

/**
 * "Today" as the device sees it. Recomputed on resume so crossing midnight while
 * the app is open moves the home screen to the new day (DL-1).
 */
fun todayIn(zone: ZoneId, clock: Clock = Clock.system(zone)): TrackingDate =
    TrackingDate.of(LocalDate.now(clock.withZone(zone)))

/**
 * Resolves a stored local time of day to a concrete instant for a date (DL-13).
 *
 * `ZonedDateTime` resolves spring-forward gaps forward, so a reminder at 02:30 on
 * a day when 02:30 does not exist fires at the next valid instant instead of being
 * dropped. Stored tracking dates are never involved in this conversion.
 */
fun reminderInstant(
    date: TrackingDate,
    hour: Int,
    minute: Int,
    zone: ZoneId,
): Instant = date.toLocalDate()
    .atTime(hour, minute)
    .atZone(zone)
    .toInstant()
