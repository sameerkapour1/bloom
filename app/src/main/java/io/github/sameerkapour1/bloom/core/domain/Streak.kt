package io.github.sameerkapour1.bloom.core.domain

import io.github.sameerkapour1.bloom.core.common.startOfWeek
import io.github.sameerkapour1.bloom.core.model.Completion
import io.github.sameerkapour1.bloom.core.model.Habit
import io.github.sameerkapour1.bloom.core.model.HabitSchedule
import io.github.sameerkapour1.bloom.core.model.ScheduleType
import io.github.sameerkapour1.bloom.core.model.TrackingDate
import io.github.sameerkapour1.bloom.core.model.Weekday

/**
 * Streaks exactly as DL-9 defines them.
 *
 * The grace rule is the important part: an unfinished day in progress never resets
 * a streak. Counting starts at today when today is already completed, otherwise at
 * the previous scheduled date — so a 40-day run still reads 40 every morning before
 * the first tap, instead of dropping to 0.
 *
 * Day-based schedules (`DAILY`/`WEEKDAYS`) count days and skip unscheduled dates
 * without breaking; `TIMES_PER_WEEK` counts consecutive met weeks (DL-8).
 */
object StreakCalculator {

    fun current(
        habit: Habit,
        schedules: List<HabitSchedule>,
        completions: List<Completion>,
        today: TrackingDate,
        firstDay: Weekday = Weekday.MONDAY,
    ): Int {
        if (today < habit.createdOn) return 0
        val done = completedDates(habit, completions)

        if (isWeeklyOn(schedules, today)) {
            return weeklyStreak(habit, schedules, done, today, firstDay)
        }

        var cursor = today
        val scheduleToday = ScheduleResolver.resolve(schedules, cursor)
        val todayScheduled = scheduleToday != null && scheduleToday.isScheduledOn(cursor)
        if (todayScheduled && cursor !in done) cursor -= 1

        var streak = 0
        while (cursor >= habit.createdOn) {
            val schedule = ScheduleResolver.resolve(schedules, cursor)
            if (schedule == null || schedule.scheduleType == ScheduleType.NONE) {
                cursor -= 1
                continue
            }
            if (schedule.scheduleType == ScheduleType.TIMES_PER_WEEK) break
            if (!schedule.isScheduledOn(cursor)) {
                cursor -= 1
                continue
            }
            if (cursor !in done) break
            streak++
            cursor -= 1
        }
        return streak
    }

    fun longest(
        habit: Habit,
        schedules: List<HabitSchedule>,
        completions: List<Completion>,
        today: TrackingDate,
        firstDay: Weekday = Weekday.MONDAY,
    ): Int {
        if (today < habit.createdOn) return 0
        val done = completedDates(habit, completions)
        var best = 0
        var run = 0
        var cursor = habit.createdOn

        while (cursor <= today) {
            val schedule = ScheduleResolver.resolve(schedules, cursor)
            if (schedule == null ||
                schedule.scheduleType == ScheduleType.NONE ||
                schedule.scheduleType == ScheduleType.TIMES_PER_WEEK ||
                !schedule.isScheduledOn(cursor)
            ) {
                cursor += 1
                continue
            }
            if (cursor in done) {
                run++
                if (run > best) best = run
            } else {
                run = 0
            }
            cursor += 1
        }

        var week = habit.createdOn.startOfWeek(firstDay)
        var weekRun = 0
        while (week <= today) {
            val weekEnd = week + 6
            val target = weeklyTarget(schedules, week, weekEnd)
            if (target != null && weekEnd <= today) {
                val completed = done.count { it >= week && it <= weekEnd }
                if (target > 0 && completed >= target) {
                    weekRun++
                    if (weekRun > best) best = weekRun
                } else {
                    weekRun = 0
                }
            }
            week += 7
        }
        return best
    }

    private fun completedDates(habit: Habit, completions: List<Completion>): Set<TrackingDate> =
        completions
            .asSequence()
            .filter { it.habitId == habit.id && it.completed }
            .map { it.trackingDate }
            .toSet()

    private fun isWeeklyOn(schedules: List<HabitSchedule>, date: TrackingDate): Boolean =
        ScheduleResolver.resolve(schedules, date)?.scheduleType == ScheduleType.TIMES_PER_WEEK

    private fun weeklyTarget(
        schedules: List<HabitSchedule>,
        weekStart: TrackingDate,
        weekEnd: TrackingDate,
    ): Int? {
        var date = weekStart
        while (date <= weekEnd) {
            val schedule = ScheduleResolver.resolve(schedules, date)
            if (schedule != null && schedule.scheduleType == ScheduleType.TIMES_PER_WEEK) {
                return schedule.timesPerWeek ?: 0
            }
            date += 1
        }
        return null
    }

    /**
     * Consecutive met weeks, with the same grace idea as days: an unfinished current
     * week does not break a run that ended last week.
     */
    private fun weeklyStreak(
        habit: Habit,
        schedules: List<HabitSchedule>,
        done: Set<TrackingDate>,
        today: TrackingDate,
        firstDay: Weekday,
    ): Int {
        var weekStart = today.startOfWeek(firstDay)
        var streak = 0
        var isCurrentWeek = true

        while (true) {
            val weekEnd = weekStart + 6
            if (weekEnd < habit.createdOn) break
            val target = weeklyTarget(schedules, weekStart, weekEnd) ?: break
            val completed = done.count { it >= weekStart && it <= weekEnd }
            val met = target > 0 && completed >= target
            if (met) {
                streak++
            } else if (!isCurrentWeek) {
                break
            }
            isCurrentWeek = false
            weekStart -= 7
        }
        return streak
    }
}
