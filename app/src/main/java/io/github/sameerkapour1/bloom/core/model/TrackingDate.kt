package io.github.sameerkapour1.bloom.core.model

import java.time.LocalDate

/**
 * A local civil date stored as an epoch day (plan.md §8.2, DL-1).
 *
 * A tracking date is never derived from an instant: a completion made at 23:59
 * belongs to that day even if the device zone changes an hour later (DL-2).
 */
@JvmInline
value class TrackingDate(val epochDay: Long) : Comparable<TrackingDate> {

    override fun compareTo(other: TrackingDate): Int = epochDay.compareTo(other.epochDay)

    operator fun plus(days: Long): TrackingDate = TrackingDate(epochDay + days)

    operator fun minus(days: Long): TrackingDate = TrackingDate(epochDay - days)

    fun toLocalDate(): LocalDate = LocalDate.ofEpochDay(epochDay)

    override fun toString(): String = toLocalDate().toString()

    companion object {
        fun of(date: LocalDate): TrackingDate = TrackingDate(date.toEpochDay())

        fun ofEpochDay(epochDay: Long): TrackingDate = TrackingDate(epochDay)
    }
}
