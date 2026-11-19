package io.github.sameerkapour1.bloom.core.model

/**
 * Weekday with its stable schedule bit (bit 0 = Monday … bit 6 = Sunday),
 * matching `habit_schedule.weekdaysMask` (plan.md §8.1).
 *
 * The mask is stored, not recomputed from the locale, so a locale change can
 * never move a habit's scheduled days. Locale only affects which day a week
 * starts on for weekly targets and date formatting (DL-14).
 */
enum class Weekday(val bit: Int) {
    MONDAY(1 shl 0),
    TUESDAY(1 shl 1),
    WEDNESDAY(1 shl 2),
    THURSDAY(1 shl 3),
    FRIDAY(1 shl 4),
    SATURDAY(1 shl 5),
    SUNDAY(1 shl 6),
    ;

    companion object {
        fun of(date: TrackingDate): Weekday = entries[date.toLocalDate().dayOfWeek.value - 1]

        fun mask(vararg days: Weekday): Int = days.fold(0) { acc, day -> acc or day.bit }
    }
}
