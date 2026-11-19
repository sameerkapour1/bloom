package io.github.sameerkapour1.bloom.core.domain

import io.github.sameerkapour1.bloom.core.model.Habit
import io.github.sameerkapour1.bloom.core.model.HabitSchedule
import io.github.sameerkapour1.bloom.core.model.ScheduleType
import io.github.sameerkapour1.bloom.core.model.TrackingDate
import io.github.sameerkapour1.bloom.core.model.Weekday

/** Resolves which schedule version is in force on a date (DL-3). */
object ScheduleResolver {

    /**
     * The schedule row with the greatest `effectiveFrom` that is ≤ [date], or null
     * when the habit has no schedule version yet.
     */
    fun resolve(schedules: List<HabitSchedule>, date: TrackingDate): HabitSchedule? =
        schedules
            .asSequence()
            .filter { it.effectiveFrom <= date }
            .maxByOrNull { it.effectiveFrom.epochDay }
}

/**
 * Whether this schedule expects an occurrence on [date]. Day-based schedules only;
 * `TIMES_PER_WEEK` is evaluated at week level (DL-4, DL-8).
 */
fun HabitSchedule.isScheduledOn(date: TrackingDate): Boolean = when (scheduleType) {
    ScheduleType.DAILY -> true
    ScheduleType.WEEKDAYS -> weekdaysMask and Weekday.of(date).bit != 0
    ScheduleType.TIMES_PER_WEEK -> true
    ScheduleType.NONE -> false
}

/**
 * Whether the habit appears on Home on [date] (DL-4): it existed, a schedule
 * resolves, and that schedule does not evaluate false for the day.
 *
 * A `TIMES_PER_WEEK` habit stays listed every day of its week, including after the
 * weekly target is met — that is when the row switches to `n/target this week`.
 */
fun Habit.isListedOn(date: TrackingDate, schedules: List<HabitSchedule>): Boolean {
    if (date < createdOn) return false
    val schedule = ScheduleResolver.resolve(schedules, date) ?: return false
    return schedule.isScheduledOn(date)
}

/**
 * Whether the habit is still waiting for the user on [date] (DL-4).
 *
 * [completedThisWeek] is the number of completed occurrences in the week that
 * contains [date]; it only matters for `TIMES_PER_WEEK`. Unscheduled days, days
 * before creation and paused days are never due.
 */
fun Habit.isDueOn(
    date: TrackingDate,
    schedules: List<HabitSchedule>,
    completedThisWeek: Int = 0,
): Boolean {
    if (!isListedOn(date, schedules)) return false
    val schedule = ScheduleResolver.resolve(schedules, date) ?: return false
    if (schedule.scheduleType != ScheduleType.TIMES_PER_WEEK) return true
    val target = schedule.timesPerWeek ?: return false
    return completedThisWeek < target
}
