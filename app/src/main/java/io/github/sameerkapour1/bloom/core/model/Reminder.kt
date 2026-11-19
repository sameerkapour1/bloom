package io.github.sameerkapour1.bloom.core.model

/**
 * A per-habit reminder stored as a local time of day, never as an instant
 * (plan.md §8.1, DL-13). The next occurrence is resolved with the device zone at
 * scheduling time, so DST changes move the reminder, never a stored date.
 */
data class Reminder(
    val habitId: String,
    val hour: Int,
    val minute: Int,
    val enabled: Boolean = true,
)
