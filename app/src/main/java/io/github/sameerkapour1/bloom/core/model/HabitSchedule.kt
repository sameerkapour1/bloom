package io.github.sameerkapour1.bloom.core.model

/** How a habit recurs (DL-4). */
enum class ScheduleType { DAILY, WEEKDAYS, TIMES_PER_WEEK, NONE }

/**
 * One effective-dated schedule version (plan.md §8.1, DL-3).
 *
 * The schedule in force on date *d* is the row with the greatest [effectiveFrom]
 * that is still ≤ *d*. Rows are never rewritten for a past date, and a write for
 * an existing [effectiveFrom] replaces that row (upsert on `(habitId, effectiveFrom)`).
 */
data class HabitSchedule(
    val id: String,
    val habitId: String,
    val effectiveFrom: TrackingDate,
    val scheduleType: ScheduleType,
    val createdAt: Long,
    val weekdaysMask: Int = 0,
    val timesPerWeek: Int? = null,
)
