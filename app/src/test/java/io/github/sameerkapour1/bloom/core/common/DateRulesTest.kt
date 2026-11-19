package io.github.sameerkapour1.bloom.core.common

import io.github.sameerkapour1.bloom.core.model.TrackingDate
import io.github.sameerkapour1.bloom.core.model.Weekday
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Locale
import org.junit.Assert.assertEquals
import org.junit.Test

/** DL-1 (epoch days), DL-13 (DST) and DL-14 (locale week start). */
class DateRulesTest {

    private val wednesday = TrackingDate.of(LocalDate.of(2026, 11, 18))

    @Test
    fun `epoch days round-trip`() {
        val date = LocalDate.of(2026, 11, 18)
        assertEquals(date, TrackingDate.of(date).toLocalDate())
    }

    @Test
    fun `weeks start on monday by default`() {
        assertEquals(TrackingDate.of(LocalDate.of(2026, 11, 16)), wednesday.startOfWeek())
        assertEquals(TrackingDate.of(LocalDate.of(2026, 11, 22)), wednesday.endOfWeek())
    }

    @Test
    fun `an american locale moves the week start to sunday`() {
        assertEquals(
            TrackingDate.of(LocalDate.of(2026, 11, 15)),
            wednesday.startOfWeek(Weekday.SUNDAY),
        )
    }

    @Test
    fun `the locale decides the first day of the week`() {
        assertEquals(Weekday.MONDAY, firstDayOfWeekOf(Locale.FRANCE))
        assertEquals(Weekday.SUNDAY, firstDayOfWeekOf(Locale.US))
    }

    @Test
    fun `a spring-forward gap resolves forward instead of dropping the reminder`() {
        // Europe/Paris skips 02:00–03:00 on 2026-03-29; 02:30 does not exist.
        val instant = reminderInstant(
            date = TrackingDate.of(LocalDate.of(2026, 3, 29)),
            hour = 2,
            minute = 30,
            zone = ZoneId.of("Europe/Paris"),
        )

        assertEquals(Instant.parse("2026-03-29T01:30:00Z"), instant)
    }
}
