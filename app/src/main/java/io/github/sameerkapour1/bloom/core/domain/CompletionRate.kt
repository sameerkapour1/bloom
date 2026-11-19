package io.github.sameerkapour1.bloom.core.domain

import io.github.sameerkapour1.bloom.core.common.startOfWeek
import io.github.sameerkapour1.bloom.core.model.Completion
import io.github.sameerkapour1.bloom.core.model.Habit
import io.github.sameerkapour1.bloom.core.model.HabitSchedule
import io.github.sameerkapour1.bloom.core.model.ScheduleType
import io.github.sameerkapour1.bloom.core.model.TrackingDate
import io.github.sameerkapour1.bloom.core.model.Weekday

/**
 * Completed and scheduled occurrence counts for a range.
 *
 * [rate] is null when nothing was scheduled — the UI shows "nothing scheduled",
 * never a fabricated 0 % or 100 % (DL-7).
 */
data class RateResult(val completed: Int, val scheduled: Int) {
    val rate: Double? get() = if (scheduled == 0) null else completed.toDouble() / scheduled
}

/**
 * Completion rate exactly as DL-7 defines the denominator:
 *
 * - an occurrence exists only for a date inside the range on which the habit
 *   already existed and had a non-`NONE` schedule that evaluated true;
 * - unscheduled days, days before creation and paused days are never counted;
 * - `TIMES_PER_WEEK` contributes one occurrence per week, and a week counts as
 *   completed when its completed occurrences reach `timesPerWeek` (DL-8).
 */
object CompletionRateCalculator {

    fun forHabit(
        habit: Habit,
        schedules: List<HabitSchedule>,
        completions: List<Completion>,
        from: TrackingDate,
        to: TrackingDate,
        firstDay: Weekday = Weekday.MONDAY,
    ): RateResult {
        val start = maxOf(from, habit.createdOn)
        if (start > to) return RateResult(0, 0)

        val completedDates = completions
            .asSequence()
            .filter { it.habitId == habit.id && it.completed }
            .map { it.trackingDate }
            .toSet()

        var scheduled = 0
        var completed = 0
        var date = start

        while (date <= to) {
            val schedule = ScheduleResolver.resolve(schedules, date)
            if (schedule == null || schedule.scheduleType == ScheduleType.NONE) {
                date += 1
                continue
            }

            if (schedule.scheduleType == ScheduleType.TIMES_PER_WEEK) {
                val weekStart = date.startOfWeek(firstDay)
                val weekEnd = weekStart + 6
                val rangeStart = maxOf(weekStart, start)
                val rangeEnd = minOf(weekEnd, to)
                val target = schedule.timesPerWeek ?: 0
                scheduled++
                val doneThisWeek = completedDates.count { it >= rangeStart && it <= rangeEnd }
                if (target > 0 && doneThisWeek >= target) completed++
                date = weekEnd + 1
                continue
            }

            if (schedule.isScheduledOn(date)) {
                scheduled++
                if (date in completedDates) completed++
            }
            date += 1
        }

        return RateResult(completed, scheduled)
    }

    /** Combines per-habit results without inventing a rate for empty contributors. */
    fun aggregate(perHabit: List<RateResult>): RateResult =
        RateResult(
            completed = perHabit.sumOf { it.completed },
            scheduled = perHabit.sumOf { it.scheduled },
        )
}
