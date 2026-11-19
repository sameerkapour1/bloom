package io.github.sameerkapour1.bloom.core.domain

import io.github.sameerkapour1.bloom.core.model.Completion
import io.github.sameerkapour1.bloom.core.model.Habit
import io.github.sameerkapour1.bloom.core.model.HabitSchedule
import io.github.sameerkapour1.bloom.core.model.ScheduleType
import io.github.sameerkapour1.bloom.core.model.TrackingDate
import io.github.sameerkapour1.bloom.core.model.TrackingType
import io.github.sameerkapour1.bloom.core.model.Weekday
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

/** DL-7 (denominator) and DL-8 (weekly target). */
class CompletionRateTest {

    private val monday = TrackingDate.of(LocalDate.of(2026, 11, 16))

    private fun habit(createdOn: TrackingDate = monday) = Habit(
        id = "h1",
        name = "Walk",
        iconKey = "walk",
        colorArgb = 0,
        trackingType = TrackingType.BOOLEAN,
        createdOn = createdOn,
        createdAt = 0L,
        updatedAt = 0L,
    )

    private fun schedule(
        type: ScheduleType,
        effectiveFrom: TrackingDate = monday,
        mask: Int = 0,
        timesPerWeek: Int? = null,
    ) = HabitSchedule(
        id = "s-${type.name}-${effectiveFrom.epochDay}",
        habitId = "h1",
        effectiveFrom = effectiveFrom,
        scheduleType = type,
        createdAt = 0L,
        weekdaysMask = mask,
        timesPerWeek = timesPerWeek,
    )

    private fun completion(date: TrackingDate): Completion = createCompletion(
        id = "c-${date.epochDay}",
        habitId = "h1",
        trackingDate = date,
        value = 1.0,
        targetValueSnapshot = 1.0,
        recordedZoneId = "Europe/Paris",
        now = 0L,
    )

    @Test
    fun `a daily habit counts every day it existed`() {
        val result = CompletionRateCalculator.forHabit(
            habit = habit(),
            schedules = listOf(schedule(ScheduleType.DAILY)),
            completions = listOf(completion(monday), completion(monday + 1)),
            from = monday,
            to = monday + 2,
        )

        assertEquals(3, result.scheduled)
        assertEquals(2, result.completed)
        assertEquals(2.0 / 3.0, result.rate!!, 0.0001)
    }

    @Test
    fun `paused days never enter the denominator`() {
        val schedules = listOf(
            schedule(ScheduleType.DAILY),
            schedule(ScheduleType.NONE, effectiveFrom = monday + 1),
        )
        val result = CompletionRateCalculator.forHabit(
            habit = habit(),
            schedules = schedules,
            completions = listOf(completion(monday)),
            from = monday,
            to = monday + 2,
        )

        assertEquals(1, result.scheduled)
        assertEquals(1, result.completed)
        assertEquals(1.0, result.rate!!, 0.0001)
    }

    @Test
    fun `weekday habits count only their scheduled days`() {
        val schedules = listOf(
            schedule(
                ScheduleType.WEEKDAYS,
                mask = Weekday.mask(Weekday.MONDAY, Weekday.WEDNESDAY, Weekday.FRIDAY),
            ),
        )
        val result = CompletionRateCalculator.forHabit(
            habit = habit(),
            schedules = schedules,
            completions = listOf(completion(monday)),
            from = monday,
            to = monday + 6,
        )

        assertEquals(3, result.scheduled)
        assertEquals(1, result.completed)
    }

    @Test
    fun `days before creation are outside the range`() {
        val result = CompletionRateCalculator.forHabit(
            habit = habit(createdOn = monday + 2),
            schedules = listOf(schedule(ScheduleType.DAILY)),
            completions = listOf(completion(monday + 2)),
            from = monday,
            to = monday + 2,
        )

        assertEquals(1, result.scheduled)
        assertEquals(1, result.completed)
    }

    @Test
    fun `a weekly schedule counts one occurrence per week`() {
        val result = CompletionRateCalculator.forHabit(
            habit = habit(),
            schedules = listOf(schedule(ScheduleType.TIMES_PER_WEEK, timesPerWeek = 2)),
            completions = listOf(completion(monday), completion(monday + 1)),
            from = monday,
            to = monday + 6,
        )

        assertEquals(1, result.scheduled)
        assertEquals(1, result.completed)
    }

    @Test
    fun `a weekly schedule that misses its target counts as not completed`() {
        val result = CompletionRateCalculator.forHabit(
            habit = habit(),
            schedules = listOf(schedule(ScheduleType.TIMES_PER_WEEK, timesPerWeek = 3)),
            completions = listOf(completion(monday), completion(monday + 1)),
            from = monday,
            to = monday + 6,
        )

        assertEquals(1, result.scheduled)
        assertEquals(0, result.completed)
    }

    @Test
    fun `an empty range has no rate instead of zero`() {
        val result = CompletionRateCalculator.forHabit(
            habit = habit(),
            schedules = listOf(schedule(ScheduleType.DAILY)),
            completions = emptyList(),
            from = monday,
            to = monday - 1,
        )

        assertEquals(0, result.scheduled)
        assertNull(result.rate)
    }
}
